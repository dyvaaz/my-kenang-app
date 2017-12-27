package com.example.diffa.applicationcarorder.Model;

/**
 * Created by Diffa on 13/12/2017.
 */

public class User {
    private String email,password,name,phone,imageUrl, rates;

    public User() {
    }
    public User(String email, String password, String name,String phone, String rates){
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.rates = rates;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getName(){
        return name;
    }
    public void setName(String Name) {
        this.name = name;
    }
    public String getPhone(){
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getImageUrl(){
        return imageUrl;
    }
    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }
    public String getRates() {
        return rates;
    }
    public void setRates(String rates){
        this.rates = rates;
    }
}

