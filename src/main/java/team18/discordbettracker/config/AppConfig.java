package team18.discordbettracker.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan("team18.discordbettracker")
@EnableJpaRepositories(basePackages = "team18.discordbettracker.repository")
@EnableTransactionManagement
public class AppConfig {

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, @Qualifier("dialect") String dialect) {
		var emf = new LocalContainerEntityManagerFactoryBean();

		emf.setDataSource(dataSource);
		emf.setPackagesToScan("team18.discordbettracker.model");

		emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

		emf.setJpaProperties(additionalProperties(dialect));

		return emf;
	}

	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}

	private Properties additionalProperties(String dialect) {
		var properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "none");
		properties.setProperty("hibernate.dialect", dialect);
		properties.setProperty("hibernate.show_sql", "true");
		properties.setProperty("hibernate.format_sql", "true");

		return properties;
	}
}
