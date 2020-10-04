package cs203t10.ryver.fts.account;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class AccountException {

    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Account already exists")
    public static class AccountAlreadyExistsException extends RuntimeException {
		private static final long serialVersionUID = 1L;

        public AccountAlreadyExistsException(Integer id) {
            super(String.format("Account %s already exists", id));
        }
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Account not found.")
    public static class AccountNotFoundException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public AccountNotFoundException(Integer id) {
            super(String.format("Account with id: %s not found", id));
        }

    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Customer has no accounts")
    public static class AccountsNotFoundForCustomerException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public AccountsNotFoundForCustomerException(Integer customerId) {
            super(String.format("Customer with id: %s has no accounts", customerId));
        }

    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Account does not belong to you.")
    public static class AccountNoAccessException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public AccountNoAccessException(Integer accountId, Integer customerId) {
            super(String.format("Account with id: %s does not belong to customer with id: %s", accountId, customerId));
        }
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Account has insufficient balance.")
    public static class AccountInsufficientBalanceException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public AccountInsufficientBalanceException(Integer id) {
            super(String.format("Account with id: %s does not have sufficient balance for this action.", id));
        }
    }
}

