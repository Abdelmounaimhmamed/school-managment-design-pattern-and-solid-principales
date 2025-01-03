package com.schoolmanagement.models;

public class Filiere {
    private int id;
    private String name;

    public Filiere(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Filiere(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
