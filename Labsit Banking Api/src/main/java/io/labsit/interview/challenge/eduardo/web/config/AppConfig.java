package io.labsit.interview.challenge.eduardo.web.config;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import io.labsit.interview.challenge.eduardo.web.api.service.ClientAccountService;
import io.labsit.interview.challenge.eduardo.web.api.service.ClientService;
import io.labsit.interview.challenge.eduardo.web.api.service.account.ClientAccountImpl;
import io.labsit.interview.challenge.eduardo.web.api.service.client.ClientServiceImpl;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableWebMvc
@EnableSwagger2
@EnableTransactionManagement
@EnableJpaRepositories("io.labsit.interview.challenge.eduardo.web.api.repository")
@ComponentScan(basePackages = { "io.labsit.interview.challenge.eduardo.web" })
public class AppConfig implements WebMvcConfigurer{
	
	@PostConstruct
	public void init() {
		
	}
	
	@Bean("clientService")
	public ClientService getClientService() {
		return new ClientServiceImpl();
	}
	
	@Bean("clientAccountService")
	public ClientAccountService getClientAccountService() {
		return new ClientAccountImpl();
	}
	
	@Bean
	LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean lfb = new LocalContainerEntityManagerFactoryBean();
		lfb.setDataSource(dataSource());
		lfb.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		lfb.setPackagesToScan("io.labsit.interview.challenge.eduardo.web");
		lfb.setJpaProperties(hibernateProps());
		return lfb;
	}

	@Bean
	DataSource dataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setUrl("jdbc:h2:file:~/labsit;INIT=RUNSCRIPT FROM '~/labSitBDInit.sql'");
		ds.setUsername("as");
		ds.setPassword("");
		ds.setDriverClassName("org.h2.Driver");
		return ds;
	}

	Properties hibernateProps() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		properties.setProperty("hibernate.show_sql", "true");
		//properties.setProperty("hibernate.hbm2ddl.auto", "update");
		
		return properties;
	}

	@Bean
	JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}
	
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
}
