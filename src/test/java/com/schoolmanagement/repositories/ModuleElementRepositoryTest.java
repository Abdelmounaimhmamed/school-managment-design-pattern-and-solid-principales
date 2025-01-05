package com.schoolmanagement.repositories;

import com.schoolmanagement.models.ModuleElement;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ModuleElementRepositoryTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    private ModuleElementRepository moduleElementRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        moduleElementRepository = new ModuleElementRepository(connection);

        // Default behavior for connection
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

    @Test
    public void saveModuleElement_ShouldExecuteCorrectSQLStatement() throws Exception {
        // Arrange
        ModuleElement moduleElement = new ModuleElement(1, "Math 101", 1, 2.0, true);

        // Act
        moduleElementRepository.saveModuleElement(moduleElement);

        // Assert
        verify(preparedStatement).setString(1, moduleElement.getName());
        verify(preparedStatement).setInt(2, moduleElement.getModuleId());
        verify(preparedStatement).setDouble(3, moduleElement.getCoefficient());
        verify(preparedStatement).setBoolean(4, moduleElement.isValidated());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void updateModuleElement_ShouldExecuteCorrectSQLStatement() throws Exception {
        // Arrange
        ModuleElement moduleElement = new ModuleElement(1, "Math 101", 1, 2.0, true);

        // Act
        moduleElementRepository.updateModuleElement(moduleElement);

        // Assert
        verify(preparedStatement).setString(1, moduleElement.getName());
        verify(preparedStatement).setInt(2, moduleElement.getModuleId());
        verify(preparedStatement).setDouble(3, moduleElement.getCoefficient());
        verify(preparedStatement).setBoolean(4, moduleElement.isValidated());
        verify(preparedStatement).setInt(5, moduleElement.getId());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void deleteModuleElement_ShouldExecuteCorrectSQLStatement() throws Exception {
        // Arrange
        int moduleElementId = 1;

        // Act
        moduleElementRepository.deleteModuleElement(moduleElementId);

        // Assert
        verify(preparedStatement).setInt(1, moduleElementId);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void findAllByModuleId_ShouldReturnListOfModuleElements() throws Exception {
        // Arrange
        int moduleId = 1;
        when(resultSet.next()).thenReturn(true, true, false); // Will return 2 module elements

        // Mock first and second module elements
        when(resultSet.getInt("id")).thenReturn(1, 2);
        when(resultSet.getString("name")).thenReturn("Math 101", "Physics 101");
        when(resultSet.getInt("module_id")).thenReturn(moduleId, moduleId);
        when(resultSet.getDouble("coefficient")).thenReturn(2.0, 1.5);
        when(resultSet.getBoolean("is_validated")).thenReturn(true, false);

        // Act
        List<ModuleElement> moduleElements = moduleElementRepository.findAllByModuleId(moduleId);

        // Assert
        assertEquals(2, moduleElements.size());

        ModuleElement firstElement = moduleElements.get(0);
        assertEquals(1, firstElement.getId());
        assertEquals("Math 101", firstElement.getName());
        assertEquals(moduleId, firstElement.getModuleId());
        assertEquals(2.0, firstElement.getCoefficient(), 0.001);
        assertTrue(firstElement.isValidated());

        ModuleElement secondElement = moduleElements.get(1);
        assertEquals(2, secondElement.getId());
        assertEquals("Physics 101", secondElement.getName());
        assertEquals(moduleId, secondElement.getModuleId());
        assertEquals(1.5, secondElement.getCoefficient(), 0.001);
        assertFalse(secondElement.isValidated());

        verify(preparedStatement).setInt(1, moduleId);
        verify(preparedStatement).executeQuery();
    }

    @Test
    public void findAllByModuleId_ShouldReturnEmptyList_WhenNoElementsFound() throws Exception {
        // Arrange
        int moduleId = 1;
        when(resultSet.next()).thenReturn(false);

        // Act
        List<ModuleElement> moduleElements = moduleElementRepository.findAllByModuleId(moduleId);

        // Assert
        assertTrue(moduleElements.isEmpty());
        verify(preparedStatement).setInt(1, moduleId);
        verify(preparedStatement).executeQuery();
    }

    @Test
    public void saveModuleElement_ShouldHandleExceptionGracefully() throws Exception {
        // Arrange
        ModuleElement moduleElement = new ModuleElement(1, "Math 101", 1, 2.0, true);
        when(preparedStatement.executeUpdate()).thenThrow(new RuntimeException("Database error"));

        // Act
        moduleElementRepository.saveModuleElement(moduleElement); // Should not throw exception

        // Assert
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void updateModuleElement_ShouldHandleExceptionGracefully() throws Exception {
        // Arrange
        ModuleElement moduleElement = new ModuleElement(1, "Math 101", 1, 2.0, true);
        when(preparedStatement.executeUpdate()).thenThrow(new RuntimeException("Database error"));

        // Act
        moduleElementRepository.updateModuleElement(moduleElement); // Should not throw exception

        // Assert
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void deleteModuleElement_ShouldHandleExceptionGracefully() throws Exception {
        // Arrange
        when(preparedStatement.executeUpdate()).thenThrow(new RuntimeException("Database error"));

        // Act
        moduleElementRepository.deleteModuleElement(1); // Should not throw exception

        // Assert
        verify(preparedStatement).executeUpdate();
    }
}