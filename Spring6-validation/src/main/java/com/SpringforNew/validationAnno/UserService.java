package com.SpringforNew.validationAnno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import org.springframework.validation.Validator;

@Service
public class UserService {
    @Autowired
    private Validator validator;

    public boolean validatorByUser(User user){
        BindException bindException = new BindException(user,user.getName());
        validator.validate(user,bindException);
        return bindException.hasErrors();
    }
}
