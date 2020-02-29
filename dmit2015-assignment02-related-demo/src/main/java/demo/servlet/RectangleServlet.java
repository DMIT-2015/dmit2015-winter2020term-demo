package demo.servlet;
import java.io.IOException;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.servlet.ServletBeanValidationHelper;

/**
 * This servlet retrieves the length and width of rectangleand writes a Rectangle object in JSON format to the response.
 
curl -i -X POST 'http://localhost:8080/servlet/RectangleServlet' \
	-d 'length=0' \
	-d 'width=0' \
	-H 'Content-Type:application/x-www-form-urlencoded' 
	
curl -i -X POST 'http://localhost:8080/servlet/RectangleServlet' \
	-d 'length=5' \
	-d 'width=10' \
	-H 'Content-Type:application/x-www-form-urlencoded' 
	
 */
@WebServlet("/servlet/RectangleServlet")
public class RectangleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletBeanValidationHelper validationHelper = new ServletBeanValidationHelper();
		
		Rectangle currentRectangle = new Rectangle();
		JsonArray resultJsonArray =  validationHelper.validateRequestParameters(request, Rectangle.class, currentRectangle);
		if (resultJsonArray.size() > 0) {
			response.setContentType("application/json;charset=UTF-8");			
			response.getWriter().println(resultJsonArray.toString());
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} else {
							
			response.setContentType("application/json;charset=UTF-8");
//			Jsonb jsonb = JsonbBuilder.create();
//			String responseBodyJson = jsonb.toJson(currentRectangle);
			JsonObject jsonResponseObject = Json.createObjectBuilder()
					.add("length", currentRectangle.getLength())
					.add("width", currentRectangle.getWidth())
					.add("area", currentRectangle.area())
					.add("perimeter", currentRectangle.perimeter())
					.add("diagonal", currentRectangle.diagonal())
					.build();
			response.getWriter().println(jsonResponseObject.toString());
		}
	}

}