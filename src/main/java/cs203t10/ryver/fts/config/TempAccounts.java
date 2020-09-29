package cs203t10.ryver.fts.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import cs203t10.ryver.fts.account.Account;
import cs203t10.ryver.fts.account.AccountService;

import static cs203t10.ryver.fts.account.AccountException.*;

@Component
@Order(1)
public class TempAccounts implements CommandLineRunner {

    @Autowired
    private AccountService accountService;

    @Override
    public void run(String... args) throws Exception {
        addTempAccount(Account.builder().customerId((long)1).balance(0).availableBalance(0).build());
        addTempAccount(Account.builder().customerId((long)1).balance(5).availableBalance(0).build());
        addTempAccount(Account.builder().customerId((long)1).balance(10).availableBalance(0).build());
    }

    public void addTempAccount(Account account) {
        try {
            System.out.println("[Add account]: " + accountService.saveAccount(account));
        } catch (AccountAlreadyExistsException e) {
            System.out.println("[Already exists]: " + account.getAccountId());
        }
    }
}

