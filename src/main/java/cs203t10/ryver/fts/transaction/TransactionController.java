package cs203t10.ryver.fts.transaction;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {
  private TransactionService transactionService;

  @Autowired
  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @GetMapping("/{accountId}/transactions")
  public List<Transaction> getTransactionsById(@PathVariable long id) {
    return transactionService.findById(id);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping(value = {"/{senderAccountId}/transactions", "/{recevierAccountId}/transactions"})
  // @PreAuthorize("@accountService.getCustomerId(#senderAccountId) == principal.uid")
  public Transaction addTransaction(
      @PathVariable Long receiverAccountId,
      @PathVariable Long senderAccountId,
      @Valid @RequestBody Transaction transaction) {

    // This should throw error if the transaction results in an illegal state.
    Transaction savedTransaction = transactionService.addTransaction(transaction);

    return savedTransaction;
  }
}
