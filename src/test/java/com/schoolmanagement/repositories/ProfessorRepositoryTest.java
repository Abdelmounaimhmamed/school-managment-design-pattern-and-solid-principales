package com.schoolmanagement.repositories;

import com.schoolmanagement.models.Professor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ProfessorRepositoryTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    private ProfessorRepository professorRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        professorRepository = new ProfessorRepository(connection);

        // Default behavior for connection
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

    @Test
    public void enterGrades_ShouldExecuteCorrectSQLStatement() throws Exception {
        // Arrange
        int moduleElementId = 1;
        int studentId = 1;
        double gradeValue = 85.5;

        // Act
        professorRepository.enterGrades(moduleElementId, studentId, gradeValue);

        // Assert
        verify(preparedStatement).setInt(1, studentId);
        verify(preparedStatement).setInt(2, moduleElementId);
        verify(preparedStatement).setDouble(3, gradeValue);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void validateGrades_ShouldExecuteCorrectSQLStatement() throws Exception {
        // Arrange
        int moduleElementId = 1;

        // Act
        professorRepository.validateGrades(moduleElementId);

        // Assert
        verify(preparedStatement).setInt(1, moduleElementId);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void exportGrades_ShouldRetrieveCorrectData() throws Exception {
        // Arrange
        int moduleElementId = 1;
        when(resultSet.next()).thenReturn(true, true, false); // Will return 2 grades
        when(resultSet.getString("name")).thenReturn("John Doe", "Jane Smith");
        when(resultSet.getString("email")).thenReturn("john@example.com", "jane@example.com");
        when(resultSet.getDouble("grade_value")).thenReturn(85.5, 90.0);

        // Act
        professorRepository.exportGrades(moduleElementId);

        // Assert
        verify(preparedStatement).setInt(1, moduleElementId);
        verify(preparedStatement).executeQuery();
        verify(resultSet, times(2)).getString("name");
        verify(resultSet, times(2)).getString("email");
        verify(resultSet, times(2)).getDouble("grade_value");
    }

    @Test
    public void findByUsernameAndPassword_ShouldReturnProfessor_WhenCredentialsMatch() throws Exception {
        // Arrange
        String username = "prof1";
        String password = "pass123";
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("code")).thenReturn("P001");
        when(resultSet.getString("username")).thenReturn(username);
        when(resultSet.getString("password")).thenReturn(password);
        when(resultSet.getString("first_name")).thenReturn("John");
        when(resultSet.getString("last_name")).thenReturn("Doe");
        when(resultSet.getString("specialty")).thenReturn("Computer Science");

        // Act
        Professor professor = professorRepository.findByUsernameAndPassword(username, password);

        // Assert
        assertNotNull(professor);
        assertEquals("P001", professor.getCode());
        assertEquals(username, professor.getUsername());
        assertEquals(password, professor.getPassword());
        assertEquals("John", professor.getFirstName());
        assertEquals("Doe", professor.getLastName());
        assertEquals("Computer Science", professor.getSpecialty());

        verify(preparedStatement).setString(1, username);
        verify(preparedStatement).setString(2, password);
    }

    @Test
    public void findByUsernameAndPassword_ShouldReturnNull_WhenCredentialsDontMatch() throws Exception {
        // Arrange
        when(resultSet.next()).thenReturn(false);

        // Act
        Professor professor = professorRepository.findByUsernameAndPassword("wrong", "wrong");

        // Assert
        assertNull(professor);
    }

    @Test
    public void saveProfessor_ShouldExecuteCorrectSQLStatement() throws Exception {
        // Arrange
        Professor professor = new Professor(
                "P001",
                "prof1",
                "pass123",
                "John",
                "Doe",
                "Computer Science"
        );

        // Act
        professorRepository.saveProfessor(professor);

        // Assert
        verify(preparedStatement).setString(1, professor.getCode());
        verify(preparedStatement).setString(2, professor.getUsername());
        verify(preparedStatement).setString(3, professor.getPassword());
        verify(preparedStatement).setString(4, professor.getFirstName());
        verify(preparedStatement).setString(5, professor.getLastName());
        verify(preparedStatement).setString(6, professor.getSpecialty());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void updateProfessor_ShouldExecuteCorrectSQLStatement() throws Exception {
        // Arrange
        int id = 1;
        String firstName = "John";
        String lastName = "Doe";
        String specialty = "Computer Science";
        String username = "prof1";
        String password = "pass123";

        // Act
        professorRepository.updateProfessor(id, firstName, lastName, specialty, username, password);

        // Assert
        verify(preparedStatement).setString(1, firstName);
        verify(preparedStatement).setString(2, lastName);
        verify(preparedStatement).setString(3, specialty);
        verify(preparedStatement).setString(4, username);
        verify(preparedStatement).setString(5, password);
        verify(preparedStatement).setInt(6, id);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void enterGrades_ShouldHandleExceptionGracefully() throws Exception {
        // Arrange
        when(preparedStatement.executeUpdate()).thenThrow(new RuntimeException("Database error"));

        // Act
        professorRepository.enterGrades(1, 1, 85.5); // Should not throw exception

        // Assert
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void validateGrades_ShouldHandleExceptionGracefully() throws Exception {
        // Arrange
        when(preparedStatement.executeUpdate()).thenThrow(new RuntimeException("Database error"));

        // Act
        professorRepository.validateGrades(1); // Should not throw exception

        // Assert
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void exportGrades_ShouldHandleExceptionGracefully() throws Exception {
        // Arrange
        when(preparedStatement.executeQuery()).thenThrow(new RuntimeException("Database error"));

        // Act
        professorRepository.exportGrades(1); // Should not throw exception

        // Assert
        verify(preparedStatement).executeQuery();
    }
}