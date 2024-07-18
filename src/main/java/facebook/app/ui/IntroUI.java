package facebook.app.ui;
import facebook.app.controller.UserController;
import facebook.app.exceptions.InvalidEmailFormatException;
import facebook.app.exceptions.MessageValidationException;
import facebook.app.exceptions.UserIOException;
import facebook.app.exceptions.UserNotFoundException;
import java.util.Scanner;

public class IntroUI {
    public void startUI() throws MessageValidationException, UserNotFoundException, InvalidEmailFormatException, UserIOException {
        UserController userController = new UserController();
        MessageUI messageUI = new MessageUI();
        FriendsUI friendsUI = new FriendsUI();
        PostsUI postsUI = new PostsUI();
        ProfileUI profileUI = new ProfileUI();
        GroupsUI groupsUI = new GroupsUI();
        RegisterUI registerUI = new RegisterUI();
        LoginUI loginUI = new LoginUI();

        Scanner scanner = new Scanner(System.in);
        int choice;
        boolean loggedInUser = false;

        do{
            System.out.println("  ----- Welcome to the Facebook App -----");
            if (loggedInUser) {
                // Display options for a logged-in user
                System.out.println("Please choose one of the following options: ");
                System.out.println("      1. Messages           2. Friends");
                System.out.println("      3. Posts              4. Groups");
                System.out.println("      5. Profile            0. Logout ");
            } else {
                // Display options for a user not logged in
                System.out.println("Please choose one of the following options: ");
                System.out.println("    1. Register           2. Login");
                System.out.println("                 0. Exit");
            }
            choice = scanner.nextInt();
            scanner.nextLine();

            if (loggedInUser) {
                // When User is logged in
                switch (choice) {
                    case 1:
                        messageUI.startMessagesSection();
                        break;
                    case 2:
                        friendsUI.startFriendsSection();
                        break;
                    case 3:
                        postsUI.startPostsSection();
                        break;
                    case 4:
                        groupsUI.startGroupsSection();
                        break;
                    case 5:
                        profileUI.startProfileSection();
                        break;
                    case 0:
                        userController.logoutAllUsers();
                        System.out.println("Exiting Facebook App. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } else {
                // When User is not logged in
                switch (choice) {
                    case 1:
                        // Register new user
                        registerUI.startRegistration();
                        break;
                    case 2:
                        // Login
                        if (loginUI.startLogin()) {
                            loggedInUser = userController.getCurrentUserStatus();
                        }
                        break;
                    case 0:
                        System.out.println("Exiting Facebook App. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } while(choice != 0);
        scanner.close();
    }
}
