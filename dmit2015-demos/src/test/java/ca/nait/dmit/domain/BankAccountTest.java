package ca.nait.dmit.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BankAccountTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testWithdraw() {
		// Create a new BankAccount with an initial balance of $1000
		BankAccount bankAccount1 = new BankAccount();
		bankAccount1.setBalance(1000);
		// Withdraw $5 on Jan 2 for Coffee
		LocalDate date1 = LocalDate.parse("2020-01-02");
		bankAccount1.withdraw(5, date1, "Coffee");
		// Withdraw $4 on Jan 3 for Tea
		LocalDate date2 = LocalDate.parse("2020-01-03");
		bankAccount1.withdraw(4, date2, "Tea");		
		// Withdraw $3 on Jan 5 for Water
		LocalDate date3 = LocalDate.parse("2020-01-05");
		bankAccount1.withdraw(3, date3, "Water");		
		// The new balance should be $988
		assertEquals(988, bankAccount1.getBalance(), 0);
		// There should be 3 transactions
		assertEquals(3, bankAccount1.getTransactions().size());
		// Display all 3 transactions to the screen
//		for(Transaction tran : bankAccount1.getTransactions()) {
//			System.out.println(tran);
//		}
		bankAccount1.getTransactions().stream().forEach(tran -> {
			System.out.println(tran);
		});
	}

	@Test
	void testDeposit() {
		// Create a new BankAccount with an initial balance of $1000
		BankAccount bankAccount1 = new BankAccount();
		bankAccount1.setBalance(1000);
		// Deposit $30 on Jan 2 from Mom
		LocalDate date1 = LocalDate.parse("2020-01-02");
		bankAccount1.deposit(30, date1, "Mom");
		// Withdraw $40 on Jan 4 from Dad
		LocalDate date2 = LocalDate.parse("2020-01-04");
		bankAccount1.deposit(40, date2, "Dad");		
		// Withdraw $50 on Jan 5 from my kid
		LocalDate date3 = LocalDate.parse("2020-01-05");
		bankAccount1.deposit(50, date3, "My Kid");		
		// The new balance should be $1120
		assertEquals(1120, bankAccount1.getBalance(), 0);
		// There should be 3 transactions
		assertEquals(3, bankAccount1.getTransactions().size());
		// Display all 3 transactions to the screen
//				for(Transaction tran : bankAccount1.getTransactions()) {
//					System.out.println(tran);
//				}
		bankAccount1.getTransactions().stream().forEach(tran -> {
			System.out.println(tran);
		});
	}

}
