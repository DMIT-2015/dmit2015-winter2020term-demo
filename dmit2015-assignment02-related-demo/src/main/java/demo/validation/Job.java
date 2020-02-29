package demo.validation;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@ValidJobSalary
@Data
public class Job {

	@Pattern(regexp = "[a-zA-Z]{2}_[a-zA-Z]{2,7}", message = "JobId must be in the format AA_AAAAAAA")
	@NotBlank(message = "JobId is required")
	@Size(max = 10, message = "JobId must be less than or equal to {max} characters")
	private String jobId;
	
	@NotBlank(message = "Job Title is required")
	@Size(max = 35, message = "Job Title must be less than or equal to {max} characters")
	private String jobTitle;
		
	private BigDecimal minimumSalary;
	
	private BigDecimal maximumSalary;
}
