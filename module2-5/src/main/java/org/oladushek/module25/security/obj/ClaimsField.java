package org.oladushek.module25.security.obj;

import lombok.Getter;

public enum ClaimsField {
    ROLE("role"),
    USERNAME("username");

    @Getter
    private final String name;

    ClaimsField(String name) {
        this.name = name;
    }

}
