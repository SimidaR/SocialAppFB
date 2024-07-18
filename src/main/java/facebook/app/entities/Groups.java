package facebook.app.entities;

public class Groups {
    private final int groupId;
    private String userId;
    private String groupName;
    private String groupDescription;

    public Groups(int groupId, String userId, String groupName, String groupDescription) {
        this.groupId = groupId;
        this.userId = String.valueOf(userId);
        this.groupName = groupName;
        this.groupDescription = groupDescription;
    }

    //Getters and Setters
    public int getGroupId() {
        return groupId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = String.valueOf(userId);
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }
}
