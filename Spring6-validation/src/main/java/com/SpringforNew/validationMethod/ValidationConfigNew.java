package com.SpringforNew.validationMethod;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@Configuration
@ComponentScan("com.SpringforNew.validationMethod")
public class ValidationConfigNew {
    @Bean
    public MethodValidationPostProcessor getValidator(){
        return new MethodValidationPostProcessor();
    }
}
