package api.requests;

import api.requests.unchecked.UncheckedBuildConfig;
import api.requests.unchecked.UncheckedProject;
import api.requests.unchecked.UncheckedUser;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;

@Getter
public class UncheckedRequests {

    private UncheckedUser userRequest;
    private UncheckedProject projectRequest;
    private UncheckedBuildConfig buildConfigRequest;

    public UncheckedRequests(RequestSpecification reqSpec) {
        this.userRequest = new UncheckedUser(reqSpec);
        this.projectRequest = new UncheckedProject(reqSpec);
        this.buildConfigRequest = new UncheckedBuildConfig(reqSpec);
    }
}
