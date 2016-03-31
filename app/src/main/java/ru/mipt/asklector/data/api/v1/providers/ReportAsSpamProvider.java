package ru.mipt.asklector.data.api.v1.providers;

import android.util.Log;

import com.squareup.okhttp.Response;

import java.io.IOException;

import ru.mipt.asklector.data.api.v1.requests.PutRequester;
import ru.mipt.asklector.data.api.v1.requests.put.ReportAsSpamRequest;
import ru.mipt.asklector.data.db.GetFromDB;
import ru.mipt.asklector.usecase.AbstractUseCase;

/**
 * Created by Dmitry Bochkov on 18.12.2015.
 */
public class ReportAsSpamProvider extends AbstractProvider {

    private long id;

    public ReportAsSpamProvider(long id){
        this.id = id;
    }
    @Override
    public void run() {
        Log.d("ReportAsSpamProvider", "run");
        String token = GetFromDB.user().getToken().string();

        PutRequester putRequester = new PutRequester();
        try {
            Response response = putRequester.run(new ReportAsSpamRequest(id, token));

            Log.d("ReportAsSpamProvider", "Response " + String.valueOf(response.code()));
            if(response.code() != 200){
                if (onFinishedListener != null)
                    onFinishedListener.onFailure(AbstractUseCase.UNKNOWN_ERROR);
                return;
            }

            if (onFinishedListener != null)
                onFinishedListener.onSuccess();
        }
        catch (IOException e){
            Log.d("ReportAsSpamProvider", "Exception");
            if (onFinishedListener != null)
                onFinishedListener.onFailure(AbstractUseCase.CONNECTION_ERROR);
            e.printStackTrace();
        }

    }
}
