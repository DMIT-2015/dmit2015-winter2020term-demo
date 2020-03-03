package ca.edmonton.data.batch;

import java.io.IOException;
import java.io.PrintWriter;

import javax.batch.operations.JobOperator;
import javax.batch.operations.JobSecurityException;
import javax.batch.operations.JobStartException;
import javax.batch.runtime.BatchRuntime;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PhotoEnforcementZoneCsvToJsonBatchClientServlet

curl -i -X GET 'http://localhost:8080/servlet/PhotoEnforcementZoneCsvToJsonBatchClientServlet' 

 */
@WebServlet("/servlet/PhotoEnforcementZoneCsvToJsonBatchClientServlet")
public class PhotoEnforcementZoneCsvToJsonBatchClientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		try {
			JobOperator jobOperator = BatchRuntime.getJobOperator();
			long jobId = jobOperator.start("convertCsvToJsonJobForPhotoEnforcementZone", // jslXMLFilename without .xml file extension
					null);
			JsonObject jsonResponseObject = Json.createObjectBuilder()
					.add("jobId", jobId)
					.build();
			out.println(jsonResponseObject.toString());
		} catch(JobStartException | JobSecurityException ex) {
			response.getWriter().println("Error submitting job! " + ex.getMessage());
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

}
