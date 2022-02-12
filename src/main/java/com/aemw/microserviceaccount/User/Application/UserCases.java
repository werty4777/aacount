package com.aemw.microserviceaccount.User.Application;

import com.aemw.microserviceaccount.Roles.Domain.IRoles;
import com.aemw.microserviceaccount.Roles.Domain.Roles;
import com.aemw.microserviceaccount.User.Domain.Exception.UserEntityException;
import com.aemw.microserviceaccount.User.Domain.ICustomer;
import com.aemw.microserviceaccount.User.Domain.ILogin;
import com.aemw.microserviceaccount.User.Domain.customer;
import com.aemw.microserviceaccount.User.Domain.login;
import com.aemw.microserviceaccount.configuration.auth.AuthDTO;
import com.aemw.microserviceaccount.configuration.Jwt.Exceptions.JwtException;
import com.aemw.microserviceaccount.configuration.Jwt.JwtService;
import com.aemw.microserviceaccount.employes.Domain.IEmployes;
import com.aemw.microserviceaccount.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserCases {

    private validationUser val;
    private ICustomer iCustomer;
    private  Util util;
    private ILogin iUser;
    private  IRoles iRoles;
    private  JwtService jwtService;
    private EntityManager entityManager;
    private IEmployes iEmployes;
    private  AuthenticationManager manager;
    private final  JdbcTemplate jdbcTemplate;

    @Autowired
    public UserCases(validationUser val, Util util, ILogin iUser, AuthenticationManager manager, JwtService jwtService, ICustomer iCustomer, IRoles iRoles, EntityManager entityManager, IEmployes iEmployes, JdbcTemplate jdbcTemplate) {
        this.val = val;
        this.util = util;
        this.iUser = iUser;
        this.manager = manager;
        this.jwtService = jwtService;
        this.iCustomer = iCustomer;
        this.iRoles = iRoles;
        this.entityManager = entityManager;
        this.iEmployes = iEmployes;
        this.jdbcTemplate = jdbcTemplate;
    }


    public ResponseEntity<?> login(AuthDTO log) {


        try {
            return setAuthAndReturnToken(log);

        } catch (Exception | JwtException ex) {
            UserEntityException error_al_logearte = new UserEntityException("Error al logearte", HttpStatus.FORBIDDEN);
            error_al_logearte.addDetalles("Datos erroneos");
            throw error_al_logearte;

        }

    }


    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> register(customer customer, login logs) {

        try {

            Optional<Roles> rolfounded = iRoles.findByRol("cliente");
            Roles rol = rolfounded.orElseThrow(() -> new UserEntityException("Error rol not found",HttpStatus.BAD_REQUEST));
            String encode = util.encode(logs.getPassword());
            logs.setPassword(encode);
            logs.setRoles(rol);
            login save = iUser.save(logs);


            customer.setIduser(logs);
            iCustomer.save(customer);
            Map<String ,Object> mapa = new HashMap<>();
            mapa.put("Message","User register");


            val.registerWithCode(save);

            return new ResponseEntity<>(mapa, HttpStatus.CREATED);
        } catch (Exception ex) {
ex.printStackTrace();
            System.out.println(ex.getMessage());
            UserEntityException error_in_register_user = new UserEntityException("Error in register user",HttpStatus.BAD_REQUEST);
            error_in_register_user.addDetalles("Error a la hora de crear un usuario");
            throw error_in_register_user;

        }


    }
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> registerEmpleado(customer customer, login logs) {

        try {

            Optional<Roles> rolfounded = iRoles.findByRol("trabajador");
            Roles rol = rolfounded.orElseThrow(() -> new UserEntityException("Error rol not found",HttpStatus.BAD_REQUEST));
            String encode = util.encode(logs.getPassword());
            logs.setPassword(encode);
            logs.setRoles(rol);
            login save = iUser.save(logs);


            customer.setIduser(logs);
            iCustomer.save(customer);
            Map<String ,Object> mapa = new HashMap<>();
            mapa.put("Message","User register");


            //val.registerWithCode(save);

            return new ResponseEntity<>(mapa, HttpStatus.CREATED);
        } catch (Exception ex) {

            System.out.println(ex.getMessage());
            UserEntityException error_in_register_user = new UserEntityException("Error in register user",HttpStatus.BAD_REQUEST);
            error_in_register_user.addDetalles("Error a la hora de crear un usuario");
            throw error_in_register_user;

        }


    }


    public ResponseEntity<?> loginAdmin(AuthDTO log) {

        try {

            Optional<login> findUser = iUser.findByEmail(log.getEmail());

            login userfound = findUser.orElseThrow(() -> new RuntimeException("User not found"));

            String rol = userfound.getRoles().getRol();
            if (rol.equalsIgnoreCase("trabajador")) {
                return setAuthAndReturnToken(log);

            } else {


                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }


        } catch (Exception | JwtException ex) {

            throw new RuntimeException("Error al logearte");

        }


    }

    private ResponseEntity<?> setAuthAndReturnToken(AuthDTO log) throws JwtException {
        System.out.println("le estoy pasando la autenticacion");
        Authentication authenticate = manager.authenticate(new UsernamePasswordAuthenticationToken(log.getEmail(), log.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        List<String> roles = authenticate.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());


        Map<String, Object> response = new HashMap<>();
        String s = roles.get(0);
        System.out.println(s);
        response.put("Token", "Bearer " + jwtService.createToken(log.getEmail(), s));
        List<Map<String, Object>> usuariocorreo = this.jdbcTemplate.queryForList("select * from login where email='" + log.getEmail() + "'");



        if(usuariocorreo.size()>0){
            List<Map<String, Object>> id_user = this.jdbcTemplate.queryForList("select * from customer where iduser='" + usuariocorreo.get(0).get("id_user") + "'");

            if(id_user.size()>0){

                response.put("data",id_user);

            }

        }



        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    private String emailUser(){


        return SecurityContextHolder.getContext().getAuthentication().getName();

    }

    public customer getCustomer() {


        Optional<login> foundUser = iUser.findByEmail(emailUser());
        login user = foundUser.orElseThrow(() -> new RuntimeException("Error user not found"));

        return user.getCustomer();



    }

    public Integer getEmploye() {

        Optional<login> foundUser = iUser.findByEmail(emailUser());
        login employe = foundUser.orElseThrow(() -> new RuntimeException("Error user not found"));

        return employe.getEmployes().getIdEmploye();

    }


    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> savePhoto(String url){

        try {
            Optional<login> byEmail = iUser.findByEmail(util.getUser().getEmail());
            login login = byEmail.orElseThrow(() -> new RuntimeException("User not found"));
            customer customer = login.getCustomer();
            customer.setPhoto(url);



            customer save = iCustomer.save(customer);
            System.out.println(save.getName());
            return new ResponseEntity<>(HttpStatus.OK);

        }catch (Exception ex){
            System.out.println(ex.getMessage());
            throw  new RuntimeException("Error save photo");
        }


    }






}

