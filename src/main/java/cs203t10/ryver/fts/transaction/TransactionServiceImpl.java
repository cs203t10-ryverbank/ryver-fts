package cs203t10.ryver.fts.transaction;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import cs203t10.ryver.fts.account.AccountService;
import cs203t10.ryver.fts.market.MarketService;
import cs203t10.ryver.fts.account.Account;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private AccountService accountService;

	@Autowired
	private MarketService marketService;

	public Transaction addTransaction(Integer senderAccountId, Integer receiverAccountId,
			Double amount) {
		accountService.deductFromAvailableBalance(senderAccountId, amount);
		Account senderAccount = accountService.deductFromBalance(senderAccountId, amount);
		accountService.addToAvailableBalance(receiverAccountId, amount);
		Account receiverAccount = accountService.addToBalance(receiverAccountId, amount);

		Transaction transaction = Transaction.builder().senderAccount(senderAccount)
				.receiverAccount(receiverAccount).amount(amount).status("accepted")
				.build();
		Transaction savedTransaction = transactionRepository.save(transaction);

		marketService.addToInitialCapital(receiverAccount.getCustomerId(), amount);
		marketService.deductFromInitialCapital(senderAccount.getCustomerId(), amount);
		return savedTransaction;
	}

	public List<Transaction> findBySenderAccountId(Integer id) {
        return transactionRepository.findAllAcceptedByAccountId(id);
	}

}
