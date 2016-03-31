package ru.mipt.asklector.data.api.v1.requests.put;

/**
 * Created by Dmitry Bochkov on 28.10.2015.
 */
public class VoteForQuestionRequest implements PutRequest {

    private final static String URL = "https://asklector.mipt.ru/api/v2/questions/";

    private int questionId;
    private String token;
    private String vote;

    public VoteForQuestionRequest(int questionId, String token, String vote){
        this.questionId = questionId;
        this.token = token;
        this.vote = vote;
    }


    @Override
    public String getURL() {
        return buildRequestUrl();
    }

    private String buildRequestUrl(){
        return URL +  String.valueOf(questionId) + "/rating/" + vote + "?token=" + token;
    }
}
