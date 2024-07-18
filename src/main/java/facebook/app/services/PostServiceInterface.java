package facebook.app.services;

import facebook.app.entities.Posts;
import facebook.app.entities.User;
import java.util.List;

public interface PostServiceInterface {
    Posts getLatestPost(User user);
    List<Posts> getAllPosts(User user);
    List<Posts> getRecentPosts(User user, int posts);
    void createPost(Posts appPost);
}
