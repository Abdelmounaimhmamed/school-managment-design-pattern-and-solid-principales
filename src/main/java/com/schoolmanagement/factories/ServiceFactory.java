package com.schoolmanagement.factories;

import com.schoolmanagement.repositories.AdminRepository;
import com.schoolmanagement.repositories.StudentRepository;
import com.schoolmanagement.services.AdminService;
import com.schoolmanagement.services.StudentService;

public class ServiceFactory {
    public static AdminService createAdminService() {
        return new AdminService(new AdminRepository());
    }
    public static StudentService createStudentService() {
        return new StudentService(new StudentRepository());
    }
}
