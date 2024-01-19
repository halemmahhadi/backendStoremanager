package com.app.storemanager.user.baseuser;

import java.text.MessageFormat;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String email){
        super(MessageFormat.format("could not find User with this email {0}",email));
    }
}
