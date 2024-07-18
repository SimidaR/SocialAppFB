package facebook.app.controller;
import facebook.app.entities.User;
import facebook.app.exceptions.InvalidEmailFormatException;
import facebook.app.exceptions.UserIOException;
import facebook.app.exceptions.UserNotFoundException;
import facebook.app.services.UserService;
import java.util.List;

public class UserController {
    private final UserService userService = new UserService();

    public void addUser(User user) throws InvalidEmailFormatException, UserIOException {
        if (isValidEmailFormat(user.getEmail())) {
            throw new InvalidEmailFormatException("Invalid email format for: " + user.getEmail());
        }
        // Ensure password meets minimum length requirement
        if (user.getPassword().length() <= 3) {
            System.out.println("Password must be at least 3 characters long. Please try again!");
            return;
        }
        userService.addUser(user);
    }
    public boolean getCurrentUserStatus() throws UserIOException {
        User userStatus = userService.getCurrentUser();
        return userStatus.isLoggedIn();
    }
    public User getUserByEmail(String email) throws UserIOException {
        return userService.getUserByEmail(email);
    }
    public User getUserByID(int id) throws UserNotFoundException, UserIOException {
        return userService.getUserByID(id);
    }
    public List<User> getAllUsers() throws UserIOException {
        return userService.getAllUsers();
    }
    public boolean login(String email, String password) throws UserIOException {
        // Check if the email contains an '@' symbol
        if (!email.contains("@")) {
            throw new UserIOException("Invalid email. Email should contain '@'.");
        }
        // Check if the password is at least 3 characters long
        if (password.length() < 3) {
            throw new UserIOException("Invalid password. Password should be at least 3 characters long.");
        }
        if (userService.login(email, password)) {
            System.out.println(" ### Login successfully! Welcome, " + email.split("@")[0].toUpperCase() + "! ###");
            return true;
        } else {
            System.out.println("Login failed. Incorrect email or password.");
            return false;
        }
    }
    public boolean isValidEmailFormat(String email) {
        return email == null || !email.contains("@");
    }
    public void logoutAllUsers() throws UserIOException {
        userService.logoutAllUsers();
    }
}
