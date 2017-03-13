package com.sri.campaigns.config.db;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.sri.campaigns.model"})
@EnableJpaRepositories(basePackages = {"com.sri.campaigns.repository"})
@EnableTransactionManagement
public class RepositoryConfig {
}
