package ru.mipt.asklector.domain;


/**
 * Created by Dmitry Bochkov on 20.10.2015.
 */
public class Token{
    private String token;

    public Token(String token){
        this.token = token;
    }


    public String string(){
        return this.token;
    }

    public void setToken(String token){
        this.token = token;
    }

    public String toString(){
        return token;
    }
}