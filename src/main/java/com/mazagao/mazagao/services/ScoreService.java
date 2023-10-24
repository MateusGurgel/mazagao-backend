package com.mazagao.mazagao.services;

import com.mazagao.mazagao.models.User;
import org.springframework.stereotype.Service;

@Service
public class ScoreService {

    public Integer calculateOreScore(String ore){
        switch (ore.toLowerCase()){
            case "coal", "copper":
                return 1;
            case "quartz":
                return 2;
            case "iron":
                return 3;
            case "lapis":
                return 5;
            case "gold":
                return 5;
            case "diamond":
                return 60;
            case "emerald":
                return 80;
            case "netherite":
                return 180;
        }
        return 0;
    }
    public Integer calculateMurderScore(User victim){
        return  (int)((double)(victim.getScore()) / 100.0 * 25);
    }
}
