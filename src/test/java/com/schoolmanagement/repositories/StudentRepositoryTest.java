package com.schoolmanagement.repositories;

import com.schoolmanagement.models.Student;
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

public class StudentRepositoryTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    private StudentRepository studentRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        studentRepository = new StudentRepository(connection);

        // Default behavior for connection
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

    @Test
    public void findAll_ShouldReturnListOfStudents() throws Exception {
        // Arrange
        when(resultSet.next()).thenReturn(true, true, false); // Will return 2 students

        when(resultSet.getInt("id")).thenReturn(1, 2);
        when(resultSet.getString("username")).thenReturn("john", "jane");
        when(resultSet.getString("password")).thenReturn("pass1", "pass2");
        when(resultSet.getString("name")).thenReturn("John Doe", "Jane Smith");
        when(resultSet.getString("email")).thenReturn("john@example.com", "jane@example.com");

        // Act
        List<Student> students = studentRepository.findAll();

        // Assert
        assertEquals(2, students.size());

        Student firstStudent = students.get(0);
        assertEquals(1, firstStudent.getId());
        assertEquals("john", firstStudent.getUsername());
        assertEquals("pass1", firstStudent.getPassword());
        assertEquals("John Doe", firstStudent.getName());
        assertEquals("john@example.com", firstStudent.getEmail());

        Student secondStudent = students.get(1);
        assertEquals(2, secondStudent.getId());
        assertEquals("jane", secondStudent.getUsername());
        assertEquals("pass2", secondStudent.getPassword());
        assertEquals("Jane Smith", secondStudent.getName());
        assertEquals("jane@example.com", secondStudent.getEmail());

        verify(preparedStatement).executeQuery();
    }

    @Test
    public void findAll_ShouldReturnEmptyList_WhenNoStudentsExist() throws Exception {
        // Arrange
        when(resultSet.next()).thenReturn(false);

        // Act
        List<Student> students = studentRepository.findAll();

        // Assert
        assertTrue(students.isEmpty());
        verify(preparedStatement).executeQuery();
    }

    @Test
    public void saveStudent_ShouldExecuteCorrectSQLStatement() throws Exception {
        // Arrange
        Student student = new Student(1, "john", "pass123", "John Doe", "john@example.com");

        // Act
        studentRepository.saveStudent(student);

        // Assert
        verify(preparedStatement).setString(1, student.getUsername());
        verify(preparedStatement).setString(2, student.getPassword());
        verify(preparedStatement).setString(3, student.getName());
        verify(preparedStatement).setString(4, student.getEmail());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void deleteById_ShouldExecuteCorrectSQLStatement() throws Exception {
        // Arrange
        int studentId = 1;

        // Act
        studentRepository.deleteById(studentId);

        // Assert
        verify(preparedStatement).setInt(1, studentId);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void findByUsernameAndPassword_ShouldReturnStudent_WhenCredentialsMatch() throws Exception {
        // Arrange
        String username = "john";
        String password = "pass123";

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("username")).thenReturn(username);
        when(resultSet.getString("password")).thenReturn(password);
        when(resultSet.getString("name")).thenReturn("John Doe");
        when(resultSet.getString("email")).thenReturn("john@example.com");

        // Act
        Student student = studentRepository.findByUsernameAndPassword(username, password);

        // Assert
        assertNotNull(student);
        assertEquals(1, student.getId());
        assertEquals(username, student.getUsername());
        assertEquals(password, student.getPassword());
        assertEquals("John Doe", student.getName());
        assertEquals("john@example.com", student.getEmail());

        verify(preparedStatement).setString(1, username);
        verify(preparedStatement).setString(2, password);
    }

    @Test
    public void findByUsernameAndPassword_ShouldReturnNull_WhenCredentialsDontMatch() throws Exception {
        // Arrange
        when(resultSet.next()).thenReturn(false);

        // Act
        Student student = studentRepository.findByUsernameAndPassword("wrong", "wrong");

        // Assert
        assertNull(student);
        verify(preparedStatement).setString(1, "wrong");
        verify(preparedStatement).setString(2, "wrong");
    }

//    @Test
//    public void findAll_ShouldHandleExceptionGracefully() throws Exception {
//        // Arrange
//        when(preparedStatement.executeQuery()).thenThrow(new RuntimeException("Database error"));
//
//        // Act
//        List<Student> students = studentRepository.findAll();
//
//        // Assert
//        assertTrue(students.isEmpty());
//        verify(preparedStatement).executeQuery();
//    }
//
//    @Test
//    public void saveStudent_ShouldHandleExceptionGracefully() throws Exception {
//        // Arrange
//        Student student = new Student(1, "john", "pass123", "John Doe", "john@example.com");
//        when(preparedStatement.executeUpdate()).thenThrow(new RuntimeException("Database error"));
//
//        // Act
//        studentRepository.saveStudent(student); // Should not throw exception
//
//        // Assert
//        verify(preparedStatement).executeUpdate();
//    }
//
//    @Test
//    public void deleteById_ShouldHandleExceptionGracefully() throws Exception {
//        // Arrange
//        when(preparedStatement.executeUpdate()).thenThrow(new RuntimeException("Database error"));
//
//        // Act
//        studentRepository.deleteById(1); // Should not throw exception
//
//        // Assert
//        verify(preparedStatement).executeUpdate();
//    }
}