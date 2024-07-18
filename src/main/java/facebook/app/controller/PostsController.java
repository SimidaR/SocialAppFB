package facebook.app.controller;

import facebook.app.entities.Posts;
import facebook.app.entities.User;
import facebook.app.exceptions.UserIOException;
import facebook.app.exceptions.UserNotFoundException;
import facebook.app.services.PostsService;
import facebook.app.services.UserService;

import java.util.List;

public class PostsController {
    private PostsService postsService = new PostsService();
    UserService userservice = new UserService();
    private final long userId = userservice.getCurrentUserId();
    User user = userservice.getUserByID((int) userId);

    public PostsController(PostsService postsService, User user) throws UserIOException {
        this.postsService =  postsService;
        this.user = user;
    }
    public PostsController() throws UserIOException {
    }

    public List<Posts> getAllPosts() throws UserNotFoundException, UserIOException {
        return postsService.getAllPosts();
    }

    public List<Posts> getRecentPosts(int limit) throws UserIOException {
        return postsService.getRecentPosts(limit);
    }

    public Posts getLatestPost() throws UserIOException {
        return postsService.getLatestPost();
    }

    public void createPost(String content) throws UserIOException {
        postsService.createPost(content);
    }

    public List<Posts> getPostsFromAnotherUser(int userId) throws UserIOException {
        return postsService.getPostsFromAnotherUser(userId);
    }
}
