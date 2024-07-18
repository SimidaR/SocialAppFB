package facebook.app.services;
import facebook.app.dao.FriendsDAO;
import facebook.app.entities.Friends;
import java.util.List;

public class FriendsService {
    private final FriendsDAO friendsDAO = new FriendsDAO();
    public List<Friends> getAllFriends() {
        return friendsDAO.getAllFriends();
    }
    public void addFriend(Friends friend) {
        friendsDAO.addFriend(friend);
    }
    public void removeFriend(int userId, int friendId) {
        friendsDAO.removeFriend(userId, friendId);
    }
}
