package common.security.config;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;

import org.glassfish.soteria.identitystores.annotation.Credentials;
import org.glassfish.soteria.identitystores.annotation.EmbeddedIdentityStoreDefinition;

@EmbeddedIdentityStoreDefinition({
	
	@Credentials(callerName = "user2015", password = "Password2015",groups = {"USER","ADMIN"}),
	@Credentials(callerName = "dmit2015", password = "Password2015",groups = "USER"),
	@Credentials(callerName = "admin2015", password = "Password2015", groups = "ADMIN"),

})

@BasicAuthenticationMechanismDefinition(
	realmName = "jaspitest"
)

@ApplicationScoped
public class ApplicationSecurityConfig {
}