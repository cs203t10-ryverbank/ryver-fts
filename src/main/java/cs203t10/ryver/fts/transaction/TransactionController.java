package cs203t10.ryver.fts.transaction;

import java.util.List;

import javax.validation.Valid;
import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import cs203t10.ryver.fts.account.AccountService;
import cs203t10.ryver.fts.transaction.view.*;
import cs203t10.ryver.fts.exception.*;
import cs203t10.ryver.fts.security.RyverPrincipal;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;

@RestController
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private AccountService accountService;

	@GetMapping("/accounts/{accountId}/transactions")
	@RolesAllowed("USER")
	@Operation(summary = "Get transactions by account id")
    @ApiResponse(responseCode = "200", 
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = Transaction[].class)))
	public List<Transaction> getTransactionsById(@PathVariable Integer accountId,
			@AuthenticationPrincipal RyverPrincipal ryverPrincipal) {
		Integer requesterId = ryverPrincipal.uid.intValue();
		Integer ownerOfAccountId = accountService.findCustomerId(accountId);
		if (!requesterId.equals(ownerOfAccountId)) {
			throw new AccountNoAccessException(accountId, requesterId);
		}
		return transactionService.findBySenderAccountId(accountId);
	}

	/*
     * A normal transaction, available balance and balance are deducted immediately.
     */
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/accounts/{accountId}/transactions")
	@RolesAllowed("USER")
	@Operation(summary = "Add transaction")
    @ApiResponse(responseCode = "201", 
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = Transaction.class)))
	public Transaction addTransaction(@PathVariable Integer accountId,
			@Valid @RequestBody TransactionInfo transactionInfo,
			@AuthenticationPrincipal RyverPrincipal ryverPrincipal) {
		Integer requesterId = ryverPrincipal.uid.intValue();
		Integer ownerOfAccountId = accountService.findCustomerId(accountId);
		if (!requesterId.equals(ownerOfAccountId)) {
			throw new AccountNoAccessException(accountId, requesterId);
		}
		Transaction savedTransaction = transactionService.addTransaction(
				transactionInfo.getSenderAccountId(),
				transactionInfo.getReceiverAccountId(),
                transactionInfo.getAmount());
		return savedTransaction;
	}

}
