package cs203t10.ryver.fts.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cs203t10.ryver.fts.account.Account;
import cs203t10.ryver.fts.account.AccountServiceImpl;
import cs203t10.ryver.fts.market.MarketService;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {


	@Mock
	private AccountServiceImpl accountService;

	@Mock
	private TransactionRepository transactions;

	@Mock
	private MarketService marketService;

	@InjectMocks
	private TransactionServiceImpl transactionService;

	@Test

	public void addTransaction_ValidTransaction_CallsCorrectMethods() {
		Random rand = new Random();
		Integer testAccountId = rand.nextInt(Integer.MAX_VALUE);
		Integer testCustomerId = rand.nextInt(Integer.MAX_VALUE);

		Account account1 = Account.builder().id(testAccountId).customerId(testCustomerId)
				.balance(1000.0).availableBalance(1000.0)
				.build();
		Account postTransactionAccount1 = Account.builder().id(testAccountId).customerId(testCustomerId)
				.balance(1000.0).availableBalance(877.0)
				.build();
		Integer testAccountId2 = rand.nextInt(Integer.MAX_VALUE);
		Integer testCustomerId2 = rand.nextInt(Integer.MAX_VALUE);

		Account account2 = Account.builder().id(testAccountId2)
				.customerId(testCustomerId2).balance(1000.0).availableBalance(1000.0)
				.build();
		Account postTransactionAccount2 = Account.builder().id(testAccountId2).customerId(testCustomerId2)
				.balance(1000.0).availableBalance(1123.0)
				.build();

		Transaction transaction = Transaction.builder().senderAccount(postTransactionAccount1)
				.receiverAccount(postTransactionAccount2).amount(123.0).status("accepted").build();

		when(accountService.deductFromAvailableBalance(any(Integer.class), any(Double.class))).thenReturn(postTransactionAccount1);
		when(accountService.addToAvailableBalance(any(Integer.class), any(Double.class))).thenReturn(postTransactionAccount2);
		when(accountService.deductFromBalance(any(Integer.class), any(Double.class))).thenReturn(postTransactionAccount1);
		when(accountService.addToBalance(any(Integer.class), any(Double.class))).thenReturn(postTransactionAccount2);
		when(transactions.save(any(Transaction.class))).thenReturn(transaction);
		doNothing().when(marketService).addToInitialCapital(any(Integer.class), any(Double.class));
		doNothing().when(marketService).deductFromInitialCapital(any(Integer.class), any(Double.class));
		Transaction executedTransaction = transactionService.addTransaction(testAccountId, testAccountId2, 123.0);

		assertEquals(transaction, executedTransaction);

		verify(accountService).deductFromAvailableBalance(testAccountId, 123.0);
		verify(accountService).addToAvailableBalance(testAccountId2, 123.0);
		verify(accountService).deductFromBalance(testAccountId, 123.0);
		verify(accountService).addToBalance(testAccountId2, 123.0);
		verify(transactions).save(transaction);
		verify(marketService).addToInitialCapital(testCustomerId2, 123.0);
		verify(marketService).deductFromInitialCapital(testCustomerId, 123.0);
		
	}

}