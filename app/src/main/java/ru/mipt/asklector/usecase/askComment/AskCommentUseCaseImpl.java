package ru.mipt.asklector.usecase.askComment;

import android.content.Intent;
import android.util.Log;

import ru.mipt.asklector.AskLector;
import ru.mipt.asklector.data.services.AskCommentService;
import ru.mipt.asklector.domain.Comment;
import ru.mipt.asklector.usecase.AbstractUseCase;

/**
 * Created by Dmitry Bochkov on 11.12.2015.
 */
public class AskCommentUseCaseImpl extends AbstractUseCase implements AskCommentUseCase {

    private String commentText;
    private int questionId;

    public AskCommentUseCaseImpl(String commentText, int questionId) {
        this.commentText = commentText;
        this.questionId = questionId;
    }

    @Override
    public void run() {
        Log.d("AskCommentUseCase", "run");
        Intent intent = new Intent(AskLector.provideContext(), AskCommentService.class);
        intent.putExtra(Comment.OBJECT, commentText);
        intent.putExtra("questionId", questionId);
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