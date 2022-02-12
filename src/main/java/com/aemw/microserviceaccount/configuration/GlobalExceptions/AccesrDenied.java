package com.aemw.microserviceaccount.configuration.GlobalExceptions;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class AccesrDenied extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        super.onAuthenticationFailure(request, response, exception);
        if(exception.getClass().isAssignableFrom(UsernameNotFoundException.class)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else if (exception.getClass().isAssignableFrom(DisabledException.class)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        else if(exception.getClass().isAssignableFrom(AccessDeniedException.class)){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
