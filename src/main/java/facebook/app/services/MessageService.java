package facebook.app.services;
import facebook.app.dao.MessageDAO;
import facebook.app.entities.Message;
import java.util.List;
import java.util.stream.Collectors;
import facebook.app.exceptions.MessageValidationException;

public class MessageService {
    private final MessageDAO messageDAO = new MessageDAO();
    public MessageService() {
    }

    public void processMessage(Message message) throws MessageValidationException {
        validateSenderAndRecipient(message);
        validateMessageLength(message);
    }
    public List<Message> getFilteredMessagesForUser(int currentUserId) {
        List<Message> messages = messageDAO.readMessages();
        return messages.stream()
                .filter(message -> message.getFrom_user_id() == currentUserId || message.getTo_user_id() == currentUserId)
                .collect(Collectors.toList());
    }

    private void validateSenderAndRecipient(Message message) {
        int senderId = message.getFrom_user_id();
        int recipientId = message.getTo_user_id();

        if (senderId <= 0 || recipientId <= 0) {
            throw new IllegalArgumentException("Invalid sender or recipient ID");
        }
    }

    private void validateMessageLength(Message message) throws MessageValidationException {
        if (message.getMessage().length() > 250) {
            throw new MessageValidationException("Message length exceeds the maximum allowed limit",
                    "Allowed maximum length: 250 characters. Provided length: " + message.getMessage().length() + " characters.");
        }
    }
}
