package facebook.app.services;
import facebook.app.dao.UserDAO;
import facebook.app.entities.User;
import facebook.app.exceptions.UserIOException;
import java.util.List;
import java.util.Optional;

public class UserService {
    private final UserDAO userDAO = new UserDAO();
    public boolean login(String email, String password) throws UserIOException {
        List<User> userList = userDAO.readUsers();
        Optional<User> userToLoginOptional = userList.stream()
                .filter(user -> user.getEmail().equals(email) && user.getPassword().equals(password))
                .findFirst();
        if (userToLoginOptional.isPresent()) {
            User userToLogin = userToLoginOptional.get();
            // Set the isLoggedIn field only for the user who is logging in
            userToLogin.setLoggedIn(true);
            // Save the changes to the file
            userDAO.writeUsers(userList);
            return true;
        } else {
            return false;
        }
    }
    public void addUser(User user) throws UserIOException {
        // Perform additional business logic/validation before adding to the DAO
        List<User> userList = userDAO.readUsers();
        if (!isUserUnique(userList, user.getEmail())) {
            System.out.println("User with email " + user.getEmail() + " already exists. Please try a different one!");
            return;
        }
        userList.add(user);
        userDAO.writeUsers(userList);
    }
    public User getUserByEmail(String email) throws UserIOException {
        // Check if the email is not empty
        if (email == null || email.trim().isEmpty()) {
            System.out.println("Email cannot be empty. Please provide a valid email.");
            return null;
        }
        // Perform additional business logic/validation before retrieving from the DAO
        return userDAO.getUserByEmail(email);
    }
    public User getCurrentUser() throws UserIOException {
        return getUserByID(getCurrentUserId());
    }
    public int getCurrentUserId() throws UserIOException {
        List<User> userList = userDAO.readUsers();
        for (User user : userList) {
            if (user.isLoggedIn()) {
                return user.getUserId();
            }
        }
        return -1; // Return -1 if no user is currently logged in
    }
    public User getUserByID(int userID) throws  UserIOException {
        return userDAO.getUserByID(userID);
    }
    public List<User> getAllUsers() throws UserIOException {
        // Perform additional business logic if needed before reading from the DAO
        return userDAO.readUsers();
    }
    public void logoutAllUsers() throws UserIOException {
        List<User> userList = userDAO.readUsers();
        userList.forEach(user -> user.setLoggedIn(false));
        userDAO.writeUsers(userList);
    }
    private boolean isUserUnique(List<User> userList, String email) {
        // Check if the user email is unique in the provided list
        return userList.stream().noneMatch(user -> user.getEmail().equals(email));
    }
}
