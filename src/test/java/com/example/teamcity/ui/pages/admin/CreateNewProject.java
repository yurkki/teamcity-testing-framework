package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.utils.Selectors;
import com.example.teamcity.ui.pages.Page;
import java.time.Duration;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class CreateNewProject extends Page {

    private SelenideElement urlInput = $x("//*[@id='url']");
    private SelenideElement projectNameInput = element(Selectors.byId("projectName"));
    private SelenideElement buildTypeNameInput = element(Selectors.byId("buildTypeName"));
    private final SelenideElement errorMessageLabel = $(".error#error_projectName");

    public CreateNewProject open(String parentProjectId) {
        Selenide.open("/admin/createObjectMenu.html?projectId=" +  parentProjectId + "&showMode=createProjectMenu");
        waitUntilPageIsLoaded();
        return this;
    }

    public CreateNewProject setupProject(String projectName, String buildTypeName) {
        $x("//*[text()='Create Project From URL']").shouldBe(visible);
        projectNameInput.clear();
        projectNameInput.sendKeys(projectName);
        buildTypeNameInput.clear();
        buildTypeNameInput.sendKeys(buildTypeName);
        submit();
        return this;
    }

        public CreateNewProject createProjectByUrl(String url) {
        urlInput.sendKeys(url);
        submit();
        return this;
    }

    public CreateNewProject checkProjectNameError() {
        errorMessageLabel.shouldBe(visible, Duration.ofSeconds(10));
        errorMessageLabel.shouldHave(text("Project name must not be empty"));
        return this;
    }
}