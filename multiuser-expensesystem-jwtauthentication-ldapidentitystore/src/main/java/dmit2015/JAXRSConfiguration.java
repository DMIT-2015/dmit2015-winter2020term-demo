package dmit2015;

import javax.annotation.security.DeclareRoles;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.eclipse.microprofile.auth.LoginConfig;

import common.security.jwt.Roles;

@ApplicationPath("webapi")
@LoginConfig(authMethod="MP-JWT", realmName="MP JWT Realm")
@DeclareRoles({Roles.EXECUTIVE, Roles.SALES_MANAGER, Roles.SALES_REP,"Software Developer"})
public class JAXRSConfiguration extends Application {

}