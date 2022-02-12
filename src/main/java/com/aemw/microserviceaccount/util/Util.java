package com.aemw.microserviceaccount.util;


import com.aemw.microserviceaccount.User.Domain.login;
import com.aemw.microserviceaccount.User.Domain.ILogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


import java.util.Optional;

@Component
public class Util {



    private ILogin iUser;
    private BCryptPasswordEncoder encoder;
    @Autowired
    public Util(ILogin iUser) {
        this.iUser = iUser;
    }

    public login getUser(){


        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Optional<login> userFound = iUser.findByEmail(authentication.getName());

        login user = userFound.orElseThrow(() -> new RuntimeException("UserNotFound"));
        return user;

    }


    public String encode(String pw) {
        encoder = new BCryptPasswordEncoder();
        return encoder.encode(pw);

    }



}
