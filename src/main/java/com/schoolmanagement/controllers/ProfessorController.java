package com.schoolmanagement.controllers;

import com.schoolmanagement.factories.ServiceFactory;
import com.schoolmanagement.services.ProfessorService;
import com.schoolmanagement.services.StudentService;

import java.util.Scanner;

public class ProfessorController {
    private final ProfessorService professorService;
    private final StudentService studentService = ServiceFactory.createStudentService();
    private final Scanner scanner = new Scanner(System.in);

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    public void manageGrades() {
        System.out.print("Enter Module Element ID: ");
        int moduleElementId = scanner.nextInt();
        scanner.nextLine();  
    
        System.out.println("1. Enter Grades");
        System.out.println("2. Validate Grades");
        System.out.println("3. Export Grades");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();  
    
        switch (choice) {
            case 1 -> {
                System.out.print("Enter Student ID: ");
                int studentId = scanner.nextInt();
                System.out.print("Enter Grade: ");
                double grade = scanner.nextDouble();
                professorService.enterGrades(moduleElementId, studentId, grade);
            }
            case 2 -> professorService.validateGrades(moduleElementId);
            case 3 -> professorService.exportGrades(moduleElementId);
            default -> System.out.println("Invalid choice!");
        }
    }

    public void showMenu() {
        while (true) {
            System.out.println("\nProfessor Menu:");
            System.out.println("1. View all students");
            System.out.println("2. Delete a student");
            System.out.println("3. Manage grades");
            System.out.println("4. Log out");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  

            switch (choice) {
                case 1 -> studentService.getAllStudents().forEach(student -> {
                    System.out.printf("ID: %d, Name: %s, Email: %s%n", student.getId(), student.getName(), student.getEmail());
                });
                case 2 -> {
                    System.out.print("Enter student ID to delete: ");
                    int studentId = scanner.nextInt();
                    scanner.nextLine();  
                    studentService.deleteStudentById(studentId);
                    System.out.println("Student deleted successfully.");
                }
                case 3 -> manageGrades();
                case 4 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}
