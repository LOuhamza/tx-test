package com.baeldung.multipledb.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration//("EdpDataSource")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "org.baeldung.multipledb.product.repository",
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "edpTransactionManager"
)
public class EdpDataSourceConfig {
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.edp")
    public DataSourceProperties edpDataSourceProperties(){ return new DataSourceProperties(); }

    @Bean
    @Primary
    public DataSource edpDataSource(){
        return edpDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Bean(name = "entityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean edpEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(edpDataSource())
                .packages("org.baeldung.multipledb.product.entity")
                .build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager edpTransactionManager(
            final @Qualifier("entityManagerFactory") LocalContainerEntityManagerFactoryBean edpEntityManagerFactory) {
        return new JpaTransactionManager(edpEntityManagerFactory.getObject());
    }
}
