package com.example.teamcity.api;

import api.enums.RoleEnum;
import api.generators.RandomData;
import api.generators.TestData;
import api.generators.TestDataGenerator;
import api.models.NewProjectDescription;
import api.models.Project;
import api.requests.checked.CheckedProject;
import api.requests.checked.CheckedUser;
import api.requests.unchecked.UncheckedProject;
import api.specifications.Specifications;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.List;
import static org.testng.AssertJUnit.assertTrue;

public class ProjectTest extends BaseAPITest {

    private TestData testData;

    @BeforeMethod
    public void beforeMethod() {
        testData = testDataStorage.addTestData();
        new CheckedUser(Specifications.getSpec().superUserSpec())
                .create(testData.getUser());
    }

    @Test(description = "Создание нового проекта с корректными данными")
    public void createProjectTest() {
        var project = new CheckedProject(Specifications.getSpec().authSpecification(testData.getUser()))
                .create(testData.getProject());
        softy.assertThat(project.getId()).isEqualTo(testData.getProject().getId());
        softy.assertThat(project.getName()).isEqualTo(testData.getProject().getName());

        new CheckedProject(Specifications.getSpec().authSpecification(testData.getUser()))
                .get(project.getId());
    }

    @DataProvider(name = "rolesProvider")
    public static Object[][] rolesProvider() {
        return new Object[][]{
                {RoleEnum.PROJECT_ADMIN},
                {RoleEnum.PROJECT_DEVELOPER},
                {RoleEnum.AGENT_MANAGER},
                {RoleEnum.PROJECT_VIEWER}
        };
    }


    @Test(description = "Создание нового проекта с корректными данными c разными ролями пользователя", dataProvider = "rolesProvider")
    public void createProjectTestWithDifferentUserRoles(RoleEnum role) {
        testData.getUser().setRoles(TestDataGenerator.generateRoles(List.of(role), "g"));
        var project = new CheckedProject(Specifications.getSpec().authSpecification(testData.getUser()))
                .create(testData.getProject());
        softy.assertThat(project.getId()).isEqualTo(testData.getProject().getId());
        softy.assertThat(project.getName()).isEqualTo(testData.getProject().getName());

        new CheckedProject(Specifications.getSpec().authSpecification(testData.getUser()))
                .get(project.getId());
    }

    @Test(description = "Отклонение создания проекта без аутентификации")
    public void createProjectByUnAuthUserTest() {
        new UncheckedProject(Specifications.getSpec().unAuthSpecification())
                .create(testData.getProject()).then().statusCode(HttpStatus.SC_UNAUTHORIZED);
        new UncheckedProject(Specifications.getSpec().authSpecification(testData.getUser()))
                .get(testData.getProject().getId())
                .then().statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test(description = "Отклонение создания проекта с некорректными данными (project_name field is empty)")
    public void createProjectWithEmptyNameFieldTest() {
        var projectDescription = NewProjectDescription.builder()
                .parentProject(Project.builder().locator("_Root").build())
                .copyAllAssociatedSettings(true)
                .build();
        var project = new UncheckedProject(Specifications.getSpec().authSpecification(testData.getUser()))
                .create(projectDescription).then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST);
        assertTrue(project.extract().asString().contains("Project name cannot be empty"));
    }

    @Test(description = "Создание проекта с зависимостями")
    public void createChildProject() {
        var parentProject = new CheckedProject(Specifications.getSpec().authSpecification(testData.getUser()))
                .create(testData.getProject());
        var projectDescription = NewProjectDescription.builder()
                .id(RandomData.getId())
                .name(RandomData.getString())
                .parentProject(Project.builder().id(parentProject.getId()).name(parentProject.getName()).build())
                .copyAllAssociatedSettings(true)
                .build();
        var childProject = new CheckedProject(Specifications.getSpec().authSpecification(testData.getUser()))
                .create(projectDescription);
        softy.assertThat(childProject.getParentProjectId()).isEqualTo(parentProject.getId());
    }
}
