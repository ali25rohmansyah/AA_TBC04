package com.gregetdev.materitbcapp.Model;

public class Admin {

    private String Password;

    public Admin() {
    }

    public Admin(String password) {
        Password = password;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
