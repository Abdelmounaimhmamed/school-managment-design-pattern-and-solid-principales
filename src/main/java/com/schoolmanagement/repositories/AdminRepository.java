package com.schoolmanagement.repositories;

import com.schoolmanagement.builders.AdminBuilder;
import com.schoolmanagement.interfaces.AdminRepositoryInterface;
import com.schoolmanagement.models.Admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminRepository implements AdminRepositoryInterface {
    private static final String DB_URL = "jdbc:sqlite:school_management.db";

    @Override
    public Admin findByUsernameAndPassword(String username, String password) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String query = """
                    SELECT id, username, password
                    FROM Admins
                    WHERE username = ? AND password = ?;
                    """;
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return new AdminBuilder()
                            .setId(rs.getInt("id"))
                            .setUsername(rs.getString("username"))
                            .setPassword(rs.getString("password"))
                            .build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addFiliere(String name) {
        String query = "INSERT INTO Filieres (name) VALUES (?);";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
            System.out.println("Filiere added successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateFiliere(int id, String name) {
        String query = "UPDATE Filieres SET name = ? WHERE id = ?;";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            System.out.println("Filiere updated successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteFiliere(int id) {
        String deleteModulesQuery = "DELETE FROM Modules WHERE filiere_id = ?;";
        String deleteFiliereQuery = "DELETE FROM Filieres WHERE id = ?;";
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            try (PreparedStatement deleteModulesStmt = conn.prepareStatement(deleteModulesQuery);
                 PreparedStatement deleteFiliereStmt = conn.prepareStatement(deleteFiliereQuery)) {
                deleteModulesStmt.setInt(1, id);
                deleteModulesStmt.executeUpdate();

                deleteFiliereStmt.setInt(1, id);
                deleteFiliereStmt.executeUpdate();
                System.out.println("Filiere and associated modules deleted successfully.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void viewFilieres() {
        String query = "SELECT * FROM Filieres;";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.printf("ID: %d, Name: %s%n", rs.getInt("id"), rs.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addProfessor(String firstName, String lastName, String specialty, String username, String password, int filiereId) {
        String query = """
                INSERT INTO Professors (code, first_name, last_name, specialty, username, password, filiere_id)
                VALUES (?, ?, ?, ?, ?, ?, ?);
                """;
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            String code = "P" + System.currentTimeMillis();
            pstmt.setString(1, code);
            pstmt.setString(2, firstName);
            pstmt.setString(3, lastName);
            pstmt.setString(4, specialty);
            pstmt.setString(5, username);
            pstmt.setString(6, password);
            pstmt.setInt(7, filiereId);
            pstmt.executeUpdate();
            System.out.println("Professor added successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateProfessor(int id, String firstName, String lastName, String specialty, String username, String password, int filiereId) {
        String query = """
                UPDATE Professors
                SET first_name = ?, last_name = ?, specialty = ?, username = ?, password = ?, filiere_id = ?
                WHERE id = ?;
                """;
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, specialty);
            pstmt.setString(4, username);
            pstmt.setString(5, password);
            pstmt.setInt(6, filiereId);
            pstmt.setInt(7, id);
            pstmt.executeUpdate();
            System.out.println("Professor updated successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteProfessor(int id) {
        String query = "DELETE FROM Professors WHERE id = ?;";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Professor deleted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void viewProfessors() {
        String query = """
                SELECT p.id, p.first_name, p.last_name, p.specialty, f.name AS filiere_name
                FROM Professors p
                LEFT JOIN Filieres f ON p.filiere_id = f.id;
                """;
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.printf("ID: %d, First Name: %s, Last Name: %s, Specialty: %s, Filiere: %s%n",
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("specialty"),
                        rs.getString("filiere_name") != null ? rs.getString("filiere_name") : "None");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
