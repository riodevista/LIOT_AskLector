package ru.mipt.asklector.data.api.v1.requests.put;

/**
 * Created by Dmitry Bochkov on 18.12.2015.
 */
public class ReportAsSpamRequest implements PutRequest {
    private final static String URL = "https://asklector.mipt.ru/api/v2/questions/";

    private long id;
    private String token;

    public ReportAsSpamRequest(long id, String token){
        this.id = id;
        this.token = token;
    }


    @Override
    public String getURL() {
        return buildRequestUrl();
    }

    private String buildRequestUrl(){
        return URL +  String.valueOf(id) + "/rating/spam?token=" + token;
    }
}
