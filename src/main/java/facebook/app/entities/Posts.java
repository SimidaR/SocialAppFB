package facebook.app.entities;

public class Posts {
    private int id;
    private int userId;
    private final String timePosted;
    private final String content;
    public Posts(int userId, String timePosted, String content) {
        this.userId = userId;
        this.timePosted = timePosted;
        this.content = content;
    }

    public Long getId() {
        return (long) id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public long getUser() {
        return userId;
    }
    public void setUser(User user) {
        this.userId = (int) user.getUserId();
    }
    public String getContent() {
        return content;
    }
    public String getTimePosted() {
        return timePosted;
    }
}
