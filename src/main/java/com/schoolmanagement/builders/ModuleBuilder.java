package com.schoolmanagement.builders;

import com.schoolmanagement.models.Module;

public class ModuleBuilder {
    private int id;
    private String code;
    private String name;
    private int filiereId;
    private int semester;
    private int professorId;

    public ModuleBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public ModuleBuilder setCode(String code) {
        this.code = code;
        return this;
    }

    public ModuleBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ModuleBuilder setFiliereId(int filiereId) {
        this.filiereId = filiereId;
        return this;
    }

    public ModuleBuilder setSemester(int semester) {
        this.semester = semester;
        return this;
    }

    public ModuleBuilder setProfessorId(int professorId) {
        this.professorId = professorId;
        return this;
    }

    public Module build() {
        return new Module(this.id, this.code, this.name, this.filiereId, this.semester, this.professorId);
    }
}
