package com.neeraj.mybank.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.neeraj.mybank.ApplicationLauncher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackageClasses = ApplicationLauncher.class)
@PropertySource("classpath:application.properties")
@EnableWebMvc
public class BankingConfiguration {

    @Bean
    public ObjectMapper objectMapper(){
        // If you just do `return new ObjectMapper()`, then the "timestamp" in JSON response would mess up.
        return new ObjectMapper().registerModule(new JavaTimeModule());
    }

    // No need to define bean for MethodValidationPostProcessor as of now.


}
