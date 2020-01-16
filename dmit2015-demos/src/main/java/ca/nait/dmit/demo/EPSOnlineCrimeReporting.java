package ca.nait.dmit.demo;

import java.time.LocalDate;

import lombok.Data;

@Data
public class EPSOnlineCrimeReporting {

	private int year;
	private String period;
	private LocalDate date;
	private int onlineCrimeReportsSubmittedOrApproved;
	private int applicableOccurrencesNotReportedOnline;
	private double percentageOfOccurrencesReportedOnline;
	
}
