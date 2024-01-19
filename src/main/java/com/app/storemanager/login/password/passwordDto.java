package com.app.storemanager.login.password;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class passwordDto {

    private String oldPassword;

    private  String token;

    private String newPassword;

}
