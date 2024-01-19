package com.app.storemanager.login;

import com.app.storemanager.user.baseuser.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.app.storemanager.login.LoginUtils.isAccountActive;

public class MyUserDetails implements UserDetails {

    private String email;
    private String password;
    private boolean active;
    private List<GrantedAuthority> authorityList;

    public MyUserDetails(User user) {
        this.password = user.getPassword();
        this.email    = user.getEmail();
        this.active   = isAccountActive(user.getAccountValidTill() );
        this.authorityList = Arrays.stream(user.getRoles().split(","))
                .map(role-> new SimpleGrantedAuthority("ROLE_"+ role))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorityList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

