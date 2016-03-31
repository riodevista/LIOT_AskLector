package ru.mipt.asklector.data.api.v1.requests.post;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ru.mipt.asklector.data.api.v1.objects.pojos.CommentToAskPOJO;

/**
 * Created by Dmitry Bochkov on 11.12.2015.
 */
public class AskCommentRequest implements PostRequest{

    private final static String URL = "https://asklector.mipt.ru/api/v2/question/";

    private String commentText;
    private String token;
    private int questionId;

    public AskCommentRequest(String commentText, String token, int questionId) {
        this.commentText = commentText;
        this.token = token;
        this.questionId = questionId;
    }

    @Override
    public String getURL() {
        return buildURL();
    }

    @Override
    public String getJSON() {
        Gson gson = new GsonBuilder().create();
        CommentToAskPOJO commentToAskPOJO = new CommentToAskPOJO(commentText);
        String json = gson.toJson(commentToAskPOJO);

        return json;
    }

    private String buildURL(){
        return URL + String.valueOf(questionId) + "/comment?token=" + token;
    }
}