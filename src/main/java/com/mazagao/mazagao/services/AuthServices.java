package com.mazagao.mazagao.services;

import com.mazagao.mazagao.data.vo.UserVO;
import com.mazagao.mazagao.data.vo.security.PlayerLoginVO;
import com.mazagao.mazagao.data.vo.security.TokenVO;
import com.mazagao.mazagao.data.vo.security.UserLoginVO;
import com.mazagao.mazagao.data.vo.security.UserRegisterVO;
import com.mazagao.mazagao.mapper.Mapper;
import com.mazagao.mazagao.models.User;
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

    @Autowired
    private PasswordService passwordService;

    public UserVO register(UserRegisterVO user){
        logger.info("Creating a new user");

        if(repository.existsByUsername(user.getUsername())){
            throw new BadCredentialsException("Username já utilizado");
        }

        if (repository.existsByEmail(user.getEmail())){
            throw new BadCredentialsException("Email já utilizado");
        }

        var hashedPassword = passwordService.encodePassword(user.getPassword());
        user.setPassword(hashedPassword);

        var entity = Mapper.parseObject(user, User.class);
        entity = repository.save(entity);

        return Mapper.parseObject(entity, UserVO.class);
    }


    public ResponseEntity playerSingIn(PlayerLoginVO data){
        try {
            var username = data.getUsername();
            var user = repository.findByUsername(username);
            var email = user.getEmail();
            var password = data.getPassword();

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));

            var tokenResponse = tokenProvider.createAccessToken(email, user.getRoles());
            return ResponseEntity.ok(tokenResponse);
        }
        catch (Exception e){
            throw new BadCredentialsException("Invalid username/password");

        }
    }

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