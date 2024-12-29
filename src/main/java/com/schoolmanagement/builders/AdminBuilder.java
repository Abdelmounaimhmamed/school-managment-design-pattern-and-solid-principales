package com.schoolmanagement.builders;

import com.schoolmanagement.models.Admin;

public class AdminBuilder {
    private int id;
    private String username;
    private String password;

    public AdminBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public AdminBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public AdminBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public Admin build() {
        return new Admin(id, username, password);
    }
}
