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

    @Autowired
    UserRepository repository;

    @Autowired
    PasswordService passwordService;

    @Autowired
    ScoreService scoreService;

    private Logger logger = Logger.getLogger(UserServices.class.getName());

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

    public void setMurderScore(String killerUsername, String victimUsername){

        logger.info("[murder] " + killerUsername + " -> " + victimUsername);

        var killer = repository.findByUsername(killerUsername);
        var victim = repository.findByUsername(victimUsername);

        if(killer == null || victim == null){
            throw new UsernameNotFoundException("Username was not found");
        }

        Integer pointsLost = scoreService.calculateMurderScore(victim);

        logger.info("[murder] " + pointsLost + " points lost");

        killer.addScore(pointsLost);
        killer.addKills(1);

        victim.addScore(-pointsLost);
        victim.addDeaths(1);

        repository.save(killer);
        repository.save(victim);

    }

    public void setMinerScore(String username, String oreName){
        logger.info("[mining] " + username + " -> " + oreName);

        var miner = repository.findByUsername(username);

        var oreValue = scoreService.calculateOreScore(oreName);

        if(miner == null){
            throw new UsernameNotFoundException("Username was not found");
        }

        miner.addScore(oreValue);
        logger.info("[mining] " + oreValue + " points earned");

        repository.save(miner);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email);

        if (user == null){
            throw new UsernameNotFoundException("Email " + email + " Not found");
        }

        return user;
    }
}
