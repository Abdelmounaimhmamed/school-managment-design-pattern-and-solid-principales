package com.schoolmanagement.services;

import com.schoolmanagement.interfaces.ModuleElementRepositoryInterface;
import com.schoolmanagement.models.ModuleElement;

import java.util.List;

public class ModuleElementService {
    private final ModuleElementRepositoryInterface repository;

    public ModuleElementService(ModuleElementRepositoryInterface repository) {
        this.repository = repository;
    }

    public void addModuleElement(ModuleElement moduleElement) {
        repository.saveModuleElement(moduleElement);
    }

    public void updateModuleElement(ModuleElement moduleElement) {
        repository.updateModuleElement(moduleElement);
    }

    public void deleteModuleElement(int id) {
        repository.deleteModuleElement(id);
    }

    public List<ModuleElement> findAllByModuleId(int moduleId) {
        return repository.findAllByModuleId(moduleId);
    }
}
