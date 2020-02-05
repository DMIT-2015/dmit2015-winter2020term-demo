package ca.nait.dmit.demo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ChatterServlet
 */
@WebServlet("/JitterGetAllServlet")
public class JitterGetAllServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JitterGetAllServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Retrieve the chatterMap from application scope
		ServletContext applicationContext = getServletContext();
		@SuppressWarnings("unchecked")
		List<Jitter> chatterList = (List<Jitter>) applicationContext.getAttribute(Constants.JITTERS_APPLICATION_SCOPE);

		// Iterate through each item in Map and print on a separate line the loginName, message, and postDate.
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		chatterList.stream()
			.forEach(currentChatter -> {
				out.println(currentChatter.getLoginName());
				out.println(currentChatter.getMessage());
				out.println(currentChatter.getPostedDate());
			});
		out.close();
				
	}

}
