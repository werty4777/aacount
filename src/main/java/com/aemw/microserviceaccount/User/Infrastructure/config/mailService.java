package com.aemw.microserviceaccount.User.Infrastructure.config;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class mailService {


    private JavaMailSender mailSender;

    @Autowired
    public mailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public boolean enviarEmail(String destinatario,String asunto,String mensaje){


        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(destinatario);
            helper.setSubject(asunto);
            helper.setText(mensaje,true);
            mailSender.send(mimeMessage);

            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }


    }

}
