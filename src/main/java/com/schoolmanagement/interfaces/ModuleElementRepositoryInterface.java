package com.schoolmanagement.interfaces;

import com.schoolmanagement.models.ModuleElement;

import java.util.List;

public interface ModuleElementRepositoryInterface {
    void saveModuleElement(ModuleElement moduleElement);
    void updateModuleElement(ModuleElement moduleElement);
    void deleteModuleElement(int id);
    List<ModuleElement> findAllByModuleId(int moduleId);
}