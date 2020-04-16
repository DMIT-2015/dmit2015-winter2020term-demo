package dmit2015.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="singleuser_expense")
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
	
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonFormat(shape=Shape.STRING, pattern="yyyy-MM-dd")
	@Column(nullable = false)
	@NotNull(message = "Date is required")
	@PastOrPresent(message = "Date cannot be in the future")
	private LocalDate date = LocalDate.now();
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(shape=Shape.STRING, pattern="yyyy-MM-dd HH:mm")	// 24-hour clock works with all locale
//	@JsonFormat(shape=Shape.STRING, pattern="yyyy-MM-dd h:mm a", locale = "US")	// 12-hour works with US locale (CANADA will not work)
	@Column(nullable = false)
	private LocalDateTime dateCreated;
	
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