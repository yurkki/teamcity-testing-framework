package api.requests.unchecked;

import api.requests.CRUDInterface;
import api.requests.Request;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class UncheckedBuildConfig extends Request implements CRUDInterface {

    private static final String BUILD_CONFIG_ENDPOINT = "/app/rest/buildTypes";

    public UncheckedBuildConfig(RequestSpecification reqSpec) {
        super(reqSpec);
    }

    @Override
    public Response create(Object obj) {
        return given()
                .spec(reqSpec)
                .body(obj)
                .post(BUILD_CONFIG_ENDPOINT);
    }

    @Override
    public Response get(String id) {
        return given()
                .spec(reqSpec)
                .get(BUILD_CONFIG_ENDPOINT + "/id:" + id);
    }

    @Override
    public Object update(Object obj) {
        return null;
    }

    @Override
    public Response delete(String id) {
        return given()
                .spec(reqSpec)
                .delete(BUILD_CONFIG_ENDPOINT + "/buildConfigurationLocator:" + id);
    }
}
