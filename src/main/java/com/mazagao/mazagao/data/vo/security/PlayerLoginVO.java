package com.mazagao.mazagao.data.vo.security;

import java.io.Serializable;
import java.util.Objects;

public class PlayerLoginVO implements Serializable {
    private static final long serialVersionID = 1l;

    private String username;
    private String password;

    public PlayerLoginVO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerLoginVO that = (PlayerLoginVO) o;
        return Objects.equals(getUsername(), that.getUsername()) && Objects.equals(getPassword(), that.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getPassword());
    }
}
