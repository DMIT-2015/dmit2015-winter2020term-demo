package common.security.config;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;

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

//@CustomFormAuthenticationMechanismDefinition(
//	loginToContinue = @LoginToContinue(
//		loginPage="/login/login.html", 
//		useForwardToLogin = false,
//		errorPage=""
//	)
//)

@ApplicationScoped
public class ApplicationSecurityConfig {
}