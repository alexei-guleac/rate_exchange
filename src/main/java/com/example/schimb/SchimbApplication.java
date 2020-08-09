package com.example.schimb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;
import java.util.Date;


@SpringBootApplication()
@ComponentScan(basePackages = {"com.example.schimb.*"})
@EnableJpaRepositories
@PropertySources({@PropertySource("classpath:application.properties"),
        // database configuration file
        @PropertySource("classpath:postgresql-config.properties"),
        // documentation
        @PropertySource("classpath:documentation.properties")})
public class SchimbApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchimbApplication.class, args);
    }

}
