package com.app.storemanager.user.baseuser;

import com.app.storemanager.user.image.Image;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@DiscriminatorColumn(name = "roles", discriminatorType = DiscriminatorType.STRING)

@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class User{
    @Id
    @GeneratedValue()
    private Long id;
    @Column(nullable = false)
    private  String email;
    @NotNull
    String firstName;
    @NotNull
    String lastName;
    @NotNull
    String phoneNumber;
    @NotNull
    String password;
    @NotNull
    @Column(columnDefinition = "DATE")
    LocalDate birthday;
    @NotNull
    String address;
    @OneToOne()
    @JoinColumn(name = "image_id" , referencedColumnName = "id")
    @JsonManagedReference
    private Image image;
    @NotNull
    LocalDate creationDate;
    @NotNull
    LocalDate accountValidTill;
    @Column(name = "roles", insertable = false, updatable = false)
    private String roles;


    public static User from(UserDto userDto){
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPassword(userDto.getPassword());
        user.setBirthday(userDto.getBirthday());
        user.setAddress(userDto.getAddress());
        user.setCreationDate(userDto.getCreationDate());
        user.setAccountValidTill(userDto.getAccountValidTill());
        user.setRoles(userDto.getRoles());
        return user;

    }

}
