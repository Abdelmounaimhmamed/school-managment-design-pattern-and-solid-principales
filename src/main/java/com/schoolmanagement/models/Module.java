package com.schoolmanagement.models;

public class Module {
    private int id;
    private String code;
    private String name;
    private int filiereId;
    private int semester;
    private int professorId;

    // Constructor
    public Module(int id, String code, String name, int filiereId, int semester, int professorId) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.filiereId = filiereId;
        this.semester = semester;
        this.professorId = professorId;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFiliereId() {
        return filiereId;
    }

    public void setFiliereId(int filiereId) {
        this.filiereId = filiereId;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getProfessorId() {
        return professorId;
    }

    public void setProfessorId(int professorId) {
        this.professorId = professorId;
    }
}
