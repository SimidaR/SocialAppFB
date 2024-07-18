package facebook.app.ui;

import facebook.app.entities.Profile;
import facebook.app.exceptions.InvalidEmailFormatException;
import facebook.app.exceptions.UserIOException;
import facebook.app.services.ProfileService;
import facebook.app.services.UserService;

import java.util.Scanner;

public class ProfileUI {
    private final ProfileService profileService = new ProfileService();
    private Profile profile = new Profile();
    private final UserService userService = new UserService();
    private final Scanner scanner = new Scanner(System.in);
    int choice;
    int userId;
    {   try {
        userId = (int) userService.getCurrentUserId();
    } catch (UserIOException e) {
        throw new RuntimeException(e);
    }
    }

    public ProfileUI() {
    }

    public void startProfileSection() throws InvalidEmailFormatException {
        do {
            profile.setId(userId);

            System.out.println("\n         --- Welcome to Profile --- ");
            System.out.println("Please choose one of the following options: ");
            System.out.println("     1. Create Profile          2. Edit Profile");
            System.out.println("     3. Display Profile         0. Back");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createProfile(profile);
                    break;
                case 2:
                    editProfile(profile);
                    break;
                case 3:
                    displayProfile(profile);
                    break;
                case 0:
                    System.out.println("Returning to Main Menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    public void createProfile(Profile profile) throws InvalidEmailFormatException {
        System.out.println("\n         --- Create New Profile --- ");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your email address: ");
        String email = scanner.nextLine();
        System.out.print("Enter your age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter your location: ");
        String location = scanner.nextLine();

        int id = profileService.generateUniqueProfileId();
        Profile userProfile = new Profile(id, name, email, age, location);

        System.out.println("\nProfile Created Successfully!");
        System.out.println("Profile Details:");
        System.out.println("ProfileID: " + userProfile.getId() +
                "\nName: " + name +
                "\nEmail: " + email +
                "\nAge: " + age +
                "\nLocation: " + location);
        profileService.createProfile(id, name, email, age, location);
        profileService.getCurrentProfileId(id);
        startProfileSection();
    }

    public void editProfile(Profile profile) throws InvalidEmailFormatException {
        System.out.println("\n         --- Edit Your Profile --- ");
        this.profile = profileService.displayCurrentProfile(userId, this.profile);

        System.out.println("Your profile ID is: " + this.profile.getId());
        System.out.println("Your profile Email is: " + this.profile.getEmail());
        System.out.print("Enter your name: ");
        this.profile.setName(scanner.nextLine());
        System.out.print("Enter your age: ");
        this.profile.setAge(scanner.nextInt());
        scanner.nextLine();
        System.out.print("Enter your location: ");
        this.profile.setLocation(scanner.nextLine());

        System.out.println("\nProfile Edited Successfully!");
        System.out.println("Profile Details:");
        System.out.println("ProfileID: " + this.profile.getId() +
                "\nName: " + this.profile.getName() +
                "\nEmail: " + this.profile.getEmail() +
                "\nAge: " + this.profile.getAge() +
                "\nLocation: " + this.profile.getLocation());
        profileService.editProfile(this.profile.getId(), this.profile.getName(), this.profile.getEmail(), this.profile.getAge(), this.profile.getLocation());

        startProfileSection();
    }

    public void displayProfile(Profile profile) throws InvalidEmailFormatException {
        System.out.println("Do you want to see your login profile or the created profile");
        System.out.println("      1. Current Profile           2. New Profile ");
        System.out.println("      0. Back ");
        do {
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    currentProfile(profile);
                    break;
                case 2:
                    newProfile(profile);
                    break;
                case 0:
                    System.out.println("Returning to Main Menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
        startProfileSection();
    }

    private void newProfile(Profile profile) throws InvalidEmailFormatException {
        this.profile = profileService.getLastProfileId();
        System.out.println("\n         --- New Profile --- ");
        System.out.println("ID: " + this.profile.getId());
        System.out.println("Name: " + this.profile.getName());
        System.out.println("Email: " + this.profile.getEmail());
        System.out.println("Age: " + this.profile.getAge());
        System.out.println("Location: " + this.profile.getLocation());
        startProfileSection();
    }

    private void currentProfile(Profile profile)  {
        this.profile = profileService.displayCurrentProfile(userId, this.profile);

            System.out.println("\n===== Your Profile =====");
            System.out.println("ID: " + this.profile.getId());
            System.out.println("Name: " + this.profile.getName());
            System.out.println("Email: " + this.profile.getEmail());
            System.out.println("Age: " + this.profile.getAge());
            System.out.println("Location: " + this.profile.getLocation());

    }
}
