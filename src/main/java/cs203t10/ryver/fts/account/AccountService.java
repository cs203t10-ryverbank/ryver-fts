package cs203t10.ryver.fts.account;

import java.util.List;

public interface AccountService {

	Account findById(Integer id);

	List<Account> findAccounts(Integer customerId);

	Account saveAccount(Account account);

	Account saveAccount(AccountInitial account);

	Integer findCustomerId(Integer accountId);

	Account addToBalance(Integer accountId, Double amount);

	Account addToAvailableBalance(Integer accountId, Double amount);

	Account deductFromAvailableBalance(Integer accountId, Double amount);

	Account deductFromBalance(Integer accountId, Double amount);

	Account resetAvailableBalance(Integer accountId);
}
