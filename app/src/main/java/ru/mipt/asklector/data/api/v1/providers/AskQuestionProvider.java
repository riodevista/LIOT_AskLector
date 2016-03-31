package ru.mipt.asklector.data.api.v1.providers;

import android.util.Log;

import com.squareup.okhttp.Response;

import java.io.IOException;

import ru.mipt.asklector.data.api.v1.requests.PostRequester;
import ru.mipt.asklector.data.api.v1.requests.post.AskQuestionRequest;
import ru.mipt.asklector.data.db.GetFromDB;
import ru.mipt.asklector.usecase.askQuestion.AskQuestionUseCaseImpl;

/**
 * Created by Dmitry Bochkov on 28.10.2015.
 */
public class AskQuestionProvider extends AbstractProvider {

    private String questionText;

    public  AskQuestionProvider(String questionText){
        this.questionText = questionText;
    }

    @Override
    public void run() {
        String token = GetFromDB.user().getToken().string();
        String lectureGUID = GetFromDB.lecture().getGuid().string();

        PostRequester postRequester = new PostRequester();
        try {
            Response response = postRequester.post(
                    new AskQuestionRequest(
                            questionText,
                            token,
                            lectureGUID
                    )
            );

            if(response.code() != 200){
                if (onFinishedListener != null)
                    onFinishedListener.onFailure(AskQuestionUseCaseImpl.UNKNOWN_ERROR);
                return;
            }

            Log.d("AskLector", "AskQuestionProvider");
            if (onFinishedListener != null)
                onFinishedListener.onSuccess();
        }
        catch (IOException e){
            if (onFinishedListener != null)
                onFinishedListener.onFailure(AskQuestionUseCaseImpl.CONNECTION_ERROR);
            e.printStackTrace();
        }
    }
}
