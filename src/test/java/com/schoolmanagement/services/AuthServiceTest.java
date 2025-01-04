package com.schoolmanagement.services;

import com.schoolmanagement.models.Admin;
import com.schoolmanagement.models.Professor;
import com.schoolmanagement.models.Student;
import com.schoolmanagement.models.User;
import com.schoolmanagement.repositories.AdminRepository;
import com.schoolmanagement.repositories.ProfessorRepository;
import com.schoolmanagement.repositories.StudentRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    private AuthService authService;
    private AdminRepository adminRepository;
    private ProfessorRepository professorRepository;
    private StudentRepository studentRepository;

    @Before
    public void setUp() {
        // Mock the repositories
        adminRepository = mock(AdminRepository.class);
        professorRepository = mock(ProfessorRepository.class);
        studentRepository = mock(StudentRepository.class);

        // Create an instance of AuthService with mocked repositories
        authService = new AuthService(adminRepository, professorRepository, studentRepository);
    }

    @Test
    public void testAuthenticateAdmin() {
        // Arrange
        String username = "admin";
        String password = "admin123";
        Admin admin = new Admin(1, username, password);  // Assuming Admin constructor accepts id, username, password

        // Mock the repository behavior
        when(adminRepository.findByUsernameAndPassword(username, password)).thenReturn(admin);

        // Act
        User result = authService.authenticate(username, password, "admin");

        // Assert
        assertNotNull(result);
        assertTrue(result instanceof Admin);
        assertEquals(admin.getUsername(), result.getUsername());
    }

    @Test
    public void testAuthenticateProfessor() {
        // Arrange
        String username = "professor";
        String password = "prof123";
        Professor professor = new Professor("P001", username, password, "John", "Doe", "Mathematics");

        // Mock the repository behavior
        when(professorRepository.findByUsernameAndPassword(username, password)).thenReturn(professor);

        // Act
        User result = authService.authenticate(username, password, "professor");

        // Assert
        assertNotNull(result);
        assertTrue(result instanceof Professor);
        assertEquals(professor.getUsername(), result.getUsername());
    }

    @Test
    public void testAuthenticateStudent() {
        // Arrange
        String username = "student";
        String password = "student123";
        Student student = new Student(1, username, password, "Alice", "alice@example.com");

        // Mock the repository behavior
        when(studentRepository.findByUsernameAndPassword(username, password)).thenReturn(student);

        // Act
        User result = authService.authenticate(username, password, "student");

        // Assert
        assertNotNull(result);
        assertTrue(result instanceof Student);
        assertEquals(student.getUsername(), result.getUsername());
    }

    @Test
    public void testAuthenticateInvalidRole() {
        // Act
        User result = authService.authenticate("anyUser", "anyPassword", "invalidRole");

        // Assert
        assertNull(result);
    }

    @Test
    public void testRegisterProfessor() {
        // Arrange
        Professor professor = new Professor("klk231231", "professor", "prof123", "John", "Doe", "Mathematics");

        // Act
        authService.registerProfessor(professor);

        // Assert
        verify(professorRepository, times(1)).saveProfessor(professor);
    }

    @Test
    public void testRegisterStudent() {
        // Arrange
        Student student = new Student(111, "student", "student123", "Alice", "alice@example.com");

        // Act
        authService.registerStudent(student);

        // Assert
        verify(studentRepository, times(1)).saveStudent(student);
    }
}