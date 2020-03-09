package review.exercise;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class Survey {

	@NotBlank(message = "Name is required")
	private String name;
	
	@Min(value = 18, message = "Age must be between 18 and 67")
	@Max(value = 67, message = "Age must be between 18 and 67")
	private int age;
	
	@NotNull(message = "Gender is required")
	private Gender gender;
	
	private String operatingSystemsUsed; // a comma separated list of operating systems used
	
	private String languagesUsed;	// a comma separated list of programming language names

}
