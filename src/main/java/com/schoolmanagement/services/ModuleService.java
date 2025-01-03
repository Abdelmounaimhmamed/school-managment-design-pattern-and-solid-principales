package com.schoolmanagement.services;

import com.schoolmanagement.interfaces.ModuleRepositoryInterface;
import com.schoolmanagement.models.Module;

import java.util.List;

public class ModuleService {
    private final ModuleRepositoryInterface moduleRepository;

    public ModuleService(ModuleRepositoryInterface moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    public void addModule(Module module) {
        moduleRepository.addModule(module);
    }

    public void updateModule(Module module) {
        moduleRepository.updateModule(module);
    }

    public void deleteModule(int moduleId) {
        moduleRepository.deleteModule(moduleId);
    }

    public Module findModuleById(int moduleId) {
        return moduleRepository.findById(moduleId);
    }

    public List<Module> findAllModules() {
        return moduleRepository.findAll();
    }
}
