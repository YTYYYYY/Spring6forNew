package com.SpringforNew.validationMethod;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class UserService {
    public String testMethod(@NotNull @Valid User user){
        return user.toString();
    }
}
