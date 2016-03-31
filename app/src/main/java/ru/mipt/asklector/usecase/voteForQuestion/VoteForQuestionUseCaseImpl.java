package ru.mipt.asklector.usecase.voteForQuestion;

import android.content.Intent;
import android.util.Log;

import ru.mipt.asklector.AskLector;
import ru.mipt.asklector.data.services.VoteForQuestionService;
import ru.mipt.asklector.domain.Question;
import ru.mipt.asklector.usecase.AbstractUseCase;

/**
 * Created by Dmitry Bochkov on 28.10.2015.
 */
public class VoteForQuestionUseCaseImpl extends AbstractUseCase implements VoteForQuestionUseCase {

    private long questionId;

    public VoteForQuestionUseCaseImpl(long questionId){
        this.questionId = questionId;
    }

    @Override
    public void run(){
        Log.d("VoteForQuestionUseCase", "run");
        Intent intent = new Intent(AskLector.provideContext(), VoteForQuestionService.class);
        intent.putExtra(Question.OBJECT, questionId);
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
