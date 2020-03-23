package common.batch.resource;

import java.net.URI;
import java.util.Set;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.JobExecution;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * This resource contains methods for starting a batch job and to check the status of a batch job.
 * 
 * 	URI					Http Method		Description
 * 	----------------	-----------		------------------------------------------	
 *	/batch-jobs			POST			Start a new Batch Job
 *	/batch-jobs/1		GET				Find the status of the specified batch job
 *	/batch-jobs/names	GET				Get a set of batch job names
 *	

curl -i -X POST 'http://localhost:8080/webapi/batch-jobs/convertCsvToJsonJobForPhotoEnforcementZone.xml' 

curl -i -X GET http://localhost:8080/webapi/batch-jobs/1

curl -i -X GET http://localhost:8080/webapi/batch-jobs/jobnames

 * 
 * 
 * @author Sam Wu
 *
 */

@ApplicationScoped						// This is a CDI-managed bean that is created only once during the life cycle of the application
@Path("batch-jobs")						// All methods of this class are associated this URL path
@Consumes(MediaType.APPLICATION_JSON)	// All methods this class accept only JSON format data 
@Produces(MediaType.APPLICATION_JSON)	// All methods returns data that has been converted to JSON format
public class BatchJobResource {
	
	@POST					// This method only accepts HTTP POST requests.
	@Path("{filename}")
	public Response startBatchJob(@PathParam("filename") String jobXMLName, @Context UriInfo uriInfo) {
		JobOperator jobOperator = BatchRuntime.getJobOperator();
		
		try {
			long jobId = jobOperator.start(jobXMLName, null);
		
			URI location = uriInfo.getAbsolutePathBuilder()
				.path(jobId + "")	// convert jobId to a String using the concatenation (+) operator
				.build();
				
			return Response
				.created(location)
				.build();
		} catch (Exception ex) {
			ex.printStackTrace();
			return Response.serverError().entity(ex.getMessage()).build();
		}
	}

	@GET 			// This method only accepts HTTP GET requests.
	@Path("{id}")	// This method accepts a path parameter and gives it a name of id
	public Response getBatchStatus(@PathParam("id") Long jobId) {
		JobOperator jobOperator = BatchRuntime.getJobOperator();
		try {
			JobExecution jobExecution = jobOperator.getJobExecution(jobId);
			String jobStatus = jobExecution.getBatchStatus().toString();
			return Response.ok(jobStatus).build();
		} catch (Exception ex) {
			ex.printStackTrace();
			return Response.status(Response.Status.NOT_FOUND).build();
		}		
	}
	
	@GET 				// This method only accepts HTTP GET requests.
	@Path("jobnames")	
	public Response getJobNames() {
		JobOperator jobOperator = BatchRuntime.getJobOperator();
		try {
			Set<String> jobNames = jobOperator.getJobNames();
			return Response.ok(jobNames).build();
		} catch (Exception ex) {
			ex.printStackTrace();
			return Response.serverError().entity(ex.getMessage()).build();
		}		
	}

}
