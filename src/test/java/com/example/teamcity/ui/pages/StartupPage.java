package com.example.teamcity.ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.utils.Selectors;

import java.time.Duration;
import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.element;

public class StartupPage extends Page {

    private final SelenideElement createAdminAccountHeader = element(Selectors.byId("header"));
    private final SelenideElement header = element(Selectors.byId("header"));
    private final SelenideElement acceptLicense = element(Selectors.byId("accept"));
    private final SelenideElement proceedButton = element(Selectors.byId("proceedButton"));
    private final SelenideElement submitButton = element(Selectors.byType("submit"));

    public StartupPage open(){
        Selenide.open("/mnt");
        return this;
    }

    public SelenideElement getHeader() {
        return createAdminAccountHeader.shouldBe(visible, Duration.ofSeconds(35));
    }

    public StartupPage setupTeamCityServer(){
        waitUntilPageIsLoaded();
        proceedButton.click();
        waitUntilPageIsLoaded();
        proceedButton.click();
        waitUntilPageIsLoaded();
        header.shouldBe(visible, Duration.ofMinutes(5));
        acceptLicense.shouldBe(enabled, Duration.ofMinutes(5));
        acceptLicense.scrollTo();
        acceptLicense.click();
        submitButton.click();
        return this;
    }
}
