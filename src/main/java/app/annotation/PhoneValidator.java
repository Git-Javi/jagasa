package app.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<Phone, String> {

	@Override
	public boolean isValid(String numTelefono, ConstraintValidatorContext context) {

		if (numTelefono.matches("^[6|7|8|9][0-9]{7,8}$")) {

			return true;

		} else {

			return false;
		}

	}

}
