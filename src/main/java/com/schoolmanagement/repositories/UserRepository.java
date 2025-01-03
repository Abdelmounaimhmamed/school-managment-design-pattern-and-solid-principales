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

    public void deleteUser(int id) {
        String query = "DELETE FROM Users WHERE id = ?;";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id); 
            pstmt.executeUpdate();
            System.out.println("User deleted successfully.");
        } catch (Exception e) {
            System.err.println("Error deleting user: " + e.getMessage());
        }
    }

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
            pstmt.setString(1, username); 
            
            pstmt.setString(2, password); 
            
            pstmt.setString(3, username); 
            pstmt.setString(4, password); 

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String userType = rs.getString("user_type");
                    if ("Professor".equals(userType)) {
                        return new Professor(
                                rs.getString("id"), 
                                rs.getString("username"),
                                rs.getString("password"),
                                rs.getString("name"),   
                                null,
                                rs.getString("extra_field") 
                        );
                    } else if ("Student".equals(userType)) {
                        return new Student(
                                rs.getInt("id"),
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
