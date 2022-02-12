package com.aemw.microserviceaccount.User.Domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class status_account  implements Serializable {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idstatus;


    private int codigo;


    @OneToOne(cascade = CascadeType.REFRESH, orphanRemoval = false,targetEntity = login.class ,fetch = FetchType.EAGER)
    @JoinColumn(name = "idusuario")
    private login idusuario;

    public int getIdstatus() {
        return idstatus;
    }

    public void setIdstatus(int idstatus) {
        this.idstatus = idstatus;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public login getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(login idusuario) {
        this.idusuario = idusuario;
    }
}
