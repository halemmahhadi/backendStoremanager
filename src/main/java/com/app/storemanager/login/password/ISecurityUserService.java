package com.app.storemanager.login.password;

public interface ISecurityUserService {
    String validatePasswordResetToken(String token);
}
