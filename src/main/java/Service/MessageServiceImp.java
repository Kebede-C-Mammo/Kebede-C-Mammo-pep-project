package Service;

import Model.Account;
import Model.Message;

import DAO.AccountDAO;
import DAO.MessageDAO;

import DAO.AccountDAOImp;
import DAO.MessageDAOImp;

import java.util.ArrayList;
import java.util.Objects;

public class MessageServiceImp implements MessageService {

    // Create members
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;

    public MessageServiceImp() {
        this.messageDAO = new MessageDAOImp();
        this.accountDAO = new AccountDAOImp();
    }

    // Implement add message method
    @Override
    public Message addMessage(Message message) {
        int account_id = message.getPosted_by();
        Account account = accountDAO.getAccountById(account_id);
        if(
            message.getMessage_text().isBlank() ||
            message.getMessage_text().length() >=255 ||
            Objects.isNull(account)) {
            return null;
        }else {
            Message newMessage = messageDAO.addMessage(message);
            return newMessage;
        }
    }


    // Implement get all messages method
    @Override
    public ArrayList<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }


    // Implement get message by id method
    @Override
    public Message getMessageById(int id) {
        return messageDAO.getMessageById(id);
    }

    // Implement get all messages by user method
    @Override
    public ArrayList<Message> getAllMessagesByUser(int id) {
        ArrayList<Message> allMessages = messageDAO.getAllMessages();
        ArrayList<Message> userMessages = new ArrayList<>();

        for(Message message: allMessages) {
            if(message.getPosted_by() == id) {
                userMessages.add(message);
            }
        }
        return userMessages;
    }

    // Implement update message by id method
    @Override
    public Message updateMessageById(int id, String messageText) {
        Message message = messageDAO.getMessageById(id);
        if(
            messageText.isBlank() ||
            Objects.isNull(message) ||
            messageText.length() >= 255) {
                return null;
        }else {
            messageDAO.updateMessageById(id, messageText);
        }
        return messageDAO.getMessageById(id);
    }

    // Implement delete message by id method
    @Override
    public Message deleteMessagesById(int id) {
        Message message = messageDAO.getMessageById(id);
        if(Objects.isNull(message)) {
            return null;
        }else {
        return messageDAO.deleteMessagesById(id);
        }
    }
    
}
