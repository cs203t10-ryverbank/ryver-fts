package cs203t10.ryver.fts.account;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import static cs203t10.ryver.fts.account.AccountException.*;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepo;

    public Account findById(Integer id) {
        return accountRepo.findById(id) 
                .orElseThrow(() -> new AccountNotFoundException(id));
    }

    public List<Account> findAccounts(Integer customerId) {
        return accountRepo.findByCustomerId(customerId)
                .orElseThrow(() -> new AccountsNotFoundForCustomerException(customerId));
    }

    public Account saveAccount(Account account) {
        try {
            return accountRepo.save(account.toBuilder().build());
        } catch (DataIntegrityViolationException e) {
            throw new AccountAlreadyExistsException(account.getAccountId());
        }
    }

    public Double addToAccountBalance(Account account, Double amount) {
        account.setAvailableBalance(account.getAvailableBalance() + amount);
        account.setBalance(account.getBalance() + amount);
        return account.getAvailableBalance();
    }

    public Double deductFromAccountBalance(Account account, Double amount) {
        Double currentAvailableBalance = account.getAvailableBalance();
        if (amount > currentAvailableBalance) {
            throw new AccountInsufficientBalanceException(account.getAccountId());
        }
        account.setAvailableBalance(currentAvailableBalance - amount);
        account.setBalance(account.getBalance() - amount);
        return account.getAvailableBalance();
    }
}   