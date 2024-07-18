package facebook.app.controller;
import facebook.app.entities.Friends;
import facebook.app.entities.User;
import facebook.app.exceptions.UserIOException;
import facebook.app.exceptions.UserNotFoundException;
import facebook.app.services.FriendsService;
import java.util.List;
import java.util.stream.Collectors;

public class FriendsController {
    private final FriendsService friendService = new FriendsService();
    private final UserController userController = new UserController();

    public FriendsController() {
    }

    public List<Friends> getFriendsOfUser(int userId) {
        List<Friends> allFriends = friendService.getAllFriends();
        return allFriends.stream()
                .filter(friend -> friend.getUserId() == userId)
                .collect(Collectors.toList());
    }
    public void addFriend(int fromUserId, int toUserId) throws UserNotFoundException, UserIOException {
        User userToAdd = userController.getUserByID(toUserId);
        String friendNameID = userToAdd.getEmail().split("@")[0];
        Friends newFriend = new Friends(fromUserId, toUserId, friendNameID);
        friendService.addFriend(newFriend);
    }
    public void removeFriend(int userId, int friendId) {
        friendService.removeFriend(userId, friendId);
    }

}
