package cs203t10.ryver.fts.account;

import java.util.List;
public interface AccountService {
    Account findById(Integer id);
    List<Account> findAccounts(Integer customerId);
    Account saveAccount(Account account);
    Integer findCustomerId(Integer accountId);
    Account addToAccountBalance(Integer accountId, Double amount);
    Account deductFromAccountBalance(Integer accountId, Double amount);
}
