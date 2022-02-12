package com.aemw.microserviceaccount.User.Application;

import com.aemw.microserviceaccount.User.Application.models.modeloLista;
import com.aemw.microserviceaccount.User.Domain.ICustomer;
import com.aemw.microserviceaccount.User.Domain.ILogin;
import com.aemw.microserviceaccount.User.Domain.customer;
import com.aemw.microserviceaccount.User.Domain.login;
import com.aemw.microserviceaccount.User.Infrastructure.api.models.userAndProfile;
import com.aemw.microserviceaccount.employes.Domain.IEmployes;
import com.aemw.microserviceaccount.employes.Domain.employes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserInfo {

    final ILogin iLogin;

    @Autowired
    ICustomer ins;

    @Autowired
    IEmployes iEmployes;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    public UserInfo(ILogin iLogin) {
        this.iLogin = iLogin;
    }


    public ResponseEntity<Map<String, Object>> getData() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<login> login = iLogin.findByEmail(email);

        if (!login.isPresent()) {


            throw new RuntimeException("Error usuario no presente");
        }

        login data = login.get();

        Optional<customer> byId_user = ins.findById(data.getCustomer().getId_customer());

        if (!byId_user.isPresent()) {


            throw new RuntimeException("Error usuario no presente");
        }
        customer customer = byId_user.get();
        Map<String, Object> mapa = new HashMap<>();
        mapa.put("email", data.getEmail());
        mapa.put("name", customer.getName());
        mapa.put("last_name", customer.getLast_name());
        mapa.put("address", customer.getAddress());
        mapa.put("phone", customer.getPhone());
        mapa.put("photo", customer.getPhoto());
        return new ResponseEntity<>(mapa, HttpStatus.OK);

    }


    public ResponseEntity<Map<String, Object>> getDataEmployes() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<login> login = iLogin.findByEmail(email);

        if (!login.isPresent()) {


            throw new RuntimeException("Error usuario no presente");
        }

        login data = login.get();

        Optional<employes> byId = iEmployes.findById(getEmploye());


        if (!byId.isPresent()) {


            throw new RuntimeException("Error usuario no presente");
        }
        employes employes = byId.get();
        Map<String, Object> mapa = new HashMap<>();
        mapa.put("email", data.getEmail());
        mapa.put("name", employes.getName());
        mapa.put("last_name", employes.getLast_name());
        mapa.put("address", employes.getAddress());
        mapa.put("phone", employes.getPhone());
        mapa.put("photo", employes.getPhoto());
        return new ResponseEntity<>(mapa, HttpStatus.OK);

    }


    public ResponseEntity<?> uploadPhoto(String url) {


        customer customer = getCustomer();
        customer.setPhoto(url);
        return null;
    }


    public customer getCustomer() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<login> byEmail = iLogin.findByEmail(name);
        login login = byEmail.get();


        return login.getCustomer();
    }

    public Integer getEmploye() {

        Optional<login> foundUser = iLogin.findByEmail(emailUser());
        login employe = foundUser.orElseThrow(() -> new RuntimeException("Error user not found"));
        System.out.println(employe.getEmail());
        System.out.println(employe.getId_user());

        System.out.println("este es el id del employe");
        //System.out.println(employe.getEmployes());
        return employe.getEmployes().getIdEmploye();

    }

    private String emailUser() {


        return SecurityContextHolder.getContext().getAuthentication().getName();

    }

    public List getUsuarios() {


        return this.iLogin.findAll().stream().map(login -> {

            modeloLista modelo = new modeloLista();
            modelo.setId(login.getId_user());
            modelo.setEmail(login.getEmail());
            modelo.setPassword(login.getPassword());
            modelo.setRol(login.getRoles().getRol());
            return modelo;
        }).collect(Collectors.toList());

    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> borrarUsuario(int id) {

        try {
            System.out.println("entre aca");
            login login = this.iLogin.findById(id).get();
            System.out.println(login.getEmail());

            //this.jdbcTemplate.execute("delete from login where id_user=" + id);
            this.iLogin.deleteById(id);


            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error al borrar usuario");

        }


    }

    @Transactional(rollbackFor = Exception.class)
    public void crearEmpleado(userAndProfile empleado) {

        try {


        } catch (Exception ex) {


        }


    }
}
