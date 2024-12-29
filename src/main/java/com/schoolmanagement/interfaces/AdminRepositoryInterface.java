package com.schoolmanagement.interfaces;

import com.schoolmanagement.models.Admin;

public interface AdminRepositoryInterface {
    Admin findByUsernameAndPassword(String username, String password);

    void addFiliere(String name);

    void updateFiliere(int id, String name);

    void deleteFiliere(int id);

    void viewFilieres();

    void addProfessor(String firstName, String lastName, String specialty, String username, String password, int filiereId);

    void updateProfessor(int id, String firstName, String lastName, String specialty, String username, String password, int filiereId);

    void deleteProfessor(int id);

    void viewProfessors();
}
