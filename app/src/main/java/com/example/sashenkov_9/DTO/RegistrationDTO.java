package com.example.sashenkov_9.DTO;

import java.io.Serializable;

public class RegistrationDTO implements Serializable {
    private String userName;
    private String email;
    private String password1;
    private String password2;

    public RegistrationDTO(String userName, String email, String password1, String password2) {
        this.userName = userName;
        this.email = email;
        this.password1 = password1;
        this.password2 = password2;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword1() {
        return password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }
}
