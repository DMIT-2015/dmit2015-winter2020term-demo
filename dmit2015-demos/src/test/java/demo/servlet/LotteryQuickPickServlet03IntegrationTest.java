package demo.servlet;
import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import demo.servlet.LottoQuickPick;

public class LotteryQuickPickServlet03IntegrationTest {
    private static String URL;

    @BeforeAll
    public static void init() {
        URL = "http://localhost:8080/servlet/LottoQuickPickServlet03";
    }

    // https://golb.hplar.ch/2019/01/java-11-http-client.html
	public static BodyPublisher ofFormData(Map<String, Object> data) {
		var builder = new StringBuilder();
		for (Map.Entry<String, Object> entry : data.entrySet()) {
			if (builder.length() > 0) {
				builder.append("&");
			}
			builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
			builder.append("=");
			builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
		}
		return BodyPublishers.ofString(builder.toString());
	}

    @Test
    public void testLottoMaxQuickPick() throws Exception {
    	HttpClient client = HttpClient.newHttpClient();

    	Map<String, Object> data = new HashMap<>();
		data.put("game", "LOTTO_MAX");
		data.put("quantity", 3);

		HttpRequest requestPost = HttpRequest.newBuilder()
        	.uri(URI.create(URL))
    		.header("Content-Type", "application/x-www-form-urlencoded")
    		.header("Accept", "application/json")
    		.version(HttpClient.Version.HTTP_1_1)
    		.POST(ofFormData(data))
       	  	.build();

   		HttpResponse<String> response = client.send(requestPost, HttpResponse.BodyHandlers.ofString());

   		System.out.println(response);

   		int statusCode = response.statusCode();

   		assertEquals(HttpServletResponse.SC_OK, statusCode, "HTTP POST failed");

   		String responseBody = response.body();

  		assertTrue(responseBody.contains("LOTTO_MAX"),"Unexpected response body");
  		
  		Jsonb jsonb = JsonbBuilder.create();
		LottoQuickPick quickPick = jsonb.fromJson(responseBody, LottoQuickPick.class);
		System.out.println(quickPick);
		assertEquals(3, quickPick.getQuantity());
		assertEquals("LOTTO_MAX", quickPick.getGame().toString());
		assertEquals(3, quickPick.getQuickPicks().size());
		
    }
    
    @Test
    public void testLotto649QuickPick() throws Exception {
    	HttpClient client = HttpClient.newHttpClient();

    	Map<String, Object> data = new HashMap<>();
		data.put("game", "LOTTO_649");
		data.put("quantity", 5);

		HttpRequest requestPost = HttpRequest.newBuilder()
        	.uri(URI.create(URL))
    		.header("Content-Type", "application/x-www-form-urlencoded")
    		.header("Accept", "application/json")
    		.version(HttpClient.Version.HTTP_1_1)
    		.POST(ofFormData(data))
       	  	.build();

   		HttpResponse<String> response = client.send(requestPost, HttpResponse.BodyHandlers.ofString());

   		System.out.println(response);

   		int statusCode = response.statusCode();

   		assertEquals(HttpServletResponse.SC_OK, statusCode, "HTTP POST failed");

   		String responseBody = response.body();

  		assertTrue(responseBody.contains("LOTTO_649"),"Unexpected response body");
  		
  		Jsonb jsonb = JsonbBuilder.create();
		LottoQuickPick quickPick = jsonb.fromJson(responseBody, LottoQuickPick.class);
		System.out.println(quickPick);
		assertEquals(5, quickPick.getQuantity());
		assertEquals("LOTTO_649", quickPick.getGame().toString());
		assertEquals(5, quickPick.getQuickPicks().size());
		
    }
}
