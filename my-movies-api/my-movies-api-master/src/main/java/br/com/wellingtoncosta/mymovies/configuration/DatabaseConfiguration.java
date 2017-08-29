package br.com.wellingtoncosta.mymovies.configuration;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

/**
 * @author Wellington Costa on 29/04/17.
 */
@Configuration
@EnableJpaRepositories
@EnableTransactionManagement
@ComponentScan({ "br.com.wellingtoncosta.mymovies" })
public class DatabaseConfiguration {

    @FlywayDataSource
    @Bean(destroyMethod = "close")
    public DataSource dataSource(DataSourceProperties dataSourceProperties) throws Exception {
        HikariConfig config = new HikariConfig();

        config.setDriverClassName(dataSourceProperties.getDriverClassName());
        config.setJdbcUrl(dataSourceProperties.getUrl());
        config.setUsername(dataSourceProperties.getUsername());
        config.setPassword(dataSourceProperties.getPassword());

        return new HikariDataSource(config);
    }

    @Bean
    public JPAQueryFactory queryFactory(EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }

}
