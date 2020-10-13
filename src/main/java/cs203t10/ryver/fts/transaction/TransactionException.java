package cs203t10.ryver.fts.transaction;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class TransactionException {

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Transaction not found.")
    public static class TransactionNotFoundException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public TransactionNotFoundException(Integer id) {
            super(String.format("Transaction with id: %s not found", id));
        }

    }


}
