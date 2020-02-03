package ca.edmonton.data.eps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EPSNeighbourhoodCriminalOccurrencesManager {

	private List<EPSNeighbourhoodCriminalOccurrences> criminalOccurences = new ArrayList<>();

	public EPSNeighbourhoodCriminalOccurrencesManager() throws IOException {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/data/EPS_Neighbourhood_Criminal_Occurrences.csv"))))	{			
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
		}
	}
	
	public List<String> findDistinctNeighbourhoodDescription() {
		return criminalOccurences.stream()
				.map(EPSNeighbourhoodCriminalOccurrences::getNeighbourhoodDescription)
				.distinct()
				.sorted()
				.collect(Collectors.toList());
	}

	public List<String> findDistinctOccurrenceViolationTypeGroup() {
		return criminalOccurences.stream()
				.map(EPSNeighbourhoodCriminalOccurrences::getOccurrenceViolationTypeGroup)
				.distinct()
				.collect(Collectors.toList());
	}

	public List<Integer> findDistinctOccurrenceReportedYear() {
		return criminalOccurences.stream()
				.map(EPSNeighbourhoodCriminalOccurrences::getOccurrenceReportedYear)
				.distinct()
				.collect(Collectors.toList());
	}

	public List<String> findDistinctOccurrenceReportedQuarter() {
		return criminalOccurences.stream()
				.map(EPSNeighbourhoodCriminalOccurrences::getOccurrenceReportedQuarter)
				.distinct()
				.collect(Collectors.toList());
	}

	public Optional<EPSNeighbourhoodCriminalOccurrences> findBy_NeighbourhoodDescription_OccurrenceViolationTypeGroup_OccurrenceReportedYear_OccurrenceReportedQuarter(
			String neighbourhoodDescription, String occurrenceViolationTypeGroup, int occurrenceReportedYear, String occurrenceReportedQuarter) {
		return criminalOccurences.stream()
				.filter(item -> item.getNeighbourhoodDescription().equalsIgnoreCase(neighbourhoodDescription))
				.filter(item -> item.getOccurrenceViolationTypeGroup().equalsIgnoreCase(occurrenceViolationTypeGroup))
				.filter(item -> item.getOccurrenceReportedYear() == occurrenceReportedYear)
				.filter(item -> item.getOccurrenceReportedQuarter().equalsIgnoreCase(occurrenceReportedQuarter))
				.findFirst();
				
	}
}
