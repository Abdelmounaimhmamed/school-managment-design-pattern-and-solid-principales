package com.schoolmanagement.services;

import com.schoolmanagement.interfaces.AdminRepositoryInterface;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class AdminServiceTest {

    private AdminService adminService;
    private AdminRepositoryInterface adminRepository;

    @Before
    public void setUp() {
        adminRepository = mock(AdminRepositoryInterface.class); // Mock the repository
        adminService = new AdminService(adminRepository);       // Inject the mock into the service
    }

    @Test
    public void testAddFiliere() {
        // Arrange
        String filiereName = "Computer Science";

        // Act
        adminService.addFiliere(filiereName);

        // Assert
        verify(adminRepository, times(1)).addFiliere(filiereName);
    }

    @Test
    public void testUpdateFiliere() {
        // Arrange
        int filiereId = 1;
        String newName = "Data Science";

        // Act
        adminService.updateFiliere(filiereId, newName);

        // Assert
        verify(adminRepository, times(1)).updateFiliere(filiereId, newName);
    }

    @Test
    public void testDeleteFiliere() {
        // Arrange
        int filiereId = 2;

        // Act
        adminService.deleteFiliere(filiereId);

        // Assert
        verify(adminRepository, times(1)).deleteFiliere(filiereId);
    }

    @Test
    public void testViewFilieres() {
        // Act
        adminService.viewFilieres();

        // Assert
        verify(adminRepository, times(1)).viewFilieres();
    }

    @Test
    public void VoidtestAddProfessor() {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        String specialty = "Mathematics";
        String username = "johndoe";
        String password = "password123";
        int filiereId = 1;

        // Act
        adminService.addProfessor(firstName, lastName, specialty, username, password, filiereId);

        // Assert
        verify(adminRepository, times(1)).addProfessor(firstName, lastName, specialty, username, password, filiereId);
    }

    @Test
    public void testUpdateProfessor() {
        // Arrange
        int professorId = 1;
        String firstName = "John";
        String lastName = "Smith";
        String specialty = "Physics";
        String username = "johnsmith";
        String password = "newpassword123";
        int filiereId = 2;

        // Act
        adminService.updateProfessor(professorId, firstName, lastName, specialty, username, password, filiereId);

        // Assert
        verify(adminRepository, times(1)).updateProfessor(professorId, firstName, lastName, specialty, username, password, filiereId);
    }

    @Test
    public void testDeleteProfessor() {
        // Arrange
        int professorId = 3;

        // Act
        adminService.deleteProfessor(professorId);

        // Assert
        verify(adminRepository, times(1)).deleteProfessor(professorId);
    }

    @Test
    public void testViewProfessors() {
        // Act
        adminService.viewProfessors();

        // Assert
        verify(adminRepository, times(1)).viewProfessors();
    }
}
