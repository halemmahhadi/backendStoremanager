package com.app.storemanager.login.password;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetResponse {
    String token;
    String message;

    public PasswordResetResponse(String message) {
        this.message = message;
    }

    public PasswordResetResponse(String token, String message) {
        this.token = token;
        this.message = message;
    }
}
