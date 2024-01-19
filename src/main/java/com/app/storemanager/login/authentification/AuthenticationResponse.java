package com.app.storemanager.login.authentification;

import com.app.storemanager.user.baseuser.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationResponse {

    private final String message;
    private String jwt;
    private User user;

    public AuthenticationResponse(String jwt, String message, User userDto) {
        this.jwt = jwt;
        this.user = userDto;
        this.message = message;
    }

    public AuthenticationResponse(String message) {
        this.message = message;
    }
}