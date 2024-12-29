package com.schoolmanagement.services;

import com.schoolmanagement.interfaces.StudentRepositoryInterface;
import com.schoolmanagement.models.Student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentService {
    private final StudentRepositoryInterface studentRepository;

    public StudentService(StudentRepositoryInterface studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public void deleteStudentById(int id) {
        studentRepository.deleteById(id);
    }

    public Map<String, Double> getGradesForStudent(int studentId) {
        Map<String, Double> grades = new HashMap<>();
        String DB_URL = "jdbc:sqlite:school_management.db";
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String query = """
                    SELECT m.name AS module_name, g.grade
                    FROM StudentGrades g
                    JOIN ModuleElements e ON g.module_element_id = e.id
                    JOIN Modules m ON e.module_id = m.id
                    WHERE g.student_id = ?;
                    """;
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, studentId);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    grades.put(rs.getString("module_name"), rs.getDouble("grade"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return grades;
    }
}
