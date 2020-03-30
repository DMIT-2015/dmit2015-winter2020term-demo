package northwind.resource;

import java.math.BigDecimal;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import northwind.entity.Product;

/**
 * Create a RESTFul web service class named ProductResource with the following methods:
 * 1. A method that returns one Product entity by a given productID
 * 2. A method that returns one Product entity by a given productName
 * 3. A method that returns of the top N most expensive Product entities sorted descending by the unitPrice.
 * 4. A method that returns the productId, productName, and unitPrice of the top N most expensive products 
 * sorted descending by unitPrice.
 * 5. A method that returns the productId, productName, and TotalQuantity 
 * for the top N selling products by TotalQuantity
 * sorted descending by the TotalQuantity.  
 * 6. A method that returns the productId, productName, and TotalQuantity 
 * for the top N selling products by TotalQuantity by a given year of the Order shippedDate 
 * and sorted descending by the TotalQuantity.
 * 7. A method that returns the productId, productName, TotalQuantity, and TotalSalesRevenue 
 * for the top N revenue products by TotalSalesRevenue
 * sorted descending by the TotalSalesRevenue.  
 * 8. A method that returns the productId, productName, TotalQuantity, and TotalSalesRevenue 
 * for the top N revenue products by TotalSalesRevenue by a given year of the shippedDate of the Order and
 * sorted descending by the TotalSalesRevenue.
        
 * URL Path													HTTP METHOD		Description
 * -------------------------------------------------------	-----------		---------------------------
 * /products/{id}											GET				Returns one Product entity by its productID value
 * /products/productName/{prodctName}						GET				Returns one Product entity bys its productName value
 * /products/mostexpensive/{maxResult}						GET				Returns of the top N most expensive Product entities sorted descending by the unitPrice.
 * /products/mostexpensive/customselect/{maxResult}			GET				Returns the productId, productName, and unitPrice of the top N most expensive products sorted descending by unitPrice.
 * /products/topselling/{maxResult}							GET
 * /products/topselling/year/{year}/{maxResult}				GET
 * /products/toprevenue/{maxResult}							GET
 * /products/toprevenue/year/{year}/{maxResult}				GET
 * 
 * Find a product with productId of 1 should return a status of 200
 curl -i -K GET  http://localhost:8080/webapi/products/1
 * Find a product with productId of 12345 should return HttpStatus 404
 curl -i http://localhost:8080/webapi/products/12345
 * Find a product with productName of Chai should return a status of 200
 curl -i  http://localhost:8080/webapi/products/productName/Chai
 * Find top 10 most expensive products
 curl -i http://localhost:8080/webapi/products/mostexpensive/10
 * Find top 5 most expensive products and return only productId, productName, unitPrice
 curl -i http://localhost:8080/webapi/products/mostexpensive/customselect/5
 * Find top 10 selling products for all years
 curl -i http://localhost:8080/webapi/products/topselling/10
 * Find top 10 selling products for 1997
 curl -i http://localhost:8080/webapi/products/topselling/year/1997/10
 * Find top 15 sales revenue products for all years
 curl -i http://localhost:8080/webapi/products/toprevenue/15
 * Find top 8 sales revenue products for 1997
 curl -i http://localhost:8080/webapi/products/toprevenue/year/1997/8  
 * 
 * @author user2015
 *
 */

@Path("products")
@Consumes(MediaType.APPLICATION_JSON)
@Produces("application/json")
public class ProductResource {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@GET
	@Path("{id}")
	public Response findByProductID(@PathParam("id") int productID) {
		Product singleResult = entityManager.find(Product.class, productID);
		
		if (singleResult == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
		return Response.ok(singleResult).build();
		
	}
//	public Product findByProductID(@PathParam("id") int productID) {
//		return entityManager.find(Product.class, productID);
//	}
	
	@GET
	@Path("productName/{prodctName}")
	public Response findByProductName(@PathParam("prodctName") String productName) {
		try {
			Product singleResult = entityManager.createQuery(
				"SELECT p "
				+ " FROM Product p "
				+ " WHERE p.productName = :nameValue "
				,Product.class)
				.setParameter("nameValue", productName)
				.getSingleResult();
			return Response.ok(singleResult).build();
		} catch (NoResultException ex) {
			return Response.status(Response.Status.NOT_FOUND).build();
		} catch (Exception ex) {
			return Response
					.serverError()
					.entity(ex.getMessage())
					.build();
		}
		
	}
	
	@GET
	@Path("mostexpensive/{maxResult}")
	public Response mostExpensive(@PathParam("maxResult") int maxResult) {
		try {
			List<Product> resultList = entityManager.createQuery(
				"SELECT p "
				+ " FROM Product p "
				+ " ORDER BY p.unitPrice DESC "
				,Product.class)
				.setMaxResults(maxResult)
				.getResultList();
			return Response.ok(resultList).build();
		} catch (Exception ex) {
			return Response
					.serverError()
					.entity(ex.getMessage())
					.build();
		}
		
	}
	
	@GET
	@Path("mostexpensive/customselect/{maxResult}")
	public Response mostExpensiveCustomSelect(@PathParam("maxResult") int maxResult) {
		try {
			List<Tuple> resultList = entityManager.createQuery(
				"SELECT p.productID as pID, p.productName as pName, p.unitPrice as pPrice "
				+ " FROM Product p "
				+ " ORDER BY p.unitPrice DESC "
				,Tuple.class)
				.setMaxResults(maxResult)
				.getResultList();
			
			JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
			resultList.forEach(tuple -> {
				
				JsonObject singleResult = Json.createObjectBuilder()
					.add("productID", tuple.get("pID", Integer.class))
					.add("productName", tuple.get("pName", String.class))
					.add("unitPrice", tuple.get("pPrice", BigDecimal.class))
					.build();
				
				arrayBuilder.add(singleResult);
			});
			
			return Response.ok(arrayBuilder.build()).build();
			
		} catch (Exception ex) {
			return Response
					.serverError()
					.entity(ex.getMessage())
					.build();
		}
		
	}
	
	@GET
	@Path("topselling/{maxResult}")
	public Response topSelling(@PathParam("maxResult") int maxResult) {
		try {
			List<Tuple> resultList = entityManager.createQuery(
				"SELECT p.productID as pID, p.productName as pName, SUM(od.quantity) as TotalQuantity "
				+ " FROM OrderDetail od, IN (od.product) p " // FROM Product p, IN (p.orderDetails) od
						// FROM OrderDetail od JOIN od.product p
				+ " GROUP BY p.productID, p.productName "
				+ " ORDER BY TotalQuantity DESC "
				,Tuple.class)
				.setMaxResults(maxResult)
				.getResultList();
			
			JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
			resultList.forEach(tuple -> {
				
				JsonObject singleResult = Json.createObjectBuilder()
					.add("productID", tuple.get("pID", Integer.class))
					.add("productName", tuple.get("pName", String.class))
					.add("totalQuantity", tuple.get("TotalQuantity", Long.class))
					.build();
				
				arrayBuilder.add(singleResult);
			});
			
			return Response.ok(arrayBuilder.build()).build();
			
		} catch (Exception ex) {
			return Response
					.serverError()
					.entity(ex.getMessage())
					.build();
		}
		
	}
	
	@GET
	@Path("topselling/year/{year}/{maxResult}")
	public Response topSellingByYear(
		@PathParam("year") int year,
		@PathParam("maxResult") int maxResult) {
		
		try {
			List<Tuple> resultList = entityManager.createQuery(
				"SELECT p.productID as pID, p.productName as pName, SUM(od.quantity) as TotalQuantity "
				+ " FROM OrderDetail od, IN (od.product) p, IN (od.order) o " 
						// FROM Order o JOIN OrderDetail od JOIN od.product p 
				+ " WHERE YEAR(o.shippedDate) = :yearValue "
				+ " GROUP BY p.productID, p.productName "
				+ " ORDER BY TotalQuantity DESC "
				,Tuple.class)
				.setParameter("yearValue", year)	
				.setMaxResults(maxResult)
				.getResultList();
			
			JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
			resultList.forEach(tuple -> {
				
				JsonObject singleResult = Json.createObjectBuilder()
					.add("productID", tuple.get("pID", Integer.class))
					.add("productName", tuple.get("pName", String.class))
					.add("totalQuantity", tuple.get("TotalQuantity", Long.class))
					.build();
				
				arrayBuilder.add(singleResult);
			});
			
			return Response.ok(arrayBuilder.build()).build();
			
		} catch (Exception ex) {
			return Response
					.serverError()
					.entity(ex.getMessage())
					.build();
		}
		
	}

}