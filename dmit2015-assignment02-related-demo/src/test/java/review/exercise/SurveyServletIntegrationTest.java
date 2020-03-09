package review.exercise;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SurveyServletIntegrationTest {

	private static String URL;

    @BeforeAll
    public static void init() {
        URL = "http://localhost:8080//servlet/SurveyServlet";
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
    public void testSurveyForm() throws Exception {
    	HttpClient client = HttpClient.newHttpClient();

    	Map<String, Object> data = new HashMap<>();
		data.put("name", "Java One");
		data.put("age", 25);
		data.put("gender", "ALIEN");
		data.put("operatingSystemsUsed", "Linux,MacOS");
		data.put("languagesUsed", "Java,Kotlin");

		HttpRequest requestPost = HttpRequest.newBuilder()
        	.uri(URI.create(URL))
    		.header("Content-Type", "application/x-www-form-urlencoded")
    		.header("Accept", "application/json")
    		.version(HttpClient.Version.HTTP_1_1)
    		.POST(ofFormData(data))
       	  	.build();

   		HttpResponse<String> response = client.send(requestPost, HttpResponse.BodyHandlers.ofString());

   		int statusCode = response.statusCode();

   		assertEquals(HttpServletResponse.SC_OK, statusCode, "HTTP POST failed");

   		String responseBody = response.body();

  		Jsonb jsonb = JsonbBuilder.create();
		Survey responseSurvey = jsonb.fromJson(responseBody, Survey.class);
		assertEquals("Java One", responseSurvey.getName());
		assertEquals(25, responseSurvey.getAge());
		assertEquals("ALIEN", responseSurvey.getGender().toString());
		assertEquals("Linux,MacOS", responseSurvey.getOperatingSystemsUsed());
		assertEquals("Java,Kotlin", responseSurvey.getLanguagesUsed());

    }

}
