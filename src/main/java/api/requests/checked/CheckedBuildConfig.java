package api.requests.checked;

import api.models.BuildType;
import api.requests.CRUDInterface;
import api.requests.Request;
import api.requests.unchecked.UncheckedBuildConfig;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

public class CheckedBuildConfig extends Request implements CRUDInterface {

    public CheckedBuildConfig(RequestSpecification reqSpec) {
        super(reqSpec);
    }

    @Override
    public BuildType create(Object obj) {
        return new UncheckedBuildConfig(reqSpec)
                .create(obj)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(BuildType.class);
    }

    @Override
    public BuildType get(String id) {
        return new UncheckedBuildConfig(reqSpec)
                .get(id)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(BuildType.class);
    }

    @Override
    public Object update(Object obj) {
        return null;
    }

    @Override
    public String delete(String id) {
        return new UncheckedBuildConfig(reqSpec)
                .delete(id)
                .then().assertThat().statusCode(HttpStatus.SC_NO_CONTENT)
                .extract().asString();
    }
}
