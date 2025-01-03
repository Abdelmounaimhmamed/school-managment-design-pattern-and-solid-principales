package com.schoolmanagement.builders;

import com.schoolmanagement.models.ModuleElement;

public class ModuleElementBuilder {
    private int id;
    private String name;
    private int moduleId;
    private double coefficient;
    private boolean isValidated;

    public ModuleElementBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public ModuleElementBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ModuleElementBuilder setModuleId(int moduleId) {
        this.moduleId = moduleId;
        return this;
    }

    public ModuleElementBuilder setCoefficient(double coefficient) {
        this.coefficient = coefficient;
        return this;
    }

    public ModuleElementBuilder setValidated(boolean isValidated) {
        this.isValidated = isValidated;
        return this;
    }

    public ModuleElement build() {
        return new ModuleElement(id, name, moduleId, coefficient, isValidated);
    }
}
