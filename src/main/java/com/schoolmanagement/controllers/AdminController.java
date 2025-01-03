package com.schoolmanagement.controllers;

import com.schoolmanagement.builders.FiliereBuilder;
import com.schoolmanagement.builders.ModuleBuilder;
import com.schoolmanagement.builders.ModuleElementBuilder;
import com.schoolmanagement.models.Filiere;
import com.schoolmanagement.models.Module;
import com.schoolmanagement.models.ModuleElement;
import com.schoolmanagement.proxies.ModuleElementServiceProxy;
import com.schoolmanagement.services.FiliereService;
import com.schoolmanagement.services.ModuleService;
import com.schoolmanagement.services.ModuleElementService;
import com.schoolmanagement.factories.ServiceFactory;

import java.util.Scanner;

public class AdminController {
    private final FiliereService filiereService = ServiceFactory.createFiliereService();
    private final ModuleService moduleService = ServiceFactory.createModuleService();
    private final ModuleElementServiceProxy moduleElementService = ServiceFactory.createModuleElementService();
    private final Scanner scanner = new Scanner(System.in);

    public void showAdminMenu() {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Manage Filieres");
            System.out.println("2. Manage Modules");
            System.out.println("3. Manage Module Elements");
            System.out.println("4. Log out");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> manageFilieres();
                case 2 -> manageModules();
                case 3 -> manageModuleElements();
                case 4 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private void manageFilieres() {
        while (true) {
            System.out.println("\nManage Filieres:");
            System.out.println("1. Add Filiere");
            System.out.println("2. Update Filiere");
            System.out.println("3. Delete Filiere");
            System.out.println("4. View All Filieres");
            System.out.println("5. Back to Admin Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> addFiliere();
                case 2 -> updateFiliere();
                case 3 -> deleteFiliere();
                case 4 -> viewAllFilieres();
                case 5 -> {
                    System.out.println("Returning to Admin Menu...");
                    return;
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private void addFiliere() {
        System.out.print("Enter Filiere Name: ");
        String name = scanner.nextLine();
        Filiere filiere = new FiliereBuilder()
                .setName(name)
                .build();
        filiereService.addFiliere(filiere);
    }

    private void updateFiliere() {
        System.out.print("Enter Filiere ID to Update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter New Filiere Name: ");
        String name = scanner.nextLine();
        Filiere filiere = new FiliereBuilder()
                .setId(id)
                .setName(name)
                .build();
        filiereService.updateFiliere(filiere);
    }

    private void deleteFiliere() {
        System.out.print("Enter Filiere ID to Delete: ");
        int id = scanner.nextInt();
        filiereService.deleteFiliere(id);
    }

    private void viewAllFilieres() {
        filiereService.findAllFilieres().forEach(filiere -> {
            System.out.printf("ID: %d, Name: %s%n", filiere.getId(), filiere.getName());
        });
    }

    private void manageModules() {
        while (true) {
            System.out.println("\nManage Modules:");
            System.out.println("1. Add Module");
            System.out.println("2. Update Module");
            System.out.println("3. Delete Module");
            System.out.println("4. View All Modules");
            System.out.println("5. Back to Admin Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> addModule();
                case 2 -> updateModule();
                case 3 -> deleteModule();
                case 4 -> viewAllModules();
                case 5 -> {
                    System.out.println("Returning to Admin Menu...");
                    return;
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private void addModule() {
        System.out.print("Enter Module Code: ");
        String code = scanner.nextLine();
        System.out.print("Enter Module Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Filiere ID: ");
        int filiereId = scanner.nextInt();
        System.out.print("Enter Semester: ");
        int semester = scanner.nextInt();
        System.out.print("Enter Professor ID (optional, enter 0 if none): ");
        int professorId = scanner.nextInt();

        Module module = new ModuleBuilder()
                .setCode(code)
                .setName(name)
                .setFiliereId(filiereId)
                .setSemester(semester)
                .setProfessorId(professorId > 0 ? professorId : 0)
                .build();

        moduleService.addModule(module);
    }

    private void updateModule() {
        System.out.print("Enter Module ID to Update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter New Module Code: ");
        String code = scanner.nextLine();
        System.out.print("Enter New Module Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter New Filiere ID: ");
        int filiereId = scanner.nextInt();
        System.out.print("Enter New Semester: ");
        int semester = scanner.nextInt();
        System.out.print("Enter New Professor ID (optional, enter 0 if none): ");
        int professorId = scanner.nextInt();

        Module module = new ModuleBuilder()
                .setId(id)
                .setCode(code)
                .setName(name)
                .setFiliereId(filiereId)
                .setSemester(semester)
                .setProfessorId(professorId > 0 ? professorId : 0)
                .build();

        moduleService.updateModule(module);
    }

    private void deleteModule() {
        System.out.print("Enter Module ID to Delete: ");
        int id = scanner.nextInt();
        moduleService.deleteModule(id);
    }

    private void viewAllModules() {
        moduleService.findAllModules().forEach(module -> {
            System.out.printf("ID: %d, Code: %s, Name: %s, Filiere ID: %d, Semester: %d, Professor ID: %d%n",
                    module.getId(), module.getCode(), module.getName(), module.getFiliereId(), module.getSemester(), module.getProfessorId());
        });
    }

    private void manageModuleElements() {
        System.out.print("Enter Module ID to Manage Elements: ");
        int moduleId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        while (true) {
            System.out.println("\nManage Module Elements:");
            System.out.println("1. Add Module Element");
            System.out.println("2. Update Module Element");
            System.out.println("3. Delete Module Element");
            System.out.println("4. View All Module Elements");
            System.out.println("5. Back to Admin Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> addModuleElement(moduleId);
                case 2 -> updateModuleElement(moduleId);
                case 3 -> deleteModuleElement();
                case 4 -> viewAllModuleElements(moduleId);
                case 5 -> {
                    System.out.println("Returning to Admin Menu...");
                    return;
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private void addModuleElement(int moduleId) {
        System.out.print("Enter Module Element Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Coefficient: ");
        double coefficient = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        ModuleElement moduleElement = new ModuleElementBuilder()
                .setName(name)
                .setModuleId(moduleId)
                .setCoefficient(coefficient)
                .setValidated(false)
                .build();
        moduleElementService.addModuleElement(moduleElement);
    }

    private void updateModuleElement(int moduleId) {
        System.out.print("Enter Module Element ID to Update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter New Module Element Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter New Coefficient: ");
        double coefficient = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        ModuleElement moduleElement = new ModuleElementBuilder()
                .setId(id)
                .setName(name)
                .setModuleId(moduleId)
                .setCoefficient(coefficient)
                .setValidated(false)
                .build();
        moduleElementService.updateModuleElement(moduleElement);
    }

    private void deleteModuleElement() {
        System.out.print("Enter Module Element ID to Delete: ");
        int id = scanner.nextInt();
        moduleElementService.deleteModuleElement(id);
    }

    private void viewAllModuleElements(int moduleId) {
        moduleElementService.findAllByModuleId(moduleId).forEach(element -> {
            System.out.printf("ID: %d, Name: %s, Coefficient: %.2f, Validated: %b%n",
                    element.getId(), element.getName(), element.getCoefficient(), element.isValidated());
        });
    }
}
