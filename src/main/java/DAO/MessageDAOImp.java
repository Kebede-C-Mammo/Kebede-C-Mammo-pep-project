package DAO;

import Model.Message;

import java.util.ArrayList;
import java.sql.*;

import Util.ConnectionUtil;

public class MessageDAOImp implements MessageDAO {

    @Override
    public Message addMessage(Message message) {

        // Open connection to database
        Connection con = ConnectionUtil.getConnection();

        try{
            // Cretae prepared statment
            String sql = "INSERT INTO message VALUES(DEFAULT, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());

            // Execute update statment
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            // Obtain generated message object
            if(rs.next()) {
                int generated_message_id = (int) rs.getLong(1);
                Message newMessage = new Message(
                    generated_message_id, 
                    message.getPosted_by(), 
                    message.getMessage_text(), 
                    message.getTime_posted_epoch());

                return newMessage;
            }
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    @Override
    public ArrayList<Message> getAllMessages() {

        // Open connection to database
        Connection con = ConnectionUtil.getConnection();
        ArrayList<Message> messages = new ArrayList<>();

        try{
            // Cretae prepared statment
            String sql = "SELECT * FROM message";
            PreparedStatement ps = con.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();

            // Process result set
            while(rs.next()) {
                Message message = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("Posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch"));
                
                messages.add(message);
            }
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }


    @Override
    public Message getMessageById(int id) {

        // Open connection to database
        Connection con = ConnectionUtil.getConnection();

        try{
            // Cretae prepared statment
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            // Process the results
            while(rs.next()) {
                Message message = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch"));

                return message;
            }
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    @Override
    public Message updateMessageById(int id, String messageText) {

        // Open connection to database
        Connection con = ConnectionUtil.getConnection();

        try{
            // Cretae prepared statment
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, messageText);
            ps.setInt(2, id);
            ps.executeUpdate();

        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    @Override
    public Message deleteMessagesById(int id) {

        // Open connection to database
        Connection con = ConnectionUtil.getConnection();

        try{
            // Cretae prepared statment
            String sql = "DELETE FROM message WHERE message_id = ?";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, id);

            ps.executeUpdate();

            // Process the results
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) {
                return new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch"));
            }
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
}
