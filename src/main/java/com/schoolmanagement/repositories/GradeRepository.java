package com.schoolmanagement.repositories;

import com.schoolmanagement.models.Grade;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class GradeRepository {
    private static final String DB_URL = "jdbc:sqlite:school_management.db";

    // Fetch grades for a specific module element
    public List<Grade> findGradesByModuleElementId(int moduleElementId) {
        List<Grade> grades = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String query = "SELECT * FROM Grades WHERE module_element_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, moduleElementId);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    grades.add(new Grade(
                            rs.getInt("id"),
                            rs.getInt("student_id"),
                            rs.getInt("module_element_id"),
                            rs.getDouble("grade_value"),
                            rs.getBoolean("is_absent")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return grades;
    }

    // Save or update grades
    public void saveGrade(Grade grade) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String query = """
                    INSERT INTO Grades (student_id, module_element_id, grade_value, is_absent)
                    VALUES (?, ?, ?, ?)
                    ON CONFLICT(student_id, module_element_id) DO UPDATE SET grade_value = ?, is_absent = ?;
                    """;
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, grade.getStudentId());
                pstmt.setInt(2, grade.getModuleElementId());
                pstmt.setDouble(3, grade.getGradeValue());
                pstmt.setBoolean(4, grade.isAbsent());
                pstmt.setDouble(5, grade.getGradeValue());
                pstmt.setBoolean(6, grade.isAbsent());
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
