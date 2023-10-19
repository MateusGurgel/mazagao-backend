package com.mazagao.mazagao.data.vo;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.Objects;

@JsonPropertyOrder({"id", "username", "email", "rank", "score", "deaths", "kills"})
public class UserVO implements Serializable {

    private static final long serialVersionID = 1l;
    private Long id;
    private String username;
    private String email;
    private String rank;
    private Number score;
    private Number deaths;
    private Number kills;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public Number getScore() {
        return score;
    }

    public void setScore(Number score) {
        this.score = score;
    }

    public Number getDeaths() {
        return deaths;
    }

    public void setDeaths(Number deaths) {
        this.deaths = deaths;
    }

    public Number getKills() {
        return kills;
    }

    public void setKills(Number kills) {
        this.kills = kills;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserVO userVO = (UserVO) o;
        return Objects.equals(id, userVO.id) && Objects.equals(username, userVO.username) && Objects.equals(email, userVO.email) && Objects.equals(rank, userVO.rank) && Objects.equals(score, userVO.score) && Objects.equals(deaths, userVO.deaths) && Objects.equals(kills, userVO.kills);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, rank, score, deaths, kills);
    }
}
