package com.schoolmanagement.controllers;

import com.schoolmanagement.factories.ServiceFactory;
import com.schoolmanagement.services.AdminService;

import java.util.Scanner;

public class AdminController {
    private final AdminService adminService = ServiceFactory.createAdminService();
    private final Scanner scanner = new Scanner(System.in);

    public void showAdminMenu() {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Manage Filieres");
            System.out.println("2. Manage Professors");
            System.out.println("3. Log out");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> manageFilieres();
                case 2 -> manageProfessors();
                case 3 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private void manageFilieres() {
        System.out.println("\nManage Filieres:");
        System.out.println("1. Add Filiere");
        System.out.println("2. Update Filiere");
        System.out.println("3. Delete Filiere");
        System.out.println("4. View Filieres");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1 -> {
                System.out.print("Enter Filiere Name: ");
                String name = scanner.nextLine();
                adminService.addFiliere(name);
            }
            case 2 -> {
                System.out.print("Enter Filiere ID to update: ");
                int id = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                System.out.print("Enter New Filiere Name: ");
                String name = scanner.nextLine();
                adminService.updateFiliere(id, name);
            }
            case 3 -> {
                System.out.print("Enter Filiere ID to delete: ");
                int id = scanner.nextInt();
                adminService.deleteFiliere(id);
            }
            case 4 -> adminService.viewFilieres();
            default -> System.out.println("Invalid choice!");
        }
    }

    private void manageProfessors() {
        System.out.println("\nManage Professors:");
        System.out.println("1. Add Professor");
        System.out.println("2. Update Professor");
        System.out.println("3. Delete Professor");
        System.out.println("4. View Professors");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1 -> {
                System.out.print("Enter First Name: ");
                String firstName = scanner.nextLine();
                System.out.print("Enter Last Name: ");
                String lastName = scanner.nextLine();
                System.out.print("Enter Specialty: ");
                String specialty = scanner.nextLine();
                System.out.print("Enter Username: ");
                String username = scanner.nextLine();
                System.out.print("Enter Password: ");
                String password = scanner.nextLine();
                System.out.print("Enter Filiere ID (Professor will be associated with this Filiere): ");
                int filiereId = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                adminService.addProfessor(firstName, lastName, specialty, username, password, filiereId);
            }
            case 2 -> {
                System.out.print("Enter Professor ID to update: ");
                int id = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                System.out.print("Enter New First Name: ");
                String firstName = scanner.nextLine();
                System.out.print("Enter New Last Name: ");
                String lastName = scanner.nextLine();
                System.out.print("Enter New Specialty: ");
                String specialty = scanner.nextLine();
                System.out.print("Enter New Username: ");
                String username = scanner.nextLine();
                System.out.print("Enter New Password: ");
                String password = scanner.nextLine();
                System.out.print("Enter Filiere ID to associate with this Professor: ");
                int filiereId = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                adminService.updateProfessor(id, firstName, lastName, specialty, username, password, filiereId);
            }
            case 3 -> {
                System.out.print("Enter Professor ID to delete: ");
                int id = scanner.nextInt();
                adminService.deleteProfessor(id);
            }
            case 4 -> adminService.viewProfessors();
            default -> System.out.println("Invalid choice!");
        }
    }
}
