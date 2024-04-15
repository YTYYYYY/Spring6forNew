package com.SpringforNew.myfactorybean;

import org.springframework.beans.factory.FactoryBean;

public class Myfbean implements FactoryBean<User> {
    @Override
    public User getObject() throws Exception {return new User();}
    @Override
    public Class<?> getObjectType() {return User.class;}
}
