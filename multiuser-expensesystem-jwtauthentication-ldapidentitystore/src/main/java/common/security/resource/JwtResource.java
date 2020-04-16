package common.security.resource;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.directory.api.ldap.model.cursor.EntryCursor;
import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.exception.LdapAuthenticationException;
import org.apache.directory.api.ldap.model.message.SearchScope;
import org.apache.directory.api.ldap.model.name.Dn;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;

import common.security.jwt.TokenUtil;

/**
 * Microprofile JWT Authentication Steps
 * Step 1: Generate the public/private key pair for signing and verification.
 * 			Use openssl to generate the private key
openssl genpkey -algorithm RSA -out private.pem -pkeyopt rsa_keygen_bits:2048
cp private.pem src/main/resources/

 * Export the public key that will be inlcuded in the deployment
openssl rsa -in private.pem -pubout -out src/main/resources/META-INF/public.pem
 
 * Step 2: Add a utility to generate JWT tokens. 
 * Step 3: Activate Microprofile JWT by adding the @LoginConfig annotation as follows:
@ApplicationPath("/webapi")
@LoginConfig(authMethod="MP-JWT", realmName="MP JWT Realm")
public class App extends Application {}

 * Step 4: Add/update the Microprofile Config properties file 'microprofile-config.properties' file that is located in the
 * 'src/main/resources/META-INF' folder with the following content 
mp.jwt.verify.publickey.location=META-INF/public.pem
mp.jwt.verify.issuer=quickstart-jwt-issuer



curl -k https://localhost:8443/webapi/jwt/formLogin/ \
	-d 'j_username=dmurphy&j_password=Password2015' \
	-H 'Content-Type:application/x-www-form-urlencoded'
	
curl -k https://localhost:8443/webapi/jwt/jsonLogin/ \
	-d '{"username":"dmurphy","password":"Password2015"}' \
	-H 'Content-Type:application/json'	

curl -k https://localhost:8443/webapi/jwt/jsonLogin/ \
	-d '{"username":"abow","password":"Password2015"}' \
	-H 'Content-Type:application/json'

curl -k https://localhost:8443/webapi/jwt/jsonLogin/ \
	-d '{"username":"bjones","password":"Password2015"}' \
	-H 'Content-Type:application/json'

 * 
 * 
 * 
 * @author samwu
 *
 */

@RequestScoped
@Path("jwt")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class JwtResource {
	
	@Inject
	private HttpServletRequest request;

	@Path("formLogin")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@POST
	public Response formLogin(
		@FormParam("j_username") String username,
		@FormParam("j_password") String password) {
		JsonObject credential = Json.createObjectBuilder()
			.add("username", username)
			.add("password", password)
			.build();
		return jsonLogin(credential);
	}

	@POST
	@Path("jsonLogin")
	public Response jsonLogin(JsonObject credential) {
		String username = credential.getString("username");
		String password = credential.getString("password");
		
		String ldapServer = request.getServletContext().getInitParameter("ldap.server");
		String ldapBindName = request.getServletContext().getInitParameter("ldap.bind.name");
		String ldapBindPassword = request.getServletContext().getInitParameter("ldap.bind.password");
		String ldapSearchDn = request.getServletContext().getInitParameter("ldap.search.dn");
		
		try (LdapConnection connection = new LdapNetworkConnection(ldapServer)) {
			connection.bind(ldapBindName,ldapBindPassword);
			
			Dn searchDn = new Dn(ldapSearchDn);
			String[] attributes = {"memberOf","cn","givenName","sn","sAMAccountName","userPrincipalName"};
			EntryCursor cursor = connection.search( searchDn, "(sAMAccountName=" + username + ")", SearchScope.SUBTREE, attributes);
			
			for (Entry entry : cursor ) {		
				Set<String> memberOfSet = new HashSet<>();
				String memberOf = entry.get("memberOf").toString();
				String[] memberOfArray = memberOf.replaceAll("memberOf: ", "").split("\n");
				for(String singleMemberOf : memberOfArray) {
					String memberOfName = singleMemberOf.substring(3, singleMemberOf.indexOf(",OU="));
					memberOfSet.add(memberOfName);
					System.out.println("Added role group: " + memberOfName);
				}
				
				try {
					connection.bind(entry.getDn(), password);
					System.out.println("Password is valid");
					String[] groups = memberOfSet.toArray(String[]::new);
					String token = TokenUtil.generateJWT(username, groups);
				    String[] parts = token.split("\\.");
			        System.out.println(String.format("\nJWT Header - %s", new String(Base64.getDecoder().decode(parts[0]), StandardCharsets.UTF_8)));
			        System.out.println(String.format("\nJWT Claims - %s", new String(Base64.getDecoder().decode(parts[1]), StandardCharsets.UTF_8)));
			        System.out.println(String.format("\nGenerated JWT Token \n%s\n", token));
					
					return Response.ok(token).build();
				} catch(LdapAuthenticationException e) {
					System.out.println("Invalid password");
					return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
				}
			}
			// Close the connection
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().entity(e.getMessage()).build();
		}
		return Response.serverError().build();
	}

	@GET
	@Path("/{principal}")
	public Response generateJWT(
		@PathParam("principal") final String principal, 
		@QueryParam("groups") final List<String> groups) {
		
		System.out.println(groups.size());
		
		try {
			String token = TokenUtil.generateJWT(principal, groups.toArray(String[]::new));
			return Response.ok(token).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}		
	}


}
