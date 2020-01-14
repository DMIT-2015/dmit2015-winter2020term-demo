package ca.nait.dmit.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LoanTest {

	@Test
	public void testGetMonthyPayment() {
		Loan loanBean = new Loan(250000, 5.29, 25);
		assertEquals(1495.56, loanBean.monthlyPayment(), 0.00);

		loanBean = new Loan(250000, 3.09, 25);
		assertEquals(1194.69, loanBean.monthlyPayment(), 0.00);

		loanBean = new Loan(250000, 5.29, 20);
		assertEquals(1682.18, loanBean.monthlyPayment(), 0.00);
	}
	
	@Test
	  public void testGetMonthlyPayment_GetLoanScheduleArray1()
	  {
		Loan loanBean = new Loan(1000, 10, 1);
		assertEquals(87.82, loanBean.monthlyPayment(), 0.00);

		LoanSchedule[] loanScheduleArray = loanBean.loanScheduleTable();

		LoanSchedule loanSchedule1 = loanScheduleArray[0];
		assertEquals(1, loanSchedule1.getPaymentNumber());
		assertEquals(8.16, loanSchedule1.getInterestPaid(), 0.00);
		assertEquals(79.66, loanSchedule1.getPrincipalPaid(), 0.00);
		assertEquals(920.34, loanSchedule1.getRemainingBalance(), 0.00);

		LoanSchedule loanSchedule2 = loanScheduleArray[1];
		assertEquals(2, loanSchedule2.getPaymentNumber());
		assertEquals(7.51, loanSchedule2.getInterestPaid(), 0.00);
		assertEquals(80.31, loanSchedule2.getPrincipalPaid(), 0.00);
		assertEquals(840.03, loanSchedule2.getRemainingBalance(), 0.00);

		LoanSchedule loanSchedule6 = loanScheduleArray[5];
		assertEquals(6, loanSchedule6.getPaymentNumber());
		assertEquals(4.86, loanSchedule6.getInterestPaid(), 0.00);
		assertEquals(82.96, loanSchedule6.getPrincipalPaid(), 0.00);
		assertEquals(512.20, loanSchedule6.getRemainingBalance(), 0.00);
		LoanSchedule loanSchedule7 = loanScheduleArray[6];
		assertEquals(7, loanSchedule7.getPaymentNumber());
		assertEquals(4.18, loanSchedule7.getInterestPaid(), 0.00);
		assertEquals(83.64, loanSchedule7.getPrincipalPaid(), 0.00);
		assertEquals(428.56, loanSchedule7.getRemainingBalance(), 0.00);

		LoanSchedule loanSchedule11 = loanScheduleArray[10];
		assertEquals(11, loanSchedule11.getPaymentNumber());
		assertEquals(1.42, loanSchedule11.getInterestPaid(), 0.00);
		assertEquals(86.40, loanSchedule11.getPrincipalPaid(), 0.00);
		assertEquals(87.13, loanSchedule11.getRemainingBalance(), 0.00);

		LoanSchedule loanSchedule12 = loanScheduleArray[11];
		assertEquals(12, loanSchedule12.getPaymentNumber());
		assertEquals(0.71, loanSchedule12.getInterestPaid(), 0.00);
		assertEquals(87.13, loanSchedule12.getPrincipalPaid(), 0.00);
		assertEquals(0.00, loanSchedule12.getRemainingBalance(), 0.00);
	  }

	  @Test
	  public void testGetMonthlyPayment_GetLoanScheduleArray2()
	  {
		Loan loanBean = new Loan(5000, 5, 2);
		assertEquals(219.24, loanBean.monthlyPayment(), 0.00);

		LoanSchedule[] loanScheduleArray = loanBean.loanScheduleTable();
		LoanSchedule loanSchedule1 = loanScheduleArray[0];
		assertEquals(1, loanSchedule1.getPaymentNumber());
		assertEquals(20.62, loanSchedule1.getInterestPaid(), 0.00);
		assertEquals(198.62, loanSchedule1.getPrincipalPaid(), 0.00);
		assertEquals(4801.38, loanSchedule1.getRemainingBalance(), 0.00);
		LoanSchedule loanSchedule2 = loanScheduleArray[1];
		assertEquals(2, loanSchedule2.getPaymentNumber());
		assertEquals(19.80, loanSchedule2.getInterestPaid(), 0.00);
		assertEquals(199.44, loanSchedule2.getPrincipalPaid(), 0.00);
		assertEquals(4601.94, loanSchedule2.getRemainingBalance(), 0.00);
		LoanSchedule loanSchedule23 = loanScheduleArray[22];
		assertEquals(23, loanSchedule23.getPaymentNumber());
		assertEquals(1.80, loanSchedule23.getInterestPaid(), 0.00);
		assertEquals(217.44, loanSchedule23.getPrincipalPaid(), 0.00);
		assertEquals(218.37, loanSchedule23.getRemainingBalance(), 0.00);
		LoanSchedule loanSchedule24 = loanScheduleArray[23];
		assertEquals(24, loanSchedule24.getPaymentNumber());
		assertEquals(0.90, loanSchedule24.getInterestPaid(), 0.00);
		assertEquals(218.37, loanSchedule24.getPrincipalPaid(), 0.00);
		assertEquals(0.00, loanSchedule24.getRemainingBalance(), 0.00);
	  }

	  @Test
	  public void testGetMonthlyPayment_GetLoanScheduleArray3()
	  {
		Loan loanBean = new Loan(10000, 8.9, 10);
		assertEquals(125.27, loanBean.monthlyPayment(), 0.00);

		LoanSchedule[] loanScheduleArray = loanBean.loanScheduleTable();
		LoanSchedule loanSchedule1 = loanScheduleArray[0];
		assertEquals(1, loanSchedule1.getPaymentNumber());
		assertEquals(72.83, loanSchedule1.getInterestPaid(), 0.00);
		assertEquals(52.44, loanSchedule1.getPrincipalPaid(), 0.00);
		assertEquals(9947.56, loanSchedule1.getRemainingBalance(), 0.00);
		LoanSchedule loanSchedule2 = loanScheduleArray[1];
		assertEquals(2, loanSchedule2.getPaymentNumber());
		assertEquals(72.45, loanSchedule2.getInterestPaid(), 0.00);
		assertEquals(52.82, loanSchedule2.getPrincipalPaid(), 0.00);
		assertEquals(9894.74, loanSchedule2.getRemainingBalance(), 0.00);
		LoanSchedule loanSchedule119 = loanScheduleArray[118];
		assertEquals(119, loanSchedule119.getPaymentNumber());
		assertEquals(1.80, loanSchedule119.getInterestPaid(), 0.00);
		assertEquals(123.47, loanSchedule119.getPrincipalPaid(), 0.00);
		assertEquals(124.29, loanSchedule119.getRemainingBalance(), 0.00);
		LoanSchedule loanSchedule120 = loanScheduleArray[119];
		assertEquals(120, loanSchedule120.getPaymentNumber());
		assertEquals(0.91, loanSchedule120.getInterestPaid(), 0.00);
		assertEquals(124.29, loanSchedule120.getPrincipalPaid(), 0.00);
		assertEquals(0.00, loanSchedule120.getRemainingBalance(), 0.00);
	  }

	  @Test
	  public void testGetMonthlyPayment_GetLoanScheduleArray4()
	  {
		Loan loanBean = new Loan(300000, 5.390, 25);
		assertEquals(1812.01, loanBean.monthlyPayment(), 0.00);
		LoanSchedule[] loanScheduleArray = loanBean.loanScheduleTable();
		LoanSchedule loanSchedule1 = loanScheduleArray[0];
		assertEquals(1, loanSchedule1.getPaymentNumber());
		assertEquals(1332.61, loanSchedule1.getInterestPaid(), 0.00);
		assertEquals(479.40, loanSchedule1.getPrincipalPaid(), 0.00);
		assertEquals(299520.60, loanSchedule1.getRemainingBalance(), 0.00);
		LoanSchedule loanSchedule2 = loanScheduleArray[1];
		assertEquals(2, loanSchedule2.getPaymentNumber());
		assertEquals(1330.48, loanSchedule2.getInterestPaid(), 0.00);
		assertEquals(481.53, loanSchedule2.getPrincipalPaid(), 0.00);
		assertEquals(299039.07, loanSchedule2.getRemainingBalance(), 0.00);
		LoanSchedule loanSchedule299 = loanScheduleArray[298];
		assertEquals(299, loanSchedule299.getPaymentNumber());
		assertEquals(15.99, loanSchedule299.getInterestPaid(), 0.00);
		assertEquals(1796.02, loanSchedule299.getPrincipalPaid(), 0.00);
		assertEquals(1804.55, loanSchedule299.getRemainingBalance(), 0.00);
		LoanSchedule loanSchedule300 = loanScheduleArray[299];
		assertEquals(300, loanSchedule300.getPaymentNumber());
		assertEquals(8.02, loanSchedule300.getInterestPaid(), 0.00);
		assertEquals(1804.55, loanSchedule300.getPrincipalPaid(), 0.00);
		assertEquals(0.00, loanSchedule300.getRemainingBalance(), 0.00);
	  }

	  @Test
	  public void testGetMonthlyPayment_GetLoanScheduleArray5()
	  {
		Loan loanBean = new Loan(100000, 7.9, 20);
		assertEquals(822.37, loanBean.monthlyPayment(), 0.00);
		LoanSchedule[] loanScheduleArray = loanBean.loanScheduleTable();
		LoanSchedule loanSchedule1 = loanScheduleArray[0];
		assertEquals(1, loanSchedule1.getPaymentNumber());
		assertEquals(647.75, loanSchedule1.getInterestPaid(), 0.00);
		assertEquals(174.62, loanSchedule1.getPrincipalPaid(), 0.00);
		assertEquals(99825.38, loanSchedule1.getRemainingBalance(), 0.00);
		LoanSchedule loanSchedule2 = loanScheduleArray[1];
		assertEquals(2, loanSchedule2.getPaymentNumber());
		assertEquals(646.62, loanSchedule2.getInterestPaid(), 0.00);
		assertEquals(175.75, loanSchedule2.getPrincipalPaid(), 0.00);
		assertEquals(99649.63, loanSchedule2.getRemainingBalance(), 0.00);
		LoanSchedule loanSchedule239 = loanScheduleArray[238];
		assertEquals(239, loanSchedule239.getPaymentNumber());
		assertEquals(10.55, loanSchedule239.getInterestPaid(), 0.00);
		assertEquals(811.82, loanSchedule239.getPrincipalPaid(), 0.00);
		assertEquals(817.14, loanSchedule239.getRemainingBalance(), 0.00);
		LoanSchedule loanSchedule240 = loanScheduleArray[239];
		assertEquals(240, loanSchedule240.getPaymentNumber());
		assertEquals(5.29, loanSchedule240.getInterestPaid(), 0.00);
		assertEquals(817.14, loanSchedule240.getPrincipalPaid(), 0.00);
		assertEquals(0.00, loanSchedule240.getRemainingBalance(), 0.00);
	  }

	  @Test
	  public void testGetMonthlyPayment_GetLoanScheduleArray6()
	  {
		Loan loanBean = new Loan(250000, 5.19, 25);
		assertEquals(1481.17, loanBean.monthlyPayment(), 0.00);
		LoanSchedule[] loanScheduleArray = loanBean.loanScheduleTable();

		LoanSchedule loanSchedule1 = loanScheduleArray[0];
		assertEquals(1, loanSchedule1.getPaymentNumber());
		assertEquals(1069.74, loanSchedule1.getInterestPaid(), 0.00);
		assertEquals(411.43, loanSchedule1.getPrincipalPaid(), 0.00);
		assertEquals(249588.57, loanSchedule1.getRemainingBalance(), 0.00);

		LoanSchedule loanSchedule240 = loanScheduleArray[299];
		assertEquals(300, loanSchedule240.getPaymentNumber());
		assertEquals(6.31, loanSchedule240.getInterestPaid(), 0.00);
		assertEquals(1474.86, loanSchedule240.getPrincipalPaid(), 0.00);
		assertEquals(0.00, loanSchedule240.getRemainingBalance(), 0.00);
	  }

}
