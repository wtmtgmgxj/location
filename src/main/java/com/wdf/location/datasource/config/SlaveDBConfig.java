package com.wdf.location.datasource.config;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.ConnectionReleaseMode;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.dialect.MySQL5Dialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "slaveEntityManagerFactory",
		transactionManagerRef = "slaveTransactionManager",
		basePackages = { "com.wdf.location.datasource.repository.slave" })
@ConfigurationProperties("slave.datasource")
public class SlaveDBConfig {

	@ConfigurationProperties(prefix = "slave.datasource")
	@Bean(name = "slaveHikariConfig")
	public HikariConfig hikariConfig() {
		HikariConfig hikariConfig = new HikariConfig();
		return hikariConfig;

	}

	@Bean(name = "slaveDataSource")
	public DataSource dataSource(@Qualifier("slaveHikariConfig") HikariConfig hikariConfig) {
		return new HikariDataSource(hikariConfig);
	}

	protected Map<String, Object> jpaProperties() {
		Map<String, Object> props = new HashMap<String, Object>();
		props.put("hibernate.physical_naming_strategy", SpringPhysicalNamingStrategy.class.getName());
		props.put("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());
		props.put("hibernate.naming-strategy", ImprovedNamingStrategy.class.getName());
		props.put("hibernate.connection.release_mode", ConnectionReleaseMode.AFTER_TRANSACTION);
		props.put("hibernate.connection.handling_mode", "DELAYED_ACQUISITION_AND_RELEASE_AFTER_TRANSACTION");
		// props.put("hibernate.dialect", MySQL5Dialect.class.getName());
		// props.put("hibernate.connection.provider_class",
		// org.hibernate.hikaricp.internal.HikariCPConnectionProvider.class.getName());
		// props.put("hibernate.show_sql", true);
		return props;
	}

	@Bean(name = "slaveEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("slaveDataSource") DataSource dataSource) {
		return builder.dataSource(dataSource)

				.packages("com.wdf.location.datasource.model").properties(jpaProperties()).build();
	}

	@Bean(name = "slaveTransactionManager")
	public PlatformTransactionManager transactionManager(
			@Qualifier("slaveEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

}
