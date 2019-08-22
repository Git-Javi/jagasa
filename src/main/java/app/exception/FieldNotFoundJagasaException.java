package app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "No se ha encontrado el campo introducido")
public class FieldNotFoundJagasaException extends MainJagasaException {

	public FieldNotFoundJagasaException(String msg) {
		super(msg);
	}

}
