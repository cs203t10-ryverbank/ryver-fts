package cs203t10.ryver.fts.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cs203t10.ryver.fts.account.Account;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

	@Mock
	private TransactionRepository transactions;

	@InjectMocks
	private TransactionService transactionService;

	@Test
	public void addTransaction_ValidTransaction_ChangesAccountBalance() {
		Random rand = new Random();
		Integer testAccountId = rand.nextInt(Integer.MAX_VALUE);
		Integer testCustomerId = rand.nextInt(Integer.MAX_VALUE);

		Account account = Account.builder().id(testAccountId).customerId(testCustomerId)
				.balance(1000.0).availableBalance(1000.0).build();

		Integer testAccountId2 = rand.nextInt(Integer.MAX_VALUE);
		Integer testCustomerId2 = rand.nextInt(Integer.MAX_VALUE);

		Account account2 = Account.builder().id(testAccountId2)
				.customerId(testCustomerId2).balance(1000.0).availableBalance(1000.0)
				.build();

		Transaction transaction = Transaction.builder().senderAccount(account)
				.receiverAccount(account2).amount(123.0).build();

		when(transactions.save(transaction)).thenReturn(transaction);

		Transaction executedTransaction = transactionService.addTransaction(testAccountId,
				testAccountId2, 123.0);

		assertEquals(877.0, account.getBalance());
		assertEquals(877.0, account.getAvailableBalance());
		assertEquals(1123.0, account2.getBalance());
		assertEquals(1123.0, account2.getAvailableBalance());
		assertEquals(transaction, executedTransaction);

	}

}