package cs203t10.ryver.fts.account;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiOperation;

import org.springframework.security.access.annotation.Secured;
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
	@PutMapping("/accounts/{accountId}/{customerId}/resetAvailableBalance")
	@RolesAllowed("MARKET")
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

}