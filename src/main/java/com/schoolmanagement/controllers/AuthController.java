package com.schoolmanagement.controllers;

import com.schoolmanagement.builders.ProfessorBuilder;
import com.schoolmanagement.builders.StudentBuilder;
import com.schoolmanagement.models.Admin;
import com.schoolmanagement.models.Professor;
import com.schoolmanagement.models.Student;
import com.schoolmanagement.models.User;
import com.schoolmanagement.services.AuthService;

import java.util.Scanner;

public class AuthController {
    private final AuthService authService = new AuthService();
    private final Scanner scanner = new Scanner(System.in);

    public User login(String role) {
        System.out.println("Login as a " + role);
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = authService.authenticate(username, password, role); // Pass role explicitly
        if (user != null) {
            if ("Admin".equalsIgnoreCase(role) && user instanceof Admin) {
                System.out.println("Admin login successful! Welcome, " + user.getUsername() + ".");
            } else if ("Professor".equalsIgnoreCase(role) && user instanceof Professor) {
                System.out.println("Professor login successful! Welcome, " + user.getUsername() + ".");
            } else if ("Student".equalsIgnoreCase(role) && user instanceof Student) {
                System.out.println("Student login successful! Welcome, " + user.getUsername() + ".");
            } else {
                System.out.println("Invalid role for the provided credentials.");
                return null;
            }
        } else {
            System.out.println("Invalid credentials.");
        }
        return user;
    }

    public User register(String role) {
        System.out.println("Register as a " + role);
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if ("Professor".equalsIgnoreCase(role)) {
            System.out.print("Enter first name: ");
            String firstName = scanner.nextLine();
            System.out.print("Enter last name: ");
            String lastName = scanner.nextLine();
            System.out.print("Enter specialty: ");
            String specialty = scanner.nextLine();
            System.out.print("Enter professor code: ");
            String code = scanner.nextLine();

            Professor professor = new ProfessorBuilder()
            .setCode(code)
            .setUsername(username)
            .setPassword(password)
            .setFirstName(firstName)
            .setLastName(lastName)
            .setSpecialty(specialty)
            .build();
            authService.registerProfessor(professor);
            System.out.println("Professor registered successfully!");
            return professor;
        } else if ("Student".equalsIgnoreCase(role)) {
            System.out.print("Enter name: ");
            String name = scanner.nextLine();
            System.out.print("Enter email: ");
            String email = scanner.nextLine();

            Student student = new StudentBuilder()
                    .setUsername(username)
                    .setPassword(password)
                    .setName(name)
                    .setEmail(email)
                    .build();
            authService.registerStudent(student);
            System.out.println("Student registered successfully!");
            return student;
        } else if ("Admin".equalsIgnoreCase(role)) {
            System.out.println("Admins cannot register through this application.");
            return null;
        } else {
            System.out.println("Invalid role specified.");
            return null;
        }
    }
}
