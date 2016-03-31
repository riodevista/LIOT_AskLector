package ru.mipt.asklector.usecase.voteForComment;

import android.content.Intent;
import android.util.Log;

import ru.mipt.asklector.AskLector;
import ru.mipt.asklector.data.services.VoteForCommentService;
import ru.mipt.asklector.usecase.AbstractUseCase;

/**
 * Created by Dmitry Bochkov on 11.12.2015.
 */
public class VoteForCommentUseCaseImpl extends AbstractUseCase implements VoteForCommentUseCase {

    private long commentId;

    public VoteForCommentUseCaseImpl(long commentId){
        this.commentId = commentId;
    }

    @Override
    public void run(){
        Log.d("VoteForCommentUseCase", "run");
        Intent intent = new Intent(AskLector.provideContext(), VoteForCommentService.class);
        intent.putExtra("commentId", commentId);
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
