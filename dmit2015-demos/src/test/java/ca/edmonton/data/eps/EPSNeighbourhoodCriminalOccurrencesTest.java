package ca.edmonton.data.eps;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EPSNeighbourhoodCriminalOccurrencesTest {

	static List<EPSNeighbourhoodCriminalOccurrences> criminalOccurences
		= new ArrayList<>();
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		// Read from the CSV file and stored the content in criminalOccurences;
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(
		EPSNeighbourhoodCriminalOccurrencesTest.class.getResourceAsStream(
		"/data/EPS_Neighbourhood_Criminal_Occurrences.csv"))))	{
			
			String line;
			final String delimiter = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
			// Skip the first line as it is containing column headings
			reader.readLine();
			while ((line = reader.readLine()) != null) {
				String[] values = line.split(delimiter);
				EPSNeighbourhoodCriminalOccurrences item = new EPSNeighbourhoodCriminalOccurrences();
				item.setNeighbourhoodDescription(values[0]);
				item.setOccurrenceViolationTypeGroup(values[1]);
				item.setOccurrenceReportedYear(Integer.parseInt(values[2]));
				item.setOccurrenceReportedQuarter(values[3]);
				item.setOccurrenceReportedMonth(Integer.parseInt(values[4]));
				item.setOccurrences(Integer.parseInt(values[5]));
				
				criminalOccurences.add(item);
			}
			assertEquals(113077, criminalOccurences.size());
			
		}
		
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testDistinctCrimeTypes() {
		// verify that there are 8 unique crime types (assault,break and enter, ...)
		List<String> crimeTypes = criminalOccurences.stream()
				.map(EPSNeighbourhoodCriminalOccurrences::getOccurrenceViolationTypeGroup)
				.distinct()
				.sorted()
				.collect(Collectors.toList());
		assertEquals(8, crimeTypes.size());
		// print the unique crime types names to the screen
		System.out.println("Violation Type Group");
		crimeTypes.stream().forEach(item -> System.out.println(item));		
	}

	// Write a test method to number of unique neighbourhood names and print them to the screen
	// sorted ascending
	@Test
	void testDistinctNeighbourhoodNames() {
		List<String> neighbourhoods = criminalOccurences.stream()
				.map(EPSNeighbourhoodCriminalOccurrences::getNeighbourhoodDescription)
				.distinct()
				.sorted()
				.collect(Collectors.toList());
		assertEquals(393, neighbourhoods.size());
		System.out.println("Neighbourhood Description");
		neighbourhoods.stream().forEach(item -> System.out.println(item));		
	}
	
	// Write a test method to check for the total number of crimes in 2018
	@Test
	void testTotalCrimesIn2018() {
//		int totalCrimesIn2018 = criminalOccurences.stream()
//			.filter(item -> item.getOccurrenceReportedYear() == 2018)
//			.map(EPSNeighbourhoodCriminalOccurences::getOccurrences)
//			.reduce(0, (item1, item2) -> item1 + item2)
//			.intValue();
		int totalCrimesIn2018 = criminalOccurences.stream()
				.filter(item -> item.getOccurrenceReportedYear() == 2018)
				.mapToInt(EPSNeighbourhoodCriminalOccurrences::getOccurrences)
				.sum();
		assertEquals(30719, totalCrimesIn2018);
		
	}
	
	// Write a test method print the total crimes in 2018 for your neighbourhood (Downtown)
	@Test
	void testTotalCrimesIn2018InDowntown() {
		int totalCrimesIn2018InDowntown = criminalOccurences.stream()
				.filter(item -> item.getOccurrenceReportedYear() == 2018)
				.filter(item -> item.getNeighbourhoodDescription().equalsIgnoreCase("Downtown"))
				.mapToInt(EPSNeighbourhoodCriminalOccurrences::getOccurrences)
				.sum();
		assertEquals(1306, totalCrimesIn2018InDowntown);
	}
	
	// Write a test method to print the total crimes in 2018 for each neighbourhood 
	// sort descending by the number of crimes
	
	
	// Write a test method to check for the neighbourhood with the most crime in 2018
	
	
}
