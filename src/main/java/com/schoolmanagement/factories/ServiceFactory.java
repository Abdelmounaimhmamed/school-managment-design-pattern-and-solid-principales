package com.schoolmanagement.factories;

import com.schoolmanagement.proxies.ModuleElementServiceProxy;
import com.schoolmanagement.repositories.AdminRepository;
import com.schoolmanagement.repositories.FiliereRepository;
import com.schoolmanagement.repositories.ModuleElementRepository;
import com.schoolmanagement.repositories.ModuleRepository;
import com.schoolmanagement.repositories.StudentRepository;
import com.schoolmanagement.services.AdminService;
import com.schoolmanagement.services.FiliereService;
import com.schoolmanagement.services.ModuleElementService;
import com.schoolmanagement.services.ModuleService;
import com.schoolmanagement.services.StudentService;
import com.schoolmanagement.singleton.DatabaseConnection;

import java.sql.Connection;

public class ServiceFactory {
    private static Connection conn = DatabaseConnection.getInstance();

    public static AdminService createAdminService() {
        return new AdminService(new AdminRepository(conn));
    }
    public static StudentService createStudentService() {
        return new StudentService(new StudentRepository(conn));
    }
    public static ModuleService createModuleService() {
        return new ModuleService(new ModuleRepository(conn));
    }
     public static FiliereService createFiliereService() {
        return new FiliereService(new FiliereRepository(conn));
    }
   
     public static ModuleElementServiceProxy createModuleElementService() {
        return new ModuleElementServiceProxy(new ModuleElementRepository(DatabaseConnection.getInstance()));
    }
}
