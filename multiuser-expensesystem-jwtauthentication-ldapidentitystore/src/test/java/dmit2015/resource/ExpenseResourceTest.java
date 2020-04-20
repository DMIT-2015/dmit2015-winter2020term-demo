package dmit2015.resource;

import static org.junit.jupiter.api.Assertions.*;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
class ExpenseResourceTest {

	final String BASE_URI_EXPENSES = "https://localhost:8443/webapi/expenses";
	final String BASE_URI_JWT = "https://localhost:8443/webapi/jwt/";
	
	// http://adambien.blog/roller/abien/entry/jax_rs_client_javax_net
	// https://docs.oracle.com/javase/tutorial/security/toolsign/rstep2.html
	private Client jaxrsClient;
	
	@BeforeEach
	public void init() throws KeyManagementException, NoSuchAlgorithmException {
		TrustManager[] noopTrustManager = new TrustManager[] {
			new X509TrustManager() {
				
				@Override
				public X509Certificate[] getAcceptedIssuers() {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					// TODO Auto-generated method stub
					
				}
			}
		};

		SSLContext sc = SSLContext.getInstance("ssl");
		sc.init(null, noopTrustManager, null);

		this.jaxrsClient = ClientBuilder.newBuilder().sslContext(sc).build();
				
	}
	
	Long deleteId = 0L;
	
	@Test
	@Order(1)
	void testCreateForExecutive() {
		WebTarget jwtResource = jaxrsClient.target(BASE_URI_JWT).path("formLogin");
		Form loginForm = new Form();
		loginForm.param("j_username", "dmurphy");
		loginForm.param("j_password", "Password2015");
		Entity<Form> entity = Entity.form(loginForm);
		Response jwtResponse = jwtResource.request().post(entity);
		assertEquals(Status.OK.getStatusCode(), jwtResponse.getStatus());
		String bearerToken = jwtResponse.readEntity(String.class);
		jwtResponse.close();
		
		jaxrsClient.register(new BearerTokenFilter(bearerToken));
		
		WebTarget eventsResource = jaxrsClient.target(BASE_URI_EXPENSES);
		JsonObject event = Json.createObjectBuilder()
			.add("description", "Item 1")
			.add("amount", 1.11)
			.add("date", "2020-04-01")
			.build();
		
		Response eventsResponse = eventsResource
				.request(MediaType.APPLICATION_JSON)
				.post( Entity.entity(event, MediaType.APPLICATION_JSON));
		assertEquals(Status.CREATED.getStatusCode(), eventsResponse.getStatus());
		String location = eventsResponse.getHeaderString("location");
		System.out.println("Location = " + location);
		deleteId = Long.parseLong(location.substring(location.lastIndexOf("/") + 1));
		System.out.println("DeleteID: " + deleteId);
		eventsResponse.close();
	}
	
	@Test
	@Order(2)
	void testCreateForSalesManager() {
		WebTarget jwtResource = jaxrsClient.target(BASE_URI_JWT).path("formLogin");
		Form loginForm = new Form();
		loginForm.param("j_username", "abow");
		loginForm.param("j_password", "Password2015");
		Entity<Form> entity = Entity.form(loginForm);
		Response jwtResponse = jwtResource.request().post(entity);
		assertEquals(Status.OK.getStatusCode(), jwtResponse.getStatus());
		String bearerToken = jwtResponse.readEntity(String.class);
		jwtResponse.close();
		
		jaxrsClient.register(new BearerTokenFilter(bearerToken));
		
		WebTarget eventsResource = jaxrsClient.target(BASE_URI_EXPENSES);
		JsonObject event = Json.createObjectBuilder()
				.add("description", "Item 2")
				.add("amount", 2.22)
				.add("date", "2020-04-02")
				.build();
		
		Response eventsResponse = eventsResource
				.request(MediaType.APPLICATION_JSON)
				.post( Entity.entity(event, MediaType.APPLICATION_JSON));
		assertEquals(Status.CREATED.getStatusCode(), eventsResponse.getStatus());
		eventsResponse.close();
	}
	
	@Test
	@Order(3)
	void testCreateForSalesRep() {
		WebTarget jwtResource = jaxrsClient.target(BASE_URI_JWT).path("formLogin");
		Form loginForm = new Form();
		loginForm.param("j_username", "afixter");
		loginForm.param("j_password", "Password2015");
		Entity<Form> entity = Entity.form(loginForm);
		Response jwtResponse = jwtResource.request().post(entity);
		assertEquals(Status.OK.getStatusCode(), jwtResponse.getStatus());
		String bearerToken = jwtResponse.readEntity(String.class);
		jwtResponse.close();
		
		jaxrsClient.register(new BearerTokenFilter(bearerToken));
		
		WebTarget eventsResource = jaxrsClient.target(BASE_URI_EXPENSES);
		JsonObject event = Json.createObjectBuilder()
				.add("description", "Item 3")
				.add("amount", 3.33)
				.add("date", "2020-04-03")
				.build();
		
		Response eventsResponse = eventsResource
				.request(MediaType.APPLICATION_JSON)
				.post( Entity.entity(event, MediaType.APPLICATION_JSON));
		assertEquals(Status.CREATED.getStatusCode(), eventsResponse.getStatus());
		eventsResponse.close();
	}
		
	@Order(4)
	@Test
	void testListAll() {
		WebTarget resource = jaxrsClient.target(BASE_URI_EXPENSES);
		
		Response response = resource.request(MediaType.APPLICATION_JSON).get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		JsonArray events = response.readEntity(JsonArray.class);
		assertEquals(3, events.size());
				
	}
	
	@Order(5)
	@Test
	void testListAllUser() {
		WebTarget jwtResource = jaxrsClient.target(BASE_URI_JWT).path("formLogin");
		Form loginForm = new Form();
		loginForm.param("j_username", "dmurphy");
		loginForm.param("j_password", "Password2015");
		Entity<Form> entity = Entity.form(loginForm);
		Response jwtResponse = jwtResource.request().post(entity);
		assertEquals(Status.OK.getStatusCode(), jwtResponse.getStatus());
		String bearerToken = jwtResponse.readEntity(String.class);
		jwtResponse.close();
		
		jaxrsClient.register(new BearerTokenFilter(bearerToken));
		WebTarget resource = jaxrsClient.target(BASE_URI_EXPENSES).path("user");
		
		Response response = resource.request(MediaType.APPLICATION_JSON).get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		JsonArray events = response.readEntity(JsonArray.class);
		assertEquals(1, events.size());
				
	}
	
	
	@Order(6)
	@Test
	void testFindOne() {
		WebTarget resource = jaxrsClient.target(BASE_URI_EXPENSES);
		Response response = resource.path("{id}").resolveTemplate("id", 1L).request().get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		JsonObject event = response.readEntity(JsonObject.class);
		System.out.println(event);
		assertNotNull(event);
		assertEquals("Item 1", event.getString("description"));
		assertEquals(1.11, event.getJsonNumber("amount").doubleValue());
		assertEquals("2020-04-01", event.getString("date"));
//		assertEquals("2020-04-20 7:40 AM", event.getString("dateCreated"));
		
		response = resource.path("{id}").resolveTemplate("id", 0).request().get();
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
		
	}
	
	@Order(7)
	@Test
	void testDelete() {
		WebTarget jwtResource = jaxrsClient.target(BASE_URI_JWT).path("formLogin");
		Form loginForm = new Form();
		loginForm.param("j_username", "abow");
		loginForm.param("j_password", "Password2015");
		Entity<Form> entity = Entity.form(loginForm);
		Response jwtResponse = jwtResource.request().post(entity);
		assertEquals(Status.OK.getStatusCode(), jwtResponse.getStatus());
		String bearerToken = jwtResponse.readEntity(String.class);
		jwtResponse.close();
		
		jaxrsClient.register(new BearerTokenFilter(bearerToken));
		
		WebTarget resource = jaxrsClient.target(BASE_URI_EXPENSES);
		Long deleteId = 1L;
		Response response = resource.path("{id}").resolveTemplate("id", deleteId).request().delete();
		assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());
		
	}
	
	@Order(8)
	@Test
	void testValidJwt() {
		WebTarget jwtResource = jaxrsClient.target(BASE_URI_JWT).path("formLogin");
		Form loginForm = new Form();
		loginForm.param("j_username", "bjones");
		loginForm.param("j_password", "Password2015");
		Entity<Form> entity = Entity.form(loginForm);
		Response jwtResponse = jwtResource.request().post(entity);
		assertEquals(Status.OK.getStatusCode(), jwtResponse.getStatus());
		String bearerToken = jwtResponse.readEntity(String.class);
		jwtResponse.close();
	}
	
	@Order(9)
	@Test
	void testInvalidJwt() {
		WebTarget jwtResource = jaxrsClient.target(BASE_URI_JWT).path("formLogin");
		Form loginForm = new Form();
		loginForm.param("j_username", "bjones");
		loginForm.param("j_password", "Password123");
		Entity<Form> entity = Entity.form(loginForm);
		Response jwtResponse = jwtResource.request().post(entity);
		assertEquals(Status.BAD_REQUEST.getStatusCode(), jwtResponse.getStatus());
		jwtResponse.close();
	}
}
