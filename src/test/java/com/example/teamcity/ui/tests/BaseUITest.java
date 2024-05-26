package com.example.teamcity.ui.tests;

import api.config.Config;
import api.models.User;
import api.requests.checked.CheckedUser;
import api.specifications.Specifications;
import com.codeborne.selenide.Configuration;
import com.example.teamcity.base.BaseTest;
import com.example.teamcity.ui.utils.BrowserSettings;
import com.example.teamcity.ui.pages.LoginPage;
import org.testng.annotations.BeforeSuite;

public class BaseUITest extends BaseTest {

    @BeforeSuite
    public void setupUiTests() {
        Configuration.baseUrl = "http://" + Config.getProperty("host");
        Configuration.remote = Config.getProperty("remote");
        Configuration.browserSize = "1980x1080";
        Configuration.reportsFolder = "target/surefire-reports";
        Configuration.downloadsFolder ="target/downloads";
        Configuration.timeout = 15000L;
        Configuration.browser = "firefox";
        BrowserSettings.setup(Config.getProperty("browser"));
    }

    public void loginAsUser(User user) {
        new CheckedUser(Specifications.getSpec().superUserSpec()).create(user);
        new LoginPage().open().login(user);
    }
}
