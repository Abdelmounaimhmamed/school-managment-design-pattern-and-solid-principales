package com.schoolmanagement.builders;

import com.schoolmanagement.models.Professor;

public class ProfessorBuilder {
    private String code;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String specialty;

    public ProfessorBuilder setCode(String code) {
        this.code = code;
        return this;
    }

    public ProfessorBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public ProfessorBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public ProfessorBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public ProfessorBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public ProfessorBuilder setSpecialty(String specialty) {
        this.specialty = specialty;
        return this;
    }

    public Professor build() {
        return new Professor(code, username, password, firstName, lastName, specialty);
    }
}
