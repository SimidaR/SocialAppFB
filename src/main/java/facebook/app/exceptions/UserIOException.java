package facebook.app.exceptions;

public class UserIOException extends Exception {
    public UserIOException(String message) {
        super(message);
    }
    public UserIOException(String message, Throwable cause) {
        super(message, cause);
    }
}

