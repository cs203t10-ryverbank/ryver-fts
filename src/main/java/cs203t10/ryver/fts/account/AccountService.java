package cs203t10.ryver.fts.account;

import java.util.List;
public interface AccountService {
    Account findById(Integer id);
    List<Account> findAccounts(Integer customerId);
    Account saveAccount(Account account);
    Double addToAccountBalance(Account account, Double amount);
    Double deductFromAccountBalance(Account account, Double amount);
}
