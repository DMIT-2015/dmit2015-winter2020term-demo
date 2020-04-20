package dmit2015.resource;

import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;

public class BearerTokenFilter implements ClientRequestFilter {

	private final String token;
		
	public BearerTokenFilter(String token) {
		this.token = token;
	}

	@Override
	public void filter(ClientRequestContext requestContext) throws IOException {
		requestContext.getHeaders().add("Authorization", "Bearer " + token);
	}

}
