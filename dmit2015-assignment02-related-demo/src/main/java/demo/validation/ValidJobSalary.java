package demo.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})	// Supported target element type for the constraint
@Retention(RetentionPolicy.RUNTIME)	// Annotations of this type will be available at runtime by means of reflection
@Constraint(validatedBy = { ValidJobSalaryValidator.class })	// Specify the validator to be used to validate elements annotated by @ValidJobSalary
@Documented		// Document this annotation declaration by JavaDoc by default
public @interface ValidJobSalary {

	String message() default "Maximum job salary must be greater than the minimum job salary";	// default error message
	
	Class<?>[] groups() default {};	// default validation group
	
	Class<? extends Payload>[] payload() default {};	// associate metadata with a given validation constraint
}
