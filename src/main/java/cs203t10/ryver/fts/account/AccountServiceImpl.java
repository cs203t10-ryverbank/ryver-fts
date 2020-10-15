package cs203t10.ryver.fts.account;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import cs203t10.ryver.fts.exception.*;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepo;

	public Account findById(Integer accountId) {
		return accountRepo.findById(accountId)
				.orElseThrow(() -> new AccountNotFoundException(accountId));
	}

	public List<Account> findAccounts(Integer customerId) {
		return accountRepo.findByCustomerId(customerId)
				.orElseThrow(() -> new AccountsNotFoundForCustomerException(customerId));
	}

	public Account saveAccount(Account account) {
		try {
			return accountRepo.save(account);
		}
		catch (DataIntegrityViolationException e) {
			throw new AccountAlreadyExistsException(account.getId());
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

}