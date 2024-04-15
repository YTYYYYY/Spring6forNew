package com.SpringforNew.validatorImpl;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class PersonValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }
    @Override   //校验规则
    public void validate(Object target, Errors errors) {
        //name不为空
        ValidationUtils.rejectIfEmpty(errors,"name","name.empty","用户名空");
        //age大于0小于150
        Person person = (Person)target;
        if(person.getAge() < 0){
            errors.rejectValue("age","age.value.error","age < 0");
        }else if(person.getAge() > 150){
            errors.rejectValue("age","age.value.error","age > 150");
        }
    }
}
