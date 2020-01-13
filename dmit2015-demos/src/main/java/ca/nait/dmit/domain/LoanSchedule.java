package ca.nait.dmit.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanSchedule {

	// Declare data fields to store data
	private int paymentNumber;
	private double interestPaid;
	private double principalPaid;
	private double remainingBalance;
	
	
}
