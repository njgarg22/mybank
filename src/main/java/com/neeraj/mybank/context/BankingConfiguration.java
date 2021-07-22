package com.neeraj.mybank.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.neeraj.mybank.ApplicationLauncher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

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

    @Bean
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());

        viewResolver.setOrder(1); // optional
        viewResolver.setViewNames(new String[] {"*.html", "*.xhtml"}); // optional
        return viewResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() { // Returns org.thymeleaf.spring5.SpringTemplateEngine;
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());

        // Required to print "timestamp" in account.html
        // <td th:text="${#temporals.format(transaction.timestamp, 'dd-MM-yyyy HH:mm')}">[Timestamp]</td>
        // Without this, "temporals" method won't be available.
        templateEngine.addDialect(new Java8TimeDialect());
        return templateEngine;
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver() { // Returns org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix("classpath:/templates/");
        templateResolver.setCacheable(false);
        return templateResolver;
    }
}
