package ca.edmonton.data.eps;

import lombok.Data;

/**
 * EPS Neighbourhood Criminal Occurrences
 * 
 * @author Sam Wu
 * @see https://dashboard.edmonton.ca/dataset/EPS-Neighbourhood-Criminal-Occurrences/xthe-mnvi
 */
@Data
public class EPSNeighbourhoodCriminalOccurrences {

	private String neighbourhoodDescription;
	private String occurrenceViolationTypeGroup;
	private int occurrenceReportedYear;
	private String occurrenceReportedQuarter;
	private int occurrenceReportedMonth;
	private int occurrences;

}