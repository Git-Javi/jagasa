package app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No se ha encontrado el recurso solicitado")
public class NotFoundJagasaException extends MainJagasaException {

	public NotFoundJagasaException(String msg) {

		super(msg);
	}
}
