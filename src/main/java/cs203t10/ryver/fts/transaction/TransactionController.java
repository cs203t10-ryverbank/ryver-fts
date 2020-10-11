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
import cs203t10.ryver.fts.account.AccountException;
import static cs203t10.ryver.fts.transaction.TransactionException.*;

@RestController
public class TransactionController {
  @Autowired
  private TransactionService transactionService;

  @Autowired
  private AccountService accountService;

  @GetMapping("/accounts/{accountId}/transactions")
  @RolesAllowed("USER")
  public List<Transaction> getTransactionsById(@PathVariable Integer accountId, @AuthenticationPrincipal Integer customerId) {
    Integer senderCustomerId = accountService.findCustomerId(accountId);
    if (senderCustomerId != customerId) {
      throw new AccountException.AccountNoAccessException(accountId, customerId);
    }
    return transactionService.findBySenderAccountId(accountId);
  }

  //normal transaction, available balance and balance are deducted immediately
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/accounts/{accountId}/transactions")
  @RolesAllowed("USER")
  public Transaction addTransaction(
      @PathVariable Integer accountId, 
      @Valid @RequestBody TransactionInfo transactionInfo, 
      @AuthenticationPrincipal Integer customerId) {
    Integer senderCustomerId = accountService.findCustomerId(accountId);
    if (senderCustomerId != customerId) {
      throw new AccountException.AccountNoAccessException(accountId, customerId);
    }
    Transaction savedTransaction = transactionService.addTransaction(
        transactionInfo.getSenderAccountId(),
        transactionInfo.getReceiverAccountId(),
        transactionInfo.getAmount());
    return savedTransaction;
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/accounts/{accountId}/createpending")
  @RolesAllowed("USER")
  public Integer addPendingTransaction(
      @PathVariable Integer accountId, 
      @Valid @RequestBody TransactionInfoWhenCreatingPending transactionInfo, 
      @AuthenticationPrincipal Integer customerId) {
    Integer senderCustomerId = accountService.findCustomerId(accountId);
    if (senderCustomerId != customerId) {
      throw new AccountException.AccountNoAccessException(accountId, customerId);
    }
    Transaction savedPendingTransaction = transactionService.addPendingTransaction(
        transactionInfo.getSenderAccountId(),
        transactionInfo.getAmount());
    return savedPendingTransaction.getId();
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PutMapping("/accounts/{transactionId}/acceptpending")
  @RolesAllowed("USER")
  public Transaction acceptPendingTransaction(
      @PathVariable Integer transactionId, 
      @Valid @RequestBody TransactionInfoWhenAcceptingPending transactionInfo,
      @AuthenticationPrincipal Integer customerId) {
    Transaction pendingTransaction = transactionService.findById(transactionId);
    Integer senderAccountId = pendingTransaction.getSenderAccount().getId();
    Integer senderCustomerId = accountService.findCustomerId(senderAccountId);
    if (senderCustomerId != customerId) {
      throw new TransactionNoAccessException(transactionId);
    }
    Integer receiverAccountId = transactionInfo.getReceiverAccountId();
    Transaction acceptedPendingTransaction = transactionService.acceptPendingTransaction(transactionId, receiverAccountId);
    
    return acceptedPendingTransaction;
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PutMapping("/accounts/{transactionId}/rejectpending")
  @RolesAllowed("USER")
  public Transaction rejectPendingTransaction(
      @PathVariable Integer transactionId, 
      @AuthenticationPrincipal Integer customerId) {
    Transaction pendingTransaction = transactionService.findById(transactionId);
    Integer senderAccountId = pendingTransaction.getSenderAccount().getId();
    Integer senderCustomerId = accountService.findCustomerId(senderAccountId);
    if (senderCustomerId != customerId) {
      throw new TransactionNoAccessException(transactionId);
    }
    Transaction rejectedPendingTransaction = transactionService.rejectPendingTransaction(transactionId);
    
    return rejectedPendingTransaction;
  }
  
}
