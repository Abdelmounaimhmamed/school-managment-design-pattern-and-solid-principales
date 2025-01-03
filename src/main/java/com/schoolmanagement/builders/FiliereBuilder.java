package com.schoolmanagement.builders;

import com.schoolmanagement.models.Filiere;

public class FiliereBuilder {
    private int id;
    private String name;

    public FiliereBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public FiliereBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public Filiere build() {
        return new Filiere(id, name);
    }
}
