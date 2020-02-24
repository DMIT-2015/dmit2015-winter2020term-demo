package demo.servlet;

import java.io.IOException;

import javax.json.JsonArray;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.servlet.ServletBeanValidationHelper;
import demo.validation.Job;

/**
 * Servlet implementation class AddJobServlet
 * 
 * 
curl -i -X GET 'http://localhost:8080/servlet/CreateJobServlet?jobId=IT&jobTitle=Programmer&minimumSalary=5000&maximumSalary=2000' -H Content-type:application/json

curl -i -X GET 'http://localhost:8080/servlet/CreateJobServlet?jobId=IT_PROG&jobTitle=Programmer&minimumSalary=2000&maximumSalary=5000' -H Content-type:application/json

curl -i -X POST 'http://localhost:8080/servlet/CreateJobServlet' \
	-d 'jobId=IT_PROG' \
	-d 'jobTitle=Programmer' \
	-d 'minimumSalary=2000' \
	-d 'maximumSalary=5000' \
	-H 'Content-Type:application/x-www-form-urlencoded' 
	
 */
@WebServlet("/servlet/CreateJobServlet")
public class CreateJobServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletBeanValidationHelper validationHelper = new ServletBeanValidationHelper();
		
		JsonArray resultJsonArray = validationHelper.validateRequestParameters(request, Job.class, new Job());
		if (resultJsonArray.size() > 0) {
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().println(resultJsonArray.toString());
		} else {
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletBeanValidationHelper validationHelper = new ServletBeanValidationHelper();
		
		Job newJob = new Job();
		JsonArray resultJsonArray = validationHelper.validateRequestParameters(request, Job.class, newJob);
		
		if (resultJsonArray.size() > 0) {
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().println(resultJsonArray.toString());
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} else {
							
			response.setContentType("application/json;charset=UTF-8");
			Jsonb jsonb = JsonbBuilder.create();
			String responseBodyJson = jsonb.toJson(newJob);		
			response.getWriter().println(responseBodyJson.toString());
		}
	}

}
