package com.example.multidatasoure.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import liquibase.integration.spring.SpringLiquibase;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.example.multidatasoure.repository.secondary",
        entityManagerFactoryRef = "secondaryEntityManagerFactory",
        transactionManagerRef = "secondaryTransactionManager"
)
public class SecondaryDataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.secondary")
    public DataSourceProperties secondaryDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource secondaryDataSource() {
        return secondaryDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean
      public LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactory() {
          LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
          emf.setDataSource(secondaryDataSource());
          emf.setPackagesToScan("com.example.multidatasoure.entity.secondary");
          emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
          return emf;
      }

      @Bean
      public SpringLiquibase secondaryLiquibase() {
          SpringLiquibase liquibase = new SpringLiquibase();
          liquibase.setDataSource(secondaryDataSource());
          liquibase.setChangeLog("classpath:db/changelog/db.changelog-secondary.xml");
          return liquibase;
      }

    @Bean
    public PlatformTransactionManager secondaryTransactionManager(
            final @Qualifier("secondaryEntityManagerFactory") LocalContainerEntityManagerFactoryBean emf) {
        return new JpaTransactionManager(emf.getObject());
    }
}
