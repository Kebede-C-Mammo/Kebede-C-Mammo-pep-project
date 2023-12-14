package Service;

import Model.Account;

import java.util.ArrayList;

public interface AccountService {

    // Create method to add account
    Account addAccount(Account account);

    // Create method for login credential
    Account getAccount(String username, String password);

    // Create method to get all accounts
    ArrayList<Account> getAllAccounts();

    // Create method to get account by id
    Account getAccountById(int id);
    
}
