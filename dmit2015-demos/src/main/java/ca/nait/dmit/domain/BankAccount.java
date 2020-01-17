package ca.nait.dmit.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import ca.nait.dmit.domain.Transaction.TransactionType;
import lombok.Getter;
import lombok.Setter;

public class BankAccount {

	@Getter
	private UUID accountId;
	
	@Getter @Setter
	private String owner = "Java Developer";
	
	@Getter @Setter
	private double balance;
	
	@Getter
	private LocalDate dateCreated;
	
	@Getter
	private ArrayList<Transaction> transactions;
	
	public BankAccount() {
		super();
		accountId = UUID.randomUUID();
		owner = "Java Developer";
		balance = 0;
		dateCreated = LocalDate.now();
		transactions = new ArrayList<>();
	}

	public BankAccount(UUID accountId, String owner, double balance) {
		super();
		this.accountId = accountId;
		this.owner = owner;
		this.balance = balance;
		dateCreated = LocalDate.now();
		transactions = new ArrayList<>();
	}
	
	public void withdraw(double amount, LocalDate date, String description) {
		// Calculate new balance 
		balance -= amount;
		// Create a new Transaction and add it to our list of transactions
//		Transaction newTransaction = new Transaction(
//				date, 'W', amount, balance, description);
		Transaction newTransaction = new Transaction(
				date, TransactionType.Withdrawal, amount, balance, description);
		transactions.add(newTransaction);
	}
	
	public void deposit(double amount, LocalDate date, String description) {
		// Calculate new balance 
		balance += amount;
		// Create a new Transaction and add it to our list of transactions
//		Transaction newTransaction = new Transaction(
//				date, 'D', amount, balance, description);
		Transaction newTransaction = new Transaction(
				date, TransactionType.Deposit, amount, balance, description);
		transactions.add(newTransaction);
	}
	
}
