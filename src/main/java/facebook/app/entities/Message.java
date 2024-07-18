package facebook.app.entities;

public class Message {
    private int from_user_id;
    private int to_user_id;
    private String date;
    private String message;

    public Message(int from_user_id, int to_user_id, String date, String message) {
        this.from_user_id = from_user_id;
        this.to_user_id = to_user_id;
        this.date = date;
        this.message = message;
    }

    public int getFrom_user_id() {
        return from_user_id;
    }
    public void setFrom_user_id(int from_use_id) {
        this.from_user_id = from_use_id;
    }
    public int getTo_user_id() {
        return to_user_id;
    }
    public void setTo_user_id(int to_user_id) {
        this.to_user_id = to_user_id;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
