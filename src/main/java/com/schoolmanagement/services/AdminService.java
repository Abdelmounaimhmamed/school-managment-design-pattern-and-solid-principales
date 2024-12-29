package com.schoolmanagement.services;

import com.schoolmanagement.interfaces.AdminRepositoryInterface;
import com.schoolmanagement.interfaces.AdminServiceInterface;

public class AdminService implements AdminServiceInterface {
    private final AdminRepositoryInterface adminRepository;

    public AdminService(AdminRepositoryInterface adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public void addFiliere(String name) {
        adminRepository.addFiliere(name);
    }

    @Override
    public void updateFiliere(int id, String name) {
        adminRepository.updateFiliere(id, name);
    }

    @Override
    public void deleteFiliere(int id) {
        adminRepository.deleteFiliere(id);
    }

    @Override
    public void viewFilieres() {
        adminRepository.viewFilieres();
    }

    @Override
    public void addProfessor(String firstName, String lastName, String specialty, String username, String password, int filiereId) {
        adminRepository.addProfessor(firstName, lastName, specialty, username, password, filiereId);
    }

    @Override
    public void updateProfessor(int id, String firstName, String lastName, String specialty, String username, String password, int filiereId) {
        adminRepository.updateProfessor(id, firstName, lastName, specialty, username, password, filiereId);
    }

    @Override
    public void deleteProfessor(int id) {
        adminRepository.deleteProfessor(id);
    }

    @Override
    public void viewProfessors() {
        adminRepository.viewProfessors();
    }
}
