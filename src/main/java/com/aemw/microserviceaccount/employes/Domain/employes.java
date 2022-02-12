package com.aemw.microserviceaccount.employes.Domain;

import com.aemw.microserviceaccount.User.Domain.login;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Entity
public class employes  implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEmploye;


    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    @JoinColumn(name = "iduser")
    private login iduser;
    @Column(length = 50)
    private String name;
    @Column(length = 50)
    private String last_name;
    @Column(length = 250)
    private String photo;
    @Column(length = 30)
    private String province;
    @Column(length = 100)
    private String address;
    @Pattern(regexp = "^(9)\\d{8}")
    @Column(length = 10)
    private String phone;


    public int getIdEmploye() {
        return idEmploye;
    }

    public void setIdEmploye(int idEmploye) {
        this.idEmploye = idEmploye;
    }

    public login getIduser() {
        return iduser;
    }

    public void setIduser(login iduser) {
        this.iduser = iduser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
