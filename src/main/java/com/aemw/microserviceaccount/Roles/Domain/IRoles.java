package com.aemw.microserviceaccount.Roles.Domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoles  extends JpaRepository<Roles,Integer> {


    Optional<Roles> findByRol(String rol);
}
