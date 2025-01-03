package com.schoolmanagement.repositories;

import com.schoolmanagement.models.ModuleElement;
import com.schoolmanagement.interfaces.ModuleElementRepositoryInterface;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ModuleElementRepository implements ModuleElementRepositoryInterface {
    private static final String DB_URL = "jdbc:sqlite:school_management.db";

    @Override
    public void saveModuleElement(ModuleElement moduleElement) {
        String query = """
                INSERT INTO ModuleElements (name, module_id, coefficient, is_validated)
                VALUES (?, ?, ?, ?);
                """;
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, moduleElement.getName());
            pstmt.setInt(2, moduleElement.getModuleId());
            pstmt.setDouble(3, moduleElement.getCoefficient());
            pstmt.setBoolean(4, moduleElement.isValidated());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateModuleElement(ModuleElement moduleElement) {
        String query = """
                UPDATE ModuleElements
                SET name = ?, module_id = ?, coefficient = ?, is_validated = ?
                WHERE id = ?;
                """;
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, moduleElement.getName());
            pstmt.setInt(2, moduleElement.getModuleId());
            pstmt.setDouble(3, moduleElement.getCoefficient());
            pstmt.setBoolean(4, moduleElement.isValidated());
            pstmt.setInt(5, moduleElement.getId());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteModuleElement(int id) {
        String query = "DELETE FROM ModuleElements WHERE id = ?;";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ModuleElement> findAllByModuleId(int moduleId) {
        String query = """
                SELECT id, name, module_id, coefficient, is_validated
                FROM ModuleElements
                WHERE module_id = ?;
                """;
        List<ModuleElement> moduleElements = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, moduleId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                moduleElements.add(new ModuleElement(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("module_id"),
                        rs.getDouble("coefficient"),
                        rs.getBoolean("is_validated")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return moduleElements;
    }
}
