package com.schoolmanagement.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.schoolmanagement.models.Professor;
import com.schoolmanagement.models.Student;
import com.schoolmanagement.models.User;

public class UserRepository {
    private static final String DB_URL = "jdbc:sqlite:school_management.db";

    // Delete a user by their ID
    public void deleteUser(int id) {
        String query = "DELETE FROM Users WHERE id = ?;";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id); // Correctly use setInt for the integer ID
            pstmt.executeUpdate();
            System.out.println("User deleted successfully.");
        } catch (Exception e) {
            System.err.println("Error deleting user: " + e.getMessage());
        }
    }

    // Find a user (Professor or Student) by their username and password
    public User findByUsernameAndPassword(String username, String password) {
        String query = """
                SELECT id, username, password, 'Professor' AS user_type, first_name AS name, specialty AS extra_field
                FROM Professors
                WHERE username = ? AND password = ?
                UNION
                SELECT id, username, password, 'Student' AS user_type, name AS name, email AS extra_field
                FROM Students
                WHERE username = ? AND password = ?;
                """;
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            // Bind parameters for both Professor and Student parts of the query
            pstmt.setString(1, username); // For Professors
            pstmt.setString(2, password); // For Professors
            pstmt.setString(3, username); // For Students
            pstmt.setString(4, password); // For Students

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String userType = rs.getString("user_type");
                    if ("Professor".equals(userType)) {
                        return new Professor(
                                rs.getString("id"), // Assuming `id` is stored as a String
                                rs.getString("username"),
                                rs.getString("password"),
                                rs.getString("name"),   // first_name mapped to "name"
                                null,
                                rs.getString("extra_field")  // Specialty
                        );
                    } else if ("Student".equals(userType)) {
                        return new Student(
                                rs.getInt("id"), // `id` as an integer for students
                                rs.getString("username"),
                                rs.getString("password"),
                                rs.getString("name"),    // name
                                rs.getString("extra_field")  // Email
                        );
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error finding user: " + e.getMessage());
        }
        return null;
    }

    // Save a professor to the database
    public void saveProfessor(Professor professor) {
        String query = """
                INSERT INTO Professors (code, username, password, first_name, last_name, specialty)
                VALUES (?, ?, ?, ?, ?, ?);
                """;
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, professor.getCode());
            pstmt.setString(2, professor.getUsername());
            pstmt.setString(3, professor.getPassword());
            pstmt.setString(4, professor.getFirstName());
            pstmt.setString(5, professor.getLastName());
            pstmt.setString(6, professor.getSpecialty());
            pstmt.executeUpdate();
            System.out.println("Professor registered successfully.");
        } catch (Exception e) {
            System.err.println("Error saving professor: " + e.getMessage());
        }
    }

    // Save a student to the database
    public void saveStudent(Student student) {
        String query = """
                INSERT INTO Students (username, password, name, email)
                VALUES (?, ?, ?, ?);
                """;
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, student.getUsername());
            pstmt.setString(2, student.getPassword());
            pstmt.setString(3, student.getName());
            pstmt.setString(4, student.getEmail());
            pstmt.executeUpdate();
            System.out.println("Student registered successfully.");
        } catch (Exception e) {
            System.err.println("Error saving student: " + e.getMessage());
        }
    }
}
