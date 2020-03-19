package northwind.resource;


import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
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
import northwind.entity.Shipper;


/**
 * 	
 * 	URI								Http Method		Request Body											Description
 * 	------------------------------	-----------		------------------------------------------------------	-----------------------------------------	
 *	/northwind/shippers				POST			{"companyName":"Canada Post","phone":"(416) 123-1234"}	Create a new Shipper
 * 	/northwind/shippers				GET																		Find all Shipper
 * 	/northwind/shippers/4			GET																		Find one Shipper with a shipperID of 4
 * 	/northwind/shippers/4			PUT				{"companyName":"DMIT Post","phone":"(789) 378-6140"}	Update the Shipper identified by a shipperID of 4
 * 	/northwind/shippers/4			DELETE																	Delete one Shipper with a shipperID of 4
 *	/northwind/categories			GET																		Get all categories
 *	/northwind/categories/{id}		GET																		Get one category by id
 * 
 * 

curl -i -X GET 'http://localhost:8080/webapi/northwind/shippers' 

curl -i -X POST 'http://localhost:8080/webapi/northwind/shippers' \
	-d '{"companyName":"Canada Post","phone":"(416) 123-1234"}' \
	-H 'Content-Type:application/json'

curl -i -X PUT 'http://localhost:8080/webapi/northwind/shippers/4' \
	-d '{"companyName":"DMIT Post","phone":"(789) 378-6140"}' \
	-H 'Content-Type:application/json'
	
curl -i -X GET 'http://localhost:8080/webapi/northwind/shippers/4' 

curl -i -X DELETE 'http://localhost:8080/webapi/northwind/shippers/4' 

curl -i -X DELETE 'http://localhost:8080/webapi/northwind/shippers/4' 

curl -i -X GET 'http://localhost:8080/webapi/northwind/categories' 

curl -i -X GET 'http://localhost:8080/webapi/northwind/categories/categoryNames'
 
curl -i -X GET 'http://localhost:8080/webapi/northwind/categories/categoryID_categoryName'

curl -i -X GET 'http://localhost:8080/webapi/northwind/categories/totals'

curl -i -X GET 'http://localhost:8080/webapi/northwind/categories/Seafood/totals/1997'

		
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

	@PersistenceContext(unitName = "mssql-jpa-pu")	// The unitName is defined in persistence.xml
	private EntityManager entityManager;

	@Path("shippers")
	@GET	// This method only accepts HTTP GET requests.
	public Response listShippers() {
		List<Shipper> resultList = entityManager.createQuery(
			"FROM Shipper s ORDER BY s.companyName "
			, Shipper.class)
			.getResultList();
		
		return Response.ok(resultList).build();
	}

	@Path("shippers/{id}")
	@GET	// This method only accepts HTTP GET requests.
	public Response findShipperById(@PathParam("id") int shipperID) {
		Shipper foundEntity = entityManager.find(Shipper.class, shipperID);
		
		if (foundEntity == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
		return Response.ok(foundEntity).build();
	}
		
	@POST			// This method only accepts HTTP POST requests.
	@Path("shippers")
	@Transactional	// This method requires a transaction to execute
	public Response addShipper(@Valid Shipper newShipper, @Context UriInfo uriInfo) {
		
		try {
			// Persist the new Shippper into the database
			entityManager.persist(newShipper);
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
	@Path("shippers/{id}")	// This method accepts a path parameter and gives it a name of id
	@Transactional
	public Response updateShipper(@PathParam("id") int shipperID, @Valid Shipper existingShipper) {
		Shipper foundEntity = entityManager.find(Shipper.class, shipperID);
		
		if (foundEntity == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
		foundEntity.setCompanyName(existingShipper.getCompanyName());
		foundEntity.setPhone(existingShipper.getPhone());
		
		try {
			entityManager.merge(foundEntity);
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
	@Path("shippers/{id}")	// This method accepts a path parameter and gives it a name of id
	@Transactional
	public Response delete(@PathParam("id") int shipperID) {
		Shipper foundEntity = entityManager.find(Shipper.class, shipperID);
		
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

	
	/**
	 * Return a list of Category entities sorted by the categoryName
	 * @return a list of Category entities 
	 */

	@Path("categories")
	@GET					// This method only accepts HTTP GET requests.
	public Response listCategories() {
		List<Category> resultList = entityManager.createQuery(
			"FROM Category e ORDER BY e.categoryName "
			, Category.class)
			.getResultList();
		
		return Response.ok(resultList).build();
	}
	

	/**
	 * Return a list of categoryName sorted ascending
	 * @return a list of category names 
	 */

	@Path("categories/categoryNames")
	@GET					// This method only accepts HTTP GET requests.
	public Response listCategoryNames() {
		List<String> resultList = entityManager.createQuery(
			"SELECT c.categoryName FROM Category c GROUP BY c.categoryName ORDER BY c.categoryName "
			, String.class)
			.getResultList();
		
		return Response.ok(resultList).build();
	}
	
	/**
	 * Return a list of categoryId, categoryName sorted ascending by the categoryName
	 * @return a list of categoryId,categoryName values 
	 */

	@Path("categories/categoryID_categoryName")
	@GET					// This method only accepts HTTP GET requests.
	public Response listCategoryIDAndCategoryName() {
		// A Tuple is an interface for extracting the elements of a query result tuple
		// An alias for each value is REQUIRED in the SELECT clause when returning a Tuple
		List<Tuple> resultList = entityManager.createQuery(
			"SELECT c.categoryID as CatID, c.categoryName as CatName FROM Category c ORDER BY c.categoryName "
			, Tuple.class)
			.getResultList();
		// Extract the elements (CatID, CatName) from the Tuple using the alias name and store it in another data structure
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
		resultList.forEach(tuple -> {
			JsonObject jsonObject = Json.createObjectBuilder()
				.add("categoryID", tuple.get("CatID", Integer.class))
				.add("categoryName", tuple.get("CatName", String.class))
				.build();
			jsonArrayBuilder.add(jsonObject);		
		});
		
		return Response.ok(jsonArrayBuilder.build()).build();
	}
	
	
	
	@Path("categories/totals")
	@GET	// This method only accepts HTTP GET requests.
	public Response listCategorySalesTotals() {
		List<Tuple> resultList = entityManager.createQuery(
			"SELECT c.categoryName as CatName, SUM(od.unitPrice * od.quantity * (1 - od.discount)) as SalesTotal "
			+ " FROM Order o, IN (o.orderDetails) od, IN (od.product) p, IN (p.category) c  "
			+ " GROUP BY c.categoryName "
			, Tuple.class)
			.getResultList();
		
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
		resultList.forEach(tuple -> {
			JsonObject jsonObject = Json.createObjectBuilder()
				.add("categoryName", tuple.get("CatName", String.class))
				.add("salesTotal", tuple.get("SalesTotal", Double.class))
				.build();
			jsonArrayBuilder.add(jsonObject);		
		});
		
		return Response.ok(jsonArrayBuilder.build()).build();
	}
	
	@Path("categories/{categoryName}/totals/{year}")
	@GET	// This method only accepts HTTP GET requests.
	public Response findCategorySalesTotalsForCategoryNameAndYear(
		@PathParam("categoryName") String categoryName, 
		@PathParam("year") int year) {
		
		try {
			Tuple singleResult = entityManager.createQuery(
				"SELECT c.categoryName as CatName, SUM(od.unitPrice * od.quantity * (1 - od.discount)) as SalesTotal "
				+ " FROM Order o, IN (o.orderDetails) od, IN (od.product) p, IN (p.category) c  "
				+ " WHERE c.categoryName = :catNameValue AND YEAR(o.shippedDate) = :yearValue "
				+ " GROUP BY c.categoryName "
				, Tuple.class)
				.setParameter("catNameValue", categoryName)
				.setParameter("yearValue", year)
				.getSingleResult();
			
			JsonObject jsonObject = Json.createObjectBuilder()
				.add("categoryName", singleResult.get("CatName", String.class))
				.add("salesTotal", singleResult.get("SalesTotal", Double.class))
				.build();
		
			return Response.ok(jsonObject).build();
		} catch (NoResultException ex) {
			return Response.noContent().build();
		} catch(Exception ex) {
			// Return a HTTP status of "500 Internal Server Error" containing the exception message
			return Response						
					.serverError()
					.encoding(ex.getMessage())
					.build();						
		}
	}
	
	
	@GET 						// This method only accepts HTTP GET requests.
	@Path("categories/{id}")	// This method accepts a path parameter and gives it a name of id
	public Response findCategoryByCategoryID(@PathParam("id") int categoryID) {
		// Find the Category instance with primary key of siteId 
		Category foundEntity = entityManager.find(Category.class, categoryID);
		
		if (foundEntity == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
		return Response.ok(foundEntity).build();
	}

	@GET 						// This method only accepts HTTP GET requests.
	@Path("categories/categoryName/{name}")	// This method accepts a path parameter and gives it a name of id
	public Response findCategoryByCategoryName(@PathParam("name") String categoryName) {
		// Find the Category instance with unique key categoryName 
		try {
			Category foundEntity = entityManager.createQuery(
				"SELECT c FROM Category c WHERE c.categoryName = :nameValue"
				, Category.class)
				.setParameter("nameValue", categoryName)
				.getSingleResult();
			
			// To EXCLUDE the binary picture from the result we can manually construct a JsonObject to return
			JsonObject jsonObject = Json.createObjectBuilder()
					.add("categoryID", foundEntity.getCategoryID())
					.add("categoryName", foundEntity.getCategoryName())
					.add("description", foundEntity.getDescription())
					.build();
			return Response.ok(jsonObject).build();
		} catch(NoResultException ex) {
			return Response.status(Response.Status.NOT_FOUND).build();			
		} catch(Exception ex) {
			return Response.status(Response.Status.BAD_REQUEST).build();						
		}
		
	}

	
}
