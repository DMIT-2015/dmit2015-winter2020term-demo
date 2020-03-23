package ca.edmonton.data.resource;


import java.net.URI;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
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

import ca.edmonton.data.entity.ScheduledPhotoEnforcementZoneCentrePoint;

/**
 * 
 * 	URI					Http Method		Request Body						Description
 * 	----------------	-----------		--------------------------------	------------------------------------------	
 *	/photopoints		POST			{"siteId":2775, "reasonCodes":"h"}	Create a new ScheduledPhotoEnforcementZoneCentrePoint
 * 	/photopoints		GET													Find all ScheduledPhotoEnforcementZoneCentrePoint
 * 	/photopoints/2615	GET													Find one ScheduledPhotoEnforcementZoneCentrePoint with a siteId of 2615
 * 	/photopoints/2775	PUT				{"speedLimit": 60}					Update the user identified by a siteId of 2775
 * 	/photopoints/7175	DELETE												Remove the ScheduledPhotoEnforcementZoneCentrePoint with a siteId of 7175
 * 
 * 
		
curl -i -X GET 'http://localhost:8080/webapi/photopoints' 

curl -i -X GET 'http://localhost:8080/webapi/photopoints/301' 
curl -i -X GET 'http://localhost:8080/webapi/photopoints/1' 

curl -i -X POST 'http://localhost:8080/webapi/photopoints' \
	-d '{"siteId":1234,"locationDescription":"NAIT HP Centre","speedLimit":50,"reasonCodes":"e","latitude":53.56811903909844,"longitude":-113.5014541645244}' \
	-H 'Content-Type:application/json'

curl -i -X POST 'http://localhost:8080/webapi/photopoints' \
	-d '{"siteId":1234,"locationDescription":"NAIT HP Centre","reasonCodes":"e","latitude":53.56811903909844,"longitude":-113.5014541645244}' \
	-H 'Content-Type:application/json'


curl -i -X PUT 'http://localhost:8080/webapi/photopoints/1234' \
	-d '{"siteId":1234,"locationDescription":"Updated NAIT HP Centre","speedLimit":30,"reasonCodes":"e,f","latitude":53.56811903909844,"longitude":-113.5014541645244}' \
	-H 'Content-Type:application/json'
	
curl -i -X DELETE 'http://localhost:8080/webapi/photopoints/123' 

 * 
 * 
 * @author Sam Wu
 *
 */

@ApplicationScoped		// This is a CDI-managed bean that is created only once during the life cycle of the application
@Path("photopoints")	// All methods of this class are associated this URL path
@Consumes(MediaType.APPLICATION_JSON)	// All methods this class accept only JSON format data 
@Produces(MediaType.APPLICATION_JSON)	// All methods returns data that has been converted to JSON format
public class ScheduledPhotoEnforcementZoneCentrePointResource {

	@PersistenceContext(unitName = "mssql-jpa-pu")	// The unitName is defined in persistence.xml
	private EntityManager entityManager;
	
	@GET	// This method only accepts HTTP GET requests.
	public Response findAll() {
		List<ScheduledPhotoEnforcementZoneCentrePoint> resultList = entityManager.createQuery(
			"FROM ScheduledPhotoEnforcementZoneCentrePoint e "
			, ScheduledPhotoEnforcementZoneCentrePoint.class)
			.getResultList();
		
		return Response.ok(resultList).build();
	}
	
	@GET 			// This method only accepts HTTP GET requests.
	@Path("{id}")	// This method accepts a path parameter and gives it a name of id
	public Response find(@PathParam("id") Short siteId) {
		// Find the ScheduledPhotoEnforcementZoneCentrePoint instance with primary key of siteId 
		ScheduledPhotoEnforcementZoneCentrePoint foundEntity = entityManager.find(ScheduledPhotoEnforcementZoneCentrePoint.class, siteId);
		
		if (foundEntity == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
		return Response.ok(foundEntity).build();
	}
	
	@POST			// This method only accepts HTTP POST requests.
	@Transactional	// This method requires a transaction to execute
	public Response add(@Valid ScheduledPhotoEnforcementZoneCentrePoint newScheduledPhotoEnforcementZoneCentrePoint, @Context UriInfo uriInfo) {
		
		// Check if the primary key is already used.  This is only required when primary is assigned instead of generated. 
		if (entityManager.find(ScheduledPhotoEnforcementZoneCentrePoint.class, newScheduledPhotoEnforcementZoneCentrePoint.getSiteId()) != null) {
			return Response
					.status(Response.Status.CONFLICT)
					.entity("Unable to create ScheduledPhotoEnforcementZoneCentrePoint, siteId is already used.")
					.build();
		}
		
		try {
			// Persist the new ScheduledPhotoEnforcementZoneCentrePoint into the database
			entityManager.persist(newScheduledPhotoEnforcementZoneCentrePoint);
		} catch (Exception ex) {
			// Return a HTTP status of "500 Internal Server Error" containing the exception message
			return Response.
					serverError()
					.entity(ex.getMessage())
					.build();
		}
		
		//userInfo is injected via @Context parameter to this method
		URI location = uriInfo.getAbsolutePathBuilder()
			.path(newScheduledPhotoEnforcementZoneCentrePoint.getSiteId().toString())
			.build();
		
		// Set the location path of the new ScheduledPhotoEnforcementZoneCentrePoint with its identifier
		// Returns an HTTP status of "201 Created" if the ScheduledPhotoEnforcementZoneCentrePoint was successfully persisted
		return Response
				.created(location)
				.build();
	}
	
	@PUT 			// This method only accepts HTTP PUT requests.
	@Path("{id}")	// This method accepts a path parameter and gives it a name of id
	@Transactional
	public Response update(@PathParam("id") Short siteId, @Valid ScheduledPhotoEnforcementZoneCentrePoint exisitingScheduledPhotoEnforcementZoneCentrePoint) {
		ScheduledPhotoEnforcementZoneCentrePoint foundEntity = entityManager.find(ScheduledPhotoEnforcementZoneCentrePoint.class, siteId);
		
		if (foundEntity == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
		foundEntity.setLocationDescription(exisitingScheduledPhotoEnforcementZoneCentrePoint.getLocationDescription());
		foundEntity.setSpeedLimit(exisitingScheduledPhotoEnforcementZoneCentrePoint.getSpeedLimit());
		foundEntity.setReasonCodes(exisitingScheduledPhotoEnforcementZoneCentrePoint.getReasonCodes());
		foundEntity.setLatitude(exisitingScheduledPhotoEnforcementZoneCentrePoint.getLatitude());
		foundEntity.setLongitude(exisitingScheduledPhotoEnforcementZoneCentrePoint.getLongitude());
		
		try {
			entityManager.merge(foundEntity);
		} catch (Exception ex) {
			// Return a HTTP status of "500 Internal Server Error" containing the exception message
			return Response.
					serverError()
					.entity(ex.getMessage())
					.build();
		}

		// Returns an HTTP status "204 No Content" if the ScheduledPhotoEnforcementZoneCentrePoint was successfully persisted		
		return Response.noContent().build();
	}

	@DELETE 		// This method only accepts HTTP DELETE requests.
	@Path("{id}")	// This method accepts a path parameter and gives it a name of id
	@Transactional
	public Response delete(@PathParam("id") Short siteId) {
		ScheduledPhotoEnforcementZoneCentrePoint foundEntity = entityManager.find(ScheduledPhotoEnforcementZoneCentrePoint.class, siteId);
		
		if (foundEntity == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
		try {
			entityManager.remove(foundEntity);	// Removes the ScheduledPhotoEnforcementZoneCentrePoint from being persisted
		} catch (Exception ex) {				
			// Return a HTTP status of "500 Internal Server Error" containing the exception message
			return Response						
					.serverError()
					.encoding(ex.getMessage())
					.build();
		}
		
		// Returns an HTTP status "204 No Content" if the ScheduledPhotoEnforcementZoneCentrePoint was successfully deleted
		return Response.noContent().build();	
	}

}
