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
    @ApiOperation(value = "Get all information of all accounts for customer",
            response = Account[].class)
    public List<Account> getCustomerAccounts(@AuthenticationPrincipal Integer customerId) {
        return accountService.findAccounts(customerId);
    }

    @GetMapping("/accounts/{accountId}")
    @RolesAllowed("USER")
    @ApiOperation(value = "Get information of specific account for customer",
            response = Account.class)
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
    @ApiOperation(value = "Create an account for customer", 
            response = Account.class)
    public Account addAccount(@Valid @RequestBody Account account){
        Account savedAccount = accountService.saveAccount(account);
        return savedAccount;
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/accounts/{accountId}/addAvailableBalance")
    @RolesAllowed("USER")
    public Account addAvailableBalance(
        @PathVariable Integer accountId, 
        @Valid @RequestParam(value = "amount") Double amount, 
        @AuthenticationPrincipal Integer customerId) {
      Integer senderCustomerId = accountService.findCustomerId(accountId);
      if (senderCustomerId != customerId) {
        throw new AccountException.AccountNoAccessException(accountId, customerId);
      }
      Account account = accountService.addToAvailableBalance(accountId, amount);
      return account;
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/accounts/{accountId}/addBalance")
    @RolesAllowed("USER")
    public Account addBalance(
        @PathVariable Integer accountId, 
        @Valid @RequestParam(value = "amount") Double amount, 
        @AuthenticationPrincipal Integer customerId) {
      Integer senderCustomerId = accountService.findCustomerId(accountId);
      if (senderCustomerId != customerId) {
        throw new AccountException.AccountNoAccessException(accountId, customerId);
      }
      Account account = accountService.addToBalance(accountId, amount);
      return account;
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/accounts/{accountId}/deductAvailableBalance")
    @RolesAllowed("USER")
    public Account deductAvailableBalance(
        @PathVariable Integer accountId, 
        @Valid @RequestParam(value = "amount") Double amount, 
        @AuthenticationPrincipal Integer customerId) {
      Integer senderCustomerId = accountService.findCustomerId(accountId);
      if (senderCustomerId != customerId) {
        throw new AccountException.AccountNoAccessException(accountId, customerId);
      }
      Account account = accountService.deductFromAvailableBalance(accountId, amount);
      return account;
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/accounts/{accountId}/deductBalance")
    @RolesAllowed("USER")
    public Account deductBalance(
        @PathVariable Integer accountId, 
        @Valid @RequestParam(value = "amount") Double amount, 
        @AuthenticationPrincipal Integer customerId) {
      Integer senderCustomerId = accountService.findCustomerId(accountId);
      if (senderCustomerId != customerId) {
        throw new AccountException.AccountNoAccessException(accountId, customerId);
      }
      Account account = accountService.deductFromBalance(accountId, amount);
      return account;
    }

}