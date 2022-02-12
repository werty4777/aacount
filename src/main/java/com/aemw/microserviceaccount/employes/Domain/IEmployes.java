package com.aemw.microserviceaccount.employes.Domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployes  extends JpaRepository<employes,Integer> {


}
