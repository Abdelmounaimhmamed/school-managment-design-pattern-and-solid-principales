package com.schoolmanagement.services;

import com.schoolmanagement.repositories.ProfessorRepository;

public class ProfessorService {
    private final ProfessorRepository professorRepository = new ProfessorRepository();

    public void enterGrades(int moduleElementId, int studentId, double gradeValue) {
        professorRepository.enterGrades(moduleElementId, studentId, gradeValue);
    }

    public void validateGrades(int moduleElementId) {
        // Implementation for validating grades
        System.out.println("Validating grades for Module Element ID: " + moduleElementId);
        // Example: Call repository method to validate grades
        professorRepository.validateGrades(moduleElementId);
    }

    public void exportGrades(int moduleElementId) {
        // Implementation for exporting grades
        System.out.println("Exporting grades for Module Element ID: " + moduleElementId);
        // Example: Call repository method to export grades
        professorRepository.exportGrades(moduleElementId);
    }
}
