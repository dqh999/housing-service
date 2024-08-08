package com.example.housing_service.Business.Service;

import com.example.housing_service.Business.Constants.Constants;
import com.example.housing_service.Business.Exception.HousingException;

public class TestService {
    public void test(){
        throw new HousingException(Constants.TEST_EXCEPTION);
    }
}
