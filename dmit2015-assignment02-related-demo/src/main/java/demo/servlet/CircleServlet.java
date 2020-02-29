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

/**
 * This servlet retrieves the radius of a radius and writes a Circle object in JSON format to the response.
 
curl -i -X POST 'http://localhost:8080/servlet/CircleServlet' \
	-d 'radius=0' \
	-H 'Content-Type:application/x-www-form-urlencoded' 
	
curl -i -X POST 'http://localhost:8080/servlet/CircleServlet' \
	-d 'radius=5' \
	-H 'Content-Type:application/x-www-form-urlencoded' 
	
 */
@WebServlet("/servlet/CircleServlet")
public class CircleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletBeanValidationHelper validationHelper = new ServletBeanValidationHelper();
		
		Circle currentCircle = new Circle();
		JsonArray resultJsonArray =  validationHelper.validateRequestParameters(request, Circle.class, currentCircle);
		if (resultJsonArray.size() > 0) {
			response.setContentType("application/json;charset=UTF-8");			
			response.getWriter().println(resultJsonArray.toString());
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} else {
							
			response.setContentType("application/json;charset=UTF-8");
			Jsonb jsonb = JsonbBuilder.create();
			String responseBodyJson = jsonb.toJson(currentCircle);		
			response.getWriter().println(responseBodyJson);
		}
	}

}