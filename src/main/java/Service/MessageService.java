package Service;

import Model.Message;
import Service.exceptions.BlankException;

import java.util.ArrayList;
import java.util.List;

import DAO.AccountDAO;
import DAO.MessageDAO;

public class MessageService {
    
    private MessageDAO messageDAO;
    private AccountDAO accountDAO = new AccountDAO();

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public Message createMessage(Message message) {

        List<Integer> accountsIds = new ArrayList<>();

        accountDAO.getAllAccounts().forEach(acc -> accountsIds.add(acc.getAccount_id()));

            if (message.getMessage_text().isBlank()) {
                throw new BlankException("Blank message");
            }
            if (message.getMessage_text().length() > 254) {
                throw new ArithmeticException("Message must be no more than 254 characters");
            }
            Integer messagePostedBy = Integer.valueOf(message.getPosted_by());
            if (!accountsIds.contains(messagePostedBy)) {
                throw new BlankException("Message is not from a registered user");
            }

            return messageDAO.createMessage(message);
    }

    public List<Message> retrieveAllMessages() {

        return messageDAO.retrieveAllMessages();
    }

    public Message retrieveMessageById(int messageId) {

        return messageDAO.retrieveMessageById(messageId);
    }

    public Message deleteMessageById(int messageId) {

        Message messageToBeDeleted = retrieveMessageById(messageId);

        messageDAO.deleteMessageById(messageId);

        return messageToBeDeleted;
    }

    public int updateMessageById(int messageId, Message newMessage) {

            if (!retrieveAllMessages().contains(retrieveMessageById(messageId))) {
                throw new BlankException("User is not registered to an account");
            }
            if (newMessage.getMessage_text().isBlank()) {
                throw new BlankException("Enter a valid new message");
            }
            if (newMessage.getMessage_text().length() > 255) {
                throw new ArithmeticException("Message exceeds 255 characters");
            }

            return messageDAO.updateMessageById(messageId, newMessage);
    }

    public List<Message> retrieveAllMessagesByUser(int accountId) {

        return messageDAO.retrieveAllMessagesByUser(accountId);
    }
    
}
