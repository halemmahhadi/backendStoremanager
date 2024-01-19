package com.app.storemanager.user.baseuser;
import com.app.storemanager.user.image.Image;
import lombok.Data;

import java.time.LocalDate;


@Data
public class UserDto {
    private Long id;
   private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String password;
    private LocalDate birthday;
    private String address;
    private Image image;
    private LocalDate creationDate;
    private LocalDate accountValidTill;
    private String roles;

    public static UserDto from(User user ){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setPassword(user.getPassword());
        userDto.setBirthday(user.getBirthday());
        userDto.setAddress(user.getAddress());
        userDto.setImage(user.getImage());
        userDto.setCreationDate(user.getCreationDate());
        userDto.setAccountValidTill(user.getAccountValidTill());
        userDto.setRoles(user.getRoles());
        return userDto;
    }


}
