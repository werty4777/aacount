package com.aemw.microserviceaccount.configuration.Jwt.Filters;


import com.aemw.microserviceaccount.User.Domain.login;
import com.aemw.microserviceaccount.User.Domain.ILogin;
import com.aemw.microserviceaccount.configuration.Jwt.Exceptions.JwtException;
import com.aemw.microserviceaccount.configuration.Jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JwtFilter extends BasicAuthenticationFilter {
    private static final String AUTH = "Authorization";


    @Autowired
    ILogin user;

    @Autowired
    JwtService jwtService;




    public JwtFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {



        String path = request.getServletPath();
        System.out.println(path);
        String auth = request.getHeader("Authorization");


        try {
            if (jwtService.checkToken(auth)) {

                String suser = jwtService.getUser(auth);
                Optional<login> byEmail = user.findByEmail(suser);
                login userFound = byEmail.orElseThrow(() ->  new RuntimeException("Error user not found"));

                List<String> roles = jwtService.roles(auth);


                List<GrantedAuthority> rolesauth = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
                System.out.println("mi password es "+userFound.getPassword());
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userFound.getEmail(), userFound.getPassword(), rolesauth);
                SecurityContextHolder.getContext().setAuthentication(token);

                chain.doFilter(request, response);

            } else {

                System.out.println("o aca tambien entro");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }catch (Exception | JwtException ex){
            ex.printStackTrace();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;

        }



    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        String path=request.getServletPath();

        if(path.startsWith("/api")|| path.startsWith("/login") || path.startsWith("/register")|| path.startsWith("/valid")){
            return true;
        }else{
            return false;
        }


    }
}
