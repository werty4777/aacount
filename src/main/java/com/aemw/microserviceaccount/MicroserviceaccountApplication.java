package com.aemw.microserviceaccount;

import com.aemw.microserviceaccount.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MicroserviceaccountApplication implements CommandLineRunner {
    @Autowired
    Util nombre;
    public static void main(String[] args) {
        SpringApplication.run(MicroserviceaccountApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(this.nombre.encode("admin1"));
    }
}
