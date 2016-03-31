package ru.mipt.asklector.data.services;

import android.content.Intent;
import android.util.Log;

import ru.mipt.asklector.data.api.v1.providers.AbstractProvider;
import ru.mipt.asklector.data.api.v1.providers.VoteForQuestionProvider;
import ru.mipt.asklector.domain.Question;

/**
 * Created by Dmitry Bochkov on 28.10.2015.
 */
public class VoteForQuestionService extends AbstractService{

    public VoteForQuestionService() {
        super("VoteForQuestionService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("VoteForQuestionService", "onHandleIntent");
        long questionId = intent.getLongExtra(Question.OBJECT, -1);
        AbstractProvider provider = new VoteForQuestionProvider(questionId);
        setUpProviderAndRun(provider);
    }
}
