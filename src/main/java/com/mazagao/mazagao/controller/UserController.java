package com.mazagao.mazagao.controller;

import com.mazagao.mazagao.data.vo.ScoreboardUserVO;
import com.mazagao.mazagao.data.vo.security.UserRegisterVO;
import com.mazagao.mazagao.data.vo.UserVO;
import com.mazagao.mazagao.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserServices service;
    @PostMapping(value = "/register")
    public UserVO create(@RequestBody UserRegisterVO registerVO){
        return service.create(registerVO);
    }

    @GetMapping(value = "/scoreboard")
    public List<ScoreboardUserVO> scoreboard(){
        return service.getScoreboard();
    }


    @GetMapping(value = "/{id}")
    public UserVO getUser(@PathVariable("id") Long id){
        return service.getUser(id);
    }
}
