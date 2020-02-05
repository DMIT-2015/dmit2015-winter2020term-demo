package ca.nait.dmit.demo.servlet;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class ChatterServletContextListener
 *
 */
@WebListener
public class JitterServletContextListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public JitterServletContextListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
    	System.out.println("Web application is shutting down.");
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
    	System.out.println("Web application is starting.");
    	
    	List<Jitter> chatterList = new ArrayList<>();
    	ServletContext applicationContext = sce.getServletContext();
    	applicationContext.setAttribute(Constants.JITTERS_APPLICATION_SCOPE, chatterList);
    }
	
}
