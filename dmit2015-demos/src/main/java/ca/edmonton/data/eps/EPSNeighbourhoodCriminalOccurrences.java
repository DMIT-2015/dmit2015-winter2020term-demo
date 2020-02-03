package ca.edmonton.data.eps;

import lombok.Data;

@Data
public class EPSNeighbourhoodCriminalOccurrences {
//	https://dashboard.edmonton.ca/dataset/EPS-Neighbourhood-Criminal-Occurrences/xthe-mnvi
	private String neighbourhoodDescription;
	private String occurrenceViolationTypeGroup;
	private int occurrenceReportedYear;
	private String occurrenceReportedQuarter;
	private int occurrenceReportedMonth;
	private int occurrences;

}
