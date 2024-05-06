package com.example.teamcity.api;

import api.generators.RandomData;
import api.generators.TestData;
import api.models.BuildType;
import api.requests.checked.CheckedBuildConfig;
import api.requests.checked.CheckedProject;
import api.requests.checked.CheckedUser;
import api.requests.unchecked.UncheckedBuildConfig;
import api.specifications.Specifications;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertTrue;

public class BuildConfigurationTest extends BaseAPITest {

    private TestData testData;

    @BeforeMethod
    public void beforeMethod() {
        testData = testDataStorage.addTestData();
        new CheckedUser(Specifications.getSpec().superUserSpec())
                .create(testData.getUser());
    }

    @Test(description = "Создание нормальной конфигурации сборки")
    void createBuildConfigurationTest() {
        var testData = testDataStorage.addTestData();

        var project = new CheckedProject(Specifications.getSpec().superUserSpec())
                .create(testData.getProject());
        var buildType = new CheckedBuildConfig(Specifications.getSpec().authSpecification(testData.getUser())).create(testData.getBuildType());
        softy.assertThat(buildType.getId()).isEqualTo(testData.getBuildType().getId());
    }

    @Test(description = "Создание конфигурации сборки с некорректными данными (empty name field)")
    void creatingBuildConfigurationWithEmptyNameFieldTest() {
        new CheckedProject(Specifications.getSpec().superUserSpec())
                .create(testData.getProject());
        var buildTypeData = BuildType.builder()
                .id(RandomData.getId())
                .project(testData.getProject())
                .build();

        var buildType = new UncheckedBuildConfig(Specifications.getSpec().authSpecification(testData.getUser())).create(buildTypeData)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST);
        assertTrue(buildType.extract().asString().contains("When creating a build type, non empty name should be provided."));
    }

    @Test(description = "Создание конфигурации сборки с дублированием существующего имени")
    void creatingBuildConfigurationThatDuplicatesAnExistingName() {
        new CheckedProject(Specifications.getSpec().superUserSpec())
                .create(testData.getProject());
        var buildTypeData = BuildType.builder()
                .name(RandomData.getString())
                .id(RandomData.getId())
                .project(testData.getProject())
                .build();

        var firstBuildConfig = new CheckedBuildConfig(Specifications.getSpec().authSpecification(testData.getUser())).create(buildTypeData);
        buildTypeData.setId(RandomData.getId());

        var secondBuildConfig = new UncheckedBuildConfig(Specifications.getSpec().authSpecification(testData.getUser())).create(buildTypeData)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST);
        assertTrue(secondBuildConfig.extract().asString().contains(String.format("Build configuration with name \"%s\" already exists in project: \"%s\"",
                buildTypeData.getName(), testData.getProject().getName())));
    }

    @Test(description = "Создание конфигурации сборки с указанием несуществующего проекта")
    void creatingBuildConfigurationSpecifyingANonExistentProject() {
        testData.getProject().setId(RandomData.getId());
        var buildConfig = new UncheckedBuildConfig(Specifications.getSpec().authSpecification(testData.getUser())).create(testData.getBuildType())
                .then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
        assertTrue(buildConfig.extract().asString().contains(String.format("Project cannot be found by external id '%s'",
                testData.getProject().getId())));
    }

    @Test(description = "Создание конфигурации сборки без аутентификации")
    void creatingBuildConfigurationWithoutAuthentication() {
        new UncheckedBuildConfig(Specifications.getSpec().unAuthSpecification()).create(testData.getBuildType())
                .then().assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED);
    }
}
