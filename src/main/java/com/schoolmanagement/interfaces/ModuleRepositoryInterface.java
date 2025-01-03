package com.schoolmanagement.interfaces;

import com.schoolmanagement.models.Module;

import java.util.List;

public interface ModuleRepositoryInterface {
    void addModule(Module module);
    void updateModule(Module module);
    void deleteModule(int moduleId);
    Module findById(int moduleId);
    List<Module> findAll();
}