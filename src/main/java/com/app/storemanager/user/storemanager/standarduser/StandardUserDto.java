package com.app.storemanager.user.storemanager.standarduser;

import com.app.storemanager.user.storemanager.StoreMangerDto;
import lombok.Data;

@Data
public class StandardUserDto extends StoreMangerDto {

    public static StandardUserDto from(StandardUser standardUser){
        StandardUserDto standardUserDto = new StandardUserDto();
        standardUserDto.setId(standardUser.getId());
        standardUserDto.setEmail(standardUser.getEmail());
        standardUserDto.setFirstName(standardUser.getFirstName());
        standardUserDto.setLastName(standardUser.getLastName());
        standardUserDto.setPhoneNumber(standardUser.getPhoneNumber());
        standardUserDto.setPassword(standardUser.getPassword());
        standardUserDto.setBirthday(standardUser.getBirthday());
        standardUserDto.setAddress(standardUser.getAddress());
        standardUserDto.setImage(standardUser.getImage());
        standardUserDto.setCreationDate(standardUser.getCreationDate());
        standardUserDto.setAccountValidTill(standardUser.getAccountValidTill());
        standardUserDto.setRoles(standardUser.getRoles());
        return standardUserDto;
    }
}
