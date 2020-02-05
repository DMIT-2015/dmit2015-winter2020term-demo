package ca.nait.dmit.demo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ChatterServlet
 */
@WebServlet("/JitterGetByLoginNameServlet")
public class JitterGetByLoginNameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JitterGetByLoginNameServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Retrieve the loginName parameter that is passed into this request
		String loginName = request.getParameter("loginName");
		
		// Retrieve the chatterMap from application scope
		ServletContext applicationContext = getServletContext();
		@SuppressWarnings("unchecked")
		List<Jitter> chatterList = (List<Jitter>) applicationContext.getAttribute(Constants.JITTERS_APPLICATION_SCOPE);
		List<Jitter> loginNameChatterList = chatterList.stream()
				.filter(currentChatter -> currentChatter.getLoginName().equalsIgnoreCase(loginName))
				.collect(Collectors.toList());
		
		// Iterate through each item in Map and print on a separate line the loginName, message, and postDate.
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		loginNameChatterList.stream()
			.forEach(currentChatter -> {
			out.println(currentChatter.getLoginName());
			out.println(currentChatter.getMessage());
			out.println(currentChatter.getPostedDate());
		});
		out.close();
		
	}

}
