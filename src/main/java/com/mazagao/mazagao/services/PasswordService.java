package com.mazagao.mazagao.services;

import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class PasswordService {

    private Logger logger = Logger.getLogger(this.getClass().getName());
    private DelegatingPasswordEncoder passwordEncoder;

    private Map<String, PasswordEncoder> encoders = new HashMap<>();

    public PasswordService(){

        Pbkdf2PasswordEncoder pbkdf2Encoder =
                new Pbkdf2PasswordEncoder(
                        "", 8, 185000,
                        Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);

        encoders.put("pbkdf2", pbkdf2Encoder);

        passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
        passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Encoder);
    }

    public String encodePassword(String password){
        logger.info("Hashing a password");
        return passwordEncoder.encode(password);
    }
}
