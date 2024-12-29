package com.schoolmanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class DatabaseSeeder {
    private static final String DB_URL = "jdbc:sqlite:school_management.db";

    public static void seed() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            // Clear existing data in a proper order to avoid foreign key constraint issues
            String clearData = """
                    DELETE FROM Grades;
                    DELETE FROM ModuleElements;
                    DELETE FROM Modules;
                    DELETE FROM Students;
                    DELETE FROM Professors;
                    DELETE FROM Filieres;
                    DELETE FROM Admins;
                    DELETE FROM EvaluationModalities;
                    """;
            stmt.executeUpdate(clearData);

            // Insert Admins
            String insertAdmins = """
                    INSERT OR IGNORE INTO Admins (username, password)
                    VALUES ('admin', 'admin123');
                    """;

            // Insert Filieres (Fields of study)
            String insertFilieres = """
                    INSERT OR IGNORE INTO Filieres (name)
                    VALUES 
                    ('Computer Science'),
                    ('Mathematics');
                    """;

            // Insert Professors with `filiere_id`
            String insertProfessors = """
                    INSERT OR IGNORE INTO Professors (code, first_name, last_name, specialty, username, password, filiere_id)
                    VALUES 
                    ('P001', 'John', 'Doe', 'Mathematics', 'johndoe', 'password123', 2),
                    ('P002', 'Jane', 'Smith', 'Computer Science', 'janesmith', 'securepass', 1);
                    """;

            // Insert Students linked to Filieres
            String insertStudents = """
                    INSERT OR IGNORE INTO Students (name, email, username, password, filiere_id)
                    VALUES 
                    ('Alice Johnson', 'alice.johnson@example.com', 'alicej', 'mypassword', 1),
                    ('Bob Smith', 'bob.smith@example.com', 'bobsmith', 'password123', 2);
                    """;

            // Insert Modules linked to Filieres and Professors
            String insertModules = """
                    INSERT OR IGNORE INTO Modules (code, name, filiere_id, semester, professor_id)
                    VALUES 
                    ('M001', 'Algorithms', 1, 3, 2),
                    ('M002', 'Linear Algebra', 2, 1, 1);
                    """;

            // Insert Module Elements linked to Modules
            String insertModuleElements = """
                    INSERT OR IGNORE INTO ModuleElements (name, module_id, coefficient, is_validated)
                    VALUES 
                    ('Midterm Exam', 1, 0.4, 0),
                    ('Final Exam', 1, 0.6, 0),
                    ('Midterm Exam', 2, 0.5, 0),
                    ('Final Exam', 2, 0.5, 0);
                    """;

            // Insert Evaluation Modalities
            String insertEvaluationModalities = """
                    INSERT OR IGNORE INTO EvaluationModalities (name)
                    VALUES 
                    ('CC'),
                    ('TP'),
                    ('Project');
                    """;

            // Insert Grades linked to Students and Module Elements
            String insertGrades = """
                    INSERT OR IGNORE INTO Grades (student_id, module_element_id, grade_value, is_absent)
                    VALUES 
                    (1, 1, 15.5, 0),
                    (1, 2, 18.0, 0),
                    (2, 3, 12.0, 0),
                    (2, 4, 14.5, 0);
                    """;

            // Execute seeding queries in the correct order
            try (
                PreparedStatement pstmtAdmins = conn.prepareStatement(insertAdmins);
                PreparedStatement pstmtFilieres = conn.prepareStatement(insertFilieres);
                PreparedStatement pstmtProfessors = conn.prepareStatement(insertProfessors);
                PreparedStatement pstmtStudents = conn.prepareStatement(insertStudents);
                PreparedStatement pstmtModules = conn.prepareStatement(insertModules);
                PreparedStatement pstmtModuleElements = conn.prepareStatement(insertModuleElements);
                PreparedStatement pstmtEvaluationModalities = conn.prepareStatement(insertEvaluationModalities);
                PreparedStatement pstmtGrades = conn.prepareStatement(insertGrades)
            ) {
                pstmtAdmins.executeUpdate();
                pstmtFilieres.executeUpdate();
                pstmtProfessors.executeUpdate();
                pstmtStudents.executeUpdate();
                pstmtModules.executeUpdate();
                pstmtModuleElements.executeUpdate();
                pstmtEvaluationModalities.executeUpdate();
                pstmtGrades.executeUpdate();
            }

            System.out.println("Database seeded successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
