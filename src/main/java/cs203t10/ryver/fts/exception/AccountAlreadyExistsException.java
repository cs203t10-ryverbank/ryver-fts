package cs203t10.ryver.fts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class AccountAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AccountAlreadyExistsException(Integer id) {
		super(String.format("Account %s already exists", id));
	}

}