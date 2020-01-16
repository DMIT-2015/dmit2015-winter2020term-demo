package ca.nait.dmit.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class DemoReadingCSVFileAsAnObject {

	public static void main(String[] args) {
		DemoReadingCSVFileAsAnObject program = new DemoReadingCSVFileAsAnObject();
		program.run();
	}
	
	public void run() {
		List<EPSOnlineCrimeReporting> reports = new ArrayList<>();
		
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/data/EPS_Online_Crime_Reporting.csv"))) ) {
			String line;
			final String delimiter = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
			// Skip the first line as it is containing column headings
			reader.readLine();
			while ((line = reader.readLine()) != null) {
				String[] values = line.split(delimiter);
				EPSOnlineCrimeReporting currentReport = new EPSOnlineCrimeReporting();
				currentReport.setYear(Integer.parseInt(values[0]));
				currentReport.setPeriod(values[1]);
				currentReport.setDate(LocalDate.parse(values[2], dateTimeFormatter));
				currentReport.setOnlineCrimeReportsSubmittedOrApproved(Integer.parseInt(values[3]));
				currentReport.setApplicableOccurrencesNotReportedOnline(Integer.parseInt(values[4]));
				currentReport.setPercentageOfOccurrencesReportedOnline(Double.parseDouble(values[5]));
				
				reports.add(currentReport);								
			}			
			
			String headerMessage = String.format("%-8s %-10s %-10s %-30s %-30s %-30s", 
					"Year", 
					"Period", 
					"Date", 
					"OnlineCrimeReportsSubmittedOrApproved", 
					"ApplicableOccurrencesNotReportedOnline", 
					"PercentageOfOccurrencesReportedOnline");
			System.out.println(headerMessage);

			reports.forEach( item -> {
				String message = String.format("%-8d %-10s %-10s %30d %30d %30s", 
						item.getYear(), 
						item.getPeriod(), 
						item.getDate().toString(), 
						item.getOnlineCrimeReportsSubmittedOrApproved(), 
						item.getApplicableOccurrencesNotReportedOnline(), 
						item.getPercentageOfOccurrencesReportedOnline());
				System.out.println(message);
			});
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

}
