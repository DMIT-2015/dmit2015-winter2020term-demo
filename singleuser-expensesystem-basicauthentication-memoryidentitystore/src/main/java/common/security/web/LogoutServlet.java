package common.security.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.logout();	// Establish null as the value returned when getUserPrincipal, getRemoteUser, and getAuthType is called on the request.

		
		Cookie usernameCookie = new Cookie("username", null);
		usernameCookie.setMaxAge(0);			// delete this cookie
		response.addCookie(usernameCookie);	
		
		request.getSession().invalidate();	// Invalidates this session then unbinds any objects bound to it
		
		response.sendRedirect("/index.html");
	}

}
