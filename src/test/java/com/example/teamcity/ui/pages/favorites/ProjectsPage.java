package com.example.teamcity.ui.pages.favorites;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.example.teamcity.ui.elements.ProjectElement;
import org.openqa.selenium.By;
import java.util.List;
import static com.codeborne.selenide.Selenide.*;

public class ProjectsPage extends FavoritesPage {

    private static final String FAVORITE_PROJECTS_URL = "/favorite/projects";
    private final ElementsCollection subprojects = elements(By.xpath("//*[contains(@class, 'Subproject__container')]"));

    public ProjectsPage open() {
        Selenide.open(FAVORITE_PROJECTS_URL);
        waitUntilFavoritePageIsLoaded();
        return this;
    }

    public List<ProjectElement> getSubprojects() {
        return generatePageElements(subprojects, ProjectElement::new);
    }
}