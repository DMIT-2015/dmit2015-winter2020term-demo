package common.batch;

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
 * This Servlet is used to start a Batch Job using the XML job filename passed into the request.

curl -i -X GET 'http://localhost:8080/servlet/BatchClientServlet?filename=importCsvDataToDatabaseForScheduledPhotoEnforcemenZoneCentrePoint'

 */
@WebServlet("/servlet/BatchClientServlet")
public class BatchClientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String jobXMLName = request.getParameter("filename");

		if (!jobXMLName.isBlank()) {
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			try {
				JobOperator jobOperator = BatchRuntime.getJobOperator();
				long jobId = jobOperator.start(jobXMLName, null);
				JsonObject jsonResponseObject = Json.createObjectBuilder()
						.add("jobId", jobId)
						.build();
				out.println(jsonResponseObject.toString());
			} catch(JobStartException | JobSecurityException ex) {
				response.getWriter().println("Error submitting job! " + ex.getMessage());
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<strong>Request is missing parameter value for the XML filename of the Batch Job to start.");
			out.close();
		}
	}

}