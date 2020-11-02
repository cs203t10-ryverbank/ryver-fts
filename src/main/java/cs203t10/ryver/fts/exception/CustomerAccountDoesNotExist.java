package cs203t10.ryver.fts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CustomerAccountDoesNotExist extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CustomerAccountDoesNotExist(Integer id) {
		super(String.format("Customer account %s does not exist", id));
	}

}
