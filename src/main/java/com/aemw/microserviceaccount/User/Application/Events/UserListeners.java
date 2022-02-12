package com.aemw.microserviceaccount.User.Application.Events;



import com.aemw.microserviceaccount.User.Domain.Exception.UserEntityException;
import com.aemw.microserviceaccount.User.Domain.ILogin;
import com.aemw.microserviceaccount.User.Domain.login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.persistence.PrePersist;
import java.util.List;
import java.util.stream.Collectors;

public class UserListeners {


    @Autowired
    ILogin ilogin;




    @PrePersist
    private void beforeInsertUser(login login) {

        List<login> all = ilogin.findAll();

        List<String> collect = all.stream().map(login1 -> login1.getEmail()).collect(Collectors.toList());
        if (collect.contains(login.getEmail())) {
            throw new UserEntityException("User Already Register", HttpStatus.CONFLICT);

        }

    }


}
