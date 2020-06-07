package app.annotation;

import app.annotation.constraint.Dni;
import app.annotation.constraint.Phone;

public interface AnnotationService {

	public String telefonoTester(@Phone String telefonoTest);
	
	public String dnitester(@Dni String dniTest);
	
}
