package DAO;

import Model.Account;

import java.util.ArrayList;

public interface AccountDAO {

    // Create method to add user account
    Account addAccount(Account account);

    // Obtain user login credential
    Account getAccount(String username, String password);

     // Get all user accounts
     ArrayList<Account> getAllAccounts();

    // Get user account by id
    Account getAccountById(int id);
}
