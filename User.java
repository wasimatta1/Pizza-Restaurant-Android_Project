package com.example.advancepizza.obects;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class User {
    private String email;
    private String firstName;

    private String LastName;

    private String phone;

    private String password;

    private String authorities;

    private String gender;
    private byte[] image;
    public User() {
    }

    public User(String email, String firstName, String lastName, String phone, String password, String authorities, String gender) {
        this.email = email;
        this.firstName = firstName;
        LastName = lastName;
        this.phone = phone;
        this.password = password;
        this.authorities = authorities;
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", authorities='" + authorities + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }




}
