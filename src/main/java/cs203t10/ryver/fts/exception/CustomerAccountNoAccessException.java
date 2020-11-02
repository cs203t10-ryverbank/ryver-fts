package cs203t10.ryver.fts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class CustomerAccountNoAccessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CustomerAccountNoAccessException(Integer requester, Integer target) {
		super(String.format("Customer %s has no permission to view customer %s", requester, target));
	}

}
