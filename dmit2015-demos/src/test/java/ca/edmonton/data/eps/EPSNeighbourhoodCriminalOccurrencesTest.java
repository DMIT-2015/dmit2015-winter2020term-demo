package ca.edmonton.data.eps;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class EPSNeighbourhoodCriminalOccurrencesTest {

	static List<EPSNeighbourhoodCriminalOccurrences> occurrences = new ArrayList<>();
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		// Write the code to read from the CSV file and load the data to occurrences.
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(
				EPSNeighbourhoodCriminalOccurrencesTest.class.getResourceAsStream(
						"/data/EPS_Neighbourhood_Criminal_Occurrences.csv"))) ) {
			String line;
			final String delimiter = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
			// Skip the first line as it is containing column headings
			reader.readLine();
			while ((line = reader.readLine()) != null) {
				String[] values = line.split(delimiter);
				EPSNeighbourhoodCriminalOccurrences currentReport = new EPSNeighbourhoodCriminalOccurrences();
				currentReport.setNeighbourhood(values[0]);
				currentReport.setType(values[1]);
				currentReport.setYear(Integer.parseInt(values[2]));
				currentReport.setQuarter(values[3]);
				currentReport.setMonth(Integer.parseInt(values[4]));
				currentReport.setOccurrences(Integer.parseInt(values[5]));
				
				occurrences.add(currentReport);								
			}
			// There should be 113077 records
			assertEquals(113077, occurrences.size());
		}
	}

	@Test
	void testUniqueCrimeTypes() {
		// There should be 8 unique criminal violation types
		List<String> crimeTypes = occurrences.stream()
				.map(EPSNeighbourhoodCriminalOccurrences::getType)
				.distinct()
				.collect(Collectors.toList());
		assertEquals(8, crimeTypes.size());		
		// Print out the unique criminal types to the screen sorted ascending
		crimeTypes.stream().sorted().forEach(System.out::println);
	}
	
	@Test
	void testUniqueNeighbourhood() {
		// There should be 393 unique neighbourhood names 
		// Print out the neighbourhood names sorted descending		
	}
	
	@Test
	void testTotalCrimesIn2018() {
		// There should be a total 30719 occurrences
	}

	@Test
	void testNeighbourhoodCrimeOccurrences() {
		// Print the top 10 neighbourhood (name and total) in 2018 with the most occurrences of crimes
		
		// Print the neighbourhood (name and total) in 2018 with the most occurrences of crimes
		
		
	}
}
