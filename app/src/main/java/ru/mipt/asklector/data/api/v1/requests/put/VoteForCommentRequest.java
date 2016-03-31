package ru.mipt.asklector.data.api.v1.requests.put;

/**
 * Created by Dmitry Bochkov on 11.12.2015.
 */
public class VoteForCommentRequest implements PutRequest {

    private final static String URL = "https://asklector.mipt.ru/api/v2/questions/";

    private int commentId;
    private String token;
    private String vote;

    public VoteForCommentRequest(int commentId, String token, String vote){
        this.commentId = commentId;
        this.token = token;
        this.vote = vote;
    }


    @Override
    public String getURL() {
        return buildRequestUrl();
    }

    private String buildRequestUrl(){
        return URL +  String.valueOf(commentId) + "/rating/" + vote + "?token=" + token;
    }
}
