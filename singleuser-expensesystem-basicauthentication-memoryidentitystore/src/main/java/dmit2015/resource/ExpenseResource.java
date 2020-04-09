package dmit2015.resource;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * 
 * Create a new Expense
curl -i -k -X POST https://localhost:8443/webapi/expenses 


 * Create a new Expense and set username/password for basic authentication
curl -i -k -X POST https://localhost:8443/webapi/expenses -u user2015:Password2015

curl -i -k -X DELETE https://localhost:8443/webapi/expenses 

 * 
 * 
 * @author user2015
 *
 */

@Path("expenses")
@Consumes("application/json")
@Produces("application/json")
@DeclareRoles({"USER","ADMIN"})
public class ExpenseResource {

	@RolesAllowed("USER")
	@POST
	public void createExpense( ) {
		System.out.println("Create was successful");
	}
	
	@PermitAll
	@GET
	public void listExpenses() {
		System.out.println("List expenses was successful");
	}
	
	@RolesAllowed({"USER","ADMIN"})
	@PUT
	public void updateExpense() {
		System.out.println("Updated was successful");
	}
	
	@RolesAllowed("ADMIN")
	@DELETE
	public void deleteExpense() {
		System.out.println("Delete was succcssful");
	}
	
	
}
