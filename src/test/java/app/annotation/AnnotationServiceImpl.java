package app.annotation;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import app.annotation.constraint.Phone;

@Validated
@Service
public class AnnotationServiceImpl implements AnnotationService {

	@Override
	public String telefonoTester(@Phone String telefonoTest) {

		return telefonoTest;
	}


}
