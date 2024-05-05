package api.requests.unchecked;

import api.requests.CRUDInterface;
import api.requests.Request;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class UncheckedUser extends Request implements CRUDInterface {

    private final static String USER_ENDPOINT = "/app/rest/users";

    public UncheckedUser(RequestSpecification reqSpec) {
        super(reqSpec);
    }

    @Override
    public Response create(Object obj) {
        return given()
                .spec(reqSpec)
                .body(obj)
                .post(USER_ENDPOINT);
    }

    @Override
    public Object get(String id) {
        return null;
    }

    @Override
    public Object update(Object obj) {
        return null;
    }

    @Override
    public Response delete(String id) {
        return given()
                .spec(reqSpec)
                .delete(USER_ENDPOINT + "/id:" + id);
    }
}
