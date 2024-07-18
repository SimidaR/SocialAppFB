package facebook.app.exceptions;

public class MessageValidationException extends Exception {
    private final String detail;
    public MessageValidationException(String message) {
        super(message);
        this.detail = "";
    }

    public MessageValidationException(String message, String detail) {
        super(message);
        this.detail = detail;
    }
}

