package com.aemw.microserviceaccount.User.Domain;

import com.aemw.microserviceaccount.User.Domain.login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ILogin extends JpaRepository<login, Integer> {


    @Query(value = "select login.email,login.password from login where email=?", nativeQuery = true)
    Optional<Object> login(String email);


    Optional<login> findByEmail(String email);
}
