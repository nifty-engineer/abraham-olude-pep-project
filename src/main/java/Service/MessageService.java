package Service;

import Model.Message;
import Service.exceptions.BlankException;

import java.util.ArrayList;
import java.util.List;

import DAO.AccountDAO;
import DAO.MessageDAO;

public class MessageService {
    
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public Message createMessage(Message message) {

        Message created = new Message();


        List<Integer> accountsIds = new ArrayList<>();
        accountDAO.getAllAccounts().forEach(acc -> accountsIds.add(acc.getAccount_id()));

        try {
            if (message.getMessage_text().isBlank()) {
                throw new BlankException("Blank message");
            }
            if (message.getMessage_text().length() < 255) {
                throw new ArithmeticException("Message must be 255 characters or more");
            }
            Integer messagePostedBy = Integer.valueOf(message.getPosted_by());
            if (!accountsIds.contains(messagePostedBy)) {
                throw new BlankException("Message is not from a registered user");
            }

            created = messageDAO.createMessage(message);
        }
        catch (ArithmeticException | BlankException e) {
            System.out.println(e);
        }

        return created;
    }

    public List<Message> retrieveAllMessages() {

        return messageDAO.retrieveAllMessages();
    }

    public Message retrieveMessageById(int messageId) {

        return messageDAO.retrieveMessageById(messageId);
    }

    public Message deleteMessageById(int messageId) {

        return messageDAO.deleteMessageById(messageId);
    }

    public Message updateMessageById(int messageId, String newMessageText) {

        try {
            if (!retrieveAllMessages().contains(retrieveMessageById(messageId))) {
                throw new BlankException("User is not registered to an account");
            }
            if (newMessageText.isBlank()) {
                throw new BlankException("Enter a valid new message");
            }
            if (newMessageText.length() > 255) {
                throw new ArithmeticException("Message exceeds 255 characters");
            }

            return messageDAO.updateMessageById(messageId, newMessageText);
        }
        catch (ArithmeticException | BlankException e) {
            System.out.println(e);
        }

        return null;
    }

    public List<Message> retrieveAllMessagesByUser(int accountId) {

        return messageDAO.retrieveAllMessagesByUser(accountId);
    }
    
}
