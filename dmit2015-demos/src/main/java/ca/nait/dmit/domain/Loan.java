package ca.nait.dmit.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.util.Precision;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Loan {

	private double mortgageAmount;
	private double annualInterestRate;
	private int amortizationPeriod;
	
	public Loan() {
		super();
		mortgageAmount = 250000;
		annualInterestRate = 5.29;
		amortizationPeriod = 25;
	}
	
	public double monthlyPayment() {
		double amount = 0;
		
		amount = mortgageAmount 
				* (Math.pow(1 + annualInterestRate/200, 1.0/6.0) - 1)
				/ (1 - Math.pow(1 + annualInterestRate/200, -2 * amortizationPeriod));
		
		amount = Precision.round(amount, 2);
		
		return amount;
	}
	
	public LoanSchedule[] loanScheduleTable() {
		// Create a new List of LoanSchedule
		List<LoanSchedule> loanScheduleList = new ArrayList<>();
		
		// Number of payments is the amortizationPeriod (in years) multiply by the number of months per year (12)
		int numberOfPayments = amortizationPeriod * 12;
		// Set the initial remaining balance to equal the mortage amount
		double remainingBalance = mortgageAmount;
		// Calculate the monthlyPercentageRate
		double monthlyPercentageRate = Math.pow(1 + annualInterestRate/200, 1.0/6.0) - 1;
		// Create a LoanSchedule for each month and add it to the loanSchedules array
		double monthlyPaymentAmount = monthlyPayment();
		
		for (int paymentNumber = 1; remainingBalance > 0; paymentNumber++) {
			// Calculate the interestPaid and round to 2 decimal places
			double interestPaid = monthlyPercentageRate * remainingBalance;
			interestPaid = Precision.round(interestPaid, 2);
			// Calculate the principalPaid and round to 2 decimal places
			double principalPaid = monthlyPaymentAmount - interestPaid;
			principalPaid = Precision.round(principalPaid, 2);
			
			// Set principalPaid to remainingBalance if the it is less than the monthlyPaymentAmount OR it is the last payment
			if (remainingBalance < monthlyPaymentAmount || paymentNumber >= numberOfPayments) {
				principalPaid = remainingBalance;				
			}
			
			// Subtract the principalPaid from the remainingBalance
			remainingBalance -= principalPaid;
			remainingBalance = Precision.round(remainingBalance, 2);
			// Create a new LoanSchedule instance and add it to loanScheduleList
			LoanSchedule currentLoanSchedule = new LoanSchedule(paymentNumber, interestPaid, principalPaid, remainingBalance);	
			loanScheduleList.add(currentLoanSchedule);
		}
		
		// Convert loan schedule list (List<LoanSchedule>) to an array (LoanSchedule[])
		return loanScheduleList.toArray(LoanSchedule[]::new);
	}
}
