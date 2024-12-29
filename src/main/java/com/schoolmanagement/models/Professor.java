package com.schoolmanagement.models;

public class Professor implements User {
    private int id;
    private String code;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String specialty;

    public Professor(String code, String username, String password, String firstName, String lastName, String specialty) {
        this.code = code;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialty = specialty;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
