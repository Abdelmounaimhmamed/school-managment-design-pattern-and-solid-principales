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

public class ServiceFactory {
    public static AdminService createAdminService() {
        return new AdminService(new AdminRepository());
    }
    public static StudentService createStudentService() {
        return new StudentService(new StudentRepository());
    }
    public static ModuleService createModuleService() {
        return new ModuleService(new ModuleRepository());
    }
     public static FiliereService createFiliereService() {
        return new FiliereService(new FiliereRepository());
    }
   
     public static ModuleElementServiceProxy createModuleElementService() {
        return new ModuleElementServiceProxy(new ModuleElementRepository());
    }
}
