package com.example.teamcity.ui.tests;

import com.example.teamcity.ui.pages.StartupPage;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.text;

public class SetupTest extends BaseUITest {

    @Test
    public void startUpTest(){
        new StartupPage()
                .open()
                .setupTeamCityServer()
                .getHeader().shouldHave(text("Create Administrator Account"));
    }
}
