package ru.mipt.asklector.data.services;

import android.content.Intent;
import android.util.Log;

import ru.mipt.asklector.data.api.v1.providers.AbstractProvider;
import ru.mipt.asklector.data.api.v1.providers.UserAuthorizationProvider;
import ru.mipt.asklector.domain.User;

/**
 * Created by Dmitry Bochkov on 19.10.2015.
 */
public class UserAuthorizationService extends AbstractService {

    public UserAuthorizationService() {
        super("UserAuthorizationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("UserAuthService", "onHandleIntent");
        User user = intent.getParcelableExtra(User.OBJECT);
        AbstractProvider provider = new UserAuthorizationProvider(user);
        setUpProviderAndRun(provider);
    }

}
