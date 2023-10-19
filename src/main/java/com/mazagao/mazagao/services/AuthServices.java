package com.mazagao.mazagao.services;

import com.mazagao.mazagao.data.vo.security.TokenVO;
import com.mazagao.mazagao.data.vo.security.UserLoginVO;
import com.mazagao.mazagao.repositories.UserRepository;
import com.mazagao.mazagao.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class AuthServices {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository repository;

    @SuppressWarnings("rawtypes")
    public ResponseEntity signin(UserLoginVO data) {

            try {
                var email = data.getEmail();
                var password = data.getPassword();

                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(email, password));

                var user = repository.findByEmail(email);

                var tokenResponse = tokenProvider.createAccessToken(email, user.getRoles());
                return ResponseEntity.ok(tokenResponse);
            }
            catch (Exception e){
                throw new BadCredentialsException("Invalid email/password");

            }
    }

    @SuppressWarnings("rawtypes")
    public ResponseEntity refreshToken(String username, String refreshToken) {
        var user = repository.findByEmail(username);

        var tokenResponse = new TokenVO();
        if (user != null) {
            tokenResponse = tokenProvider.refreshToken(refreshToken);
        } else {
            throw new UsernameNotFoundException("Username " + username + " not found!");
        }
        return ResponseEntity.ok(tokenResponse);
    }
}