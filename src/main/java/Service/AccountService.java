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

        Account registered = new Account();

        try {
            if(accountDAO.getAllAccounts().contains(account)) {
                throw new ExistingAccountException("Account already exists");
            }
            if(account.getUsername().isBlank()) {
                throw new BlankException("Enter a valid username");
            }
            if(account.getPassword().length() < 4) {
                throw new ArithmeticException("Password should be four or more characters");
            }
            registered = accountDAO.registerAccount(account);
        }
        catch(ExistingAccountException | BlankException e) {
            System.out.println(e);
        }

        return registered;
    }

    public Account loginAccount(Account account) {

        try {
            account = accountDAO.loginAccount(account);
            if(account == null) {
                throw new BlankException("Incorrect username or password");
            }
        }
        catch (BlankException e) {
            System.out.println(e);
        }

        return account;
    }
}
