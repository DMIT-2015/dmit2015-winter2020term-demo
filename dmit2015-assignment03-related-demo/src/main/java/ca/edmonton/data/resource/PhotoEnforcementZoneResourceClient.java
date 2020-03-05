package ca.edmonton.data.resource;
// Step 1: Register a rest client API
// Step 2: Inject the client API to the client microservice JAX-RS resource
// Step 3: Rebind the backend microservice in src/main/resources/META-INF/microprofile-config.properties

import javax.enterprise.context.Dependent;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Dependent
@RegisterRestClient
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface PhotoEnforcementZoneResourceClient {

	@POST
	@Path("/pez")
	Response postJsonPhotoEnforcementZone(JsonObject newPhotoEnforcementZone);
	
	@PUT
	@Path("/pez/{id}")
	Response updatePhotoEnforcementZone(@PathParam("id") Long id, JsonObject editedPhotoEnforcementZone);
	
	@DELETE
	@Path("/pez/{id}")
	Response deletePhotoEnforcementZone(@PathParam("id") Long id);
	
	@GET
	@Path("/pez")
	JsonArray findAllPhotoEnforcementZones();

	@GET
	@Path("/pez/{id}")
	JsonObject findOneZone(@PathParam("id") Long id);
	
}
