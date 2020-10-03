package cs203t10.ryver.fts.account;

import java.util.List;
public interface AccountService {
    Account findById(Integer id);
    List<Account> findAccounts(Integer customerId);
    Account saveAccount(Account account);
    double deductFromAccountBalance(Integer id, double amount);
    double addToAccountBalance(Integer id, double amount);
}