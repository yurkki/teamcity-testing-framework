package com.example.teamcity.api;

import api.enums.RoleEnum;
import api.generators.TestDataGenerator;
import api.requests.checked.CheckedBuildConfig;
import api.requests.checked.CheckedProject;
import api.requests.checked.CheckedUser;
import api.requests.unchecked.UncheckedBuildConfig;
import api.requests.unchecked.UncheckedProject;
import api.specifications.Specifications;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;
import java.util.List;

public class RolesTest extends BaseAPITest {

    @Test
    public void unauthorizedUserShouldNotHaveRightsToCreateProject() {
        var testData = testDataStorage.addTestData();
        new UncheckedProject(Specifications.getSpec().unAuthSpecification())
                .create(testData.getProject())
                    .then().assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED);
        new CheckedUser(Specifications.getSpec().superUserSpec())
                .create(testData.getUser());
        new UncheckedProject(Specifications.getSpec().authSpecification(testData.getUser()))
                .get(testData.getProject().getId()).then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void systemAdminShouldHaveRightsToCreateProject(){
        var testData = testDataStorage.addTestData();
        testData.getUser().setRoles(TestDataGenerator.generateRoles(List.of(RoleEnum.SYSTEM_ADMIN), "g"));
        new CheckedUser(Specifications.getSpec().superUserSpec())
                .create(testData.getUser());
        var project = new CheckedProject(Specifications.getSpec().superUserSpec())
                .create(testData.getProject());
        softy.assertThat(project.getId()).isEqualTo(testData.getProject().getId());
    }

    @Test
    public void projectAdminShouldHaveRightsToCreateBuildConfigToHisProject(){
        var testData = testDataStorage.addTestData();
        testData.getUser().setRoles(TestDataGenerator.generateRoles(List.of(RoleEnum.PROJECT_ADMIN), "g"));
        new CheckedUser(Specifications.getSpec().superUserSpec())
                .create(testData.getUser());
        new CheckedProject(Specifications.getSpec().authSpecification(testData.getUser()))
                .create(testData.getProject());
        var buildConfig = new CheckedBuildConfig(Specifications.getSpec().authSpecification(testData.getUser()))
                .create(testData.getBuildType());

        softy.assertThat(buildConfig.getId()).isEqualTo(testData.getBuildType().getId());
    }

    @Test
    public void projectAdminShouldNotHaveRightsToCreateBuildConfigToAnotherProject(){
        var firstTestData = testDataStorage.addTestData();
        var secondTestData = testDataStorage.addTestData();

        new CheckedUser(Specifications.getSpec().superUserSpec()).create(firstTestData.getUser());
        new CheckedUser(Specifications.getSpec().superUserSpec()).create(secondTestData.getUser());

        new CheckedProject(Specifications.getSpec().authSpecification(firstTestData.getUser())).create(firstTestData.getProject());
        new CheckedProject(Specifications.getSpec().authSpecification(secondTestData.getUser())).create(secondTestData.getProject());

        firstTestData.getUser().setRoles(TestDataGenerator.generateRoles(List.of(RoleEnum.PROJECT_ADMIN), "p:" + firstTestData.getProject().getId()));
        secondTestData.getUser().setRoles(TestDataGenerator.generateRoles(List.of(RoleEnum.PROJECT_ADMIN), "p:" + secondTestData.getProject().getId()));

        // check
        var buildConfig = new UncheckedBuildConfig(Specifications.getSpec().authSpecification(firstTestData.getUser()))
                .create(secondTestData.getBuildType())
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST);
    }
}
