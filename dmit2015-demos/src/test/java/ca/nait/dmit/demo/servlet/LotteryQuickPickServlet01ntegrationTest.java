package ca.nait.dmit.demo.servlet;

import static org.junit.jupiter.api.Assertions.*;

import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonReader;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LotteryQuickPickServlet01ntegrationTest {
    private static String URL;

    @BeforeAll
    public static void init() {
    	System.setProperty("http.port", "8080");
    	System.setProperty("war.name", "dmit2015-demos");
        String port = System.getProperty("http.port");
        String war = System.getProperty("war.name");
        URL = "http://localhost:" + port + "/" + war + "/" + "LottoQuickPickServlet01";
    }

    @Test
    public void testHtmlResponset() throws Exception {
    	System.out.println(URL);
    	
    	HttpClient client = HttpClient.newHttpClient();
    	
    	HttpRequest requestGet = HttpRequest.newBuilder()
    		.GET()
    		.uri(URI.create(URL))
    		.build();
   	
   		HttpResponse<String> response = client.send(requestGet, HttpResponse.BodyHandlers.ofString());
    	
   		System.out.println(response);
  
   		int statusCode = response.statusCode();
   		
   		assertEquals(HttpServletResponse.SC_OK, statusCode, "HTTP GET failed");
   		
   		String responseBody = response.body();
   		
   		assertTrue(responseBody.contains("The LOTTO MAX quick pick numbers are"),"Unexpected response body");
   		
    }
    
    @Test
    public void testTextResponset() throws Exception {
    	System.out.println(URL);
    	
    	HttpClient client = HttpClient.newHttpClient();
    	
    	HttpRequest requestGet = HttpRequest.newBuilder()
    		.GET()
    		.uri(URI.create(URL))
    		.header("Content-Type", "text/plain")
    		.build();
   	
   		HttpResponse<String> response = client.send(requestGet, HttpResponse.BodyHandlers.ofString());
    	
   		System.out.println(response);
  
   		int statusCode = response.statusCode();
   		
   		assertEquals(HttpServletResponse.SC_OK, statusCode, "HTTP GET failed");
   		
   		String responseBody = response.body();
   		
   		String[] quickPickNumbersArray = responseBody.split(",");
   		
   		assertEquals(7, quickPickNumbersArray.length);
   		System.out.print("Text data: ");
   		Arrays.asList( quickPickNumbersArray ).stream()
   			.forEach(num -> System.out.print(num + " "));
   		
    }
    
    @Test
    public void testJsonDataResponse() throws Exception {
    	System.out.println(URL);
    	
    	HttpClient client = HttpClient.newHttpClient();
    	
    	HttpRequest requestGet = HttpRequest.newBuilder()
    		.GET()
    		.uri(URI.create(URL))
    		.header("Content-Type", "application/json")
    		.build();
   	
   		HttpResponse<String> response = client.send(requestGet, HttpResponse.BodyHandlers.ofString());
    	
   		System.out.println(response);
  
   		int statusCode = response.statusCode();
   		
   		assertEquals(HttpServletResponse.SC_OK, statusCode, "HTTP GET failed");
   		
   		String responseBody = response.body();
   		
   		JsonReader reader = Json.createReader(new StringReader(responseBody));
   		JsonArray jsonArray = reader.readArray();
   		
   		assertEquals(7, jsonArray.size());
   		System.out.print("JSON data: ");
   		jsonArray.stream()
			.forEach(num -> System.out.print(num + " "));

    }
}