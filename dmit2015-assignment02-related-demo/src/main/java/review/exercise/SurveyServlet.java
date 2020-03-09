package review.exercise;
import java.io.IOException;
//import java.io.StringReader;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import javax.json.Json;
//import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
//import javax.json.JsonObject;
//import javax.json.JsonReader;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang3.math.NumberUtils;

/**
 
curl -i -X POST 'http://localhost:8080/servlet/SurveyServlet' \
	-d 'name=Java One' \
	-d 'age=67' \
	-d 'gender=MALE' \
	-d 'operatingSystemsUsed=Windows' \
	-d 'operatingSystemsUsed=Linux' \
	-d 'languagesUsed=cs' \
	-d 'languagesUsed=java' \
	-d 'languagesUsed=swift' \
	-H 'Content-Type:application/x-www-form-urlencoded' 
	
 */
@WebServlet("/servlet/SurveyServlet")
public class SurveyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");			

		// Retrieve all form field parameters passed into this request
		String nameParam = request.getParameter("name");
		String ageParam = request.getParameter("age");
		String genderParam = request.getParameter("gender");
		String[] osParams = request.getParameterValues("operatingSystemsUsed");
		String[] languageParams = request.getParameterValues("languagesUsed");

		// Create a Survey object and initialize it with the request parameter values
		Survey currentSurvey = new Survey();
		currentSurvey.setName(nameParam);
	 	if (NumberUtils.isParsable(ageParam) ) {
	 		currentSurvey.setAge( Integer.parseInt(ageParam) );
	 	}
	 	currentSurvey.setGender(Gender.valueOf(genderParam));
	 	// Convert the array of param values to a CSV  
	 	currentSurvey.setOperatingSystemsUsed( Arrays.asList( osParams ).stream().collect(Collectors.joining(",")));
	 	currentSurvey.setLanguagesUsed( Arrays.asList( languageParams ).stream().collect(Collectors.joining(",")));
		
	 	// Validate all constraints of the Survey object and construct an array of JSON object for each validation error.
	 	JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
	 	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Survey>> constraintViolations = validator.validate(currentSurvey);
		// For each ConstraintViolation create a JsonObject with field name and validation error message and add it to jsonArrayArrayBuilder
		constraintViolations.stream().forEach(singleConstraintViolation -> {
			// For class-level constraints use the class name instead of the field name for the errory key 
			String errorKey = !singleConstraintViolation.getPropertyPath().toString().isBlank() ? singleConstraintViolation.getPropertyPath().toString() : singleConstraintViolation.getRootBeanClass().getName();
			String errorValue = singleConstraintViolation.getMessage();
			JsonObject constraintJsonObject = Json.createObjectBuilder()
				.add(errorKey, errorValue)
				.build();
				jsonArrayBuilder.add(constraintJsonObject);
			});
		JsonArray resultJsonArray = jsonArrayBuilder.build();

		// You can use ServletBeanValidationHelper class to perform the same task above if you modify it to handle the Gender enum as follows:
//		ServletBeanValidationHelper validationHelper = new ServletBeanValidationHelper();		
//		Survey currentSurvey = new Survey();
//		JsonArray resultJsonArray =  validationHelper.validateRequestParameters(request, Survey.class, currentSurvey);
		
		if (resultJsonArray.size() > 0) {
			response.getWriter().println(resultJsonArray.toString());
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} else {
			// Convert the currenySuvery object into String in JSON format
			
			// You can use Jsonb to convert a Java object into a String in JSON format as follows:
//			Jsonb jsonb = JsonbBuilder.create();
//			String responseBodyJson = jsonb.toJson(currentSurvey);	// create property/value pairs using the getters of the Java object
			
			// Jsonb maybe include more or less name/value pairs then you need in your JSON string.
			// In this case is better to use the JSON Processing (JSON-P) API object model to create a JsonObject
			// https://javaee.github.io/jsonp/
			JsonObject jsonResponseObject = Json.createObjectBuilder()
				.add("name", currentSurvey.getName())
				.add("age", currentSurvey.getAge())
				.add("gender", currentSurvey.getGender().toString())
				.add("operatingSystemsUsed", currentSurvey.getOperatingSystemsUsed())
				.add("languagesUsed", currentSurvey.getLanguagesUsed())
				.build();
			String responseBodyJson = jsonResponseObject.toString();
			
			// Write the JSON representation to the response			
			response.getWriter().println(responseBodyJson);
								
		}
	}

}