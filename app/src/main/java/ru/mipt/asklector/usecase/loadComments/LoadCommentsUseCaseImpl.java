package ru.mipt.asklector.usecase.loadComments;

import android.content.Intent;
import android.util.Log;

import ru.mipt.asklector.AskLector;
import ru.mipt.asklector.data.services.LoadCommentsService;
import ru.mipt.asklector.usecase.AbstractUseCase;

/**
 * Created by Dmitry Bochkov on 24.10.2015.
 */
public class LoadCommentsUseCaseImpl extends AbstractUseCase implements LoadCommentsUseCase {

    private int questionId;

    public LoadCommentsUseCaseImpl(int questionId){
        this.questionId = questionId;
    }
    @Override
    public void run() {
        Log.d("LoadCommentsUseCase", "run");
        Intent intent = new Intent(AskLector.provideContext(), LoadCommentsService.class);
        intent.putExtra("questionId", questionId);
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
