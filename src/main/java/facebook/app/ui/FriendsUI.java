package facebook.app.ui;
import facebook.app.controller.FriendsController;
import facebook.app.controller.UserController;
import facebook.app.entities.Friends;
import facebook.app.entities.User;
import facebook.app.exceptions.UserIOException;
import facebook.app.exceptions.UserNotFoundException;
import facebook.app.services.UserService;
import java.util.List;
import java.util.Scanner;

public class FriendsUI {
    private final FriendsController friendsController = new FriendsController();
    private final UserController userController = new UserController();
    private final UserService userService = new UserService();
    private final Scanner scanner = new Scanner(System.in);
    public FriendsUI() {
    }

    public void startFriendsSection() throws UserNotFoundException, UserIOException {
        int choice;
        do {
            System.out.println("\n      --- Friends Management ---");
            System.out.println("1. View Friends       2. Add a Friend");
            System.out.println("3. Remove a Friend    0. Back to Main Menu");
            System.out.println("        Please choose an option: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewFriends();
                    break;
                case 2:
                    addFriend();
                    break;
                case 3:
                    removeFriend();
                    break;
                case 0:
                    System.out.println("Returning to Main Menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    private void viewFriends() throws UserIOException {
        int userId = (int) userService.getCurrentUserId();
        List<Friends> friendsList = friendsController.getFriendsOfUser(userId);

        if (friendsList.isEmpty()) {
            System.out.println("No friends to display.");
        } else {
            System.out.println("Friends List:");
            for (Friends friend : friendsList) {
                System.out.println("Friend ID: " + friend.getFriendId() + " | Name: " + friend.getFriendNameID());
            }
            System.out.println("------------------------------");
        }
    }

    private void addFriend() throws UserNotFoundException, UserIOException {
        int userId = (int) userService.getCurrentUserId();
        List<Friends> friendsList = friendsController.getFriendsOfUser(userId);

        // Fetch all users
        List<User> users = userController.getAllUsers();

        boolean availableUsersToAdd = false;

        // Display users who are not already friends
        System.out.println("Enter the User ID of the user you want to add as a friend:");
        for (User user : users) {
            if (user.getUserId() == userId) {
                continue; // Skip current logged-in user
            }

            boolean isAlreadyFriend = friendsList.stream()
                    .anyMatch(friend -> friend.getFriendId() == user.getUserId() || friend.getUserId() == user.getUserId());

            if (!isAlreadyFriend) {
                System.out.println("User ID: " + user.getUserId() + ", Name: " + user.getEmail().split("@")[0]);
                availableUsersToAdd = true;
            }
        }

        if (!availableUsersToAdd) {
            System.out.println("There are no new users to add as friends.");
            return; // Return to the previous menu
        }

        int friendId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        friendsController.addFriend(userId, friendId);
        System.out.println(userController.getUserByID(friendId).getName().toUpperCase() + " successfully added to your friends list!");
    }

    private void removeFriend() throws UserNotFoundException, UserIOException {
        viewFriends();
        System.out.println("Enter the User ID of the friend you want to remove:");

        int userId = (int) userService.getCurrentUserId();
        int friendId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        friendsController.removeFriend(userId, friendId);
        System.out.println(userController.getUserByID(friendId).getName().toUpperCase() + " successfully removed from your friends list!");
    }
}
