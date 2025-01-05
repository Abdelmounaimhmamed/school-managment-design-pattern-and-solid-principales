package com.schoolmanagement.repositories;

import com.schoolmanagement.models.Professor;
import com.schoolmanagement.models.Student;
import com.schoolmanagement.models.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UserRepositoryTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    private UserRepository userRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userRepository = new UserRepository(mockConnection);
    }

    @Test
    public void testDeleteUser_Success() throws Exception {
        // Arrange
        int userId = 1;
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        // Act
        userRepository.deleteUser(userId);

        // Assert
        verify(mockPreparedStatement).setInt(1, userId);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testDeleteUser_Exception() throws Exception {
        // Arrange
        int userId = 1;
        when(mockConnection.prepareStatement(anyString())).thenThrow(new RuntimeException("DB error"));

        // Act
        userRepository.deleteUser(userId);

        // Assert
        // No exception should propagate; print statements handle errors
        verify(mockConnection).prepareStatement(anyString());
    }

    @Test
    public void testFindByUsernameAndPassword_FindsProfessor() throws Exception {
        // Arrange
        String username = "prof1";
        String password = "securePassword";

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("user_type")).thenReturn("Professor");
        when(mockResultSet.getString("username")).thenReturn(username);
        when(mockResultSet.getString("password")).thenReturn(password);
        when(mockResultSet.getString("name")).thenReturn("John Doe");
        when(mockResultSet.getString("extra_field")).thenReturn("Mathematics");

        // Act
        User user = userRepository.findByUsernameAndPassword(username, password);

        // Assert
        assertNotNull(user);
        assertTrue(user instanceof Professor);
        Professor professor = (Professor) user;
        assertEquals(username, professor.getUsername());
        assertEquals(password, professor.getPassword());
        assertEquals("John Doe", professor.getFirstName());
        assertEquals("Mathematics", professor.getSpecialty());
    }

    @Test
    public void testFindByUsernameAndPassword_FindsStudent() throws Exception {
        // Arrange
        String username = "student1";
        String password = "studentPass";

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("user_type")).thenReturn("Student");
        when(mockResultSet.getString("username")).thenReturn(username);
        when(mockResultSet.getString("password")).thenReturn(password);
        when(mockResultSet.getString("name")).thenReturn("Alice");
        when(mockResultSet.getString("extra_field")).thenReturn("alice@example.com");

        // Act
        User user = userRepository.findByUsernameAndPassword(username, password);

        // Assert
        assertNotNull(user);
        assertTrue(user instanceof Student);
        Student student = (Student) user;
        assertEquals(username, student.getUsername());
        assertEquals(password, student.getPassword());
        assertEquals("Alice", student.getName());
        assertEquals("alice@example.com", student.getEmail());
    }

    @Test
    public void testSaveProfessor_Success() throws Exception {
        // Arrange
        Professor professor = new Professor("P001", "prof1", "securePass", "John", "Doe", "Math");

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        // Act
        userRepository.saveProfessor(professor);

        // Assert
        verify(mockPreparedStatement).setString(1, professor.getCode());
        verify(mockPreparedStatement).setString(2, professor.getUsername());
        verify(mockPreparedStatement).setString(3, professor.getPassword());
        verify(mockPreparedStatement).setString(4, professor.getFirstName());
        verify(mockPreparedStatement).setString(5, professor.getLastName());
        verify(mockPreparedStatement).setString(6, professor.getSpecialty());
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testSaveStudent_Success() throws Exception {
        // Arrange
        Student student = new Student(1, "student1", "securePass", "Alice", "alice@example.com");

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        // Act
        userRepository.saveStudent(student);

        // Assert
        verify(mockPreparedStatement).setString(1, student.getUsername());
        verify(mockPreparedStatement).setString(2, student.getPassword());
        verify(mockPreparedStatement).setString(3, student.getName());
        verify(mockPreparedStatement).setString(4, student.getEmail());
        verify(mockPreparedStatement).executeUpdate();
    }
}
