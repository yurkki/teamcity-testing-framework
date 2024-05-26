package com.example.teamcity.ui.tests;

import api.generators.RandomData;
import api.requests.checked.CheckedBuildConfig;
import api.specifications.Specifications;
import com.codeborne.selenide.Condition;
import com.example.teamcity.ui.pages.admin.BuildConfigurationPage;
import com.example.teamcity.ui.pages.admin.CreateNewProject;
import com.example.teamcity.ui.pages.favorites.ProjectsPage;
import org.testng.annotations.Test;

public class BuildConfigurationTest extends BaseUITest {

    @Test
    public void createBuildConfigurationByAuthUser() {
        var testData = testDataStorage.addTestData();
        var url = "https://github.com/yurkki/teamcity-testing-framework.git";
        loginAsUser(testData.getUser());
        new CreateNewProject()
                .open(testData.getProject().getParentProject().getLocator())
                .createProjectByUrl(url)
                .setupProject(testData.getProject().getName(), testData.getBuildType().getName());
        new ProjectsPage().open()
                .getSubprojects()
                .stream().reduce((first, second) -> second).get()
                .getHeader().shouldHave(Condition.text(testData.getProject().getName()));
        new BuildConfigurationPage()
                .open(testData.getProject().getParentProject().getLocator())
                .createBuildConfigurationFromRepositoryURL(url)
                .setupBuild(testData.getBuildType().getName());
    }

    @Test
    public void createBuildConfigurationManuallyByAuthUser() {
        var testData = testDataStorage.addTestData();
        var url = "https://github.com/yurkki/teamcity-testing-framework.git";
        loginAsUser(testData.getUser());
        new CreateNewProject()
                .open(testData.getProject().getParentProject().getLocator())
                .createProjectByUrl(url)
                .setupProject(testData.getProject().getName(), testData.getBuildType().getName());
        new ProjectsPage().open()
                .getSubprojects()
                .stream().reduce((first, second) -> second).get()
                .getHeader().shouldHave(Condition.text(testData.getProject().getName()));
        new BuildConfigurationPage()
                .open(testData.getProject().getParentProject().getLocator())
                .createBuildConfigurationManually(RandomData.getString())
                .setupBuild(testData.getBuildType().getName());
        new CheckedBuildConfig(Specifications.getSpec().superUserSpec()).get(testData.getProject().getName() + "_" + BuildConfigurationPage.currentBuildId);
    }

    @Test
    public void createBuildConfigWithEmptyName() {
        var testData = testDataStorage.addTestData();
        var url = "https://github.com/yurkki/teamcity-testing-framework.git";
        loginAsUser(testData.getUser());
        new CreateNewProject()
                .open(testData.getProject().getParentProject().getLocator())
                .createProjectByUrl(url)
                .setupProject(testData.getProject().getName(), testData.getBuildType().getName());
        new ProjectsPage().open()
                .getSubprojects()
                .stream().reduce((first, second) -> second).get()
                .getHeader().shouldHave(Condition.text(testData.getProject().getName()));
        new BuildConfigurationPage()
                .open(testData.getProject().getParentProject().getLocator())
                .createBuildConfigurationFromRepositoryURL(url)
                .setupBuild("")
                .checkBuildNameError();
    }
}