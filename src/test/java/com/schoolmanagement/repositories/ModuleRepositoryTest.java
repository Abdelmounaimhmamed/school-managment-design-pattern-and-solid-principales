package com.schoolmanagement.repositories;

import com.schoolmanagement.models.Module;
import com.schoolmanagement.builders.ModuleBuilder;
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

public class ModuleRepositoryTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    private ModuleRepository moduleRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        moduleRepository = new ModuleRepository(connection);

        // Default behavior for connection
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

    @Test
    public void addModule_ShouldExecuteCorrectSQLStatement() throws Exception {
        // Arrange
        Module module = new ModuleBuilder()
                .setId(1)
                .setCode("CS101")
                .setName("Computer Science 101")
                .setFiliereId(1)
                .setSemester(1)
                .setProfessorId(1)
                .build();

        // Act
        moduleRepository.addModule(module);

        // Assert
        verify(preparedStatement).setString(1, module.getCode());
        verify(preparedStatement).setString(2, module.getName());
        verify(preparedStatement).setInt(3, module.getFiliereId());
        verify(preparedStatement).setInt(4, module.getSemester());
        verify(preparedStatement).setInt(5, module.getProfessorId());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void updateModule_ShouldExecuteCorrectSQLStatement() throws Exception {
        // Arrange
        Module module = new ModuleBuilder()
                .setId(1)
                .setCode("CS101")
                .setName("Computer Science 101")
                .setFiliereId(1)
                .setSemester(1)
                .setProfessorId(1)
                .build();

        // Act
        moduleRepository.updateModule(module);

        // Assert
        verify(preparedStatement).setString(1, module.getCode());
        verify(preparedStatement).setString(2, module.getName());
        verify(preparedStatement).setInt(3, module.getFiliereId());
        verify(preparedStatement).setInt(4, module.getSemester());
        verify(preparedStatement).setInt(5, module.getProfessorId());
        verify(preparedStatement).setInt(6, module.getId());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void deleteModule_ShouldExecuteCorrectSQLStatement() throws Exception {
        // Arrange
        int moduleId = 1;

        // Act
        moduleRepository.deleteModule(moduleId);

        // Assert
        verify(preparedStatement).setInt(1, moduleId);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void findById_ShouldReturnModule_WhenModuleExists() throws Exception {
        // Arrange
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("code")).thenReturn("CS101");
        when(resultSet.getString("name")).thenReturn("Computer Science 101");
        when(resultSet.getInt("filiere_id")).thenReturn(1);
        when(resultSet.getInt("semester")).thenReturn(1);
        when(resultSet.getInt("professor_id")).thenReturn(1);

        // Act
        Module module = moduleRepository.findById(1);

        // Assert
        assertNotNull(module);
        assertEquals(1, module.getId());
        assertEquals("CS101", module.getCode());
        assertEquals("Computer Science 101", module.getName());
        assertEquals(1, module.getFiliereId());
        assertEquals(1, module.getSemester());
        assertEquals(1, module.getProfessorId());

        verify(preparedStatement).setInt(1, 1);
        verify(preparedStatement).executeQuery();
    }

    @Test
    public void findById_ShouldReturnNull_WhenModuleDoesNotExist() throws Exception {
        // Arrange
        when(resultSet.next()).thenReturn(false);

        // Act
        Module module = moduleRepository.findById(1);

        // Assert
        assertNull(module);
        verify(preparedStatement).setInt(1, 1);
        verify(preparedStatement).executeQuery();
    }

    @Test
    public void findAll_ShouldReturnListOfModules() throws Exception {
        // Arrange
        when(resultSet.next()).thenReturn(true, true, false); // Will return 2 modules
        when(resultSet.getInt("id")).thenReturn(1, 2);
        when(resultSet.getString("code")).thenReturn("CS101", "CS102");
        when(resultSet.getString("name")).thenReturn("Computer Science 101", "Computer Science 102");
        when(resultSet.getInt("filiere_id")).thenReturn(1, 1);
        when(resultSet.getInt("semester")).thenReturn(1, 1);
        when(resultSet.getInt("professor_id")).thenReturn(1, 2);

        // Act
        List<Module> modules = moduleRepository.findAll();

        // Assert
        assertEquals(2, modules.size());

        Module firstModule = modules.get(0);
        assertEquals(1, firstModule.getId());
        assertEquals("CS101", firstModule.getCode());
        assertEquals("Computer Science 101", firstModule.getName());

        Module secondModule = modules.get(1);
        assertEquals(2, secondModule.getId());
        assertEquals("CS102", secondModule.getCode());
        assertEquals("Computer Science 102", secondModule.getName());

        verify(preparedStatement).executeQuery();
    }

    @Test
    public void findAll_ShouldReturnEmptyList_WhenNoModulesExist() throws Exception {
        // Arrange
        when(resultSet.next()).thenReturn(false);

        // Act
        List<Module> modules = moduleRepository.findAll();

        // Assert
        assertTrue(modules.isEmpty());
        verify(preparedStatement).executeQuery();
    }

//    @Test
//    public void addModule_ShouldHandleExceptionGracefully() throws Exception {
//        // Arrange
//        Module module = new ModuleBuilder()
//                .setId(1)
//                .setCode("CS101")
//                .setName("Computer Science 101")
//                .setFiliereId(1)
//                .setSemester(1)
//                .setProfessorId(1)
//                .build();
//        when(preparedStatement.executeUpdate()).thenThrow(new RuntimeException("Database error"));
//
//        // Act
//        moduleRepository.addModule(module); // Should not throw exception
//
//        // Assert
//        verify(preparedStatement).executeUpdate();
//    }
//
//    @Test
//    public void updateModule_ShouldHandleExceptionGracefully() throws Exception {
//        // Arrange
//        Module module = new ModuleBuilder()
//                .setId(1)
//                .setCode("CS101")
//                .setName("Computer Science 101")
//                .setFiliereId(1)
//                .setSemester(1)
//                .setProfessorId(1)
//                .build();
//        when(preparedStatement.executeUpdate()).thenThrow(new RuntimeException("Database error"));
//
//        // Act
//        moduleRepository.updateModule(module); // Should not throw exception
//
//        // Assert
//        verify(preparedStatement).executeUpdate();
//    }
//
//    @Test
//    public void deleteModule_ShouldHandleExceptionGracefully() throws Exception {
//        // Arrange
//        when(preparedStatement.executeUpdate()).thenThrow(new RuntimeException("Database error"));
//
//        // Act
//        moduleRepository.deleteModule(1); // Should not throw exception
//
//        // Assert
//        verify(preparedStatement).executeUpdate();
//    }
}