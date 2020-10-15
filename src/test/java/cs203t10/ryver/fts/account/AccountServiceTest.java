package cs203t10.ryver.fts.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

	@Mock
	private AccountRepository accounts;

	@InjectMocks
	private AccountServiceImpl accountService;

	@Test
	public void saveAccount_NewAccount_ReturnsAccount() {

		Random rand = new Random();
		Integer testAccountId = rand.nextInt(Integer.MAX_VALUE);
		Integer testCustomerId = rand.nextInt(Integer.MAX_VALUE);

		Account account = Account.builder().id(testAccountId).customerId(testCustomerId)
				.balance(1000.0).availableBalance(1000.0).build();

		when(accounts.save(any(Account.class))).thenReturn(account);

		Account savedAccount = accountService.saveAccount(account);

		assertEquals(account, savedAccount);
		verify(accounts).save(account);

	}

	// @Test
	// public void deductFromAccountBalance_SufficientBalance_DeductsBothBalance() {
	// Random rand = new Random();
	// Integer testAccountId = rand.nextInt(Integer.MAX_VALUE);
	// Integer testCustomerId = rand.nextInt(Integer.MAX_VALUE);

	// Account account = Account.builder()
	// .id(testAccountId)
	// .customerId(testCustomerId)
	// .balance(1000.0)
	// .availableBalance(500.0)
	// .build();

	// Account updatedAccount = accountService.deductFromAccountBalance(testAccountId,
	// 10.0);
	// assertEquals(490.0, account.getAvailableBalance());
	// assertEquals(990.0, account.getBalance());
	// }

	// @Test
	// public void deductFromAccountBalance_InsufficientBalance_DoesNotDeduct() {
	// Random rand = new Random();
	// Integer testAccountId = rand.nextInt(Integer.MAX_VALUE);
	// Integer testCustomerId = rand.nextInt(Integer.MAX_VALUE);

	// Account account = Account.builder()
	// .id(testAccountId)
	// .customerId(testCustomerId)
	// .balance(1000.0)
	// .availableBalance(10.0)
	// .build();

	// assertThrows(RuntimeException.class,
	// () -> accountService.deductFromAccountBalance(testAccountId, 500.0));
	// assertEquals(10.0, account.getAvailableBalance());
	// assertEquals(1000.0, account.getBalance());
	// }

	// @Test
	// public void addToAccountBalance_AnyBalance_AddsBothBalance() {
	// Random rand = new Random();
	// Integer testAccountId = rand.nextInt(Integer.MAX_VALUE);
	// Integer testCustomerId = rand.nextInt(Integer.MAX_VALUE);

	// Account account = Account.builder()
	// .id(testAccountId)
	// .customerId(testCustomerId)
	// .balance(1000.0)
	// .availableBalance(10.50)
	// .build();

	// Account updatedAccount = accountService.addToAccountBalance(testAccountId, 100.30);
	// assertEquals(110.80, account.getAvailableBalance());
	// assertEquals(1100.30, account.getBalance());
	// }

	// @Test
	// public void findById_InvalidAccountNumber_ThrowsException() {

	// when(accounts.findById(any(Integer.class))).thenReturn(Optional.empty());

	// Random rand = new Random();
	// Integer testId = rand.nextInt(Integer.MAX_VALUE);

	// RuntimeException exception = assertThrows(RuntimeException.class,
	// () -> accountService.findById(testId));

	// String expectedMessage = String.format("Account with id: %s not found", testId);
	// String actualMessage = exception.getMessage();
	// assertEquals(actualMessage, expectedMessage);
	// verify(accounts).findById(testId);
	// }

	// @Test
	// public void findAccounts_CustomerNoAccounts_ThrowsException() {

	// when(accounts.findByCustomerId(any(Integer.class))).thenReturn(Optional.empty());

	// Random rand = new Random();
	// Integer testCustomerId = rand.nextInt(Integer.MAX_VALUE);

	// RuntimeException exception = assertThrows(RuntimeException.class,
	// () -> accountService.findAccounts(testCustomerId));

	// String expectedMessage = String.format("Customer with id: %s has no accounts",
	// testCustomerId);
	// String actualMessage = exception.getMessage();
	// assertEquals(actualMessage, expectedMessage);
	// verify(accounts).findByCustomerId(testCustomerId);
	// }

}