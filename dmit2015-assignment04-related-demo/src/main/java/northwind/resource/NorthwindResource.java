package northwind.resource;


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

import northwind.entity.Category;


/**
 * 
 * 	URI								Http Method		Description
 * 	------------------------------	-----------		--------------------------------	------------------------------------------	
 *	/northwind/categories			GET				Get all categories
 *	/northwind/categories/{id}		GET				Get one category by id
 * 
 * 
		
curl -i -X GET 'http://localhost:8080/webapi/northwind/categories' 

		
curl -i -X GET 'http://localhost:8080/webapi/northwind/categories/1'
 

 * 
 * 
 * @author Sam Wu
 *
 */

@ApplicationScoped		// This is a CDI-managed bean that is created only once during the life cycle of the application
@Path("northwind")	// All methods of this class are associated this URL path
@Consumes(MediaType.APPLICATION_JSON)	// All methods this class accept only JSON format data 
@Produces(MediaType.APPLICATION_JSON)	// All methods returns data that has been converted to JSON format
public class NorthwindResource {

	@PersistenceContext(unitName = "mssqlNorthwind-jpa-pu")	// The unitName is defined in persistence.xml
	private EntityManager entityManager;
	
	@Path("categories")
	@GET	// This method only accepts HTTP GET requests.
	public Response findAll() {
		List<Category> resultList = entityManager.createQuery(
			"FROM Category e ORDER BY e.categoryName "
			, Category.class)
			.getResultList();
		
		return Response.ok(resultList).build();
	}
	
	@GET 			// This method only accepts HTTP GET requests.
	@Path("categories/{id}")	// This method accepts a path parameter and gives it a name of id
	public Response find(@PathParam("id") int categoryID) {
		// Find the Category instance with primary key of siteId 
		Category foundEntity = entityManager.find(Category.class, categoryID);
		
		if (foundEntity == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
		return Response.ok(foundEntity).build();
	}
	
	
}
