package ru.mipt.asklector.data.services;

import android.content.Intent;
import android.util.Log;

import ru.mipt.asklector.data.api.v1.providers.AbstractProvider;
import ru.mipt.asklector.data.api.v1.providers.AskCommentProvider;
import ru.mipt.asklector.domain.Comment;

/**
 * Created by Dmitry Bochkov on 11.12.2015.
 */
public class AskCommentService extends AbstractService {

    public AskCommentService() {
        super("AskCommentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("AskCommentService", "onHandleIntent");
        String questionText = intent.getStringExtra(Comment.OBJECT);
        int questionId = intent.getIntExtra("questionId", -1);
        AbstractProvider provider = new AskCommentProvider(questionText, questionId);
        setUpProviderAndRun(provider);
    }
}
