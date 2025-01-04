package com.schoolmanagement.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AdminTest {

    @Test
    public void testAdminConstructor() {
        // Arrange
        int id = 1;
        String username = "admin";
        String password = "password123";

        // Act
        Admin admin = new Admin(id, username, password);

        // Assert
        assertEquals(id, admin.getId());
        assertEquals(username, admin.getUsername());
        assertEquals(password, admin.getPassword());
    }

    @Test
    public void testSetPassword() {
        // Arrange
        Admin admin = new Admin(1, "admin", "password123");

        // Act
        admin.setPassword("newpassword");

        // Assert
        assertEquals("newpassword", admin.getPassword());
    }
}
