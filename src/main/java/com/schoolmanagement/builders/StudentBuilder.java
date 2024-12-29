package com.schoolmanagement.builders;

import com.schoolmanagement.models.Student;

public class StudentBuilder {
    private int id;
    private String username;
    private String password;
    private String name;
    private String email;

    public StudentBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public StudentBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public StudentBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public StudentBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public StudentBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public Student build() {
        return new Student(this.id, this.username, this.password, this.name, this.email);
    }
}
