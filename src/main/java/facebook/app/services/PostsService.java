package facebook.app.services;

import facebook.app.dao.UserDAO;
import facebook.app.dao.PostsDAO;
import facebook.app.entities.Posts;
import facebook.app.entities.User;
import facebook.app.exceptions.UserIOException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

public class PostsService {
    private PostsDAO userPostsDAO = new PostsDAO();
    private UserDAO userDAO = new UserDAO();
    private User user = new User();
    private final UserService userService = new UserService();
    public PostsService(PostsDAO userPostsDAO, UserDAO userDAO) {
        this.userPostsDAO = userPostsDAO;
        this.userDAO = userDAO;
    }
    public PostsService() {}

    public List<Posts> getAllPosts() throws UserIOException {
        int currentUserId = (int) userService.getCurrentUserId();
        User currentUser = userService.getUserByID(currentUserId);
        return userPostsDAO.getAllPosts(currentUser);
    }

    public List<Posts> getRecentPosts(int limit) throws UserIOException {
        user = userService.getUserByID((int) userService.getCurrentUserId());
        if (user != null) {
            user = userDAO.getUserByID((int) user.getUserId());
            if (user != null) {
                return userPostsDAO.getRecentPosts(user, limit);
            } else {
                System.out.println("User not found.");
            }
        } else {
            System.out.println("User is null.");
        }
        return Collections.emptyList();
    }
    public Posts getLatestPost() throws UserIOException {
        user = userService.getUserByID((int) userService.getCurrentUserId());
        return userPostsDAO.getLatestPost(user);
    }
    public void createPost(String content) throws UserIOException {
        if (content == null) {
            System.out.println("Invalid input for creating a post. User and content are required.");
            return;
        }
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String formattedDateTime = now.format(formatter);
        Posts post = new Posts((int) userService.getCurrentUserId(), formattedDateTime, content);
        // Call DAO to write the post to the database
        userPostsDAO.createPost(post);
    }

    public List<Posts> getPostsFromAnotherUser(int userId) {
        // Delegate to DAO to retrieve posts
        return userPostsDAO.getPostsFromAnotherUser(userId);
    }
}
