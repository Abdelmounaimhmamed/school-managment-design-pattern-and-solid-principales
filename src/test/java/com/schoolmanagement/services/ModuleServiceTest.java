package com.schoolmanagement.services;

import com.schoolmanagement.interfaces.ModuleRepositoryInterface;
import com.schoolmanagement.models.Module;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class ModuleServiceTest {

    private ModuleRepositoryInterface moduleRepository;
    private ModuleService moduleService;

    @Before
    public void setUp() {
        moduleRepository = mock(ModuleRepositoryInterface.class);
        moduleService = new ModuleService(moduleRepository);
    }

    @Test
    public void testAddModule_ShouldCallRepositoryAdd() {
        // Arrange
        Module module = new Module(1, "MAT101", "Math", 1, 1, 1);

        // Act
        moduleService.addModule(module);

        // Assert
        verify(moduleRepository, times(1)).addModule(module);
    }

    @Test
    public void testUpdateModule_ShouldCallRepositoryUpdate() {
        // Arrange
        Module module = new Module(1, "MAT102", "advance Math", 1, 1, 1);

        // Act
        moduleService.updateModule(module);

        // Assert
        verify(moduleRepository, times(1)).updateModule(module);
    }

    @Test
    public void testDeleteModule_ShouldCallRepositoryDelete() {
        // Act
        moduleService.deleteModule(1);

        // Assert
        verify(moduleRepository, times(1)).deleteModule(1);
    }

    @Test
    public void testFindModuleById_ShouldReturnModule() {
        // Arrange
        int moduleId = 1;
        Module mockModule = new Module(moduleId, "info101", "Informatique", 1, 1, 2);
        when(moduleRepository.findById(moduleId)).thenReturn(mockModule);

        // Act
        Module result = moduleService.findModuleById(moduleId);

        // Assert
        assertNotNull(result);
        assertEquals("Informatique", result.getName());
        assertEquals("info101", result.getCode());
        verify(moduleRepository, times(1)).findById(moduleId);
    }

    @Test
    public void testFindAllModules_ShouldReturnListOfModules() {
        // Arrange
        List<Module> mockModules = Arrays.asList(
                new Module(1, "MAT101", "Math", 1, 1, 1),
                new Module(1, "INFO101", "Informatique", 1, 1, 2)
        );
        when(moduleRepository.findAll()).thenReturn(mockModules);

        // Act
        List<Module> result = moduleService.findAllModules();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Math", result.get(0).getName());
        assertEquals("Informatique", result.get(1).getName());
        verify(moduleRepository, times(1)).findAll();
    }
}
