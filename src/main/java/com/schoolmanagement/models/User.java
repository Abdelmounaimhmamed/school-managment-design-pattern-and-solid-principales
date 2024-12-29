package com.schoolmanagement.models;


public interface User {
    int getId();
    String getUsername();
    String getPassword();
    void setPassword(String password);
}