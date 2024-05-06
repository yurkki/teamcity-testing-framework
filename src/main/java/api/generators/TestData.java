package api.generators;

import api.models.BuildType;
import api.models.NewProjectDescription;
import api.models.User;
import api.requests.unchecked.UncheckedProject;
import api.specifications.Specifications;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestData {
    private User user;
    private NewProjectDescription project;
    private BuildType buildType;

    public void delete() {
        var spec = Specifications.getSpec().authSpecification(user);
        new UncheckedProject(spec).delete(project.getId());
        new UncheckedProject(spec).delete(user.getUsername());
    }


}