package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;


public class MessageDAO {

    Connection connection = ConnectionUtil.getConnection();

    public Message createMessage(Message message) {

        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();

            if (pkeyResultSet.next()) {
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public List<Message> retrieveAllMessages() {

        List<Message> messages = new ArrayList<>();

        try {
            String sql = "SELECT * FROM message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
    
            while(rs.next()) {
                Message message = new Message(
                    rs.getInt("message_id"), 
                    rs.getInt("posted_by"), 
                    rs.getString("message_text"), 
                    rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return messages;
    }

    public Message retrieveMessageById(int messageId) {

        try {
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
        
            preparedStatement.setInt(1, messageId);
            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next()) {
                Message message = new Message(
                    rs.getInt("message_id"), 
                    rs.getInt("posted_by"), 
                    rs.getString("message_text"), 
                    rs.getLong("time_posted_epoch"));
                return message;          
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return new Message();
    }

    public void deleteMessageById(int messageId) {

        try {
            String sql = "DELETE FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setInt(1, messageId);
            
            preparedStatement.executeUpdate();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }    
    }

    public void updateMessageById(int messageId, Message newMessage) {
        
        try {
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setString(1, newMessage.getMessage_text());
            preparedStatement.setInt(2, messageId);
            
            preparedStatement.executeUpdate();         
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Message> retrieveAllMessagesByUser(int accountId) {

        List<Message> messages = new ArrayList<>();

        try {
            String sql = "SELECT * FROM message WHERE posted_by = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, accountId);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                Message message = new Message(
                    rs.getInt("message_id"), 
                    rs.getInt("posted_by"), 
                    rs.getString("message_text"), 
                    rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return messages;
    }    
}
