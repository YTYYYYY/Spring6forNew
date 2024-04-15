package com.SpringforNew.validationAnno;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
@ComponentScan("com.SpringforNew.validationAnno")
public class ValidationConfig {
    @Bean
    public LocalValidatorFactoryBean getValidator(){
        return new LocalValidatorFactoryBean();
    }
}
