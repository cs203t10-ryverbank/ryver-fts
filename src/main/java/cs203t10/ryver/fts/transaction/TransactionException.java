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

    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "You cannot modify this transaction.")
    public static class TransactionNoAccessException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public TransactionNoAccessException(Integer id) {
            super(String.format("Transaction with id: %s not modifiable by the logged-in user", id));
        }
    }

    
}

