package com.app.storemanager.user.superadmin;

import java.text.MessageFormat;

public class AdminIsAlreadyAssignmentException extends RuntimeException{
    public AdminIsAlreadyAssignmentException(String adminEmail, String email){
        super(MessageFormat.format("admin:{0}is already assign to superAdmin {1}",adminEmail ,email));
    }
}
