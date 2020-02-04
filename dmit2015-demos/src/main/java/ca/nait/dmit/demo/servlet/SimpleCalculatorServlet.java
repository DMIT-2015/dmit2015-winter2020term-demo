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
		String operand1 = request.getParameter("operand1");
		String operand2 = request.getParameter("operand2");
		String operation = request.getParameter("operation");
		String outputOptions = request.getParameter("outputResultOptions");

		PrintWriter out = response.getWriter();
		
		SimpleCalculator calculator = new SimpleCalculator();
		try {
			calculator.setOperand1(Double.parseDouble(operand1));
			calculator.setOperand2(Double.parseDouble(operand2));

			switch(operation) {
			case "+": 
				calculator.add();
				break;
			case "-": 
				calculator.subtract();
				break;
			case "*": 
				calculator.multiply();
				break;
			case "/": 
				calculator.divide();
				break;
			}
		} catch(NumberFormatException ex) {
			if (outputOptions.toLowerCase().equals("samehtml")) {
				String message = String.format("Error processing request with exception: %s", ex.getMessage());
				request.setAttribute("errorMessage", message);
				getServletContext()
					.getRequestDispatcher("/demo/servlet/SimpleCalculator.html")
					.forward(request, response);
			} else {
				response.setContentType("text/html");
				out.println("<html>");
			    out.println("<head>");
			    out.println("<title>SimpleCalculator Results</title>");            
			    out.println("</head>");
			    out.println("<body>");
			    out.println("<h1>Error processing request: " + ex.getMessage() + "</h1>");
			    out.println("<br/>");
			    out.println("</body>");
			    out.println("</html>");				
			}
		}

		switch(outputOptions.toLowerCase()) {
		case "html":
			response.setContentType("text/html");
			out.println("<html>");
	        out.println("<head>");
	        out.println("<title>SimpleCalculator Results</title>");            
	        out.println("</head>");
	        out.println("<body>");
	        out.println("<h1>" + operand1 + " " + operation + " " + operand2 + " = " + calculator.getResult() + "</h1>");
	        out.println("<br/>");
	        out.println("</body>");
	        out.println("</html>");
			break;
		case "text":
			response.setContentType("text/plain");
			out.println(operand1 + "," + operation + "," + operand2 + "," + calculator.getResult());
			break;
		case "json":
			response.setContentType("application/json");
			JsonObject jsonResult = Json.createObjectBuilder()
					.add("operand1", operand1)
					.add("operation", operation)
					.add("operand2", operand2)
					.add("result", calculator.getResult())
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
