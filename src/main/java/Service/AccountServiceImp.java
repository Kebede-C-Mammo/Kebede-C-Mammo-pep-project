package Service;

import Model.Account;

import DAO.AccountDAO;
import DAO.AccountDAOImp;

import java.util.ArrayList;

public class AccountServiceImp implements AccountService {

    // Create member
    private AccountDAO accountDAO;

    public AccountServiceImp() {
        this.accountDAO = new AccountDAOImp();
    }

    // Implement add account method
    @Override
    public Account addAccount(Account account) {
        if(
            account.getUsername().isBlank() ||
            account.getPassword().length() < 4) {
                return null;
            }else {
                return accountDAO.addAccount(account);
            }
    }

    // Implement login method
    @Override
    public Account getAccount(String username, String password) {
        return accountDAO.getAccount(username, password);
    }


    // Implement get all accounts method
    @Override
    public ArrayList<Account> getAllAccounts() {
        return accountDAO.getAllAccounts();
    } 


    // Implement get account by id method
    @Override
    public Account getAccountById(int id) {
        return accountDAO.getAccountById(id);
    }
    
}
