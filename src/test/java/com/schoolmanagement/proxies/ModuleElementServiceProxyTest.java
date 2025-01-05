package com.schoolmanagement.proxies;

import com.schoolmanagement.interfaces.ModuleElementRepositoryInterface;
import com.schoolmanagement.models.ModuleElement;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

public class ModuleElementServiceProxyTest {

    private ModuleElementRepositoryInterface mockRepository;
    private ModuleElementServiceProxy serviceProxy;

    @Before
    public void setUp() {
        // Initialize mock repository and service proxy
        mockRepository = mock(ModuleElementRepositoryInterface.class);
        serviceProxy = new ModuleElementServiceProxy(mockRepository);
    }

    @Test
    public void testAddModuleElementWithValidCoefficient() {
        // Arrange
        ModuleElement moduleElement = new ModuleElement(1, "C++", 1, 0.5, true);

        // Act
        serviceProxy.addModuleElement(moduleElement);

        // Assert
        verify(mockRepository, times(1)).saveModuleElement(moduleElement);
    }

    @Test
    public void testAddModuleElementWithInvalidCoefficient() {
        // Arrange
        ModuleElement moduleElement = new ModuleElement(1, "C++", 1, 0.7, true);
        moduleElement.setCoefficient(1.5); // Invalid coefficient

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            serviceProxy.addModuleElement(moduleElement);
        });

        assertEquals("Coefficient must be between 0 and 1.", thrown.getMessage());
        verify(mockRepository, times(0)).saveModuleElement(moduleElement);
    }

    @Test
    public void testUpdateModuleElementWithValidCoefficient() {
        // Arrange
        ModuleElement moduleElement = new ModuleElement(1, "Java", 1, 0.3, true);

        // Act
        serviceProxy.updateModuleElement(moduleElement);

        // Assert
        verify(mockRepository, times(1)).updateModuleElement(moduleElement);
    }

    @Test
    public void testUpdateModuleElementWithInvalidCoefficient() {
        // Arrange
        ModuleElement moduleElement = new ModuleElement(1, "C++", 1, 3, true);

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            serviceProxy.updateModuleElement(moduleElement);
        });

        assertEquals("Coefficient must be between 0 and 1.", thrown.getMessage());
        verify(mockRepository, times(0)).updateModuleElement(moduleElement); // Ensure the method wasn't called
    }

    @Test
    public void testDeleteModuleElement() {
        // Act
        serviceProxy.deleteModuleElement(1);

        // Assert
        verify(mockRepository, times(1)).deleteModuleElement(1);
    }

    @Test
    public void testFindAllByModuleId() {
        // Arrange
        int moduleId = 1;

        // Act
        serviceProxy.findAllByModuleId(moduleId);

        // Assert
        verify(mockRepository, times(1)).findAllByModuleId(moduleId);
    }
}
