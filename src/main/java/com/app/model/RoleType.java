package com.app.model;

public enum RoleType {

    ADMIN("ADMIN"),
    USER("USER");

    private String name;

    private RoleType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
