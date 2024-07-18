package facebook.app.ui;
import facebook.app.controller.MessageController;
import facebook.app.controller.UserController;
import facebook.app.entities.Message;
import facebook.app.entities.User;
import facebook.app.exceptions.MessageValidationException;
import facebook.app.exceptions.UserIOException;
import facebook.app.exceptions.UserNotFoundException;
import facebook.app.services.UserService;

import java.text.SimpleDateFormat;
import java.util.*;

public class MessageUI {
    private final MessageController messageController = new MessageController();
    private final UserController userController = new UserController();
    private final UserService userService = new UserService();
    private final Scanner scanner = new Scanner(System.in);

    public MessageUI(){}

    public void startMessagesSection() throws MessageValidationException, UserNotFoundException, UserIOException {
        int choice;
        do {
            System.out.println("\n     --- Message management ---");
            System.out.println("1. View Messages      2. Send a Message");
            System.out.println("         0. Back to Main Menu");
            System.out.println("       Please choose an option: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewMessages();
                    break;
                case 2:
                    sendMessage();
                    break;
                case 0:
                    System.out.println("Returning to Main Menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    private void viewMessages() throws UserNotFoundException, UserIOException {
        int currentUserId = (int) userService.getCurrentUserId();
        List<Message> messages = messageController.getFilteredMessagesForUser(currentUserId);
        if (messages.isEmpty()) {
            System.out.println("No messages to display.");
        } else {
            System.out.println("Messages:");
            for (Message message : messages) {
                String formattedDate = "Date: " + message.getDate();
                List<String> splitMessage = splitIntoLines(message.getMessage());
                if (message.getFrom_user_id() == (int) userService.getCurrentUserId()){
                    System.out.println("#                                                                           #");
                    // Message from the current user - align right
                    System.out.printf("%74s\n", "(Me -> "+ userController.getUserByID(message.getTo_user_id()).getEmail().split("@")[0] + ") " + formattedDate);
                    for (String line : splitMessage) {

                        System.out.printf("%74s\n", line);
                    }
                    System.out.println("#                                                                           #");
                } else {
                    System.out.println("#                                                                           #");
                    // Message from other users - align left
                    System.out.printf("   %-20s\n", formattedDate + " (" + userController.getUserByID(message.getFrom_user_id()).getEmail().split("@")[0] + ")");
                    for (String line : splitMessage) {

                        System.out.printf("   %-20s\n", line);
                    }
                    System.out.println("#                                                                           #");
                }
            }
        }
    }

    private List<String> splitIntoLines(String text) {
        List<String> lines = new ArrayList<>();
        while (text.length() > 22) {
            int breakPoint = text.lastIndexOf(' ', 22);
            if (breakPoint == -1) breakPoint = 22;
            lines.add(text.substring(0, breakPoint));
            text = text.substring(breakPoint).trim();
        }
        lines.add(text);
        return lines;
    }

    private void sendMessage() throws MessageValidationException, UserIOException {
        UserController userController = new UserController();
        int fromUserId = (int) userService.getCurrentUserId();

        // Display a list of users for the recipient selection
        List<User> users = userController.getAllUsers();
        System.out.println("Select a recipient you wanna send a message by User ID:");

        for (User user : users) {
            if (user.getUserId() != fromUserId){
                System.out.println("User ID: " + user.getUserId() + ", Name: " + user.getEmail().split("@")[0]);
            }
        }

        int recipientChoice = scanner.nextInt();
        scanner.nextLine();

        boolean recipientFound = false;
        int toUserId = -1;

        for (User user : users) {
            if (user.getUserId() == recipientChoice) {
                toUserId = (int) user.getUserId();
                recipientFound = true;
                break;
            }
        }

        if (!recipientFound) {
            System.out.println("Invalid choice. Message not sent.");
            return;
        }

        // Automatically set the current date
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy HH:mm", Locale.ENGLISH);
        String date = dateFormat.format(currentDate);

        System.out.println("Enter your message:");
        String messageText = scanner.nextLine();

        // Call the controller to send the message
        messageController.sendMessage(fromUserId, toUserId, date, messageText);
        System.out.println("Message sent successfully!");
    }
}
