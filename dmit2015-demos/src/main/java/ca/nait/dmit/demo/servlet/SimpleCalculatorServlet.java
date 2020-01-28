package ca.nait.dmit.demo.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SimpleCalculatorServlet
 */
@WebServlet("/SimpleCalculatorServlet")
public class SimpleCalculatorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SimpleCalculatorServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String num1 = request.getParameter("num1");
		String num2 = request.getParameter("num2");
		String operator = request.getParameter("operator");
		String outputOptions = request.getParameter("outputResultOptions");

		double result = 0;
		try {
			switch(operator) {
			case "+": 
				result = Double.parseDouble(num1) + Double.parseDouble(num2);
				break;
			case "-": 
				result = Double.parseDouble(num1) - Double.parseDouble(num2);
				break;
			case "*": 
				result = Double.parseDouble(num1) * Double.parseDouble(num2);
				break;
			case "/": 
				result = Double.parseDouble(num1) / Double.parseDouble(num2);
				break;
			}
		} catch(NumberFormatException ex) {
//			out.println("Both parameters must be numbers");
		}

		PrintWriter out = response.getWriter();
		switch(outputOptions.toLowerCase()) {
		case "html":
			response.setContentType("text/html");
			out.println("<html>");
	        out.println("<head>");
	        out.println("<title>SimpleCalculator Results</title>");            
	        out.println("</head>");
	        out.println("<body>");
	        out.println("<h1>" + num1 + " " + operator + " " + num2 + " = " + result + "</h1>");
	        out.println("<br/>");
	        out.println("</body>");
	        out.println("</html>");
			break;
		case "text":
			response.setContentType("text/plain");
			out.println(num1 + "," + operator + "," + num2 + "," + result);
			break;
		case "json":
			response.setContentType("application/json");
			JsonObject jsonResult = Json.createObjectBuilder()
					.add("num1", num1)
					.add("operator", operator)
					.add("num2", num2)
					.add("result", result)
					.build();
			out.print(jsonResult);
			break;
		default:
			response.setContentType("text/html");
			out.println("<html>");
		    out.println("<head>");
		    out.println("<title>SimpleCalculator Results</title>");            
		    out.println("</head>");
		    out.println("<body>");
		    out.println("<h1>Unable to process request</h1>");
		    out.println("<br/>");
		    out.println("</body>");
		    out.println("</html>");
		    break;				
		}
		
			
	}

}
