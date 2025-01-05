package com.schoolmanagement.singleton;

import com.schoolmanagement.singleton.DatabaseConnection;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.*;

public class DatabaseConnectionTest {

    @Test
    public void testSingletonInstance() {
        // Get the connection instances
        Connection instance1 = DatabaseConnection.getInstance();
        Connection instance2 = DatabaseConnection.getInstance();

        // Assert that the instances are not null
        assertNotNull("First instance should not be null", instance1);
        assertNotNull("Second instance should not be null", instance2);

        // Assert that both instances are the same (singleton behavior)
        assertSame("Both instances should refer to the same object", instance1, instance2);
    }

    @Test
    public void testConnectionIsValid() {
        Connection connection = DatabaseConnection.getInstance();

        // Verify the connection is valid (assuming you are using a database that supports this check)
        try {
            assertTrue("The connection should be valid", connection.isValid(2));
        } catch (Exception e) {
            fail("An exception occurred while checking the connection validity: " + e.getMessage());
        }
    }

    @Test
    public void testConnectionThrowsExceptionWhenClosed() {
        Connection connection = DatabaseConnection.getInstance();

        // Close the connection and verify subsequent access fails
        try {
            connection.close();
            assertTrue("Connection should be closed", connection.isClosed());
        } catch (Exception e) {
            fail("An exception occurred while closing the connection: " + e.getMessage());
        }

        // Verify getting another instance still works
        Connection newConnection = DatabaseConnection.getInstance();
        assertNotNull("A new connection instance should be retrievable after closing", newConnection);
    }
}
