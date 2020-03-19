package northwind.resource;

import java.net.URI;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import northwind.entity.Shipper;
import northwind.repository.ShipperRepository;

/**
 * 
 
 
curl -i -X GET 'http://localhost:8080/webapi/shippers' 

curl -i -X POST 'http://localhost:8080/webapi/shippers' \
	-d '{"companyName":"Canada Post","phone":"(416) 123-1234"}' \
	-H 'Content-Type:application/json'

curl -i -X PUT 'http://localhost:8080/webapi/shippers/10' \
	-d '{"companyName":"DMIT Post","phone":"(789) 378-6140"}' \
	-H 'Content-Type:application/json'
	
curl -i -X GET 'http://localhost:8080/webapi/shippers/10' 

curl -i -X DELETE 'http://localhost:8080/webapi/shippers/10' 

curl -i -X DELETE 'http://localhost:8080/webapi/shippers/4' 

 * 
 * @author samwu
 *
 */

@ApplicationScoped		// This is a CDI-managed bean that is created only once during the life cycle of the application
@Path("shippers")	// All methods of this class are associated this URL path
@Consumes(MediaType.APPLICATION_JSON)	// All methods this class accept only JSON format data 
@Produces(MediaType.APPLICATION_JSON)	// All methods returns data that has been converted to JSON format
public class ShipperResource {

	@Inject
	private ShipperRepository shipperRepository;
	
	@GET	// This method only accepts HTTP GET requests.
	public Response listShippers() {
		return Response.ok(shipperRepository.findAll()).build();
	}

	@Path("{id}")
	@GET	// This method only accepts HTTP GET requests.
	public Response findShipperById(@PathParam("id") int shipperID) {
		Shipper foundEntity = shipperRepository.findById(shipperID);
		
		if (foundEntity == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
		return Response.ok(foundEntity).build();
	}
		
	@POST			// This method only accepts HTTP POST requests.
	public Response addShipper(@Valid Shipper newShipper, @Context UriInfo uriInfo) {
		
		try {
			// Persist the new Shippper into the database
			shipperRepository.add(newShipper);
		} catch (Exception ex) {
			// Return a HTTP status of "500 Internal Server Error" containing the exception message
			return Response.
					serverError()
					.entity(ex.getMessage())
					.build();
		}
		
		//userInfo is injected via @Context parameter to this method
		URI location = uriInfo.getAbsolutePathBuilder()
			.path(newShipper.getShipperID() + "")
			.build();
		
		// Set the location path of the new entity with its identifier
		// Returns an HTTP status of "201 Created" if the Shipper was successfully persisted
		return Response
				.created(location)
				.build();
	}
	
	@PUT 			// This method only accepts HTTP PUT requests.
	@Path("{id}")	// This method accepts a path parameter and gives it a name of id
	public Response updateShipper(@PathParam("id") int shipperID, @Valid Shipper existingShipper) {
		Shipper foundEntity = shipperRepository.findById(shipperID);
		
		if (foundEntity == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
				
		foundEntity.setCompanyName(existingShipper.getCompanyName());
		foundEntity.setPhone(existingShipper.getPhone());
		
		try {
			shipperRepository.update(foundEntity);
		} catch (Exception ex) {
			// Return a HTTP status of "500 Internal Server Error" containing the exception message
			return Response.
					serverError()
					.entity(ex.getMessage())
					.build();
		}

		// Returns an HTTP status "204 No Content" if the update was successfully persisted		
		return Response.noContent().build();
	}

	@DELETE 				// This method only accepts HTTP DELETE requests.
	@Path("{id}")	// This method accepts a path parameter and gives it a name of id
	public Response delete(@PathParam("id") int shipperID) {
		Shipper foundEntity = shipperRepository.findById(shipperID);
		
		if (foundEntity == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
		try {
			shipperRepository.remove(foundEntity);	// Removes the Shipper from being persisted
		} catch (Exception ex) {				
			// Return a HTTP status of "500 Internal Server Error" containing the exception message
			return Response						
					.serverError()
					.encoding(ex.getMessage())
					.build();
		}
		
		// Returns an HTTP status "204 No Content" if the Shipper was successfully deleted
		return Response.noContent().build();	
	}

}
