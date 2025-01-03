package com.schoolmanagement.proxies;

import com.schoolmanagement.models.ModuleElement;
import com.schoolmanagement.interfaces.ModuleElementRepositoryInterface;
import  java.util.List;

public class ModuleElementServiceProxy {
    private final ModuleElementRepositoryInterface moduleElementRepository;

    public ModuleElementServiceProxy(ModuleElementRepositoryInterface moduleElementRepository) {
        this.moduleElementRepository = moduleElementRepository;
    }

    public void addModuleElement(ModuleElement moduleElement) {
        // Validate data (e.g., ensure coefficient is valid)
        if (moduleElement.getCoefficient() <= 0 || moduleElement.getCoefficient() > 1) {
            throw new IllegalArgumentException("Coefficient must be between 0 and 1.");
        }
        moduleElementRepository.saveModuleElement(moduleElement);
    }

    public void updateModuleElement(ModuleElement moduleElement) {
        // Validate data
        if (moduleElement.getCoefficient() <= 0 || moduleElement.getCoefficient() > 1) {
            throw new IllegalArgumentException("Coefficient must be between 0 and 1.");
        }
        moduleElementRepository.updateModuleElement(moduleElement);
    }

    public void deleteModuleElement(int id) {
        moduleElementRepository.deleteModuleElement(id);
    }

    public List
    <ModuleElement> findAllByModuleId(int moduleId) {
        return moduleElementRepository.findAllByModuleId(moduleId);
    }
}
