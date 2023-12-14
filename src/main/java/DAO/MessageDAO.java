package DAO;

import Model.Message;

import java.util.ArrayList;

public interface MessageDAO {

    // Create method to add message
    Message addMessage(Message message);

    // Create method to get all messages
    ArrayList<Message> getAllMessages();

    // Create method to get message by id
    Message getMessageById(int id);

    // Create method to update message by id
    Message updateMessageById(int id, String messageText);

    // Creat method to delete message by id
    Message deleteMessagesById(int id);
      
}
