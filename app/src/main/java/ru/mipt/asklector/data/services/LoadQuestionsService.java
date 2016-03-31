package ru.mipt.asklector.data.services;

import android.content.Intent;
import android.util.Log;

import ru.mipt.asklector.data.api.v1.providers.AbstractProvider;
import ru.mipt.asklector.data.api.v1.providers.LoadQuestionsProvider;

/**
 * Created by Dmitry Bochkov on 24.10.2015.
 */
public class LoadQuestionsService extends AbstractService{

    public LoadQuestionsService() {
        super("LoadQuestionsService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("LoadQuestionsService", "onHandleIntent");
        AbstractProvider provider = new LoadQuestionsProvider();
        setUpProviderAndRun(provider);
    }

}
