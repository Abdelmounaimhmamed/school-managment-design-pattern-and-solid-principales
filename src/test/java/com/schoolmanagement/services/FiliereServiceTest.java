package com.schoolmanagement.services;

import com.schoolmanagement.models.Filiere;
import com.schoolmanagement.repositories.FiliereRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class FiliereServiceTest {

    private FiliereRepository filiereRepository;
    private FiliereService filiereService;

    @Before
    public void setUp() {
        filiereRepository = mock(FiliereRepository.class);
        filiereService = new FiliereService(filiereRepository);
    }

    @Test
    public void testAddFiliere_WhenNameExists_ShouldPrintError() {
        // Arrange
        Filiere existingFiliere = new Filiere(1, "Computer Science");
        when(filiereRepository.isFiliereNameExists("Computer Science")).thenReturn(true);

        // Act
        filiereService.addFiliere(existingFiliere);

        // Assert
        verify(filiereRepository, never()).saveFiliere(any(Filiere.class));
    }

    @Test
    public void testAddFiliere_WhenNameDoesNotExist_ShouldSaveFiliere() {
        // Arrange
        Filiere newFiliere = new Filiere(1, "Mathematics");
        when(filiereRepository.isFiliereNameExists("Mathematics")).thenReturn(false);

        // Act
        filiereService.addFiliere(newFiliere);

        // Assert
        verify(filiereRepository, times(1)).saveFiliere(newFiliere);
    }

    @Test
    public void testUpdateFiliere_ShouldCallRepositoryUpdate() {
        // Arrange
        Filiere updatedFiliere = new Filiere(1, "Physics");

        // Act
        filiereService.updateFiliere(updatedFiliere);

        // Assert
        verify(filiereRepository, times(1)).updateFiliere(updatedFiliere);
    }

    @Test
    public void testDeleteFiliere_ShouldCallRepositoryDelete() {
        // Act
        filiereService.deleteFiliere(1);

        // Assert
        verify(filiereRepository, times(1)).deleteFiliere(1);
    }

    @Test
    public void testFindAllFilieres_ShouldReturnListOfFilieres() {
        // Arrange
        List<Filiere> filieres = Arrays.asList(
                new Filiere(1, "Computer Science"),
                new Filiere(2, "Mathematics")
        );
        when(filiereRepository.findAllFilieres()).thenReturn(filieres);

        // Act
        List<Filiere> result = filiereService.findAllFilieres();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Computer Science", result.get(0).getName());
        assertEquals("Mathematics", result.get(1).getName());
        verify(filiereRepository, times(1)).findAllFilieres();
    }
}