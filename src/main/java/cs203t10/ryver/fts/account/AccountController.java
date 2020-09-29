package cs203t10.ryver.fts.account;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import com.auth0.jwt.JWT;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;

import static cs203t10.ryver.fts.account.AccountException.*;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/")
    @RolesAllowed("USER")
    public List<Long> getCustomerAccounts(@AuthenticationPrincipal Long customerId) {
        return accountService.findAccounts(customerId);
    }

    @GetMapping("/{accountId}")
    @RolesAllowed("USER")
    public Account getAccount(@PathVariable Long accountId, @AuthenticationPrincipal Long customerId) {
        List<Long> customerAccounts = accountService.findAccounts(customerId);
        if (customerAccounts.indexOf(accountId) == -1) {
            throw new AccountNoAccessException(accountId, customerId);
        }
        return accountService.findById(accountId);
        
    }

    @PostMapping("/")
    @RolesAllowed("MANAGER")
    @ResponseStatus(HttpStatus.CREATED)
    public Account addAccount(@Valid @RequestBody Account account){
        Account savedAccount = accountService.saveAccount(account);
        return savedAccount;
    }
}