package ru.mipt.asklector.data.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import ru.mipt.asklector.data.api.v1.providers.AbstractProvider;

/**
 * Created by Dmitry Bochkov on 07.12.2015.
 */
public abstract class AbstractService extends IntentService{

    public static final String RESULT_SUCCESS = "Success";
    public static final String RESULT_FAILURE = "Failure";
    public static final String ERROR_CODE = "ErrorCode";
    public static final String INTENT_CODE = "AbstractService";

    public AbstractService(String name) {
        super(name);
    }

    protected void setUpProviderAndRun(AbstractProvider provider){
        provider.setOnFinishedListener(
                new AbstractProvider.OnFinishedListener() {
                    @Override
                    public void onSuccess() {
                        Intent intent = new Intent(INTENT_CODE);
                        intent.putExtra(INTENT_CODE, RESULT_SUCCESS);
                        LocalBroadcastManager.getInstance(AbstractService.this).
                                sendBroadcast(intent);
                    }

                    @Override
                    public void onFailure(int error) {
                        Intent intent = new Intent(INTENT_CODE);
                        intent.putExtra(INTENT_CODE, RESULT_FAILURE);
                        intent.putExtra(ERROR_CODE, error);
                        LocalBroadcastManager.getInstance(AbstractService.this).
                                sendBroadcast(intent);
                    }
                }
        );
        provider.run();
    }
}
