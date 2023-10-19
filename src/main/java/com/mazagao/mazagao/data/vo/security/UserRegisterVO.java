package com.mazagao.mazagao.data.vo.security;

import java.io.Serializable;
import java.util.Objects;

public class UserRegisterVO implements Serializable {

    private static final long serialVersionID = 1l;

    private String username;
    private String email;
    private String password;

    public UserRegisterVO(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
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
        UserRegisterVO that = (UserRegisterVO) o;
        return Objects.equals(getUsername(), that.getUsername()) && Objects.equals(getEmail(), that.getEmail()) && Objects.equals(getPassword(), that.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getEmail(), getPassword());
    }
}
