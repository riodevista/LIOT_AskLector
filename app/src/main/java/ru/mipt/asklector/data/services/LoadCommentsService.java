package ru.mipt.asklector.data.services;

import android.content.Intent;
import android.util.Log;

import ru.mipt.asklector.data.api.v1.providers.AbstractProvider;
import ru.mipt.asklector.data.api.v1.providers.LoadCommentsProvider;

/**
 * Created by Dmitry Bochkov on 10.12.2015.
 */
public class LoadCommentsService extends AbstractService {

    public LoadCommentsService() {
        super("LoadCommentsService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("LoadCommentsService", "onHandleIntent");
        int questionId = intent.getIntExtra("questionId", 0);
        AbstractProvider provider = new LoadCommentsProvider(questionId);
        setUpProviderAndRun(provider);
    }
}
