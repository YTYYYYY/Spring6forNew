package com.SpringforNew.ResourceLoaderAwareDemo;

import org.springframework.core.io.Resource;

public class User {
    private Resource resource;

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
