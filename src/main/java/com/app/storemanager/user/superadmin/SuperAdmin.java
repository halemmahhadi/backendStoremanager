package com.app.storemanager.user.superadmin;

import com.app.storemanager.user.baseuser.User;
import com.app.storemanager.user.storemanager.admin.Admin;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class SuperAdmin extends User {

    @OneToMany(mappedBy = "superAdmin", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Admin> admins = new ArrayList<>();

    public static SuperAdmin from(SuperAdminDto superAdminDto) {
        SuperAdmin superAdmin = new SuperAdmin();
        superAdmin.setEmail(superAdminDto.getEmail());
        superAdmin.setFirstName(superAdminDto.getFirstName());
        superAdmin.setLastName(superAdminDto.getLastName());
        superAdmin.setPhoneNumber(superAdminDto.getPhoneNumber());
        superAdmin.setPassword(superAdminDto.getPassword());
        superAdmin.setBirthday(superAdminDto.getBirthday());
        superAdmin.setAddress(superAdminDto.getAddress());
        superAdmin.setCreationDate(superAdminDto.getCreationDate());
        superAdmin.setAccountValidTill(superAdminDto.getAccountValidTill());
        superAdmin.setRoles(superAdminDto.getRoles());
        return superAdmin;
    }

    public void assignAdminToSuperAdmin(Admin admin) {
        admins.add(admin);
    }

    public void deleteAdminFromSuperAdmin(Admin admin) {
        admins.remove(admin);
    }
}


