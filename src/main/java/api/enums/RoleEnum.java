package api.enums;

import lombok.Getter;

@Getter
public enum RoleEnum {

    SYSTEM_ADMIN("SYSTEM_ADMIN"),
    PROJECT_ADMIN("PROJECT_ADMIN"),
    PROJECT_DEVELOPER("PROJECT_DEVELOPER"),
    PROJECT_VIEWER("PROJECT_VIEWER"),
    AGENT_MANAGER("AGENT_MANAGER");

    private final String role;

    RoleEnum(String role) {
        this.role = role;
    }
}
