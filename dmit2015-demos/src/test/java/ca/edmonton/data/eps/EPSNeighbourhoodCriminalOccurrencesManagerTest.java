package ca.edmonton.data.eps;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class EPSNeighbourhoodCriminalOccurrencesManagerTest {

	static EPSNeighbourhoodCriminalOccurrencesManager manager;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		manager = new EPSNeighbourhoodCriminalOccurrencesManager();
	}

	@Test
	void testFindDistinctNeighbourhoodDescription() {
		List<String> neighbourhoodDescriptionList = manager.findDistinctNeighbourhoodDescription();
		neighbourhoodDescriptionList.stream().forEach(System.out::println);
		assertEquals(393, neighbourhoodDescriptionList.size());
	}

	@Test
	void testFindBySuccess() {
		String neighbourhoodDescription = "ABBOTTSFIELD";
		String occurrenceViolationTypeGroup = "Assault";
		int occurrenceReportedYear = 2009;
		String occurrenceReportedQuarter = "Q4";
		Optional<EPSNeighbourhoodCriminalOccurrences> optionalItem = manager.findBy_NeighbourhoodDescription_OccurrenceViolationTypeGroup_OccurrenceReportedYear_OccurrenceReportedQuarter(
				neighbourhoodDescription, 
				occurrenceViolationTypeGroup, 
				occurrenceReportedYear, 
				occurrenceReportedQuarter);
		assertEquals(false, optionalItem.isEmpty());
		EPSNeighbourhoodCriminalOccurrences item = optionalItem.get();
		System.out.println(item);
	}
	
	@Test
	void testFindByFailure() {
		String neighbourhoodDescription = "ABBOTTSFIELD";
		String occurrenceViolationTypeGroup = "Assault";
		int occurrenceReportedYear = 2020;
		String occurrenceReportedQuarter = "Q4";
		Optional<EPSNeighbourhoodCriminalOccurrences> optionalItem = manager.findBy_NeighbourhoodDescription_OccurrenceViolationTypeGroup_OccurrenceReportedYear_OccurrenceReportedQuarter(
				neighbourhoodDescription, 
				occurrenceViolationTypeGroup, 
				occurrenceReportedYear, 
				occurrenceReportedQuarter);
		assertEquals(true, optionalItem.isEmpty());
	}
}
