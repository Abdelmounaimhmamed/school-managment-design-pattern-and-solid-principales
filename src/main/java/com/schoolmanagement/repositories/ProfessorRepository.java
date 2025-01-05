package com.schoolmanagement.repositories;

import com.schoolmanagement.models.Professor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProfessorRepository {
    private final Connection conn;

    public ProfessorRepository(Connection conn) {
        this.conn = conn;
    }

    public void enterGrades(int moduleElementId, int studentId, double gradeValue) {
        String query = """
                INSERT OR REPLACE INTO Grades (student_id, module_element_id, grade_value, is_absent)
                VALUES (?, ?, ?, 0);
                """;
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, moduleElementId);
            pstmt.setDouble(3, gradeValue);
            pstmt.executeUpdate();
            System.out.println("Grade entered successfully.");
        } catch (Exception e) {
            System.err.println("Error entering grades: " + e.getMessage());
        }
    }

    public void validateGrades(int moduleElementId) {
        String query = """
                UPDATE ModuleElements
                SET is_validated = 1
                WHERE id = ?;
                """;
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, moduleElementId);
            pstmt.executeUpdate();
            System.out.println("Grades validated successfully.");
        } catch (Exception e) {
            System.err.println("Error validating grades: " + e.getMessage());
        }
    }

    public void exportGrades(int moduleElementId) {
        String query = """
                SELECT g.grade_value, s.name, s.email
                FROM Grades g
                JOIN Students s ON g.student_id = s.id
                WHERE g.module_element_id = ?;
                """;
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, moduleElementId);
            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println("Exporting Grades:");
                while (rs.next()) {
                    System.out.printf("Student: %s, Email: %s, Grade: %.2f%n",
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getDouble("grade_value"));
                }
            }
        } catch (Exception e) {
            System.err.println("Error exporting grades: " + e.getMessage());
        }
    }

    public Professor findByUsernameAndPassword(String username, String password) {
        String query = """
                SELECT id, username, password, first_name, last_name, specialty, code
                FROM Professors
                WHERE username = ? AND password = ?;
                """;
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Professor(
                            rs.getString("code"), // Assuming code is String
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("specialty")
                    );
                }
            }
        } catch (Exception e) {
            System.err.println("Error finding professor: " + e.getMessage());
        }
        return null;
    }

    public void saveProfessor(Professor professor) {
        String query = """
                INSERT INTO Professors (code, username, password, first_name, last_name, specialty)
                VALUES (?, ?, ?, ?, ?, ?);
                """;
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
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

    public void updateProfessor(int id, String firstName, String lastName, String specialty, String username, String password) {
        String query = """
                UPDATE Professors
                SET first_name = ?, last_name = ?, specialty = ?, username = ?, password = ?
                WHERE id = ?;
                """;
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, specialty);
            pstmt.setString(4, username);
            pstmt.setString(5, password);
            pstmt.setInt(6, id);
            pstmt.executeUpdate();
            System.out.println("Professor updated successfully.");
        } catch (Exception e) {
            System.err.println("Error updating professor: " + e.getMessage());
        }
    }
}
