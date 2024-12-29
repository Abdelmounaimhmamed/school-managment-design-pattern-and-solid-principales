package com.schoolmanagement;

import com.schoolmanagement.controllers.AdminController;
import com.schoolmanagement.controllers.AuthController;
import com.schoolmanagement.controllers.ProfessorController;
import com.schoolmanagement.controllers.StudentController;
import com.schoolmanagement.models.Admin;
import com.schoolmanagement.models.Professor;
import com.schoolmanagement.models.Student;
import com.schoolmanagement.models.User;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Initialize database and seed data (uncomment as needed)
        // DatabaseSchema.initializeDatabase();
        // DatabaseSeeder.seed();

        AuthController authController = new AuthController();
        AdminController adminController = new AdminController();
        ProfessorController professorController = new ProfessorController();
        StudentController studentController = new StudentController();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the School Management System!");
        System.out.println("Are you a:");
        System.out.println("1. Admin");
        System.out.println("2. Professor");
        System.out.println("3. Student");
        System.out.print("Enter your choice: ");
        int roleChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        String role;
        switch (roleChoice) {
            case 1 -> role = "Admin";
            case 2 -> role = "Professor";
            case 3 -> role = "Student";
            default -> {
                System.out.println("Invalid role selected. Exiting...");
                return;
            }
        }

        System.out.println("Do you want to:");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.print("Enter your choice: ");
        int actionChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        User user = null;

        if (actionChoice == 1) { // Login
            user = authController.login(role);
        } else if (actionChoice == 2) { // Register
            if ("Admin".equalsIgnoreCase(role)) {
                System.out.println("Admin registration is not allowed via this application.");
                return;
            }
            user = authController.register(role);
        } else {
            System.out.println("Invalid action selected. Exiting...");
            return;
        }

        if (user != null) {
            if (user instanceof Admin) {
                adminController.showAdminMenu();
            } else if (user instanceof Professor) {
                professorController.showMenu();
            } else if (user instanceof Student) {
                studentController.showMenu((Student) user);
            }
        } else {
            System.out.println("Authentication failed. Exiting...");
        }
    }
}
