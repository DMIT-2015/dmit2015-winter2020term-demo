package ca.edmonton.data.resource;

import java.net.URI;
import java.util.List;

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

import ca.edmonton.data.jpa.ScheduledPhotoEnforcementZoneCentrePoint;

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
	-d '{"locationDescription":"Updated NAIT HP Centre","speedLimit":30,"reasonCodes":"e,f","latitude":53.56811903909844,"longitude":-113.5014541645244}' \
	-H 'Content-Type:application/json'
	
curl -i -X DELETE 'http://localhost:8080/webapi/photopoints/123' 

 * 
 * 
 * @author Sam Wu
 *
 */
		
@Path("photopoints")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ScheduledPhotoEnforcementZoneCentrePointResource {

	@PersistenceContext(unitName = "mssql-jpa-pu")
	private EntityManager entityManager;
	
	@GET
	public Response findAll() {
		List<ScheduledPhotoEnforcementZoneCentrePoint> resultList = entityManager.createQuery(
			"FROM ScheduledPhotoEnforcementZoneCentrePoint e "
			, ScheduledPhotoEnforcementZoneCentrePoint.class)
			.getResultList();
		
		return Response.ok(resultList).build();
	}
	
	@GET @Path("{id}")
	public Response find(@PathParam("id") Short siteId) {
		ScheduledPhotoEnforcementZoneCentrePoint foundEntity = entityManager.find(ScheduledPhotoEnforcementZoneCentrePoint.class, siteId);
		
		if (foundEntity == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
		return Response.ok(foundEntity).build();
	}
	
	@POST
	@Transactional
	public Response add(@Valid ScheduledPhotoEnforcementZoneCentrePoint newScheduledPhotoEnforcementZoneCentrePoint, @Context UriInfo uriInfo) {
		
		entityManager.persist(newScheduledPhotoEnforcementZoneCentrePoint);
		
		//userInfo is injected via @Context parameter to this method
		URI location = uriInfo.getAbsolutePathBuilder()
			.path(newScheduledPhotoEnforcementZoneCentrePoint.getSiteId().toString())
			.build();
		
		return Response.created(location).build();
	}
	
	@PUT @Path("{id}")
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
		entityManager.merge(foundEntity);
		
		return Response.ok().build();
	}

	@DELETE @Path("{id}")
	@Transactional
	public Response delete(@PathParam("id") Short siteId) {
		ScheduledPhotoEnforcementZoneCentrePoint foundEntity = entityManager.find(ScheduledPhotoEnforcementZoneCentrePoint.class, siteId);
		
		if (foundEntity == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		entityManager.remove(foundEntity);
		
		return Response.ok().build();
	}

}
