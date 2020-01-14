package ca.nait.dmit.domain;

import org.apache.commons.math3.util.Precision;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Loan {

	private double mortgageAmount;
	private double annualInterestRate;
	private int amortizationPeriod;
	
	public double monthlyPayment() {
		double amount = 0;
		
		amount = mortgageAmount 
				* (Math.pow(1 + annualInterestRate/200, 1.0/6.0) - 1)
				/ (1 - Math.pow(1 + annualInterestRate/200, -2 * amortizationPeriod));
		
		amount = Precision.round(amount, 2);
		
		return amount;
	}
	
	public LoanSchedule[] loanScheduleTable() {
		// Number of payments is the amortizationPeriod (in years) multipy by the number of months per year (12)
		int numberOfPayments = amortizationPeriod * 12;
		LoanSchedule[] loanSchedules = new LoanSchedule[numberOfPayments];
		// Set the initial remaining balance to equal the mortage amount
		double remainingBalance = mortgageAmount;
		// Calculate the monthlyPercentageRate
		double monthlyPercentageRate = Math.pow(1 + annualInterestRate/200, 1.0/6.0) - 1;
		// Create a LoanSchedule for each month and add it to the loanSchedules array
		double monthlyPaymentAmount = monthlyPayment();
		for (int index = 0; index < numberOfPayments; index++) {
			// Calculate the interestPaid and round to 2 decimal places
			double interestPaid = monthlyPercentageRate * remainingBalance;
			interestPaid = Precision.round(interestPaid, 2);
			// Calculate the principalPaid and round to 2 decimal places
			double principalPaid = monthlyPaymentAmount - interestPaid;
			principalPaid = Precision.round(principalPaid, 2);
			
			// Set principalPaid to remainingBalance if the it is less thean monthlyPaymentAmount
			if (remainingBalance < monthlyPaymentAmount) {
				principalPaid = remainingBalance;				
			}
			
			// Subtract the principalPaid from the remainingBalance
			remainingBalance -= principalPaid;
			remainingBalance = Precision.round(remainingBalance, 2);
			// Create a new LoanSchedule instance and add it to the loanSchedules array
			int paymentNumber = index + 1;
			loanSchedules[index] = new LoanSchedule(paymentNumber, interestPaid, principalPaid, remainingBalance);			
		}
		return loanSchedules;
	}
}
