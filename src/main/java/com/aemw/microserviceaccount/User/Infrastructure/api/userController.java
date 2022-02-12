package com.aemw.microserviceaccount.User.Infrastructure.api;

import com.aemw.microserviceaccount.User.Application.UserCases;
import com.aemw.microserviceaccount.User.Application.UserInfo;
import com.aemw.microserviceaccount.User.Application.validationUser;
import com.aemw.microserviceaccount.User.Domain.customer;
import com.aemw.microserviceaccount.User.Infrastructure.api.models.userAndProfile;
import com.aemw.microserviceaccount.configuration.auth.AuthDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class userController {


    @Autowired
    UserCases userCases;


    @Autowired
    UserInfo info;

    @Autowired
    validationUser val;


    @PreAuthorize("permitAll")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDTO log) {

        return userCases.login(log);


    }

    @PreAuthorize("permitAll")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody userAndProfile reg) {
        return userCases.register(reg.getProfile(), reg.getUser());
    }

    @PreAuthorize("permitAll")
    @PostMapping("/loginEmployes")
    public ResponseEntity<?> loginEmployes(@RequestBody AuthDTO log) {

        return userCases.loginAdmin(log);


    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/user", produces = {"application/json; charset=UTF-8"})
    public ResponseEntity<?> me() {

        return info.getData();

    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/useremploye")
    public ResponseEntity<?> meEmploye() {

        return info.getDataEmployes();

    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/photo")
    public ResponseEntity<?> photo(@RequestHeader("photo") String url) {

        return userCases.savePhoto(url);
    }


    @PreAuthorize("permitAll")
    @GetMapping("/valid/{code}")
    public String validarCuenta(@PathVariable("code") int codigo) {


        return val.validarCuenta(codigo);

    }


    @PreAuthorize("authenticated")
    @GetMapping("/customer")
    public customer getCustomer() {

        return userCases.getCustomer();

    }

    @PreAuthorize("hasRole('trabajador')")
    @GetMapping("/user/id")
    public Integer getIdEmploye() {

        return userCases.getEmploye();

    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/user/lista")
    public List listaEmpleados() {

        return this.info.getUsuarios();
    }


    @PreAuthorize("hasRole('admin')")
    @DeleteMapping("/usuario/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable("id") int id) {

        return this.info.borrarUsuario(id);


    }

    @PreAuthorize("hasRole('admin')")
    @PostMapping("/user/empleado")
    public ResponseEntity<?> crearEmpleado(@RequestBody userAndProfile empleado) {


        return userCases.registerEmpleado(empleado.getProfile(), empleado.getUser());


    }


}
