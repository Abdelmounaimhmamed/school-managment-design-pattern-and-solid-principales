package com.schoolmanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseSchema {
    private static final String DB_URL = "jdbc:sqlite:school_management.db";

    private DatabaseSchema() {
        // Private constructor to implement Singleton pattern
    }

    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            // Create Admins table
            String createAdminsTable = """
                    CREATE TABLE IF NOT EXISTS Admins (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        username TEXT NOT NULL UNIQUE,
                        password TEXT NOT NULL
                    );
                    """;

            // Create Filieres table
            String createFiliereTable = """
                    CREATE TABLE IF NOT EXISTS Filieres (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        name TEXT NOT NULL UNIQUE
                    );
                    """;

            // Create Professors table
            String createProfessorsTable = """
                    CREATE TABLE IF NOT EXISTS Professors (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        code TEXT NOT NULL UNIQUE,
                        first_name TEXT NOT NULL,
                        last_name TEXT NOT NULL,
                        specialty TEXT NOT NULL,
                        username TEXT NOT NULL UNIQUE,
                        password TEXT NOT NULL,
                        filiere_id INTEGER,
                        FOREIGN KEY(filiere_id) REFERENCES Filieres(id)
                    );
                    """;

            // Create Students table
            String createStudentsTable = """
                    CREATE TABLE IF NOT EXISTS Students (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        name TEXT NOT NULL,
                        email TEXT UNIQUE NOT NULL,
                        username TEXT NOT NULL UNIQUE,
                        password TEXT NOT NULL,
                        filiere_id INTEGER NOT NULL,
                        FOREIGN KEY(filiere_id) REFERENCES Filieres(id)
                    );
                    """;

            // Create Modules table
            String createModulesTable = """
                    CREATE TABLE IF NOT EXISTS Modules (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        code TEXT NOT NULL UNIQUE,
                        name TEXT NOT NULL,
                        filiere_id INTEGER NOT NULL,
                        semester INTEGER NOT NULL,
                        professor_id INTEGER,
                        FOREIGN KEY(filiere_id) REFERENCES Filieres(id),
                        FOREIGN KEY(professor_id) REFERENCES Professors(id)
                    );
                    """;

            // Create ModuleElements table
            String createModuleElementsTable = """
                    CREATE TABLE IF NOT EXISTS ModuleElements (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        name TEXT NOT NULL,
                        module_id INTEGER NOT NULL,
                        coefficient REAL NOT NULL,
                        is_validated INTEGER DEFAULT 0,
                        FOREIGN KEY(module_id) REFERENCES Modules(id)
                    );
                    """;

            // Create Grades table
            String createGradesTable = """
                    CREATE TABLE IF NOT EXISTS Grades (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        student_id INTEGER NOT NULL,
                        module_element_id INTEGER NOT NULL,
                        grade_value REAL CHECK (grade_value >= 0 AND grade_value <= 20),
                        is_absent INTEGER DEFAULT 0,
                        FOREIGN KEY(student_id) REFERENCES Students(id),
                        FOREIGN KEY(module_element_id) REFERENCES ModuleElements(id),
                        UNIQUE(student_id, module_element_id)
                    );
                    """;

            // Create EvaluationModalities table
            String createEvaluationModalitiesTable = """
                    CREATE TABLE IF NOT EXISTS EvaluationModalities (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        name TEXT NOT NULL UNIQUE
                    );
                    """;

            // Create StudentGrades table
            String createStudentGradesTable = """
                    CREATE TABLE IF NOT EXISTS StudentGrades (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        student_id INTEGER NOT NULL,
                        module_element_id INTEGER NOT NULL,
                        grade REAL NOT NULL,
                        FOREIGN KEY(student_id) REFERENCES Students(id),
                        FOREIGN KEY(module_element_id) REFERENCES ModuleElements(id)
                    );
                    """;

            // Create Notes table (optional for additional notes on grades)

            String createNotesTable = """
                    CREATE TABLE IF NOT EXISTS Notes (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        student_id INTEGER NOT NULL,
                        module_element_id INTEGER NOT NULL,
                        score REAL NOT NULL,
                        FOREIGN KEY(student_id) REFERENCES Students(id),
                        FOREIGN KEY(module_element_id) REFERENCES ModuleElements(id)
                    );
                    """;

            // Execute table creation in the correct order
            stmt.execute(createAdminsTable);
            stmt.execute(createFiliereTable);
            stmt.execute(createProfessorsTable);
            stmt.execute(createStudentsTable);
            stmt.execute(createModulesTable);
            stmt.execute(createModuleElementsTable);
            stmt.execute(createGradesTable);
            stmt.execute(createEvaluationModalitiesTable);
            stmt.execute(createStudentGradesTable);
            stmt.execute(createNotesTable);

            System.out.println("Database initialized successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
