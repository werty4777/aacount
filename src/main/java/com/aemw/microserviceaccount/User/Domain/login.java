package com.aemw.microserviceaccount.User.Domain;

import com.aemw.microserviceaccount.Roles.Domain.Roles;
import com.aemw.microserviceaccount.User.Application.Events.UserListeners;
import com.aemw.microserviceaccount.employes.Domain.employes;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
@EntityListeners(UserListeners.class)
@Entity
public class login  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_user;


    @NotNull
    @NotBlank
    @NotEmpty
    @Column(length = 50)
    private String email;


    @NotNull
    @NotBlank
    @NotEmpty
    @Column(length = 250)
    private String password;

    @OneToOne()
    @JoinColumn(name = "id_rol")
    private Roles roles;

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    @OneToOne(mappedBy = "iduser",cascade = CascadeType.ALL,orphanRemoval = true)
    private customer customer;


    @OneToOne(mappedBy = "iduser",cascade = CascadeType.ALL,orphanRemoval = true)
    private employes employes;

    public com.aemw.microserviceaccount.employes.Domain.employes getEmployes() {
        return employes;
    }

    public void setEmployes(com.aemw.microserviceaccount.employes.Domain.employes employes) {
        this.employes = employes;
    }

    public customer getCustomer() {
        return customer;
    }

    public void setCustomer(customer customer) {
        this.customer = customer;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
