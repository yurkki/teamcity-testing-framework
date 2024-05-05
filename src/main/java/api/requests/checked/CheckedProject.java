package api.requests.checked;

import api.models.Project;
import api.requests.CRUDInterface;
import api.requests.Request;
import api.requests.unchecked.UncheckedProject;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

public class CheckedProject extends Request implements CRUDInterface {

    public CheckedProject(RequestSpecification reqSpec) {
        super(reqSpec);
    }

    @Override
    public Project create(Object obj) {
        return new UncheckedProject(reqSpec).create(obj)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(Project.class);
    }

    @Override
    public Project get(String id) {
        return new UncheckedProject(reqSpec)
                .get(id)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(Project.class);
    }

    @Override
    public Object update(Object obj) {
        return null;
    }

    @Override
    public Object delete(String id) {
        return new UncheckedProject(reqSpec)
                .delete(id)
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().asString();
    }
}
