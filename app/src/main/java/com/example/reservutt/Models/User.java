package com.example.reservutt.Models;

public class User
{
    private String id,username,phone,profession;

    public User()
    {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User(String id, String username, String phone, String profession) {
        this.id = id;
        this.username = username;
        this.phone = phone;
        this.profession = profession;
    }
}
