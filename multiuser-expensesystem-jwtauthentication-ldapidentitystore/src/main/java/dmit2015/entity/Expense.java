package dmit2015.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import lombok.Getter;
import lombok.Setter;

/**
 * JAX-RS implementation (RESTEasy) will use Jackson instead of the Jakarata JSON Binding API (JSON-B) for the JSON provider 
 * if a Jackson dependency is detected in pom.xml so either remove all dependencies on Jackson
 * in pom.xml OR use the Jackson annotations @JsonSerialize/@JsonDeserialize/@JsonFormat to serialize/deserialize
 * LocalDateTime, LocalDate, or LocalTime data types.
 * 
 * The @JsonbDateFormat annotation is only used when JSON-B is used as the JSON provider.
 * 
 * To parse a 12-hour clock you must set the locale to US (CANADA will not work).
 * 
 * @author samwu
 *
 */

@Entity
@Table(name="multiuser_expense")
@Getter @Setter
public class Expense implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
		
	@Column(nullable = false)
	@NotBlank(message = "Description value is required")
	private String description;
	
	@Column(nullable = false)
	@NotNull(message = "Amount value is required")
	@DecimalMin(value = "1.00", message = "Amount must be equal to greater than ${value}")
	private BigDecimal amount;
	
	@Column(nullable = false)
	@NotNull(message = "Date is required")
	@PastOrPresent(message = "Date cannot be in the future")
	private LocalDate date = LocalDate.now();
	
	@JsonbDateFormat(value = "yyyy-MM-dd h:mm a", locale = "US")
	@Column(nullable = false)
	private LocalDateTime dateCreated;
	
	@Column(nullable = false)
	private String username;
	
	/**
	 * https://vladmihalcea.com/the-best-way-to-implement-equals-hashcode-and-tostring-with-jpa-and-hibernate/
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) 
			return true;
		
		if (!(o instanceof Expense)) 
			return false;
		
		Expense other = (Expense) o;
		return Objects.equals(getId(), other.getId());
	}
	
	/**
	 * https://vladmihalcea.com/the-best-way-to-implement-equals-hashcode-and-tostring-with-jpa-and-hibernate/
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}
}