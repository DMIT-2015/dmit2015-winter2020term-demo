package demo.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.json.JsonArray;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.servlet.ServletBeanValidationHelper;
import demo.validation.Employee;

/**
 * Servlet implementation class AddEmployeeServlet
 * 
 * 
curl -i -X GET 'http://localhost:8080/servlet/AddEmployeeServlet?age=22' -H Content-type:application/json

curl -i -X GET 'http://localhost:8080/servlet/AddEmployeeServlet?age=99' -H Content-type:application/json

 */
@WebServlet("/servlet/AddEmployeeServlet")
public class AddEmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletBeanValidationHelper validationHelper = new ServletBeanValidationHelper();
		
		JsonArray resultJsonArray = validationHelper.validateRequestParameters(request, Employee.class, new Employee());
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
		
		Employee newEmployee = new Employee();
		JsonArray resultJsonArray = validationHelper.validateRequestParameters(request, Employee.class, newEmployee);
		
		if (resultJsonArray.size() > 0) {
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().println(resultJsonArray.toString());
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} else {
							
			response.setContentType("application/json;charset=UTF-8");
			PrintWriter out = response.getWriter();
			Jsonb jsonb = JsonbBuilder.create();
			String responseBodyJson = jsonb.toJson(newEmployee);		
			out.println(responseBodyJson);
			out.close();
		}
	}

}
