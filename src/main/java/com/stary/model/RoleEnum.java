package com.stary.model;

import lombok.Getter;

@Getter
public enum RoleEnum {
    ADMIN("admin"),
    COMMON("common"),
    SPECIAL("special"),
    ;
    private String roleName;

    RoleEnum(String roleName) {
        this.roleName = roleName;
    }
}
