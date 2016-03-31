package ru.mipt.asklector.data.api.v1.requests.get;

import ru.mipt.asklector.domain.GUID;
import ru.mipt.asklector.domain.Token;

/**
 * Created by Dmitry Bochkov on 24.10.2015.
 */
public class LoadQuestionsRequest implements GetRequest{

    private final static String URL = "https://asklector.mipt.ru/api/v2/lectures/";

    private GUID guid;
    private Token token;
    private int page;

    public LoadQuestionsRequest(GUID guid, Token token, int page){
        this.guid = guid;
        this.token = token;
        this.page = page;
    }


    @Override
    public String getURL() {
        return buildRequestUrl();
    }

    private String buildRequestUrl(){
        return URL +  guid.string() +
                "/questions?token=" + token.string() +
                "&page=" + page;
    }
}
