package review.exercise;
import java.io.IOException;
//import java.io.StringReader;

//import javax.json.Json;
import javax.json.JsonArray;
//import javax.json.JsonObject;
//import javax.json.JsonReader;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		ServletBeanValidationHelper validationHelper = new ServletBeanValidationHelper();
		
		response.setContentType("application/json;charset=UTF-8");			
		Survey currentSurvey = new Survey();
		JsonArray resultJsonArray =  validationHelper.validateRequestParameters(request, Survey.class, currentSurvey);
		if (resultJsonArray.size() > 0) {
			response.getWriter().println(resultJsonArray.toString());
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} else {
							
			response.setContentType("application/json;charset=UTF-8");
			Jsonb jsonb = JsonbBuilder.create();
			String responseBodyJson = jsonb.toJson(currentSurvey);
			// Write the JSON representation to the response			
			response.getWriter().println(responseBodyJson);
								
		}
	}

}