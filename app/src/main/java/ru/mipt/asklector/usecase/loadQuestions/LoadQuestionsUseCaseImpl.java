package ru.mipt.asklector.usecase.loadQuestions;

import android.content.Intent;
import android.util.Log;

import ru.mipt.asklector.AskLector;
import ru.mipt.asklector.data.services.LoadQuestionsService;
import ru.mipt.asklector.usecase.AbstractUseCase;

/**
 * Created by Dmitry Bochkov on 24.10.2015.
 */
public class LoadQuestionsUseCaseImpl extends AbstractUseCase implements LoadQuestionsUseCase {

    @Override
    public void run() {
        Log.d("LoadQuestionsUseCase", "run");
        Intent intent = new Intent(AskLector.provideContext(), LoadQuestionsService.class);
        AskLector.provideContext().startService(intent);
    }

    @Override
    public void setOnFinishedListener(final OnFinishedListener onFinishedListener) {
        registerReceiver(onFinishedListener);
    }

    @Override
    protected void checkError(int errorCode) {

    }
}
