package com.app.storemanager.user.superadmin;

import com.app.storemanager.user.storemanager.admin.Admin;
import com.app.storemanager.user.storemanager.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;

@Service
public class SuperAdminService {
    @Autowired
    SuperAdminRepository superAdminRepository;
    @Autowired
    AdminService adminService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public SuperAdmin save(SuperAdmin superAdmin) {
        superAdmin.setPassword(passwordEncoder.encode(superAdmin.getPassword()));
        return superAdminRepository.save(superAdmin);
    }

    public SuperAdmin findByEmail(String email) {
        Optional<SuperAdmin> superAdmin = superAdminRepository.findByEmail(email);
        return superAdmin.orElse(null);
    }

    @Transactional
    public SuperAdmin assignAdminToSuperAdmin(String email, String adminEmail) {
        SuperAdmin superAdmin = findByEmail(email);
        Admin admin = adminService.findByEmail(adminEmail);
        if (Objects.nonNull(admin.getSuperAdmin())) {
            throw new AdminIsAlreadyAssignmentException(adminEmail, admin.getSuperAdmin().getEmail());

        }
        superAdmin.assignAdminToSuperAdmin(admin);
        admin.setSuperAdmin(superAdmin);
        return superAdmin;
    }

    @Transactional
    public SuperAdmin deleteAdminFromSuperAdmin(String superEmail, String adminEmail) {
        SuperAdmin superAdmin = findByEmail(superEmail);
        Admin admin = adminService.findByEmail(adminEmail);
        superAdmin.deleteAdminFromSuperAdmin(admin);
        return superAdmin;
    }

    public SuperAdmin delete(String email) {
        SuperAdmin superAdmin = findByEmail(email);
        superAdminRepository.delete(superAdmin);
        return superAdmin;
    }
}
