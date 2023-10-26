package Service;

import Model.Account;
import DAO.AccountDAO;
import Service.exceptions.*;


public class AccountService {
    
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account registerAccount(Account account) {

            if(accountDAO.getAllAccounts().contains(account)) {
                throw new ExistingAccountException("Account already exists");
            }
            if(account.getUsername().isBlank()) {
                throw new BlankException("Enter a valid username");
            }
            if(account.getPassword().length() < 4) {
                throw new ArithmeticException("Password should be four or more characters");
            }
            
            return accountDAO.registerAccount(account);
    }

    public Account loginAccount(Account account) {

            account = accountDAO.loginAccount(account);
            if(account == null) {
                throw new BlankException("Incorrect username or password");
            }

        return account;
    }
}
