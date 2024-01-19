package com.app.storemanager.user.superadmin;

import com.app.storemanager.user.baseuser.User;
import com.app.storemanager.user.baseuser.UserDto;
import com.app.storemanager.user.storemanager.StoreManager;
import com.app.storemanager.user.storemanager.StoreMangerDto;
import com.app.storemanager.user.storemanager.admin.AdminDto;
import com.app.storemanager.warehouse.WarehouseDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Data
public class SuperAdminDto extends UserDto {
    @JsonIgnore
    private List<AdminDto>adminsDto=new ArrayList<>();

    public static SuperAdminDto from(SuperAdmin superAdmin) {
        SuperAdminDto superAdminDto = new SuperAdminDto();
        superAdminDto.setId(superAdmin.getId());
        superAdminDto.setEmail(superAdmin.getEmail());
        superAdminDto.setFirstName(superAdmin.getFirstName());
        superAdminDto.setLastName(superAdmin.getLastName());
        superAdminDto.setPhoneNumber(superAdmin.getPhoneNumber());
        superAdminDto.setPassword(superAdmin.getPassword());
        superAdminDto.setBirthday(superAdmin.getBirthday());
        superAdminDto.setAddress(superAdmin.getAddress());
        superAdminDto.setImage(superAdmin.getImage());
        superAdminDto.setCreationDate(superAdmin.getCreationDate());
        superAdminDto.setAccountValidTill(superAdmin.getAccountValidTill());
        superAdminDto.setRoles(superAdmin.getRoles());
        superAdminDto.setAdminsDto(superAdmin.getAdmins().stream().map(AdminDto::from).collect(Collectors.toList()));
        return superAdminDto;
    }

}
