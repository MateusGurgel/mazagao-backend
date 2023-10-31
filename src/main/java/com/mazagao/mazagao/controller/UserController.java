package com.mazagao.mazagao.controller;

import com.mazagao.mazagao.data.vo.ScoreboardUserVO;
import com.mazagao.mazagao.data.vo.UserVO;
import com.mazagao.mazagao.models.User;
import com.mazagao.mazagao.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserServices service;

    @GetMapping(value = "/scoreboard")
    public List<ScoreboardUserVO> scoreboard(){
        return service.getScoreboard();
    }

    @GetMapping(value = "/me")
    public UserVO getLoggedUser(@AuthenticationPrincipal User user){
        return service.userToUserVO(user);
    }

    @GetMapping(value = "/{id}")
    public UserVO getUser(@PathVariable("id") Long id){
        return service.getUser(id);
    }
}