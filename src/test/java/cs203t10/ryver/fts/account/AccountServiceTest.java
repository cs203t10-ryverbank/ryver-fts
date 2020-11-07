package cs203t10.ryver.fts.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
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
	public void findById_InvalidAccountNumber_ThrowsException() throws Exception {

		when(accounts.findById(any(Integer.class))).thenReturn(Optional.empty());

		Random rand = new Random();
		Integer testId = rand.nextInt(Integer.MAX_VALUE);

		assertThrows(RuntimeException.class, () -> 
						accountService.findById(testId));

		verify(accounts).findById(testId);
	}

	@Test
	public void findAccounts_CustomerNoAccounts_ThrowsException() throws Exception {

		when(accounts.findByCustomerId(any(Integer.class))).thenReturn(Optional.empty());

		Random rand = new Random();
		Integer testCustomerId = rand.nextInt(Integer.MAX_VALUE);

		assertThrows(RuntimeException.class, () -> 
						accountService.findAccounts(testCustomerId));

		verify(accounts).findByCustomerId(testCustomerId);
	}

	@Test
	public void getTotalBalance__MultipleAccounts_FindsBalance() throws Exception {
		List<Account> customerAccounts = new ArrayList<>();
		Double balance1 = 1000.0;
		Double balance2 = 1050.0;
		Double balance3 = 10.0;

		Account account1 = Account.builder()
							.id(1)
							.balance(balance1)
							.availableBalance(123.0)
							.build();
		Account account2 = Account.builder()
							.id(1)
							.balance(balance2)
							.availableBalance(132.0)
							.build();
		Account account3 = Account.builder()
							.id(1)
							.balance(balance3)
							.availableBalance(10.0)
							.build();	

		customerAccounts.add(account1);
		customerAccounts.add(account2);
		customerAccounts.add(account3);

		when(accounts.findByCustomerId(any(Integer.class)))
			.thenReturn(Optional.of(customerAccounts));
		Double accountSum = accountService.getTotalBalance(1);
		assertEquals(balance1+balance2+balance3, accountSum);
	}

}