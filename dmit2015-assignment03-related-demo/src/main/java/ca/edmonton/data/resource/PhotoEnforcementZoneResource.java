package ca.edmonton.data.resource;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import ca.edmonton.data.entity.PhotoEnforcementZone;

import javax.ws.rs.core.MediaType;

/**
 * 

curl -i -X DELETE 'http://localhost:8080/webapi/pez/11' 

curl -i -X POST 'http://localhost:8080/webapi/pez' \
	-d '{"locationDescription":"JSON School", "speedLimit":30, "reasonCodes":"b"}' \
	-H 'Content-Type:application/json'

curl -i -X POST 'http://localhost:8080/webapi/pez/form' \
	-d 'locationDescription=Form%20School' \
	-d 'speedLimit=35' \
	-d 'reasonCodes=c' \
	-H 'Content-Type:application/x-www-form-urlencoded'

curl -i -X POST 'http://localhost:8080/webapi/pez/path/Path%20School/40/d' 

curl -i -X POST 'http://localhost:8080/webapi/pez/query?locationDescription=Query%20School&speedLimit=45&reasonCodes=e,f' 

curl -i -X PUT 'http://localhost:8080/webapi/pez/1060' \
	-d '{"locationDescription":"Updated JSON School", "speedLimit":35, "reasonCodes":"f, h"}' \
	-H 'Content-Type:application/json'
		
curl -i -X GET 'http://localhost:8080/webapi/pez' 

curl -i -X GET 'http://localhost:8080/webapi/pez/9'

curl -i -X GET 'http://localhost:8080/webapi/pez/speedLimit?lowerLimit=30&upperLimit=40'

curl -i -X GET 'http://localhost:8080/webapi/pez/reasonCodes?reasonCode=a'

curl -i -X GET 'http://localhost:8080/webapi/pez/reasonCodes?reasonCode=h'

 * @author Sam Wu
 *
 */
				
@Path("pez")	// "http://server/webapi/pez"
				// @Path before a class is relative to @ApplicationPath
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PhotoEnforcementZoneResource {

	@PersistenceContext(unitName = "mssql-jpa-pu")
	private EntityManager entityManager;
	
	@POST		// "http://server/webapi/pez
	@Transactional
	public Response postJsonPhotoEnforcementZone(PhotoEnforcementZone newPhotoEnforcementZone) {
		entityManager.persist(newPhotoEnforcementZone);
		return Response.ok(newPhotoEnforcementZone.getId()).build();
	}

	@POST			
	@Path("form")	// "http://server/webapi/pez/form
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Transactional
	public Response postFormParamPhotoEnforcementZone(
		@FormParam("locationDescription") String locationDescription,
		@FormParam("speedLimit") int speedLimit,
		@FormParam("reasonCodes") String reasonCodes) {
		
		PhotoEnforcementZone newPhotoEnforcementZone = new PhotoEnforcementZone();
		newPhotoEnforcementZone.setLocationDescription(locationDescription);
		newPhotoEnforcementZone.setSpeedLimit(speedLimit);
		newPhotoEnforcementZone.setReasonCodes(reasonCodes);
		
		entityManager.persist(newPhotoEnforcementZone);
		return Response.ok(newPhotoEnforcementZone.getId()).build();
	}

	@POST	// "http://server/webapi/pez/path/location1/100/f"
	@Path("path/{locationDescription}/{speedLimit}/{reasonCodes}")	
	@Consumes(MediaType.TEXT_PLAIN)
	@Transactional
	public Response postPathParamPhotoEnforcementZone(
		@PathParam("locationDescription") String locationDescription,
		@PathParam("speedLimit") int speedLimit,
		@PathParam("reasonCodes") String reasonCodes) {
		
		PhotoEnforcementZone newPhotoEnforcementZone = new PhotoEnforcementZone();
		newPhotoEnforcementZone.setLocationDescription(locationDescription);
		newPhotoEnforcementZone.setSpeedLimit(speedLimit);
		newPhotoEnforcementZone.setReasonCodes(reasonCodes);
		
		entityManager.persist(newPhotoEnforcementZone);
		return Response.ok(newPhotoEnforcementZone.getId()).build();
	}

	@POST	// "http://server/webapi/pez/query?locationDescription=new%20location&speedLimit=50&reasonCodes=b"
	@Path("query")
	@Consumes(MediaType.TEXT_PLAIN)
	@Transactional
	public Response postQueryParamPhotoEnforcementZone(
		@QueryParam("locationDescription") String locationDescription,
		@QueryParam("speedLimit") int speedLimit,
		@QueryParam("reasonCodes") String reasonCodes) {
		
		PhotoEnforcementZone newPhotoEnforcementZone = new PhotoEnforcementZone();
		newPhotoEnforcementZone.setLocationDescription(locationDescription);
		newPhotoEnforcementZone.setSpeedLimit(speedLimit);
		newPhotoEnforcementZone.setReasonCodes(reasonCodes);
		
		entityManager.persist(newPhotoEnforcementZone);
		return Response.ok(newPhotoEnforcementZone.getId()).build();
	}

	@PUT
	@Path("{id}")	// "http://server/webapi/pez/100"
	@Transactional
	public Response updatePhotoEnforcementZone(@PathParam("id") Long id, PhotoEnforcementZone editedPhotoEnforcementZone) {
		PhotoEnforcementZone existingPhotoEnforcementZone = entityManager.find(PhotoEnforcementZone.class, id);
		existingPhotoEnforcementZone.setLocationDescription(editedPhotoEnforcementZone.getLocationDescription());
		existingPhotoEnforcementZone.setSpeedLimit(editedPhotoEnforcementZone.getSpeedLimit());
		existingPhotoEnforcementZone.setReasonCodes(editedPhotoEnforcementZone.getReasonCodes());
		entityManager.merge(existingPhotoEnforcementZone);
		entityManager.flush();
		return Response.ok(existingPhotoEnforcementZone).build();
	}

	@DELETE
	@Path("{id}")	// "http://server/webapi/pez/100"
	@Transactional
	public Response deletePhotoEnforcementZone(@PathParam("id") Long id) {
		PhotoEnforcementZone existingPhotoEnforcementZone = entityManager.find(PhotoEnforcementZone.class, id);
		entityManager.remove(existingPhotoEnforcementZone);
		return Response.ok(existingPhotoEnforcementZone).build();
	}

	@GET	// "http://server/webapi/pez"
	public Response findAllZones() {
		List<PhotoEnforcementZone> zoneList = entityManager.createQuery(
			"SELECT e "
			+ " FROM PhotoEnforcementZone e "
			+ " ORDER BY e.speedLimit, e.locationDescription "
			, PhotoEnforcementZone.class)
			.setMaxResults(99)
			.getResultList();
		
		return Response.ok(zoneList).build();
	}

	@GET
	@Path("{id}")	// "http://server/webapi/pez/{id}"
	public Response findOneZone(@PathParam("id") Long id) {
		PhotoEnforcementZone singleZone = entityManager.createQuery(
			"SELECT e "
			+ " FROM PhotoEnforcementZone e "
			+ " WHERE e.id = :idValue"
			,PhotoEnforcementZone.class)
			.setParameter("idValue", id)
			.getSingleResult();
		
		return Response.ok(singleZone).build();
	}

	@GET
	@Path("speedLimit")
	public Response findsZoneBySpeedLimitRange(
			@QueryParam("lowerLimit") int lowerSpeedLimit, 
			@QueryParam("upperLimit") int upperSpeedLimit) {
		
		List<PhotoEnforcementZone> resultList = entityManager.createQuery(
			"SELECT e "
			+ " FROM PhotoEnforcementZone e "
			+ " WHERE e.speedLimit BETWEEN :lowerValue AND :upperValue ORDER BY e.speedLimit"
			, PhotoEnforcementZone.class)
			.setParameter("lowerValue", lowerSpeedLimit)
			.setParameter("upperValue", upperSpeedLimit)
			.getResultList();
		
		return Response.ok(resultList).build();
	}
	
	@GET
	@Path("reasonCodes")
	public Response findsZoneByOneReasonCode(@QueryParam("reasonCode") String singleReasonCode) {
		
		List<PhotoEnforcementZone> resultList = entityManager.createQuery(
			"SELECT e "
			+ " FROM PhotoEnforcementZone e "
			+ " WHERE e.reasonCodes = :singleReasonCodeValue "
			,PhotoEnforcementZone.class)
			.setParameter("singleReasonCodeValue", singleReasonCode)
			.getResultList();
		
		return Response.ok(resultList).build();
	}

}
