package com.khanabir42gmail.abir.myapplication;

/**
 * Created by abir on 20/06/2016.
 */
public class Contact {
    public Contact() {
    }

    public Contact(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Contact(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    String name;
    String email;
    String password;
}
