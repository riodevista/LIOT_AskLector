package ru.mipt.asklector.data.api.v1.requests.get;

import ru.mipt.asklector.domain.Token;

/**
 * Created by Dmitry Bochkov on 10.12.2015.
 */
public class LoadCommentsRequest implements GetRequest{

    private final static String URL = "https://asklector.mipt.ru/api/v2/question/";

    private int questionId;
    private Token token;
    private int page;

    public LoadCommentsRequest(int questionId, Token token, int page){
        this.questionId = questionId;
        this.token = token;
        this.page = page;
    }


    @Override
    public String getURL() {
        return buildRequestUrl();
    }

    private String buildRequestUrl(){
        return URL +  String.valueOf(questionId) +
                "/comments?token=" + token.string() +
                "&page=" + page;
    }
}