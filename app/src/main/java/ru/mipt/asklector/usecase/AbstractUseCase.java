package ru.mipt.asklector.usecase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import ru.mipt.asklector.AskLector;
import ru.mipt.asklector.data.services.AbstractService;

/**
 * Created by Dmitry Bochkov on 07.12.2015.
 */
public abstract class AbstractUseCase {

    public static final int CONNECTION_ERROR = 0;
    public static final int UNKNOWN_ERROR = 1;

    BroadcastReceiver broadcastReceiver;

    protected void registerReceiver(final RequestListener RequestListener){

        broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                String result = intent.getStringExtra(AbstractService.INTENT_CODE);
                switch (result){
                    case AbstractService.RESULT_SUCCESS:
                        RequestListener.onSuccess();
                        break;
                    case AbstractService.RESULT_FAILURE:
                        int errorCode = intent.getIntExtra(
                                AbstractService.ERROR_CODE,
                                UNKNOWN_ERROR
                        );
                        switch (errorCode){
                            case CONNECTION_ERROR:
                                RequestListener.onConnectionError();
                                break;
                            case UNKNOWN_ERROR:
                                RequestListener.onUnknownError();
                                break;
                            default:
                                break;
                        }
                        checkError(errorCode);
                        break;
                    default:
                        break;
                }
                LocalBroadcastManager.getInstance(AskLector.provideContext())
                        .unregisterReceiver(broadcastReceiver);
            }
        };

        LocalBroadcastManager.getInstance(AskLector.provideContext()).registerReceiver(broadcastReceiver,
                new IntentFilter(AbstractService.INTENT_CODE));
    }

    protected abstract void checkError(int errorCode);
}
