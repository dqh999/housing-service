package com.example.housing_service.Business.Exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = "classpath:exception.properties", ignoreResourceNotFound = true)
public class MappingException {
    @Autowired
    Environment environment;
    public String getErrorMessage (String errorCode) {
        String errorMessage =
                environment.getProperty("T-02");
        return errorMessage;
    }
}
