package com.schoolmanagement.services;

import com.schoolmanagement.models.Professor;
import com.schoolmanagement.models.Student;
import com.schoolmanagement.models.User;
import com.schoolmanagement.repositories.AdminRepository;
import com.schoolmanagement.repositories.ProfessorRepository;
import com.schoolmanagement.repositories.StudentRepository;

public class AuthService {
    private final AdminRepository adminRepository;
    private final ProfessorRepository professorRepository;
    private final StudentRepository studentRepository;

    public AuthService(AdminRepository adminRepository, ProfessorRepository professorRepository, StudentRepository studentRepository) {
        this.adminRepository = adminRepository;
        this.professorRepository = professorRepository;
        this.studentRepository = studentRepository;
    }

    // Authenticate a user (Admin, Professor, or Student)
    public User authenticate(String username, String password, String role) {
        switch (role.toLowerCase()) {
            case "admin" -> {
                return adminRepository.findByUsernameAndPassword(username, password);
            }
            case "professor" -> {
                return professorRepository.findByUsernameAndPassword(username, password);
            }
            case "student" -> {
                return studentRepository.findByUsernameAndPassword(username, password);
            }
            default -> {
                System.out.println("Invalid role: " + role);
                return null;
            }
        }
    }

    // Register a professor
    public void registerProfessor(Professor professor) {
        professorRepository.saveProfessor(professor);
    }

    // Register a student
    public void registerStudent(Student student) {
        studentRepository.saveStudent(student);
    }
}
