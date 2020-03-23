package common.config;

import javax.enterprise.context.ApplicationScoped;
import javax.annotation.sql.DataSourceDefinition;
import javax.annotation.sql.DataSourceDefinitions;

@DataSourceDefinitions({

	@DataSourceDefinition(name = "java:app/datasources/h2databaseDS", 
			className = "org.h2.jdbcx.JdbcDataSource", 
			url = "jdbc:h2:file:~/dmit2015db", 
			user = "sa", 
			password = "sa"), })

@ApplicationScoped
public class DatabaseConfig {

}