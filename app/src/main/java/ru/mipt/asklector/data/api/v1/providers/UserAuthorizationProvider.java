package ru.mipt.asklector.data.api.v1.providers;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Calendar;

import ru.mipt.asklector.AskLector;
import ru.mipt.asklector.data.api.v1.objects.mappers.TokenMapper;
import ru.mipt.asklector.data.api.v1.objects.pojos.TokenPOJO;
import ru.mipt.asklector.data.api.v1.requests.PostRequester;
import ru.mipt.asklector.data.api.v1.requests.post.UserAuthorizationRequest;
import ru.mipt.asklector.data.db.tables.UserDB;
import ru.mipt.asklector.domain.User;
import ru.mipt.asklector.usecase.authorization.BackendAuthorization;

/**
 * Created by Dmitry Bochkov on 20.10.2015.
 */
public class UserAuthorizationProvider extends AbstractProvider {

    private User user;

    public  UserAuthorizationProvider(User user){
        this.user = user;
    }

    @Override
    public void run() {
        Log.d("AskLector", "Provider run");
        PostRequester postRequester = new PostRequester();
        try {
            Response response = postRequester.post(new UserAuthorizationRequest(user));

            if(response.code() == 409){
                if (onFinishedListener != null)
                    onFinishedListener.onFailure(BackendAuthorization.CONFLICT_USERNAME_ERROR);
                return;
            }
            if(response.code() != 201){
                if (onFinishedListener != null)
                    onFinishedListener.onFailure(BackendAuthorization.UNKNOWN_ERROR);
                return;
            }

            Gson gson = new GsonBuilder().create();

            TokenPOJO tokenPojo = gson.fromJson(response.body().string(), TokenPOJO.class);

            ru.mipt.asklector.domain.Token token = TokenMapper.toAskLectorObject(tokenPojo);

            user.setToken(token);

            UserDB userDB = new UserDB();
            userDB.clear(); //Только один авторизованный пользователь существует.
            userDB.insert(user);
            AskLector.setAuthorizedUser(user);

            MixpanelAPI mixpanel = AskLector.getMixpanel();
            mixpanel.getPeople().identify(user.getUsername());
            mixpanel.getPeople().set("Token", user.getToken().string());
            Calendar now = Calendar.getInstance();
            mixpanel.getPeople().set("Sign in time", now.toString());
            if (onFinishedListener != null)
                onFinishedListener.onSuccess();
        }
        catch (IOException e){
            if (onFinishedListener != null)
                onFinishedListener.onFailure(BackendAuthorization.CONNECTION_ERROR);
            e.printStackTrace();
        }
    }

}
