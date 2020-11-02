package cs203t10.ryver.fts.transaction;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

public class ExtendedTransactionRepositoryImpl implements ExtendedTransactionRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Transaction> findAllAcceptedByAccountId(Integer accountId) {
        final String jpql = String.join(" ",
            "FROM Transaction WHERE (receiver_account = :id",
            "OR sender_account = :id)",
            "AND status = 'accepted'"
        );
        TypedQuery<Transaction> query = entityManager.createQuery(jpql, Transaction.class);
        return query
            .setParameter("id", accountId)
            .getResultList();
    }
}

