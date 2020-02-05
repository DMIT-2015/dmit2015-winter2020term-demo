package ca.nait.dmit.demo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * Servlet implementation class ChatterServlet
 */
@WebServlet("/JitterPostServlet")
public class JitterPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JitterPostServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Retrieve parameter values passed into this request
		String loginName = request.getParameter("loginName");
		String message = request.getParameter("message");
		
		// Construct a new Chatter object and set the loginName and message
		Jitter newChatter = new Jitter();
		newChatter.setLoginName(loginName);
		newChatter.setMessage(message);
		
		Set<ConstraintViolation<Jitter>> constraintViolations = validate(newChatter);
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		constraintViolations.stream().forEach(item -> {
			out.println(item.getMessage());
		});
		out.close();
	}
	
	private Set<ConstraintViolation<Jitter>> validate(Jitter newChatter) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Jitter>> constraintViolations = validator.validate(newChatter);
		return constraintViolations;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Retrieve al parameter values passed into this request
		String loginName = request.getParameter("loginName");
		String message = request.getParameter("message");
		
		// Construct a new Chatter object and set the loginName and message
		Jitter newChatter = new Jitter();
		newChatter.setLoginName(loginName);
		newChatter.setMessage(message);
		
		Set<ConstraintViolation<Jitter>> constraintViolations = validate(newChatter);
		if (constraintViolations.size() == 0) {
			// Retrieve the list of chatters from application scope
			ServletContext applicationContext = getServletContext();
			@SuppressWarnings("unchecked")
			List<Jitter> chatterList = (List<Jitter>) applicationContext.getAttribute(Constants.JITTERS_APPLICATION_SCOPE);
			
			// Add the newChatter to chatterList
			chatterList.add(newChatter);
			
			// Store chatterMap in application scope
			applicationContext.setAttribute(Constants.JITTERS_APPLICATION_SCOPE, chatterList);			
			
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.println("<p>Jitter was posted successfully</p>");
			out.close();

		} else {
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.println("<p>Please fix the following input errors:</p>");
			out.println("<ul>");
			constraintViolations.stream().forEach(item -> out.println("<li>" + item.getMessage() + "</li>"));
			out.println("</ul>");
			out.close();
		}
	}

}
