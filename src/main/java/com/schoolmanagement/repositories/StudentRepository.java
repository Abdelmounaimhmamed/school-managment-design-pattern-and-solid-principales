package com.schoolmanagement.repositories;

import com.schoolmanagement.interfaces.StudentRepositoryInterface;
import com.schoolmanagement.models.Student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository implements StudentRepositoryInterface {
    private static final String DB_URL = "jdbc:sqlite:school_management.db";

    @Override
    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Students")) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                students.add(new Student(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }

    @Override
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
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(int id) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Students WHERE id = ?")) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Student findByUsernameAndPassword(String username, String password) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String query = """
                    SELECT id, username, password, name, email
                    FROM Students
                    WHERE username = ? AND password = ?;
                    """;
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return new Student(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("name"),
                            rs.getString("email")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
