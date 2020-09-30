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

    public Account findById(Long id) {
        return accountRepo.findById(id) 
                .orElseThrow(() -> new AccountNotFoundException(id));
    }

    public List<Long> findAccounts(Long customerId) {
        List<Account> accounts = accountRepo.findByCustomerId(customerId);
        List<Long> accountIds = new ArrayList<>();
        for (Account account : accounts) {
            accountIds.add(account.getAccountId());
        }
        return accountIds;
    }

    public Account saveAccount(Account account) {
        try {
            return accountRepo.save(account.toBuilder().build());
        } catch (DataIntegrityViolationException e) {
            throw new AccountAlreadyExistsException(account.getAccountId());
        }
    }

    public double deductFromAccountBalance(Long id, double amount) {
        Account account = findById(id);
        double currentAvailableBalance = account.getAvailableBalance();
        account.setAvailableBalance(currentAvailableBalance + amount);
        return account.getAvailableBalance();
    }

    public double addToAccountBalance(long id, double amount) {
        Account account = findById(id);
        double currentAvailableBalance = account.getAvailableBalance();
        if (amount > currentAvailableBalance) {
            throw new AccountInsufficientBalanceException(id);
        }
        account.setAvailableBalance(currentAvailableBalance - amount);
        return account.getAvailableBalance();
    }
}   