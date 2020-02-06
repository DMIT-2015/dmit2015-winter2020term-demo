package ca.nait.dmit.demo.servlet;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class Jitter {

	@NotBlank(message = "Login Name value is required")
	@Size(min=3, max=25, message="Login Name must contain 3 to 25 characters.")
	private String loginName;
	
	@NotBlank(message = "Message value is required")
	private String message;
	
//	@Past
	private LocalDate postedDate = LocalDate.now();
	
}
