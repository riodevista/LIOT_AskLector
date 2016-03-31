package ru.mipt.asklector.data.services;

import android.content.Intent;
import android.util.Log;

import ru.mipt.asklector.data.api.v1.providers.AbstractProvider;
import ru.mipt.asklector.data.api.v1.providers.AskQuestionProvider;
import ru.mipt.asklector.domain.Question;

/**
 * Created by Dmitry Bochkov on 28.10.2015.
 */
public class AskQuestionService extends AbstractService{

    public AskQuestionService() {
        super("AskQuestionService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("AskQuestionService", "onHandleIntent");
        String questionText = intent.getStringExtra(Question.OBJECT);
        AbstractProvider provider = new AskQuestionProvider(questionText);
        setUpProviderAndRun(provider);
    }

}
