package cs203t10.ryver.fts.account;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import cs203t10.ryver.fts.auth.AuthService;
import cs203t10.ryver.fts.exception.*;
import cs203t10.ryver.fts.market.MarketService;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepo;

	@Autowired
	private MarketService marketService;

    @Autowired
    private AuthService authService;

	@Override
	public void resetAccounts() {
		accountRepo.deleteAll();
	}

	public Account findById(Integer accountId) {
		return accountRepo.findById(accountId)
				.orElseThrow(() -> new AccountNotFoundException(accountId));
	}

	public List<Account> findAccounts(Integer customerId) {
		return accountRepo.findByCustomerId(customerId)
				.orElseThrow(() -> new AccountsNotFoundForCustomerException(customerId));
	}

	public Double getTotalBalance(Integer customerId) {
		List<Account> customerAccounts = accountRepo.findByCustomerId(customerId).orElse(null);
		if (customerAccounts == null) {
			return 0.0;
		}
		Double totalBalance = 0.0;
		for(Account account : customerAccounts) {
			totalBalance += account.getBalance();
		}
		return totalBalance;
	}

	public Account saveAccount(Account account) {
		try {
			Account savedAccount = accountRepo.save(account);
			marketService.addToInitialCapital(savedAccount.getCustomerId(), savedAccount.getBalance());
			return savedAccount;
		}
		catch (DataIntegrityViolationException e) {
			throw new AccountAlreadyExistsException(account.getId());
		}
	}

    /**
     * Save a new account, or throw an error if the customer id does not exist.
     */
	public Account saveAccount(AccountInitial accountInitial) {
        int customerId = accountInitial.getCustomerId();
        if (!authService.customerExists(customerId)) {
            throw new CustomerAccountDoesNotExist(customerId);
        }
		try {
			Account savedAccount = accountRepo.save(accountInitial.toAccount());
			marketService.addToInitialCapital(savedAccount.getCustomerId(), savedAccount.getBalance());
			return savedAccount;
		}
		catch (DataIntegrityViolationException e) {
			throw new AccountAlreadyExistsException(accountInitial.getId());
		}
	}

	public Integer findCustomerId(Integer accountId) {
		Account account = accountRepo.findById(accountId)
				.orElseThrow(() -> new AccountNotFoundException(accountId));
		return account.getCustomerId();
	}

	public Account addToBalance(Integer accountId, Double amount) {
		return accountRepo.findById(accountId).map(account -> {
			account.setBalance(account.getBalance() + amount);
			return accountRepo.save(account);
		}).orElseThrow(() -> new AccountNotFoundException(accountId));
	}

	public Account addToAvailableBalance(Integer accountId, Double amount) {
		return accountRepo.findById(accountId).map(account -> {
			account.setAvailableBalance(account.getAvailableBalance() + amount);
			return accountRepo.save(account);
		}).orElseThrow(() -> new AccountNotFoundException(accountId));
	}

	public Account deductFromBalance(Integer accountId, Double amount) {
		return accountRepo.findById(accountId).map(account -> {
			if (amount > account.getBalance()) {
				throw new AccountInsufficientBalanceException(accountId);
			}
			else {
				account.setBalance(account.getBalance() - amount);
				return accountRepo.save(account);
			}
		}).orElseThrow(() -> new AccountNotFoundException(accountId));
	}

	public Account deductFromAvailableBalance(Integer accountId, Double amount) {
		return accountRepo.findById(accountId).map(account -> {
			if (amount > account.getAvailableBalance()) {
				throw new AccountInsufficientBalanceException(accountId);
			}
			else {
				account.setAvailableBalance(account.getAvailableBalance() - amount);
				return accountRepo.save(account);
			}
		}).orElseThrow(() -> new AccountNotFoundException(accountId));
	}

	public Account resetAvailableBalance(Integer accountId) {
		return accountRepo.findById(accountId).map(account -> {
			account.setAvailableBalance(account.getBalance());
			return account;
		}).orElseThrow(() -> new AccountNotFoundException(accountId));
	}

}
