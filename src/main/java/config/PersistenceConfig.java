package config;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@SuppressWarnings("restriction")
@Configuration
@PropertySource(ResourceNames.PROPERTIES)
public class PersistenceConfig {

	@Autowired
	private Environment environment;

	private String driver;
	private String url;
	private String username;
	private String password;

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	@PostConstruct
	private void init() {
		driver = environment.getProperty("datasource.driver");
		url = environment.getProperty("datasource.url");
		username = environment.getProperty("datasource.username");
		password = environment.getProperty("datasource.password");
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driver);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		LogManager.getLogger(this.getClass()).info("Datasource: " + dataSource.getUrl());
		return dataSource;
	}

}
