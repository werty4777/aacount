package com.aemw.microserviceaccount.User.Domain;

import com.aemw.microserviceaccount.User.Domain.customer;
import com.aemw.microserviceaccount.User.Domain.login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICustomer  extends JpaRepository<customer,Integer> {


    List<customer> findAll();

    Optional<customer> findByIduser(login a );
}
