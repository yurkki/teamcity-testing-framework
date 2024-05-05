package com.example.teamcity.api;

import api.generators.TestDataStorage;
import api.requests.CheckedRequests;
import api.requests.UncheckedRequests;
import api.specifications.Specifications;
import org.testng.annotations.*;

public class BaseAPITest extends BaseTest {

    protected TestDataStorage testDataStorage;
    protected CheckedRequests checkedRequestsWithSuperUser = new CheckedRequests(Specifications.getSpec().superUserSpec());
    protected UncheckedRequests uncheckedRequestsWithSuperUser = new UncheckedRequests(Specifications.getSpec().superUserSpec());

    @BeforeMethod
    public void setupTest(){
        testDataStorage = TestDataStorage.getStorage();
    }

    @AfterMethod
    public void cleanTest(){
        testDataStorage.delete();
    }
}
