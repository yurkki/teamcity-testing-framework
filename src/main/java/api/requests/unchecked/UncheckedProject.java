package api.requests.unchecked;

import api.requests.CRUDInterface;
import api.requests.Request;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class UncheckedProject extends Request implements CRUDInterface {

    private static final String PROJECT_ENDPOINT = "/app/rest/projects";

    public UncheckedProject(RequestSpecification reqSpec) {
        super(reqSpec);
    }

    @Override
    public Response create(Object projectDescription) {
        return given().spec(reqSpec)
                .body(projectDescription)
                .post(PROJECT_ENDPOINT);
    }

    @Override
    public Response get(String id) {
        return given()
                .spec(reqSpec)
                .get(PROJECT_ENDPOINT + "/id:" + id);
    }

    @Override
    public Object update(Object obj) {
        return null;
    }

    @Override
    public Response delete(String username) {
        return given()
                .spec(reqSpec)
                .delete(PROJECT_ENDPOINT + "/username:" + username);
    }
}
