package com.schoolmanagement.controllers;

import com.schoolmanagement.models.Student;
import com.schoolmanagement.services.StudentService;
import com.schoolmanagement.factories.ServiceFactory;

import java.util.Scanner;

public class StudentController {
    private final StudentService studentService = ServiceFactory.createStudentService();
    private final Scanner scanner = new Scanner(System.in);

    public void showMenu(Student student) {
        while (true) {
            System.out.println("\nStudent Menu:");
            System.out.println("1. View grades");
            System.out.println("2. Log out");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> {
                    System.out.println("Your grades:");
                    studentService.getGradesForStudent(student.getId()).forEach((module, grade) -> {
                        System.out.printf("Module: %s, Grade: %.2f%n", module, grade);
                    });
                }
                case 2 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}
