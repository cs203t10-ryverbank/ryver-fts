package cs203t10.ryver.fts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class AccountNoAccessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AccountNoAccessException(Integer accountId, Integer customerId) {
		super(String.format("Account with id: %s does not belong to customer with id: %s",
				accountId, customerId));
	}

}