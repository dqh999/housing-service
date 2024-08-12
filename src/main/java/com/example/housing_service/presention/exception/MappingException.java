package com.example.housing_service.presention.exception;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = "classpath:exception.properties", ignoreResourceNotFound = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class MappingException {
    Environment environment;
    public String getErrorMessage (String errorCode) {
        return environment.getProperty(errorCode);
    }
}
