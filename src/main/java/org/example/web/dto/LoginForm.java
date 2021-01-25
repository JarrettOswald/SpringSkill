package org.example.web.dto;

import javax.validation.constraints.Size;
import java.util.Objects;

public class LoginForm {

    @Size(min = 6)
    private String username;
    @Size(min = 3)
    private String password;

    public LoginForm(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginForm() {
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
    public String toString() {
        return "LoginForm{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginForm loginForm = (LoginForm) o;
        return username.equals(loginForm.username) &&
                password.equals(loginForm.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
