package cs203t10.ryver.fts.account;

import java.beans.FeatureDescriptor;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static cs203t10.ryver.fts.account.AccountException.*;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepo;

    public Account findById(Integer id) {
        return accountRepo.findById(id) 
                .orElseThrow(() -> new AccountNotFoundException(id));
    }

    public List<Integer> findAccounts(Integer customerId) {
        List<Account> accounts = accountRepo.findByCustomerId(customerId);
        List<Integer> accountIds = new ArrayList<>();
        for (Account account : accounts) {
            accountIds.add(account.getAccountId());
        }
        return accountIds;
    }

    public Integer getCustomerId(Integer accountId) {
        Account account = accountRepo.findById(accountId) 
                            .orElseThrow(() -> new AccountNotFoundException(accountId));
        return account.getCustomerId();
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