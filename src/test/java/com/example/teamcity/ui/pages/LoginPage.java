package com.example.teamcity.ui.pages;

import api.models.User;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import static com.codeborne.selenide.Selenide.element;
import static com.example.teamcity.ui.utils.Selectors.byId;

@Getter
public class LoginPage extends Page {

    private static final String LOGIN_PAGE_URL = "/login.html";
    private SelenideElement usernameInput = element(byId("username"));
    private SelenideElement passwordInput = element(byId("password"));

    public LoginPage open() {
        Selenide.open(LOGIN_PAGE_URL);
        return this;
    }

    public void login(User user) {
        usernameInput.sendKeys(user.getUsername());
        passwordInput.sendKeys(user.getPassword());
        submit();
    }
}
