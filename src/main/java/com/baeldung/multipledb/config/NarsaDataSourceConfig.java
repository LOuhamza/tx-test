package com.baeldung.multipledb.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.baeldung.multipledb.user.repository",
        entityManagerFactoryRef = "narsaEntityManagerFactory",
        transactionManagerRef = "narsaTransactionManager"
)
public class NarsaDataSourceConfig {
    @Bean
    @ConfigurationProperties("spring.datasource.narsa")
    public DataSourceProperties narsaDataSourceProperties(){
        return new DataSourceProperties();
    }

    @Bean
    public DataSource narsaDataSource(){
        return narsaDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Bean(name = "narsaEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean narsaEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(narsaDataSource())
                .packages("com.baeldung.multipledb.user.entity")
                .build();
    }

    @Bean
    public PlatformTransactionManager narsaTransactionManager(
            final @Qualifier("narsaEntityManagerFactory") LocalContainerEntityManagerFactoryBean narsaEntityManagerFactory) {
        return new JpaTransactionManager(narsaEntityManagerFactory.getObject());
    }
}
