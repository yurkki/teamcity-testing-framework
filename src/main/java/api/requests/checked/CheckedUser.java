package api.requests.checked;

import api.models.User;
import api.requests.CRUDInterface;
import api.requests.Request;
import api.requests.unchecked.UncheckedUser;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

public class CheckedUser extends Request implements CRUDInterface {

    public CheckedUser(RequestSpecification reqSpec) {
        super(reqSpec);
    }

    @Override
    public User create(Object obj) {
        return new UncheckedUser(reqSpec)
                .create(obj)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(User.class);
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
    public String delete(String id) {
        return new UncheckedUser(reqSpec)
                .delete(id)
                .then().statusCode(HttpStatus.SC_NO_CONTENT)
                .extract().asString();
    }
}
