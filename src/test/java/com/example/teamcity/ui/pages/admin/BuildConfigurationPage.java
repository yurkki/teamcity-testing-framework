package com.example.teamcity.ui.pages.admin;

import api.generators.RandomData;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.pages.Page;
import java.time.Duration;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class BuildConfigurationPage extends Page {

    private final SelenideElement buildTypeNameInput = element(Selectors.byId("buildTypeName"));
    private final SelenideElement urlInput = element(Selectors.byId("url"));
    private final SelenideElement errorMessageLabel = $(".error#error_buildTypeName");
    private final SelenideElement manuallyOption = $(Selectors.byAttribute("href", "#createManually"));
    private final SelenideElement nameInput = $(Selectors.byAttribute("name", "buildTypeName"));
    public final SelenideElement buildIdInput = element(Selectors.byId("buildTypeExternalId"));
    public static String currentBuildId;

    public BuildConfigurationPage open(String parentProjectId) {
        Selenide.open("/admin/createObjectMenu.html?projectId=" + parentProjectId + "&showMode=createBuildTypeMenu");
        waitUntilPageIsLoaded();
        return this;
    }

    public BuildConfigurationPage createBuildConfigurationFromRepositoryURL(String url) {
        urlInput.sendKeys(url);
        submit();
        return this;
    }

    public BuildConfigurationPage createBuildConfigurationManually(String buildName) {
        manuallyOption.should(visible).click();
        nameInput.sendKeys(buildName);
        submit();
        return this;
    }

    public BuildConfigurationPage setupBuild(String buildTypeName) {
        buildTypeNameInput.clear();
        buildTypeNameInput.sendKeys(buildTypeName);
        currentBuildId = buildIdInput.getAttribute("generated");
        submit();
        return this;
    }

    public BuildConfigurationPage checkBuildNameError() {
        errorMessageLabel.shouldBe(visible, Duration.ofSeconds(10));
        errorMessageLabel.shouldHave(text("Build configuration name must not be empty"));
        return this;
    }
}
