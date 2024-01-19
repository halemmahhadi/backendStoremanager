package com.app.storemanager.user.storemanager.admin;

import com.app.storemanager.user.storemanager.StoreManager;
import com.app.storemanager.user.superadmin.SuperAdmin;
import com.app.storemanager.warehouse.Warehouse;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
@SuperBuilder(toBuilder = true)
public class Admin extends StoreManager {
    @ManyToOne
    @JoinColumn(name = "superAdminEmail")
    @JsonManagedReference
    private SuperAdmin superAdmin;

    public Admin() {
        super();
    }

    public static Admin from(AdminDto adminDto) {
        Admin admin = new Admin();
        admin.setEmail(adminDto.getEmail());
        admin.setFirstName(adminDto.getFirstName());
        admin.setLastName(adminDto.getLastName());
        admin.setPhoneNumber(adminDto.getPhoneNumber());
        admin.setPassword(adminDto.getPassword());
        admin.setBirthday(adminDto.getBirthday());
        admin.setAddress(adminDto.getAddress());
        admin.setCreationDate(adminDto.getCreationDate());
        admin.setAccountValidTill(adminDto.getAccountValidTill());
        admin.setRoles(adminDto.getRoles());
        return admin;
    }

    public void assignWarehouseToAdmin(Warehouse warehouse) {
        assignWarehouseToManger(warehouse);
    }

    public void removeWarehouseFromAdmin(Warehouse warehouse) {
        removeWarehouseFromManger(warehouse);
    }

}

