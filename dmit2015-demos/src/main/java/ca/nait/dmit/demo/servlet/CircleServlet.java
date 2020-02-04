package ca.nait.dmit.demo.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.nait.dmit.domain.Circle;

/**
 * Servlet implementation class CircleServlet
 */
@WebServlet("/CircleServlet")
public class CircleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CircleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Retrieve the form submission query parameter for radius
		String radiusString = request.getParameter("radius");
		// Convert the radius from a String type to an double type
		double radius = Double.parseDouble(radiusString);
		// Construct a new Circle instance
		Circle currentCircle = new Circle();
		// Set the radius of the currentCircle
		currentCircle.setRadius(radius);
		
		// Send HTTP response with a HTML message containing the area, circumference, and diameter of the circle
		PrintWriter writer = response.getWriter();
		response.setContentType("text/html");
		writer.write("<h1>Calculation Results</h1>");
		
		String areaMessage = String.format("The area for a circle with a radius of %.1f is %.2f", 
				currentCircle.getRadius(), currentCircle.getArea());
		writer.write("<p>" + areaMessage + "</p>");
		
		String circumferenceMessage = String.format("The circumference for a circle with a radius of %.1f is %.2f", 
				currentCircle.getRadius(), currentCircle.getCircumference());
		writer.write("<p>" + circumferenceMessage + "</p>");
		
		String diameterMessage = String.format("The diameter for a circle with a radius of %.1f is %.2f", 
				currentCircle.getRadius(), currentCircle.getDiameter() );
		writer.write("<p>" + diameterMessage + "</p>");
		
		writer.close();
		
	}

}
