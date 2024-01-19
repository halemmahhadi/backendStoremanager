package com.app.storemanager.login;

import com.app.storemanager.user.baseuser.User;
import com.app.storemanager.user.baseuser.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserService userServices;

    //the email address is used instead of the username
    @Override
    public UserDetails loadUserByUsername(String email) {

        User user = userServices.findByEmail(email);
        Optional<UserDetails> userDetails = Optional.of(new MyUserDetails(user));

        return userDetails.get();
    }

}
