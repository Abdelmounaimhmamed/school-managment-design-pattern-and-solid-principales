package com.schoolmanagement.repositories;

import com.schoolmanagement.models.Filiere;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FiliereRepositoryTest {

    private FiliereRepository filiereRepository;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    @Before
    public void setUp() throws Exception {
        // Mock the Connection, PreparedStatement, and ResultSet
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        // Initialize FiliereRepository with the mocked connection
        filiereRepository = new FiliereRepository(mockConnection);
    }

    @Test
    public void testIsFiliereNameExists_True() throws Exception {
        String filiereName = "Engineering";

        // Mock the behavior of the ResultSet
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);  // Simulate a matching filiere

        boolean exists = filiereRepository.isFiliereNameExists(filiereName);

        assertTrue("Filiere name should exist", exists);
        verify(mockPreparedStatement, times(1)).setString(1, filiereName);
    }

    @Test
    public void testIsFiliereNameExists_False() throws Exception {
        String filiereName = "NonExistingFiliere";

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);  // Simulate no matching filiere

        boolean exists = filiereRepository.isFiliereNameExists(filiereName);

        assertFalse("Filiere name should not exist", exists);
        verify(mockPreparedStatement, times(1)).setString(1, filiereName);
    }

    @Test
    public void testSaveFiliere() throws Exception {
        Filiere filiere = new Filiere("Engineering");

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        filiereRepository.saveFiliere(filiere);

        verify(mockPreparedStatement, times(1)).setString(1, filiere.getName());
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testFindAllFilieres() throws Exception {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);  // Simulate one row returned
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("name")).thenReturn("Engineering");

        List<Filiere> filieres = filiereRepository.findAllFilieres();

        assertNotNull("Filieres list should not be null", filieres);
        assertEquals("There should be one filiere", 1, filieres.size());
        assertEquals("Filiere name should match", "Engineering", filieres.get(0).getName());
    }

    @Test
    public void testUpdateFiliere() throws Exception {
        Filiere filiere = new Filiere(1, "Updated Filiere");

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        filiereRepository.updateFiliere(filiere);

        verify(mockPreparedStatement, times(1)).setString(1, filiere.getName());
        verify(mockPreparedStatement, times(1)).setInt(2, filiere.getId());
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testDeleteFiliere() throws Exception {
        int filiereId = 1;

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        filiereRepository.deleteFiliere(filiereId);

        verify(mockPreparedStatement, times(1)).setInt(1, filiereId);
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }
}
