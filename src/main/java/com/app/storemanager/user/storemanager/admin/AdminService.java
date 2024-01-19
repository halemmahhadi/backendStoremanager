package com.app.storemanager.user.storemanager.admin;
import com.app.storemanager.user.image.Image;
import com.app.storemanager.user.image.ImageService;
import com.app.storemanager.warehouse.Warehouse;
import com.app.storemanager.warehouse.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    WarehouseService warehouseService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    ImageService imageService;

    public Admin save(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return (Admin)adminRepository.save(admin);
    }
    public Admin findByEmail(String email) {
        Optional<Admin> admin = adminRepository.findByEmail(email);
        return admin.orElse(null);
    }
    public Admin getAdminRef(String email) {
        return (Admin) adminRepository.getOne(Long.valueOf(email));
    }

    public Admin deleteAdmin(String email){
        Admin admin= findByEmail(email);
        adminRepository.delete(admin);
        return admin;
    }

    @Transactional
    public Admin editAdmin(String email,Admin admin){
        Admin adminToEdit=findByEmail(email);
        adminToEdit.setCreationDate(admin.getCreationDate());
        adminToEdit.setAddress(admin.getAddress());
        adminToEdit.setPhoneNumber(admin.getPhoneNumber());
        adminToEdit.setAccountValidTill(admin.getAccountValidTill());
        adminToEdit.setFirstName(admin.getFirstName());
        adminToEdit.setLastName(admin.getLastName());
        adminToEdit.setPhoneNumber(admin.getPhoneNumber());
        adminToEdit.setBirthday(admin.getBirthday());
        adminToEdit.setPassword(passwordEncoder.encode(admin.getPassword()));
        return  adminToEdit;

    }
    @Transactional
    public Admin assignWarehouseToAdmin(String email, Long warehouseID){
        Admin admin=findByEmail(email);
        Warehouse warehouse=warehouseService.getWarehouseById(warehouseID);
        admin.assignWarehouseToAdmin(warehouse);
        return admin;
    }

    @Transactional
    public Admin removeWarehouseFromAdmin(String email, Long warehouseID){
        Admin admin=findByEmail(email);
        Warehouse warehouse=warehouseService.getWarehouseById(warehouseID);
        admin.removeWarehouseFromAdmin(warehouse);
        return admin;
    }

    @Transactional
    public Admin addImage(String email, Long imageId){
        Admin admin=findByEmail(email);
        Image image=imageService.getById(imageId);
        admin.setImage(image);
        return admin;
    }

}