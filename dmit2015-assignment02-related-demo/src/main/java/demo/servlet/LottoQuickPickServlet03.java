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
 * Generate a set of LOTTO quick pick numbers and write the numbers to the HTTP response as a JSON message.
 
curl -i -X POST 'http://localhost:8080/servlet/LottoQuickPickServlet03' \
	-d 'game=LOTTO_MAX' \
	-d 'quantity=2' \
	-H 'Content-Type:application/x-www-form-urlencoded' 
	
curl -i -X POST 'http://localhost:8080/servlet/LottoQuickPickServlet03' \
	-d 'game=LOTTO_649' \
	-d 'quantity=5' \
	-H 'Content-Type:application/x-www-form-urlencoded' 
	
 */
@WebServlet("/servlet/LottoQuickPickServlet03")
public class LottoQuickPickServlet03 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletBeanValidationHelper validationHelper = new ServletBeanValidationHelper();
		
		LottoQuickPick quickPick = new LottoQuickPick();
		JsonArray resultJsonArray =  validationHelper.validateRequestParameters(request, LottoQuickPick.class, quickPick);
		if (resultJsonArray.size() > 0) {
			response.setContentType("application/json;charset=UTF-8");			
			response.getWriter().println(resultJsonArray.toString());
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} else {
							
			response.setContentType("application/json;charset=UTF-8");
			Jsonb jsonb = JsonbBuilder.create();
			String responseBodyJson = jsonb.toJson(quickPick);		
			response.getWriter().println(responseBodyJson);
		}
	}

}