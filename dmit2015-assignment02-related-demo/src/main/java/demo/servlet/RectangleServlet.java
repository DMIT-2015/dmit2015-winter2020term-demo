package demo.servlet;
import java.io.IOException;
//import java.io.StringReader;
import java.text.NumberFormat;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
//import javax.json.JsonReader;
//import javax.json.bind.Jsonb;
//import javax.json.bind.JsonbBuilder;
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
	-d 'length=24' \
	-d 'width=60' \
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

			/*	Problem: 	How to create a JSON representation to include more data than values obtained using the getters of the Java object?
			 * 				The methods to get the area, perimeter, and diagonal of Rectangle does not start with a get prefix.
			 * 
			 * 	Solution 1:	Use the JSON Processing (JSON-P) API object model to create a JsonObject
			 */
			NumberFormat nf = NumberFormat.getNumberInstance();
			nf.setMaximumFractionDigits(2);
			String formattedAreaMessage = "Area = " + nf.format(currentRectangle.area());
			String formattedPerimeterMessage = "Perimeter = " + nf.format(currentRectangle.perimeter());
			String formattedDiagonalMessage = "Diagonal = " + nf.format(currentRectangle.diagonal());

			// Use the JSON Processing (JSON-P) API object model to create a JsonObject
			// https://javaee.github.io/jsonp/
			JsonObject jsonResponseObject = Json.createObjectBuilder()
				.add("length", currentRectangle.getLength())
				.add("width", currentRectangle.getWidth())
				.add("area", currentRectangle.area())
				.add("perimeter", currentRectangle.perimeter())
				.add("diagonal", currentRectangle.diagonal())
				.add("areaMessage", formattedAreaMessage)
				.add("perimeterMessage", formattedPerimeterMessage)
				.add("diagonalMessage", formattedDiagonalMessage)
				.build();

			// Write the JSON representation to the response
			response.getWriter().println(jsonResponseObject.toString());

/**
 * 
			// Solution 2: You can also use a combination of JSON-B and JSON-P to create a JsonObject
			Jsonb jsonb = JsonbBuilder.create();
			String responseBodyJson = jsonb.toJson(currentRectangle);
			// The area, perimeter, and diagonal of the currentRectangle are NOT included in the JSON representation since those methods do NOT have a get prefix
			// To add the area, perimeter, and diagonal values to the JSON representation:			
			
			// STEP 1: Convert JSON representation to a JsonObject using JsonbBuilder.create().fromJson() method 
			JsonObject jsonResponseObject = jsonb.fromJson(responseBodyJson, JsonObject.class);
			// STEP 1 ALTERNATIVE: Convert JSON representation to a JsonObject using Json.createReader().readObject() mnethod
//			JsonReader jsonReader = Json.createReader(new StringReader(responseBodyJson));
//			JsonObject jsonResponseObject2 = jsonReader.readObject();

			// STEP 2: Use JSON Patch API to add a value to the JsonObject
			jsonResponseObject = Json.createPatchBuilder()
				.add("/area", Json.createValue(currentRectangle.area()))
				.add("/perimeter", Json.createValue(currentRectangle.perimeter()))
				.add("/diagonal", Json.createValue(currentRectangle.diagonal()))
				.add("/areaMessage", formattedAreaMessage)
				.add("/perimeterMessage", formattedPerimeterMessage)
				.add("/diagonalMessage", formattedDiagonalMessage)
				.build()
				.apply(jsonResponseObject);
			response.getWriter().println(jsonResponseObject.toString());

			// STEP 2 ALTERNATIVE: Use JSON-Pointer API to add a value to the JsonObject
			jsonResponseObject = Json.createPointer("/area")
					.add(jsonResponseObject, Json.createValue(currentRectangle.area()));
			jsonResponseObject = Json.createPointer("/perimeter")
					.add(jsonResponseObject, Json.createValue(currentRectangle.perimeter()));
			jsonResponseObject = Json.createPointer("/diagonal")
					.add(jsonResponseObject, Json.createValue(currentRectangle.diagonal()));
			jsonResponseObject = Json.createPointer("/areaMessage")
					.add(jsonResponseObject, Json.createValue(formattedAreaMessage));
			jsonResponseObject = Json.createPointer("/perimeterMessage")
					.add(jsonResponseObject, Json.createValue(formattedPerimeterMessage));
			jsonResponseObject = Json.createPointer("/diagonalMessage")
					.add(jsonResponseObject, Json.createValue(formattedDiagonalMessage));
			response.getWriter().println(jsonResponseObject.toString());
			

*/									
					
		}
	}

}