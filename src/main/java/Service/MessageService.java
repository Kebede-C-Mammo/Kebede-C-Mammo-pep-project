package Service;

import Model.Message;

import java.util.ArrayList;

public interface MessageService {

    // Create method to add message
    Message addMessage(Message message);

    // Create method to get all messages
    ArrayList<Message> getAllMessages();

    // Creat method to get message by id
    Message getMessageById(int id);

    // Create method to get all messages by user
    ArrayList<Message> getAllMessagesByUser(int id);

    // Create method to update message by id
    Message updateMessageById(int id, String messageText);

    // Create method to delete message by id
    Message deleteMessagesById(int id);

    
}
