package team18.discordbettracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@PropertySource( "classpath:/application.properties")
public class HsqlDataSource {

	@Bean
	public DataSource dataSource(Environment env) {
		var ds = new DriverManagerDataSource();
		ds.setDriverClassName("org.hsqldb.jdbcDriver");
		ds.setUrl(env.getProperty("hsql.url"));

		return ds;
	}

	@Bean("dialect")
	public String dialect() {
		return "org.hibernate.dialect.HSQLDialect";
	}
}
