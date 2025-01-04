package com.schoolmanagement.services;

import com.schoolmanagement.interfaces.ModuleElementRepositoryInterface;
import com.schoolmanagement.models.ModuleElement;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ModuleElementServiceTest {

    private ModuleElementRepositoryInterface repository;
    private ModuleElementService service;

    @Before
    public void setUp() {
        repository = mock(ModuleElementRepositoryInterface.class);
        service = new ModuleElementService(repository);
    }

    @Test
    public void testAddModuleElement_ShouldCallRepositorySave() {
        // Arrange
        ModuleElement element = new ModuleElement(1, "C++", 1, 2.0, true);

        // Act
        service.addModuleElement(element);

        // Assert
        verify(repository, times(1)).saveModuleElement(element);
    }

    @Test
    public void testUpdateModuleElement_ShouldCallRepositoryUpdate() {
        // Arrange
        ModuleElement element = new ModuleElement(1, "Java", 1, 2.0, true);

        // Act
        service.updateModuleElement(element);

        // Assert
        verify(repository, times(1)).updateModuleElement(element);
    }

    @Test
    public void testDeleteModuleElement_ShouldCallRepositoryDelete() {
        // Act
        service.deleteModuleElement(1);

        // Assert
        verify(repository, times(1)).deleteModuleElement(1);
    }

    @Test
    public void testFindAllByModuleId_ShouldReturnListOfModuleElements() {
        // Arrange
        int moduleId = 101;
        List<ModuleElement> mockElements = Arrays.asList(
                new ModuleElement(1, "C++", moduleId, 2, false),
                new ModuleElement(2, "Advanced Topics", moduleId, 2, false)
        );
        when(repository.findAllByModuleId(moduleId)).thenReturn(mockElements);

        // Act
        List<ModuleElement> result = service.findAllByModuleId(moduleId);

        // Assert
        assertEquals(2, result.size());
        assertEquals("C++", result.get(0).getName());
        assertEquals("Advanced Topics", result.get(1).getName());
        verify(repository, times(1)).findAllByModuleId(moduleId);
    }
}

