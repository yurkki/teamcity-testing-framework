package api.requests;

import api.requests.checked.CheckedBuildConfig;
import api.requests.checked.CheckedProject;
import api.requests.checked.CheckedUser;
import api.requests.unchecked.UncheckedBuildConfig;
import api.requests.unchecked.UncheckedProject;
import api.requests.unchecked.UncheckedUser;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;

@Getter
public class CheckedRequests {

    private CheckedUser userRequest;
    private CheckedProject projectRequest;
    private CheckedBuildConfig buildConfigRequest;

    public CheckedRequests(RequestSpecification reqSpec) {
        this.userRequest = new CheckedUser(reqSpec);
        this.projectRequest = new CheckedProject(reqSpec);
        this.buildConfigRequest = new CheckedBuildConfig(reqSpec);
    }
}
