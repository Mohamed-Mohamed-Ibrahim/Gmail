package com.example.Email.Services;

import java.util.ArrayList;

public class Contact {
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    private String name;
    private ArrayList<String> email;

    private  String user;

    public Contact(){}
    public Contact(String name, ArrayList<String> email, String user) {
        this.name = name;
        this.email = email;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getEmailAddresses() {
        return email;
    }

    public void addEmailAddress(String emailAddress) {
        email.add(emailAddress);
    }
    public void removeEmailAddress(String emailAddress){
        email.remove(emailAddress);
    }

    public ArrayList<String> getEmail() {
        return email;
    }

    public void setEmail(ArrayList<String> email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", email=" + email +
                ", user='" + user + '\'' +
                '}';
    }
}

