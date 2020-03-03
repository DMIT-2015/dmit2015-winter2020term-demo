package ca.edmonton.data.jpa;

import javax.enterprise.context.ApplicationScoped;
import javax.annotation.sql.DataSourceDefinition;
import javax.annotation.sql.DataSourceDefinitions;

@DataSourceDefinitions({
	@DataSourceDefinition(
		name="java:app/datasources/h2databaseDS",
		className="org.h2.jdbcx.JdbcDataSource",
		url="jdbc:h2:mem:dmit2015db",
		user="sa",
		password="sa"),
	@DataSourceDefinition(
		name="java:app/datasources/mssqlDS",
		className="com.microsoft.sqlserver.jdbc.SQLServerDataSource",
		url="jdbc:sqlserver://localhost;databaseName=DMIT2015DB",
		user="user2015",
		password="Password2015"),
})

@ApplicationScoped
public class DatabaseConfig {

}
