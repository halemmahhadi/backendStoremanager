package com.app.storemanager.user.storemanager.admin;
import com.app.storemanager.user.storemanager.StoreMangerDto;
import com.app.storemanager.warehouse.WarehouseDto;
import lombok.Data;

import java.util.stream.Collectors;

@Data
public class AdminDto extends StoreMangerDto {

    public static AdminDto from(Admin admin){
        AdminDto adminDto = new AdminDto();
        adminDto.setId(admin.getId());
        adminDto.setEmail(admin.getEmail());
        adminDto.setFirstName(admin.getFirstName());
        adminDto.setLastName(admin.getLastName());
        adminDto.setPhoneNumber(admin.getPhoneNumber());
        adminDto.setPassword(admin.getPassword());
        adminDto.setBirthday(admin.getBirthday());
        adminDto.setAddress(admin.getAddress());
        adminDto.setImage(admin.getImage());
        adminDto.setCreationDate(admin.getCreationDate());
        adminDto.setAccountValidTill(admin.getAccountValidTill());
        adminDto.setRoles(admin.getRoles());
        adminDto.setWarehousesDto(admin.getWarehouses().stream().map(WarehouseDto::from).collect(Collectors.toList()));

        return adminDto;
    }
}
