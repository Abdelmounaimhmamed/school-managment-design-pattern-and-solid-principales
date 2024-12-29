package com.schoolmanagement.interfaces;

import com.schoolmanagement.models.Student;

import java.util.List;

public interface StudentRepositoryInterface {
    List<Student> findAll();
    void saveStudent(Student student);
    void deleteById(int id);
    Student findByUsernameAndPassword(String username, String password);
}