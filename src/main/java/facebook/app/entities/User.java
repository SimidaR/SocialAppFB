package facebook.app.entities;

public class User {
    private int user_id;
    private String email;
    private String password;
    private boolean isLoggedIn;
    public User(int user_id, String email, String password) {
        this.user_id = user_id;
        this.email = email;
        this.password = password;
    }
    public User(){}

    public boolean isLoggedIn() {
        return isLoggedIn;
    }
    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }
    public int getUserId() {
        return user_id;
    }
    public void setUserId(long user_id) {
        this.user_id = (int) user_id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public String getName () {
            return email.split("@")[0];
        }
}
