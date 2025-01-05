package com.schoolmanagement.repositories;

import com.schoolmanagement.models.Module;
import com.schoolmanagement.builders.ModuleBuilder;
import com.schoolmanagement.interfaces.ModuleRepositoryInterface;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ModuleRepository implements ModuleRepositoryInterface {
    private final Connection conn;

    public ModuleRepository(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void addModule(Module module) {
        String query = """
                INSERT INTO Modules (code, name, filiere_id, semester, professor_id)
                VALUES (?, ?, ?, ?, ?);
                """;
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, module.getCode());
            pstmt.setString(2, module.getName());
            pstmt.setInt(3, module.getFiliereId());
            pstmt.setInt(4, module.getSemester());
            pstmt.setInt(5, module.getProfessorId());
            pstmt.executeUpdate();
            System.out.println("Module added successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateModule(Module module) {
        String query = """
                UPDATE Modules
                SET code = ?, name = ?, filiere_id = ?, semester = ?, professor_id = ?
                WHERE id = ?;
                """;
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, module.getCode());
            pstmt.setString(2, module.getName());
            pstmt.setInt(3, module.getFiliereId());
            pstmt.setInt(4, module.getSemester());
            pstmt.setInt(5, module.getProfessorId());
            pstmt.setInt(6, module.getId());
            pstmt.executeUpdate();
            System.out.println("Module updated successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteModule(int moduleId) {
        String query = "DELETE FROM Modules WHERE id = ?;";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, moduleId);
            pstmt.executeUpdate();
            System.out.println("Module deleted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Module findById(int moduleId) {
        String query = "SELECT * FROM Modules WHERE id = ?;";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, moduleId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new ModuleBuilder()
                        .setId(rs.getInt("id"))
                        .setCode(rs.getString("code"))
                        .setName(rs.getString("name"))
                        .setFiliereId(rs.getInt("filiere_id"))
                        .setSemester(rs.getInt("semester"))
                        .setProfessorId(rs.getInt("professor_id"))
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Module> findAll() {
        List<Module> modules = new ArrayList<>();
        String query = "SELECT * FROM Modules;";
        try (PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                modules.add(new ModuleBuilder()
                        .setId(rs.getInt("id"))
                        .setCode(rs.getString("code"))
                        .setName(rs.getString("name"))
                        .setFiliereId(rs.getInt("filiere_id"))
                        .setSemester(rs.getInt("semester"))
                        .setProfessorId(rs.getInt("professor_id"))
                        .build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modules;
    }
}
