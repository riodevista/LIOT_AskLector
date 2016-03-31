package ru.mipt.asklector.data.api.v1.objects.pojos;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dmitry Bochkov on 20.10.2015.
 */
public class TokenPOJO {
    private String token;

    //Other fields from JSON that we have not considered.
    private Map<String , Object> otherProperties = new HashMap<String , Object>();

    public TokenPOJO(String token){
        this.token = token;
    }

    public String getToken(){
        return this.token;
    }
    public void setToken(String token){
        this.token = token;
    }

    public Object get(String name) {
        return otherProperties.get(name);
    }
}