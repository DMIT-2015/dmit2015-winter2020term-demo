package ca.edmonton.data.resource;

import java.io.IOException;
import java.io.PrintWriter;
//import java.net.URI;
//import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

//import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 * Servlet implementation class PhotoEnforcementZoneResourceServletClient
 * 
curl -i -X GET 'http://localhost:8080/servlet/PhotoEnforcementZoneResourceServletClient'

 */
@WebServlet("/servlet/PhotoEnforcementZoneResourceServletClient")
public class PhotoEnforcementZoneResourceServletClient extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@Inject
	@RestClient
	PhotoEnforcementZoneResourceClient apiClient;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		
//		try {
//			PhotoEnforcementZoneResourceClient apiClient = RestClientBuilder.newBuilder()
//				.baseUri(new URI("http://localhost:8080/webapi"))
//				.connectTimeout(2, TimeUnit.SECONDS)
//				.readTimeout(2,  TimeUnit.SECONDS)
//				.build(PhotoEnforcementZoneResourceClient.class);
			
//			apiClient.findAllPhotoEnforcementZones().forEach(System.out::println);
			
			PrintWriter out = response.getWriter();
			// Create a new zone
			JsonObject newPhotoEnforcementZone = Json.createObjectBuilder()
				.add("locationDescription", "NAIT HP Center")
				.add("speedLimit", 50)
				.add("reasonCodes", "e,f")
				.build();
			
			Response createResult = apiClient.postJsonPhotoEnforcementZone(newPhotoEnforcementZone);
			Long pezId = createResult.readEntity(Long.class);
			out.println("Id: " + pezId);
			
			// Update an existing zone
			JsonObject updatePhotoEnforcementZone = Json.createObjectBuilder()
					.add("locationDescription", "Updated NAIT HP Center")
					.add("speedLimit", 40)
					.add("reasonCodes", "e")
					.build();
			Response updateResult = apiClient.updatePhotoEnforcementZone(pezId, updatePhotoEnforcementZone);
			out.println( updateResult.getStatus());

			// Find an existing zone
			JsonObject singleResult = apiClient.findOneZone(10L);
			out.println( singleResult );

			// Delete an exisitng zone
			Response deleteResult = apiClient.deletePhotoEnforcementZone(pezId);
			out.println( deleteResult.getStatus());
						
			// Find all zones
			out.println( apiClient.findAllPhotoEnforcementZones() );
			
//		} catch(Exception ex) {
//			ex.printStackTrace();
//		}
	}

}
