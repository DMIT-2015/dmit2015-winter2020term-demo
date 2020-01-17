package ca.nait.dmit.domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Transaction {
	
	public enum TransactionType { Withdrawal, Deposit };
	
	private LocalDate date;
//	private char type;			// 'W' for withdrawal, 'D' for deposit
	private TransactionType type;
	private double amount;	
	private double balance;		// balance after subtract amount
	private String description;

}
