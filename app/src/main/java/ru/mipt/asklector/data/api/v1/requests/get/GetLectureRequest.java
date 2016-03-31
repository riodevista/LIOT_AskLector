package ru.mipt.asklector.data.api.v1.requests.get;

import ru.mipt.asklector.domain.LectureID;
import ru.mipt.asklector.domain.Token;

/**
 * Created by Dmitry Bochkov on 20.10.2015.
 */
public class GetLectureRequest implements GetRequest{

    private final static String URL = "https://asklector.mipt.ru/api/v2/lectures/";

    private LectureID lectureID;
    private Token token;

    public GetLectureRequest(LectureID lectureID, Token token){
        this.lectureID = lectureID;
        this.token = token;
    }


    @Override
    public String getURL() {
        return buildRequestUrl();
    }

    private String buildRequestUrl(){
        return URL +  lectureID.getId() + "?select_by=code&token=" + token.string();
    }
}
