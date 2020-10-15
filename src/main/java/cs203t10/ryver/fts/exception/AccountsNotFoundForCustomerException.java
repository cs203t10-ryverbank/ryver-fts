package cs203t10.ryver.fts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AccountsNotFoundForCustomerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AccountsNotFoundForCustomerException(Integer customerId) {
		super(String.format("Customer with id: %s has no accounts", customerId));
	}

}