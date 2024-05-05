package api.requests.checked;

import api.models.User;
import api.specifications.Specifications;
import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;

public class AuthRequest {

    private User user;

    public AuthRequest(User user){
        this.user = user;
    }

    public String getCsrfToken(){
        return given()
                .spec(Specifications.getSpec().authSpecification(user))
                .get("/authenticationTest.html?csrf")
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().asString();
    }
}
