package facebook.app.controller;

import facebook.app.entities.Message;
import facebook.app.exceptions.MessageValidationException;
import facebook.app.exceptions.UserIOException;
import facebook.app.exceptions.UserNotFoundException;
import facebook.app.services.MessageService;
import facebook.app.dao.MessageDAO;
import facebook.app.ui.MessageUI;

import java.util.List;

public class MessageController {
    private final MessageService messageService = new MessageService();
    private final MessageDAO messageDAO = new MessageDAO();
    private MessageUI messageUI;

    public MessageController() {
    }

    public List<Message> getFilteredMessagesForUser(int userId) {

        return messageService.getFilteredMessagesForUser(userId);
    }

    public void setMessageUI(MessageUI messageUI) {
        this.messageUI = messageUI;
    }

    public void sendMessage(int fromUserId, int toUserId, String date, String messageText) throws MessageValidationException {
        // Create a Message object
        Message message = new Message(fromUserId, toUserId, date, messageText);

        // Perform business logic using the service
        messageService.processMessage(message);

        // Persist the message using the DAO
        messageDAO.saveMessage(message);
    }

    public void startMessaging() throws MessageValidationException, UserNotFoundException, UserIOException {
        messageUI.startMessagesSection();
    }

}
