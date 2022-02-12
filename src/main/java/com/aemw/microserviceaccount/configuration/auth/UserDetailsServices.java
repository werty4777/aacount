package com.aemw.microserviceaccount.configuration.auth;

import com.aemw.microserviceaccount.User.Domain.ILogin;
import com.aemw.microserviceaccount.User.Domain.IStatus;
import com.aemw.microserviceaccount.User.Domain.login;
import com.aemw.microserviceaccount.User.Domain.status_account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserDetailsServices implements UserDetailsService {

    @Autowired
    private ILogin iUser;

    @Autowired
    IStatus iStatus;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        System.out.println(username);
        try {
            System.out.println("entre al details service");
            Optional<login> byEmail = iUser.findByEmail(username);

            login user = byEmail.orElseThrow(() -> new RuntimeException("Error usuario no encontrado"));


            Optional<status_account> byIdusuario = iStatus.findByIdusuario(user);

            boolean estado = false;

            estado = !byIdusuario.isPresent();


            UserAuth Userauth = new UserAuth(user, estado);

            return Userauth;

        } catch (Exception ex) {




            throw new RuntimeException("Error in service detail user");
        }


    }
}
