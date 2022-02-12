package com.aemw.microserviceaccount.User.Domain;

import com.aemw.microserviceaccount.User.Domain.login;
import com.aemw.microserviceaccount.User.Domain.status_account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Optional;

@Repository
public interface IStatus extends JpaRepository<status_account,Integer> {



    Optional<status_account> findByIdusuario(login idusuario);


    Optional<status_account> findByCodigo(int integer);
}
