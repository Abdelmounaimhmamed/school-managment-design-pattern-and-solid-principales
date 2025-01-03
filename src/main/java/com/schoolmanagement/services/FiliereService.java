package com.schoolmanagement.services;

import com.schoolmanagement.models.Filiere;
import com.schoolmanagement.repositories.FiliereRepository;

import java.util.List;

public class FiliereService {
    private final FiliereRepository filiereRepository;

    public FiliereService(FiliereRepository filiereRepository) {
        this.filiereRepository = filiereRepository;
    }

    public void addFiliere(Filiere filiere) {
        if (filiereRepository.isFiliereNameExists(filiere.getName())) {
            System.out.println("Error: Filiere with this name already exists.");
        } else {
            filiereRepository.saveFiliere(filiere);
        }
    }

    public void updateFiliere(Filiere filiere) {
        filiereRepository.updateFiliere(filiere);
    }

    public void deleteFiliere(int id) {
        filiereRepository.deleteFiliere(id);
    }

    public List<Filiere> findAllFilieres() {
        return filiereRepository.findAllFilieres();
    }
}
