package com.mazagao.mazagao.data.vo;


import java.io.Serializable;
import java.util.Objects;


public class ScoreboardUserVO implements Serializable {
    private String username;
    private Number score;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Number getScore() {
        return score;
    }

    public void setScore(Number score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScoreboardUserVO that = (ScoreboardUserVO) o;
        return Objects.equals(getUsername(), that.getUsername()) && Objects.equals(getScore(), that.getScore());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getScore());
    }
}
