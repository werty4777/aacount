package com.aemw.microserviceaccount.configuration.auth;

import com.aemw.microserviceaccount.User.Domain.login;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;
import java.util.Collection;

public class UserAuth extends User {





    public UserAuth(login user,boolean enabled) {

        super(user.getEmail(), user.getPassword(),enabled,true,true,true ,Arrays.asList(new SimpleGrantedAuthority("ROLE_" + user.getRoles().getRol())));

    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return super.getAuthorities();
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getUsername();

    }

    @Override
    public boolean isEnabled() {
        return super.isEnabled();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

       return super.isAccountNonExpired();

    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


}
