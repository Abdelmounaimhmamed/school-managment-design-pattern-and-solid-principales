package com.schoolmanagement.services;

import com.schoolmanagement.repositories.ProfessorRepository;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class ProfessorServiceTest {

    private ProfessorRepository professorRepository;
    private ProfessorService professorService;

    @Before
    public void setUp() {
        professorRepository = mock(ProfessorRepository.class);
        professorService = new ProfessorService(professorRepository);
    }

    @Test
    public void testEnterGrades_ShouldCallRepositoryEnterGrades() {
        // Arrange
        int moduleElementId = 1;
        int studentId = 101;
        double gradeValue = 85.5;

        // Act
        professorService.enterGrades(moduleElementId, studentId, gradeValue);

        // Assert
        verify(professorRepository, times(1)).enterGrades(moduleElementId, studentId, gradeValue);
    }

    @Test
    public void testValidateGrades_ShouldCallRepositoryValidateGrades() {
        // Arrange
        int moduleElementId = 1;

        // Act
        professorService.validateGrades(moduleElementId);

        // Assert
        verify(professorRepository, times(1)).validateGrades(moduleElementId);
    }

    @Test
    public void testExportGrades_ShouldCallRepositoryExportGrades() {
        // Arrange
        int moduleElementId = 1;

        // Act
        professorService.exportGrades(moduleElementId);

        // Assert
        verify(professorRepository, times(1)).exportGrades(moduleElementId);
    }
}
