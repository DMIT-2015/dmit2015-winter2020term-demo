package ca.nait.dmit.demo.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.nait.dmit.demo.servlet.LotteryCanada.LotteryType;

/**
 * Generate one set of LOTTO MAX quick pick numbers and write the numbers to the HTTP response.
 */
@WebServlet("/LottoQuickPickServlet01")
public class LottoQuickPickServlet01 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

	    // Construct a LotteryCanada object for LottoMAX
	    LotteryCanada lotto = new LotteryCanada(LotteryType.LOTTO_MAX);
	    // Generate one LOTTO MAX quick pick
	    Integer[] quickPickArray = lotto.doOneQuickPick();
	    // Print the quick pick numbers to the HTTP response
	    // Check if request is for HTML or Text content
		if (request.getContentType() == null || request.getContentType().equalsIgnoreCase("text/html") ) {
			response.setContentType("text/html");
		    out.print("<p>The LOTTO MAX quick pick numbers are: <strong>");
		    for(Integer num : quickPickArray) {
		    	out.print(num + " ");
		    }
		    out.println("</strong></p>");
			
		} else if (request.getContentType().equalsIgnoreCase("text/plain")) {
			response.setContentType("text/plain");
			StringBuilder stringBuilder = new StringBuilder();
			// Write the output in CSV data format where each value is separate by a comma
		    for(int index = 0; index < quickPickArray.length; index += 1) {
		    	stringBuilder.append(quickPickArray[index]);
		    	// Append a comma after element except for the last element
		    	if (index < (quickPickArray.length - 1)) {
		    		stringBuilder.append(",");
		    	}
		    }
		    out.print(stringBuilder.toString());
		} else if (request.getContentType().equalsIgnoreCase("application/json")) {
			response.setContentType("application/json");
			JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
			// Write the output in JSON data format as an array of numbers
			for(Integer num : quickPickArray) {
		    	arrayBuilder.add(num);
		    }
		    Json.createWriter(out).writeArray(arrayBuilder.build());
		}    
	    out.close();
	}
}
