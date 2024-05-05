package api.generators;

import api.enums.RoleEnum;
import api.models.*;
import java.util.List;
import java.util.stream.Collectors;

public class TestDataGenerator {

    public static TestData generate() {
        var user = User.builder()
                .username(RandomData.getUsername())
                .password(RandomData.getUsername())
                .email(RandomData.getString() + "@gmail.com")
                .roles(Roles.builder()
                        .role(List.of(Role.builder()
                                .roleId(RoleEnum.SYSTEM_ADMIN.getRole())
                                .scope("g")
                                .build()))
                        .build())
                .build();
        var project = NewProjectDescription
                .builder()
                .parentProject(Project.builder()
                        .locator("_Root")
                        .build())
                .name(RandomData.getString())
                .id(RandomData.getId())
                .copyAllAssociatedSettings(true)
                .build();

        var buildType = BuildType.builder()
                .id(RandomData.getId())
                .name(RandomData.getString())
                .project(project)
                .build();

        return TestData.builder()
                .user(user)
                .project(project)
                .buildType(buildType)
                .build();
    }

    public static Roles generateRoles (List<RoleEnum> roles, String  scope) {
        List<Role> roleList = roles.stream()
                .map(role -> Role.builder()
                        .roleId(role.getRole())
                        .scope(scope)
                        .build())
                .collect(Collectors.toList());
        return Roles.builder().role(roleList).build();
    }
}

