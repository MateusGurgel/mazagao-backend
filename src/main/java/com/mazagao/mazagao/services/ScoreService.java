package com.mazagao.mazagao.services;

import com.mazagao.mazagao.models.User;
import org.springframework.stereotype.Service;

@Service
public class ScoreService {

    public Integer calculateOreScore(String ore){
        System.out.println(ore);
        switch (ore){
            case "COAL_ORE", "COPPER_ORE", "DEEPSLATE_COPPER_ORE":
                return 1;
            case "NETHER_QUARTZ_ORE", "NETHER_GOLD_ORE":
                return 2;
            case "IRON_ORE", "DEEPSLATE_IRON_ORE":
                return 3;
            case "LAPIS_ORE", "DEEPSLATE_LAPIS_ORE":
                return 5;
            case "GOLD_ORE":
                return 5;
            case "DIAMOND_ORE", "DEEPSLATE_DIAMOND_ORE":
                return 60;
            case "EMERALD_ORE", "DEEPSLATE_EMERALD_ORE":
                return 80;
        }
        return 0;
    }
    public Integer calculateMurderScore(User victim){
        return  (int)((double)(victim.getScore()) / 100.0 * 25);
    }
}
