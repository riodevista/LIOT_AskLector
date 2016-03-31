package ru.mipt.asklector.data.api.v1.requests.post;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ru.mipt.asklector.data.api.v1.objects.mappers.UserMapper;
import ru.mipt.asklector.domain.User;

/**
 * Created by Dmitry Bochkov on 19.10.2015.
 */
public class UserAuthorizationRequest implements PostRequest {

    private final static String URL = "https://asklector.mipt.ru/api/v2/users";

    private User user;

    public UserAuthorizationRequest(User user){
        this.user = user;
    }

    @Override
    public String getURL() {
        return URL;
    }

    @Override
    public String getJSON() {

        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(UserMapper.toPOJO(user));

        return json;
    }
}
