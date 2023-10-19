package com.mazagao.mazagao.services;

import com.mazagao.mazagao.data.vo.ScoreboardUserVO;
import com.mazagao.mazagao.data.vo.security.UserRegisterVO;
import com.mazagao.mazagao.data.vo.UserVO;
import com.mazagao.mazagao.mapper.Mapper;
import com.mazagao.mazagao.models.User;
import com.mazagao.mazagao.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class UserServices implements UserDetailsService {
    private Logger logger = Logger.getLogger(UserServices.class.getName());

    @Autowired
    UserRepository repository;

    @Autowired
    PasswordService passwordService;

    public UserVO create(UserRegisterVO user){
        logger.info("Creating one user");

        var hashedPassword = passwordService.encodePassword(user.getPassword());
        user.setPassword(hashedPassword);

        var entity = Mapper.parseObject(user, User.class);
        entity = repository.save(entity);

        var vo = Mapper.parseObject( entity, UserVO.class );
        return vo;
    }

    public UserVO getUser(Long id) throws UsernameNotFoundException{

        logger.info("Getting User: " + id);

        User user = repository.findById(id).orElse(null);

        if (user == null){
            //TODO Change that exception
            throw new UsernameNotFoundException("User not found");
        }

        var vo = Mapper.parseObject( user, UserVO.class );

        return vo;
    }

    public List<ScoreboardUserVO> getScoreboard(){
        return Mapper.parseListObjects(repository.getScoreboard(), ScoreboardUserVO.class);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email);

        if (user == null){
            throw new UsernameNotFoundException("Username " + email + " Not found");
        }

        return user;
    }
}
