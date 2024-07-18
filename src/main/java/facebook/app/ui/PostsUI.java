package facebook.app.ui;

import facebook.app.controller.PostsController;
import facebook.app.controller.UserController;
import facebook.app.entities.Posts;
import facebook.app.entities.User;
import facebook.app.exceptions.UserIOException;
import facebook.app.exceptions.UserNotFoundException;
import facebook.app.services.UserService;

import java.util.List;
import java.util.Scanner;

public class PostsUI {
    private final PostsController postsController = new PostsController();
    private final UserService userService = new UserService();
    private final Scanner keyboard = new Scanner(System.in);

    public PostsUI() throws UserIOException {
    }

    public void startPostsSection() throws UserIOException {
        boolean keepRunning = true;
        while (keepRunning) {
            System.out.println("\n             --- Posts Management ---");
            System.out.println("1. See all posts                   2. See latest post");
            System.out.println("3. See recent posts                4. Create new post");
            System.out.println("5. See posts from another user     0. Return to Main Menu");
            System.out.println("                Please choose an option: ");

            int choice = keyboard.nextInt();

            switch (choice) {
                case 1:
                    displayAllPosts();
                    break;
                case 2:
                    displayLatestPost();
                    break;
                case 3:
                    displayRecentPosts();
                    break;
                case 4:
                    createNewPost();
                    break;
                case 5:
                    displayPostsFromAnotherUser();
                    break;
                case 0:
                    keepRunning = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void displayAllPosts() {
        try {
            List<Posts> allPosts = postsController.getAllPosts();
            System.out.println("                 All posts so far: ");
            System.out.println("-------------------------------------------------------");
            allPosts.forEach(post -> {
                try {
                    System.out.println("User: " + userService.getUserByID((int) post.getUser()).getEmail().split("@")[0] + " | Date: " + post.getTimePosted() + "\nContent: " + post.getContent() + "\n");
                } catch (UserIOException e) {
                    throw new RuntimeException(e);
                }
            });
            System.out.println("-------------------------------------------------------");
        } catch (UserNotFoundException e) {
            System.out.println("Error: User not found.");
        } catch (UserIOException e) {
            System.out.println("Error: Unable to retrieve posts.");
        }
    }

    private void displayLatestPost() {
        try {
            Posts latestPost = postsController.getLatestPost();
            System.out.println(latestPost.getContent());
        } catch (UserIOException e) {
            System.out.println("Error: Unable to retrieve the latest post.");
        }
    }

    private void displayRecentPosts() {
        System.out.print("Enter the number of recent posts you want to see: ");
        int limit = keyboard.nextInt();
        try {
            List<Posts> recentPosts = postsController.getRecentPosts(limit);
            recentPosts.forEach(post -> System.out.println(post.getContent()));
        } catch (UserIOException e) {
            System.out.println("Error: Unable to retrieve recent posts.");
        }
    }

    private void createNewPost() throws UserIOException {
        keyboard.nextLine();
        System.out.print("Enter the content of the post: ");
        String content = keyboard.nextLine();
        postsController.createPost(content);
        System.out.println("Post created successfully");
    }

    private void displayPostsFromAnotherUser() throws UserIOException {
        System.out.println("Choose user by userID");
        UserController userController = new UserController();
        List<User> users = userController.getAllUsers();
        int fromUserId = (int) userService.getCurrentUserId();
        for (User user : users) {
            if (user.getUserId() != fromUserId) {
                System.out.println("User ID: " + user.getUserId() + ", Name: " + user.getEmail().split("@")[0]);
            }
        }
        keyboard.nextLine();
        System.out.print("Enter the user ID to retrieve posts: ");
        int userId = keyboard.nextInt();
        keyboard.nextLine();
        try {
            List<Posts> userPosts = postsController.getPostsFromAnotherUser(userId);
            System.out.println("Posts from user with ID " + userId + ":");
            if (userPosts.isEmpty()) {
                System.out.println("User has not posted yet");
            } else {
                userPosts.forEach(post -> System.out.println(post.getContent()));
            }

        } catch (UserIOException e) {
            System.out.println("Error: Unable to retrieve posts for the specified user.");
        }
    }

}
