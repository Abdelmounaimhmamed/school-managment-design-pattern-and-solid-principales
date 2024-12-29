package com.schoolmanagement.models;

public class ModuleElement {
    private int id;
    private String name;
    private int moduleId;
    private double coefficient;
    private boolean isValidated;

    public ModuleElement(int id, String name, int moduleId, double coefficient, boolean isValidated) {
        this.id = id;
        this.name = name;
        this.moduleId = moduleId;
        this.coefficient = coefficient;
        this.isValidated = isValidated;
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getName() { return name; }
    public int getModuleId() { return moduleId; }
    public double getCoefficient() { return coefficient; }
    public boolean isValidated() { return isValidated; }
    public void setValidated(boolean validated) { isValidated = validated; }
}
