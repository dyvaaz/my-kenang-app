package com.example.diffa.kenangapplication.Model;

/**
 * Created by Diffa on 26/12/2017.
 */

public class Rates {
    private String rate;
    private String comment;


    public Rates(){

    }

    public Rates(String rate, String comment){
        this.rate = rate;
        this.comment = comment;
    }
    public String getRate(){
        return rate;
    }
    public void setRate(String rate){
        this.rate = rate;
    }
    public String getComment(){
        return comment;
    }
    public void setComment(String comment){
        this.comment = comment;
    }

}
