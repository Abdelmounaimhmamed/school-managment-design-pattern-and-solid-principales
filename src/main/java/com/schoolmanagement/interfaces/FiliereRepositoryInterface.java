package com.schoolmanagement.interfaces;


import com.schoolmanagement.models.Filiere;

import java.util.List;

public interface FiliereRepositoryInterface {
    void saveFiliere(Filiere filiere);

    void updateFiliere(Filiere filiere);

    void deleteFiliere(int id);

    List<Filiere> findAllFilieres();
}
