package cs203t10.ryver.fts.account;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiOperation;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import cs203t10.ryver.fts.exception.*;
import cs203t10.ryver.fts.security.RyverPrincipal;

@RestController
public class AccountController {

	@Autowired
	private AccountService accountService;

	@GetMapping("/accounts")
	@RolesAllowed("USER")
	@ApiOperation(value = "Get all information of all accounts for customer", response = Account[].class)
	public List<Account> getCustomerAccounts(
			@AuthenticationPrincipal RyverPrincipal ryverPrincipal) {
		Integer customerId = ryverPrincipal.uid.intValue();
		return accountService.findAccounts(customerId);
	}

	@GetMapping("/accounts/{accountId}")
	@RolesAllowed({"USER","MANAGER"})
	@PreAuthorize("principal.uid != null and (hasRole('ROLE_USER') or hasRole('ROLE_MANAGER'))" )
	@ApiOperation(value = "Get information of specific account for customer", response = Account.class)
	public Account getAccount(@PathVariable Integer accountId,
			@AuthenticationPrincipal RyverPrincipal ryverPrincipal) {
		Integer customerId = ryverPrincipal.uid.intValue();
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
	@ApiOperation(value = "Create an account for customer", response = Account.class)
	public Account addAccount(@Valid @RequestBody AccountInitial account) {
		Account savedAccount = accountService.saveAccount(account);
		return savedAccount;
	}

	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/accounts/{accountId}/addAvailableBalance")
	@RolesAllowed("USER")
	@ApiOperation(value = "Add available balance to account", response = Account.class)
	public Account addAvailableBalance(@PathVariable Integer accountId,
			@Valid @RequestParam(value = "amount") Double amount,
			@AuthenticationPrincipal RyverPrincipal ryverPrincipal) {
		Integer customerId = ryverPrincipal.uid.intValue();
		Integer senderCustomerId = accountService.findCustomerId(accountId);
		if (senderCustomerId != customerId) {
			throw new AccountNoAccessException(accountId, customerId);
		}
		Account account = accountService.addToAvailableBalance(accountId, amount);
		return account;
	}

	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/accounts/{accountId}/addBalance")
	@RolesAllowed("USER")
	@ApiOperation(value = "Add balance to account", response = Account.class)
	public Account addBalance(@PathVariable Integer accountId,
			@Valid @RequestParam(value = "amount") Double amount,
			@AuthenticationPrincipal RyverPrincipal ryverPrincipal) {
		Integer customerId = ryverPrincipal.uid.intValue();
		Integer senderCustomerId = accountService.findCustomerId(accountId);
		if (senderCustomerId != customerId) {
			throw new AccountNoAccessException(accountId, customerId);
		}
		Account account = accountService.addToBalance(accountId, amount);
		return account;
	}

	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/accounts/{accountId}/deductAvailableBalance")
	@RolesAllowed("USER")
	@ApiOperation(value = "Deduct available balance from account", response = Account.class)
	public Account deductAvailableBalance(@PathVariable Integer accountId,
			@Valid @RequestParam(value = "amount") Double amount,
			@AuthenticationPrincipal RyverPrincipal ryverPrincipal) {
		Integer customerId = ryverPrincipal.uid.intValue();
		Integer senderCustomerId = accountService.findCustomerId(accountId);
		if (senderCustomerId != customerId) {
			throw new AccountNoAccessException(accountId, customerId);
		}
		Account account = accountService.deductFromAvailableBalance(accountId, amount);
		return account;
	}

	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/accounts/{accountId}/deductBalance")
	@RolesAllowed("USER")
	@ApiOperation(value = "Deduct balance from account", response = Account.class)
	public Account deductBalance(@PathVariable Integer accountId,
			@Valid @RequestParam(value = "amount") Double amount,
			@AuthenticationPrincipal RyverPrincipal ryverPrincipal) {
		Integer customerId = ryverPrincipal.uid.intValue();
		Integer senderCustomerId = accountService.findCustomerId(accountId);
		if (senderCustomerId != customerId) {
			throw new AccountNoAccessException(accountId, customerId);
		}
		Account account = accountService.deductFromBalance(accountId, amount);
		return account;
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/accounts/{customerId}/getTotalBalance")
	@RolesAllowed({"USER", "MANAGER"})
	@ApiOperation(value = "Retrieve total balance from account", response = Account.class)
	public Double getTotalBalance(@PathVariable Integer customerId, @AuthenticationPrincipal RyverPrincipal ryverPrincipal) {
		Integer ryverCustomerId = ryverPrincipal.uid.intValue();
		return accountService.getTotalBalance(ryverCustomerId);
	}

	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/accounts/{accountId}/{customerId}/resetAvailableBalance")
	@RolesAllowed("MARKET")
	@ApiOperation(value = "Reset available balance for a specific account", response = Account.class)
	public Account resetAvailableBalance(@PathVariable Integer accountId,
			@PathVariable Integer customerId) {
		Integer senderCustomerId = accountService.findCustomerId(accountId);
		if (senderCustomerId != customerId) {
			throw new AccountNoAccessException(accountId, customerId);
		}
		Account account = accountService.resetAvailableBalance(accountId);
		return account;
	}

	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/accounts/{accountId}/{customerId}/addAvailableBalance")
	@RolesAllowed("MARKET")
	@ApiOperation(value = "Add available balance by Market", response = Account.class)
	public Account addAvailableBalanceByMarket(@PathVariable Integer accountId,
			@Valid @RequestParam(value = "amount") Double amount,
			@PathVariable Integer customerId) {
		Integer senderCustomerId = accountService.findCustomerId(accountId);
		if (senderCustomerId != customerId) {
			throw new AccountNoAccessException(accountId, customerId);
		}
		Account account = accountService.addToAvailableBalance(accountId, amount);
		return account;
	}

	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/accounts/{accountId}/{customerId}/deductAvailableBalance")
	@RolesAllowed("MARKET")
	@ApiOperation(value = "Deduct available balance by Market", response = Account.class)
	public Account deductAvailableBalanceByMarket(@PathVariable Integer accountId,
			@Valid @RequestParam(value = "amount") Double amount,
			@PathVariable Integer customerId) {
		Integer senderCustomerId = accountService.findCustomerId(accountId);
		if (senderCustomerId != customerId) {
			throw new AccountNoAccessException(accountId, customerId);
		}
		Account account = accountService.deductFromAvailableBalance(accountId, amount);
		return account;
	}

	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/accounts/{accountId}/{customerId}/addBalance")
	@RolesAllowed("MARKET")
	@ApiOperation(value = "Add balance by Market", response = Account.class)
	public Account addBalanceByMarket(@PathVariable Integer accountId,
			@Valid @RequestParam(value = "amount") Double amount,
			@PathVariable Integer customerId) {
		Integer senderCustomerId = accountService.findCustomerId(accountId);
		if (senderCustomerId != customerId) {
			throw new AccountNoAccessException(accountId, customerId);
		}
		Account account = accountService.addToBalance(accountId, amount);
		return account;
	}

	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/accounts/{accountId}/{customerId}/deductBalance")
	@RolesAllowed("MARKET")
	@ApiOperation(value = "deduct balance by Market", response = Account.class)
	public Account deductBalanceByMarket(@PathVariable Integer accountId,
			@Valid @RequestParam(value = "amount") Double amount,
			@PathVariable Integer customerId) {
		Integer senderCustomerId = accountService.findCustomerId(accountId);
		if (senderCustomerId != customerId) {
			throw new AccountNoAccessException(accountId, customerId);
		}
		Account account = accountService.deductFromBalance(accountId, amount);
		return account;
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/reset")
	@RolesAllowed("MANAGER")
	@ApiOperation(value = "Reset Accounts", response = Account.class)
	public void resetAccounts() {
		accountService.resetAccounts();
	}

}
