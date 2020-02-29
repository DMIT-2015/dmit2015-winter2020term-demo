package demo.servlet;
import java.io.IOException;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.edmonton.data.eps.EPSNeighbourhoodCriminalOccurrences;
import ca.edmonton.data.eps.EPSNeighbourhoodCriminalOccurrencesManager;

/**
 * This servlet can process a GET and a POST request.
 * For a GET request it writes to the response a JSON array of neighbourhood names.
 * For a POST request when passed into the request a neighbourhood name it writes to the response 
 * a JSON object with one property that contains an array of JSON objects.
 
curl -i -X GET 'http://localhost:8080/servlet/LookupEPSNeighbourhoodCriminalOccurencesByNeighbourhoodDescriptionServlet' 
	
curl -i -X POST 'http://localhost:8080/servlet/LookupEPSNeighbourhoodCriminalOccurencesByNeighbourhoodDescriptionServlet' \
	-d 'neighbourhoodDescription=SPRUCE AVENUE' \
	-H 'Content-Type:application/x-www-form-urlencoded' 
	
 */
@WebServlet("/servlet/LookupEPSNeighbourhoodCriminalOccurencesByNeighbourhoodDescriptionServlet")
public class LookupEPSNeighbourhoodCriminalOccurencesByNeighbourhoodDescriptionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");			
		EPSNeighbourhoodCriminalOccurrencesManager manager = new EPSNeighbourhoodCriminalOccurrencesManager();
		List<String> neighbourhoods = manager.findDistinctNeighbourhoodDescription();
		Jsonb jsonb = JsonbBuilder.create();
		String responseBodyJson = jsonb.toJson(neighbourhoods);
		response.getWriter().println(responseBodyJson);			
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String neighbourhoodDescription = request.getParameter("neighbourhoodDescription");
		
		response.setContentType("application/json;charset=UTF-8");			
		if (!neighbourhoodDescription.isBlank()) {
			EPSNeighbourhoodCriminalOccurrencesManager manager = new EPSNeighbourhoodCriminalOccurrencesManager();
			List<EPSNeighbourhoodCriminalOccurrences> occurrences = manager.findOccurrencesByNeighbourhoodDescription(neighbourhoodDescription);

			// Use JSON-B API to convert Java object to a JSON formatted message
//			Jsonb jsonb = JsonbBuilder.create();
//			String responseBodyJson = jsonb.toJson(occurrences);
//			response.getWriter().println(responseBodyJson);		
			
			// Use JSON API to manually create a JSON object with a single property named "neighbourhoodOccurrences" 
			// that contains an array of JSON objects with EPSNeighbourhoodCriminalOccurrences data.
			JsonArrayBuilder occurrencesArrayBuilder = Json.createArrayBuilder();
			occurrences.forEach(singleOccurrence -> {
				JsonObject occurrenceJsonObject = Json.createObjectBuilder()
						.add("occurrenceViolationTypeGroup", singleOccurrence.getOccurrenceViolationTypeGroup())
						.add("occurrenceReportedYear", singleOccurrence.getOccurrenceReportedYear())
						.add("occurrenceReportedQuarter", singleOccurrence.getOccurrenceReportedQuarter())
						.add("occurrenceReportedMonth", singleOccurrence.getOccurrenceReportedMonth())
						.add("occurrences", singleOccurrence.getOccurrences())
						.build();		
				occurrencesArrayBuilder.add(occurrenceJsonObject);
				
			});
			JsonObject jsonObject = Json.createObjectBuilder()
				.add("neighbourhoodOccurrences", occurrencesArrayBuilder)
				.build();
			response.getWriter().println(jsonObject.toString());
			
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);			
		}
	}

}