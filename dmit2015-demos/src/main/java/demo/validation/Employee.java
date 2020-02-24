package demo.validation;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;
/**
 * Add the required constraints for business rules listed below.
 * Write a JUnit Test Case class with a method to test each data field
 * 
 * @author user2015
 *
 */
@Data
public class Employee {

	@NotNull(message="EmployeeID is required")
	@Min(value=100000000, message="EmployeeID must contain exactly 9 digits")
	@Max(value=999999999, message="EmployeeID must contain exactly 9 digits")	
	private Long employeeId;	// Must contain exactly 9 digits
	
	@Size(min=3, message="Name must contain at least 3 characters")
	@NotBlank(message = "Name value is required")
	private String name;		// Must contain at least 3 characters and is required
	
	@Min(value=18, message="Age must between {value} and 65")
	@Max(value=65, message="Age must between 18 and {value}")	
	private int age;		// Must be between 18 and 65
	
	@DecimalMin(value="15.00", message="Hourly Wage must be at least ${formatter.format('$%,.2f',validatedValue)}")
	private BigDecimal hourlyWage;	// Must at minimum $15.00 and is required
	
	@PastOrPresent(message = "Hire Date must be in the past or present")
	private LocalDate hireDate;		// Must be current or in the past
	
	@Pattern(regexp = "^(?!.*[DFIOQU])[A-VXY][0-9][A-Z] ?[0-9][A-Z][0-9]$", message="Postal Code format is invalid. Must be in A1A1A1 format.")
	private String postalCode;	// Must be in the format A1A1A1
	
	@Email(message = "Email must be from a .ca or .com domain")
	private String email;		// Must be a valid email address with a .ca or .com domain
	
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\\\d)[a-zA-Z\\\\d]{8,64}$")
	private String password;			// Must contain at least 1 digit, 1 lower case letter, 1 upper case letter
										// and between 8 and 64 characters
										// Belongs to a validation group named ChangePasswordGroup
	private String confirmedPassword;	// Required
										// Must match password field
										// Belongs to a validation group named ChangePasswordGroup	
}
