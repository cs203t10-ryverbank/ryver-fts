package cs203t10.ryver.fts.transaction;

import java.util.List;

public interface ExtendedTransactionRepository {
    List<Transaction> findAllAcceptedByAccountId(Integer accountId);
}
