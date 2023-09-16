package com.br.alura.forum.constrains;

public enum Role {
    ADMIN("admin"),
    USER("user");

    String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
