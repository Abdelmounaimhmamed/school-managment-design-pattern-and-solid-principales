package com.schoolmanagement.models;

public class Student implements User {
    private int id;
    private String username;
    private String password;
    private String name;
    private String email;

    public Student(int id, String username, String password, String name, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getName() { return name; }
    public String getEmail() { return email; }
}
