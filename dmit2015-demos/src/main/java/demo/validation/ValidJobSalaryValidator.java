package demo.validation;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidJobSalaryValidator implements ConstraintValidator<ValidJobSalary, Job> {

	@Override
	public boolean isValid(Job value, ConstraintValidatorContext context) {		
		if (Objects.isNull(value) 
				|| Objects.isNull(value.getMaximumSalary()) 
				|| Objects.isNull(value.getMinimumSalary())
			) {
				return true;
		}
		// Maximum salary must be greater than minimum salary if both values are set
		return value.getMaximumSalary().compareTo(value.getMinimumSalary()) > 0;
	}

}
