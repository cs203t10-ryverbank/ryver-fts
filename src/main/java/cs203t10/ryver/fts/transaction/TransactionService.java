package cs203t10.ryver.fts.transaction;

import java.util.List;

public interface TransactionService {

	Transaction addTransaction(Integer senderAccountId, Integer receiverAccountId,
			Double amount);

	List<Transaction> findBySenderAccountId(Integer id);

}
