package dmit2015.resource;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Set;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import dmit2015.entity.Expense;

/**
 *  URI						Http Method		Request Body						Description
 * 	----------------------	-----------		--------------------------------	------------------------------------------	
 *	/expenses				POST			{"description":"value",				Create a new Expense entity
 *											"amount":1.23,
 *											"date":"yyyy-MM-dd"}				
 * 	/expenses				GET													Return a list all Expense entity
 * 	/expenses/{id}			GET													Return one Expense entity by id 
 * 	/expenses/{id}			PUT				{"description":"value",				Update the Expense entity by id
 * 											"amount":1.23,
 * 											"date":"yyyy-MM-dd"}		
 * 	/expenses/{id}			DELETE												Delete the Expense entity by id
 * 
 * Create three new Expense
curl -i -k -X POST https://localhost:8443/webapi/expenses \
 -d '{"description":"April 1 item","amount": 1.11, "date":"2020-04-01"}' \
 -H 'Content-Type:application/json' \
 -u user2015:Password2015
curl -i -k -X POST https://localhost:8443/webapi/expenses \
 -d '{"description":"April 2 item","amount": 2.22, "date":"2020-04-02"}' \
 -H 'Content-Type:application/json' \
 -u user2015:Password2015
curl -i -k -X POST https://localhost:8443/webapi/expenses \
 -d '{"description":"April 3 item","amount": 3.33, "date":"2020-04-03"}' \
 -H 'Content-Type:application/json' \
 -u user2015:Password2015
 
curl -i -k -X POST https://localhost:8443/webapi/expenses/form \
 	--data-urlencode 'description=April 5 Item' \
 	-d 'amount=5.55' \
 	-d 'date=2020-04-05' \
	-H 'Content-Type:application/x-www-form-urlencoded' \
 	-u user2015:Password2015
curl -i -k -X POST https://localhost:8443/webapi/expenses/basic-login \
	--data 'username=user2015' \
	--data-urlencode 'password=Password2015' \
	-H 'Content-Type:application/x-www-form-urlencoded' \
 * Delete one Expense with id of 7 
curl -i -k -X DELETE https://localhost:8443/webapi/expenses/7 -u user2015:Password2015
 
 * List all expenses
curl -i -k -X GET https://localhost:8443/webapi/expenses 
 
 * Update an Expense 
curl -i -k -X PUT https://localhost:8443/webapi/expenses/6 \
 -d '{"description":"April 6 item","amount": 6.66, "date":"2020-04-06"}' \
 -H 'Content-Type:application/json' \
 -u user2015:Password2015
curl -i -k -X POST https://localhost:8443/webapi/expenses \
 -d '{"description":"April 10 item","amount": 10.10, "date":"2020-04-10"}' \
 -H 'Content-Type:application/json' 
  
 * @author samcw
 *
 */

@Path("expenses")
@Consumes(MediaType.APPLICATION_JSON)	// All methods this class accept only JSON format data 
@Produces(MediaType.APPLICATION_JSON)	// All methods returns data that has been converted to JSON format
@Transactional
public class ExpenseResource {
	

	@PersistenceContext
	private EntityManager entityManager;
	
	@RolesAllowed({"USER"})
	@POST			// This method only accepts HTTP POST requests.
	public Response add(@Valid Expense newExpense, @Context UriInfo uriInfo) {
				
		try {
			newExpense.setDateCreated(LocalDateTime.now());
			entityManager.persist(newExpense);
			URI location = uriInfo.getAbsolutePathBuilder()
				.path(newExpense.getId().toString())
				.build();		
			return Response
					.created(location)
					.build();
		} catch (Exception ex) {
			return Response.
					serverError()
					.entity(ex.getMessage())
					.build();
		}
		
	}

	@RolesAllowed({"USER"})
	@Path("form")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@POST			// This method only accepts HTTP POST requests.
	public Response addFormData(
		@FormParam("date") String dateString,
		@FormParam("description") String description,
		@FormParam("amount") String amountString,
		@Context UriInfo uriInfo) {
						
		try {
			LocalDate date = LocalDate.parse(dateString);
			BigDecimal amount = new BigDecimal(amountString);
			Expense newExpense = new Expense();
			newExpense.setDate(date);
			newExpense.setDescription(description);
			newExpense.setAmount(amount);

			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			Validator validator = factory.getValidator();
			
			Set<ConstraintViolation<Expense>> constraintViolations = validator.validate(newExpense);
			if (constraintViolations.size() > 0) {
				List<String> errorMessages = new ArrayList<>();
				constraintViolations.stream().forEach(singleConstraintViolation -> {
					errorMessages.add( singleConstraintViolation.getMessage() );
				});
				return Response.status(Status.BAD_REQUEST).entity(errorMessages).build();
			} else {
				newExpense.setDateCreated(LocalDateTime.now());
				entityManager.persist(newExpense);
				URI location = uriInfo.getAbsolutePathBuilder()
					.path(newExpense.getId().toString())
					.build();		
				return Response
						.created(location)
						.build();
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
			return Response.
					serverError()
					.entity(ex.getMessage())
					.build();
		}
		
	}

	@PermitAll
	@GET	// This method only accepts HTTP GET requests.
	public Response findAll() {
		try {
			List<Expense> resultList = entityManager.createQuery(
				"FROM Expense e ORDER BY e.date DESC"
				, Expense.class)
				.getResultList();
			return Response.ok(resultList).build();			
		} catch (Exception ex) {
			return Response
					.serverError()
					.entity(ex.getMessage())
					.build();
		}
	}
	
	@PermitAll
	@GET 			// This method only accepts HTTP GET requests.
	@Path("{id}")	// This method accepts a path parameter and gives it a name of id
	public Response find(@PathParam("id") Long id) {
		// Find the LoginGroup instance with primary key of id 
		Expense foundEntity =  entityManager.find(Expense.class, id);
		
		if (foundEntity == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
		return Response.ok(foundEntity).build();
	}
	
	@RolesAllowed({"USER"})
	@PUT 			// This method only accepts HTTP PUT requests.
	@Path("{id}")	// This method accepts a path parameter and gives it a name of id
	public Response update(@PathParam("id") Long id, @Valid Expense exisitingExpense) {
		Expense foundEntity =  entityManager.find(Expense.class, id);
		
		if (foundEntity == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
		foundEntity.setDescription(exisitingExpense.getDescription());
		foundEntity.setAmount(exisitingExpense.getAmount());
		foundEntity.setDate(exisitingExpense.getDate());
		
		try {
			entityManager.merge(foundEntity);
			entityManager.flush();
		} catch (Exception ex) {
			return Response.
					serverError()
					.entity(ex.getMessage())
					.build();
		}

		return Response.noContent().build();
	}

	@RolesAllowed({"ADMIN"})
	@DELETE 		// This method only accepts HTTP DELETE requests.
	@Path("{id}")	// This method accepts a path parameter and gives it a name of id
	public Response delete(@PathParam("id") Long id) {
		Expense foundEntity = entityManager.find(Expense.class, id);
		
		if (foundEntity == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
		try {
			entityManager.remove(foundEntity);
			entityManager.flush();
		} catch (Exception ex) {				
			return Response						
					.serverError()
					.encoding(ex.getMessage())
					.build();
		}
		
		return Response.noContent().build();	
	}
	
	
	@Path("basic-login")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	@POST
	public Response login(
		@FormParam("username") String username,
		@FormParam("password") String password) {
		// Basic authentication requires a HTTP request with the "Authorization" header that contains the "Basic" word followed
		// by a space and base-encoded string "username:password".
		String plainTextCredential = String.format("%s:%s",  username, password);
		String encodedCredential = String.format("Basic %s", Base64.getEncoder().encodeToString(plainTextCredential.getBytes()));
		return Response.ok(encodedCredential).build();
	}

}
