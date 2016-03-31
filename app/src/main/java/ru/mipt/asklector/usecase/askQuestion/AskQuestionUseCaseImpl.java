package ru.mipt.asklector.usecase.askQuestion;

import android.content.Intent;
import android.util.Log;

import ru.mipt.asklector.AskLector;
import ru.mipt.asklector.data.services.AskQuestionService;
import ru.mipt.asklector.domain.Question;
import ru.mipt.asklector.usecase.AbstractUseCase;

/**
 * Created by Dmitry Bochkov on 27.10.2015.
 */
public class AskQuestionUseCaseImpl extends AbstractUseCase implements AskQuestionUseCase {

    private String questionText;

    public AskQuestionUseCaseImpl(String questionText) {
        this.questionText = questionText;
    }

    @Override
    public void run() {
        Log.d("AskQuestionUseCase", "run");
        Intent intent = new Intent(AskLector.provideContext(), AskQuestionService.class);
        intent.putExtra(Question.OBJECT, questionText);
        AskLector.provideContext().startService(intent);
    }

    @Override
    public void setOnFinishedListener(final OnFinishedListener onFinishedListener){
        registerReceiver(onFinishedListener);
    }


    @Override
    protected void checkError(int errorCode) {

    }
}
