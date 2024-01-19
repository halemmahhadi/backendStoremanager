package com.app.storemanager.user.storemanager.standarduser;

import com.app.storemanager.user.baseuser.User;
import com.app.storemanager.user.storemanager.StoreManager;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.time.Month;

@Getter
@Setter
@Entity
//@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class StandardUser extends StoreManager {

    public StandardUser() {
        super();
    }


    public static StandardUser from(StandardUserDto standardUserDto){
        StandardUser standardUser = new StandardUser();
        standardUser.setEmail(standardUserDto.getEmail());
        standardUser.setFirstName(standardUserDto.getFirstName());
        standardUser.setLastName(standardUserDto.getLastName());
        standardUser.setPhoneNumber(standardUserDto.getPhoneNumber());
        standardUser.setPassword(standardUserDto.getPassword());
        standardUser.setBirthday(standardUserDto.getBirthday());
        standardUser.setAddress(standardUserDto.getAddress());
        standardUser.setCreationDate(standardUserDto.getCreationDate());
        standardUser.setAccountValidTill(standardUserDto.getAccountValidTill());
        standardUser.setRoles(standardUserDto.getRoles());
        return standardUser;
    }

    public static StandardUser convertUserToStandardUer(User user){
        return StandardUser.builder()
                .birthday(user.getBirthday())
                .accountValidTill(LocalDate.of(2030, Month.DECEMBER, 17))
                .creationDate(LocalDate.now())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .password(user.getPassword())
                .address(user.getAddress())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
