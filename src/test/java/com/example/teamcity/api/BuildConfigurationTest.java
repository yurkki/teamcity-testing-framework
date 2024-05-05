package com.example.teamcity.api;

import api.requests.checked.CheckedProject;
import api.requests.checked.CheckedUser;
import api.specifications.Specifications;
import org.testng.annotations.Test;

public class BuildConfigurationTest extends BaseAPITest {

    @Test
    void buildConfigurationTest() {
        var testData = testDataStorage.addTestData();

        new CheckedUser(Specifications.getSpec().superUserSpec())
                .create(testData.getUser());
        var project = new CheckedProject(Specifications.getSpec().superUserSpec())
                .create(testData.getProject());
        softy.assertThat(project.getId()).isEqualTo(testData.getProject().getId());
    }
}
