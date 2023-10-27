package DAO;

import Util.ConnectionUtil;
import Model.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AccountDAO {

    Connection connection = ConnectionUtil.getConnection();

    public List<Account> getAllAccounts() {

        List<Account> accounts = new ArrayList<>();

        try {
            String sql = "SELECT * FROM account";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                Account account = new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password"));
                accounts.add(account);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return accounts;
    }
    
    public Account registerAccount(Account account) {

        try {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();

            if (pkeyResultSet.next()) {
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
    }

    public Account loginAccount(Account account) {

        try {
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) {
                return new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password"));                
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
    
}
