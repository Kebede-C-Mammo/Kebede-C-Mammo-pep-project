package DAO;

import Model.Account;

import java.util.ArrayList;
import java.sql.*;

import Util.ConnectionUtil;

public class AccountDAOImp implements AccountDAO {
    
    // Create user account
    @Override
    public Account addAccount(Account account) {

        // Open connection to database
        Connection con = ConnectionUtil.getConnection();

        try{
            // Create prepared statment 
            String sql = "INSERT INTO account VALUES(default, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            // Obtain result set & generated key
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            // Obtain generate account object
            if(rs.next()) {
                int generated_account_id = (int) rs.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }
        }catch (SQLException e){
                System.out.println(e.getMessage());
            }
            return null;
        }

    // Create user login
    @Override
    public Account getAccount(String username, String password) {

        // Open connection to database
        Connection con = ConnectionUtil.getConnection();

        try{
            // Create prepared statement
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            // Obtain result set
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("account_id");
                String usernameRslt = rs.getString("username");
                String passwordRslt = rs.getString("password");

                return new Account(id, usernameRslt, passwordRslt);
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    // Get all user accounts
    @Override
    public ArrayList<Account> getAllAccounts() {
        // Open connection to database
        Connection con = ConnectionUtil.getConnection();
        ArrayList<Account> accounts = new ArrayList<>();

        try{
            // Create prepared statement
            String sql = "SELECT * FROM account";
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            // Process result set
            while(rs.next()) {
                Account account = new Account(
                    rs.getInt("account_id"), 
                    rs.getString("username"), 
                    rs.getString("password"));

                    accounts.add(account);
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return accounts;
    }


    // Get user account by id
    @Override
    public Account getAccountById(int id) {

        // Open connection to database
        Connection con = ConnectionUtil.getConnection();

        try{
            // Create prepared statement
            String sql = "SELECT * FROM account WHERE account_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            // Obtain and process result set
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int idRslt = rs.getInt("account_id");
                String usernameRslt = rs.getString("username");
                String passwordRslt = rs.getString("password");

                Account account = new Account(idRslt, usernameRslt, passwordRslt);
                
                return account; 
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

        
}

