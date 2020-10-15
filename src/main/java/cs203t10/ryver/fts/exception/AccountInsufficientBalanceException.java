package cs203t10.ryver.fts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public class AccountInsufficientBalanceException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public AccountInsufficientBalanceException(Integer id) {
            super(String.format("Account with id: %s does not have sufficient balance for this action.", id));
        }
    }