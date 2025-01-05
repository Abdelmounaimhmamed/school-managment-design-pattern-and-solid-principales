package com.schoolmanagement.repositories;

import com.schoolmanagement.interfaces.FiliereRepositoryInterface;
import com.schoolmanagement.models.Filiere;
import com.schoolmanagement.singleton.DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FiliereRepository implements FiliereRepositoryInterface {
    private final Connection conn;


    public FiliereRepository(Connection conn) {
        this.conn = conn;
    }

    public boolean isFiliereNameExists(String name) {
        String query = "SELECT 1 FROM Filieres WHERE name = ?;";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // Returns true if a row exists
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void saveFiliere(Filiere filiere) {
        String query = "INSERT INTO Filieres (name) VALUES (?);";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, filiere.getName());
            pstmt.executeUpdate();
            System.out.println("Filiere added successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateFiliere(Filiere filiere) {
        String query = "UPDATE Filieres SET name = ? WHERE id = ?;";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, filiere.getName());
            pstmt.setInt(2, filiere.getId());
            pstmt.executeUpdate();
            System.out.println("Filiere updated successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteFiliere(int id) {
        String query = "DELETE FROM Filieres WHERE id = ?;";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Filiere deleted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Filiere> findAllFilieres() {
        List<Filiere> filieres = new ArrayList<>();
        String query = "SELECT * FROM Filieres;";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Filiere filiere = new Filiere(
                        rs.getInt("id"),
                        rs.getString("name")
                );
                filieres.add(filiere);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filieres;
    }
}
