package cs203t10.ryver.fts.account;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiOperation;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import static cs203t10.ryver.fts.account.AccountException.*;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/accounts")
    @RolesAllowed("USER")
    @ApiOperation(value = "View user accounts")
    public List<Account> getCustomerAccounts(@AuthenticationPrincipal Integer customerId) {
        return accountService.findAccounts(customerId);
    }

    @GetMapping("/accounts/{accountId}")
    @RolesAllowed("USER")
    public Account getAccount(@PathVariable Integer accountId, @AuthenticationPrincipal Integer customerId) {
        List<Account> customerAccounts = accountService.findAccounts(customerId);
        Account thisAccount = accountService.findById(accountId);
        if (customerAccounts.indexOf(thisAccount) == -1) {
            throw new AccountNoAccessException(accountId, customerId);
        }
        return accountService.findById(accountId);
        
    }

    @PostMapping("/accounts")
    @RolesAllowed("MANAGER")
    @ResponseStatus(HttpStatus.CREATED)
    public Account addAccount(@Valid @RequestBody Account account){
        Account savedAccount = accountService.saveAccount(account);
        return savedAccount;
    }
}