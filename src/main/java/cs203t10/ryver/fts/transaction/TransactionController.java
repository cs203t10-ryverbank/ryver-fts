package cs203t10.ryver.fts.transaction;

import java.util.List;

import javax.validation.Valid;
import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import cs203t10.ryver.fts.account.AccountService;
import cs203t10.ryver.fts.transaction.view.*;
import cs203t10.ryver.fts.exception.*;

@RestController
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private AccountService accountService;

	@GetMapping("/accounts/{accountId}/transactions")
	@RolesAllowed("USER")
	public List<Transaction> getTransactionsById(@PathVariable Integer accountId,
			@AuthenticationPrincipal Integer customerId) {
		Integer senderCustomerId = accountService.findCustomerId(accountId);
		if (senderCustomerId != customerId) {
			throw new AccountNoAccessException(accountId, customerId);
		}
		return transactionService.findBySenderAccountId(accountId);
	}

	// normal transaction, available balance and balance are deducted immediately
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/accounts/{accountId}/transactions")
	@RolesAllowed("USER")
	public Transaction addTransaction(@PathVariable Integer accountId,
			@Valid @RequestBody TransactionInfo transactionInfo,
			@AuthenticationPrincipal Integer customerId) {
		Integer senderCustomerId = accountService.findCustomerId(accountId);
		if (senderCustomerId != customerId) {
			throw new AccountNoAccessException(accountId, customerId);
		}
		Transaction savedTransaction = transactionService.addTransaction(
				transactionInfo.getSenderAccountId(),
				transactionInfo.getReceiverAccountId(), transactionInfo.getAmount());
		return savedTransaction;
	}

}
