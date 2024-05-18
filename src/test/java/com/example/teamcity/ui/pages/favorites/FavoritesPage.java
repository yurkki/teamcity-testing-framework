package com.example.teamcity.ui.pages.favorites;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.pages.Page;
import org.openqa.selenium.By;
import java.time.Duration;
import static com.codeborne.selenide.Selenide.element;

public class FavoritesPage extends Page {

    private SelenideElement header = element(By.xpath("//*[contains(@class, 'ProjectPageHeader__title')]"));

    public void waitUntilFavoritePageIsLoaded() {
        waitUntilPageIsLoaded();
        header.shouldBe(Condition.visible, Duration.ofSeconds(10));
    }
}