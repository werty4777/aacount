package com.aemw.microserviceaccount.Roles.Domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Roles implements Serializable {


    @Id
    @Column(name = "id_rol")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRol;


    @Column(name = "rol")
    private String rol;

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Roles{" +
                "idRol=" + idRol +
                ", rol='" + rol + '\'' +
                '}';
    }
}
