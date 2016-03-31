package ru.mipt.asklector.data.api.v1.providers;

import android.util.Log;

import com.squareup.okhttp.Response;

import java.io.IOException;

import ru.mipt.asklector.data.api.v1.requests.PostRequester;
import ru.mipt.asklector.data.api.v1.requests.post.AskCommentRequest;
import ru.mipt.asklector.data.db.GetFromDB;
import ru.mipt.asklector.usecase.askComment.AskCommentUseCaseImpl;

/**
 * Created by Dmitry Bochkov on 11.12.2015.
 */
public class AskCommentProvider extends AbstractProvider {

    private String commentText;
    private int questionId;

    public  AskCommentProvider(String commentText, int questionId){
        this.commentText = commentText;
        this.questionId = questionId;
    }

    @Override
    public void run() {
        String token = GetFromDB.user().getToken().string();

        PostRequester postRequester = new PostRequester();
        try {
            Response response = postRequester.post(
                    new AskCommentRequest(
                            commentText,
                            token,
                            questionId
                    )
            );

            Log.d("AskCommentProvider", "Response " + String.valueOf(response.code()));
            if(response.code() != 200){
                if (onFinishedListener != null)
                    onFinishedListener.onFailure(AskCommentUseCaseImpl.UNKNOWN_ERROR);
                return;
            }

            Log.d("AskCommentProvider", "Success");
            if (onFinishedListener != null)
                onFinishedListener.onSuccess();
        }
        catch (IOException e){
            if (onFinishedListener != null)
                onFinishedListener.onFailure(AskCommentUseCaseImpl.CONNECTION_ERROR);
            e.printStackTrace();
        }
    }
}