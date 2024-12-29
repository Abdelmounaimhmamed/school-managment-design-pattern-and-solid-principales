package com.schoolmanagement.models;

public class Grade {
    private int id;
    private int studentId;
    private int moduleElementId;
    private double gradeValue;
    private boolean isAbsent;

    public Grade(int id, int studentId, int moduleElementId, double gradeValue, boolean isAbsent) {
        this.id = id;
        this.studentId = studentId;
        this.moduleElementId = moduleElementId;
        this.gradeValue = gradeValue;
        this.isAbsent = isAbsent;
    }

    // Getters and Setters
    public int getId() { return id; }
    public int getStudentId() { return studentId; }
    public int getModuleElementId() { return moduleElementId; }
    public double getGradeValue() { return gradeValue; }
    public void setGradeValue(double gradeValue) { this.gradeValue = gradeValue; }
    public boolean isAbsent() { return isAbsent; }
    public void setAbsent(boolean absent) { isAbsent = absent; }
}
