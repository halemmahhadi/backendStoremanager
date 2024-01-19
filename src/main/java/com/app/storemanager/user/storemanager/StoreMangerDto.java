package com.app.storemanager.user.storemanager;


import com.app.storemanager.user.baseuser.UserDto;

import com.app.storemanager.warehouse.WarehouseDto;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class StoreMangerDto extends UserDto {
    private  List<WarehouseDto> warehousesDto ;

    public static StoreMangerDto from(StoreManager storeManager) {
        StoreMangerDto storeManagerDto = new StoreMangerDto();
        storeManagerDto.setId(storeManager.getId());
        storeManagerDto.setEmail(storeManager.getEmail());
        storeManagerDto.setFirstName(storeManager.getFirstName());
        storeManagerDto.setLastName(storeManager.getLastName());
        storeManagerDto.setPhoneNumber(storeManager.getPhoneNumber());
        storeManagerDto.setPassword(storeManager.getPassword());
        storeManagerDto.setBirthday(storeManager.getBirthday());
        storeManagerDto.setAddress(storeManager.getAddress());
        storeManagerDto.setImage(storeManager.getImage());
        storeManagerDto.setCreationDate(storeManager.getCreationDate());
        storeManagerDto.setAccountValidTill(storeManager.getAccountValidTill());
        storeManagerDto.setRoles(storeManager.getRoles());
        storeManagerDto.setWarehousesDto(storeManager.getWarehouses().stream().map(WarehouseDto::from).collect(Collectors.toList()));
        return storeManagerDto;
    }
}

