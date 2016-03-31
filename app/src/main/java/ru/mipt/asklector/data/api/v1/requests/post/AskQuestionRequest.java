package ru.mipt.asklector.data.api.v1.requests.post;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ru.mipt.asklector.data.api.v1.objects.pojos.QuestionToAskPOJO;

/**
 * Created by Dmitry Bochkov on 28.10.2015.
 */
public class AskQuestionRequest implements PostRequest {

    private final static String URL = "https://asklector.mipt.ru/api/v2/lectures/";

    private String questionText;
    private String token;
    private String lectureGUID;

    public AskQuestionRequest(String questionText, String token, String lectureGUID) {
        this.questionText = questionText;
        this.token = token;
        this.lectureGUID = lectureGUID;
    }

    @Override
    public String getURL() {
        return buildURL();
    }

    @Override
    public String getJSON() {
        Gson gson = new GsonBuilder().create();
        QuestionToAskPOJO questionToAskPOJO = new QuestionToAskPOJO(questionText);
        String json = gson.toJson(questionToAskPOJO);

        return json;
    }

    private String buildURL(){
        return URL + lectureGUID + "/questions?token=" + token;
    }
}
