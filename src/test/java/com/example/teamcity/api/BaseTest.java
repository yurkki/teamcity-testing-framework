package com.example.teamcity.api;

import api.generators.TestDataStorage;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected SoftAssertions softy;
    public TestDataStorage testDataStorage;

    @BeforeMethod
    public void beforeTest(){
        softy = new SoftAssertions();
        testDataStorage = TestDataStorage.getStorage();
    }

    @AfterMethod
    public void afterTest(){
       softy.assertAll();
    }
}
