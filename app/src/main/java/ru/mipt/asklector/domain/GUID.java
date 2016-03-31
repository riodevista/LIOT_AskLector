package ru.mipt.asklector.domain;

/**
 * Created by Dmitry Bochkov on 20.10.2015.
 */
public class GUID implements AskLectorObject{
    
    private String guid;


    public GUID(String guid) {
        this.guid = guid;
    }


    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String string(){
        return guid;
    }
}
