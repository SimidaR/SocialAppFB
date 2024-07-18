package facebook.app.entities;

public class Friends {
    private final int userId;
    private int friendId;
    private final String friendNameID;

    public Friends(int userId, int friendId, String friendNameID) {
        this.userId = userId;
        this.friendId = friendId;
        this.friendNameID = friendNameID;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }
    public int getFriendId() {
        return friendId;
    }
    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }
    public String getFriendNameID() {
        return friendNameID;
    }
}
