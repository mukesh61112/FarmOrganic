package com.example.farmorganic.models;

public class LoginModel {
    private  String firstName;
    private String lastName;
    private String about;
    private String image;

    public LoginModel(String firstName, String lastName, String about, String image) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.about = about;
        this.image = image;
    }

    public LoginModel() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
