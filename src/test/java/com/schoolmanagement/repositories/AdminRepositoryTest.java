package com.schoolmanagement.repositories;

import com.schoolmanagement.models.Admin;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AdminRepositoryTest {
    private AdminRepository adminRepository;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    @Before
    public void setUp() throws Exception {
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        // Replace the actual connection with the mock
        adminRepository = new AdminRepository(mockConnection);
//        DatabaseConnection mockDatabaseConnection = Mockito.mock(DatabaseConnection.class);
//        when(mockDatabaseConnection.getInstance()).thenReturn(mockConnection);
    }
    @Test
    public void testFindByUsernameAndPassword_ValidCredentials() throws Exception {
        String username = "admin";
        String password = "password";

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("username")).thenReturn(username);
        when(mockResultSet.getString("password")).thenReturn(password);

        Admin admin = adminRepository.findByUsernameAndPassword(username, password);

        assertNotNull("Admin should not be null", admin);
        assertEquals("Username should match", username, admin.getUsername());
        assertEquals("Password should match", password, admin.getPassword());

        verify(mockPreparedStatement, times(1)).setString(1, username);
        verify(mockPreparedStatement, times(1)).setString(2, password);
    }

    @Test
    public void testFindByUsernameAndPassword_InvalidCredentials() throws Exception {
        String username = "invalid";
        String password = "wrong";

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        Admin admin = adminRepository.findByUsernameAndPassword(username, password);

        assertNull("Admin should be null for invalid credentials", admin);
        verify(mockPreparedStatement, times(1)).setString(1, username);
        verify(mockPreparedStatement, times(1)).setString(2, password);
    }

    @Test
    public void testAddFiliere_Success() throws Exception {
        String filiereName = "Engineering";

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        adminRepository.addFiliere(filiereName);

        verify(mockPreparedStatement, times(1)).setString(1, filiereName);
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testUpdateFiliere_Success() throws Exception {
        int id = 1;
        String newName = "Updated Filiere";

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        adminRepository.updateFiliere(id, newName);

        verify(mockPreparedStatement, times(1)).setString(1, newName);
        verify(mockPreparedStatement, times(1)).setInt(2, id);
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testDeleteFiliere_Success() throws Exception {
        int id = 1;

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        adminRepository.deleteFiliere(id);

        verify(mockPreparedStatement, times(2)).setInt(1, id);
        verify(mockPreparedStatement, times(2)).executeUpdate();
    }

    @Test
    public void testViewFilieres() throws Exception {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("id")).thenReturn(1, 2);
        when(mockResultSet.getString("name")).thenReturn("Engineering", "Science");

        adminRepository.viewFilieres();

        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockResultSet, atLeastOnce()).getInt("id");
        verify(mockResultSet, atLeastOnce()).getString("name");
    }

}
