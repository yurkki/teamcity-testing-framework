package api.specifications;

import api.config.Config;
import api.models.User;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.preemptive;

public class Specifications {

    private static Specifications spec;

    private Specifications() {
    }

    public static Specifications getSpec() {
        if (spec == null) {
            return new Specifications();
        }
        return spec;
    }

    private RequestSpecBuilder reqBuilder() {
        return new RequestSpecBuilder()
                .setBaseUri(Config.getProperty("base.uri"))
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON);
    }

    public RequestSpecification unAuthSpecification() {
        return reqBuilder().build();
    }

    public RequestSpecification authSpecification(User user) {
        PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
        authScheme.setUserName(user.getUsername());
        authScheme.setPassword(user.getPassword());
        return reqBuilder()
                .setAuth(authScheme)
                .build();
    }

    public RequestSpecification superUserSpec() {
        return reqBuilder()
                .setAuth(preemptive().basic("", Config.getProperty("superUserToken")))
                .build();
    }
}
