package ca.edmonton.data.eps;

import lombok.Data;

@Data
public class EPSNeighbourhoodCriminalOccurrences {
	
	// Declare data fields for each column name in the CSV file 
	// EPS_Neighbourhood_Criminal_Occurrences.csv
	private String neighbourhood;
	private String type;
	private int year;
	private String quarter;
	private int month;
	private int occurrences;
	
}
