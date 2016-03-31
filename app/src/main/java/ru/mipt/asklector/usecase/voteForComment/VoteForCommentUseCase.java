package ru.mipt.asklector.usecase.voteForComment;

import ru.mipt.asklector.usecase.RequestListener;
import ru.mipt.asklector.usecase.UseCase;

/**
 * Created by Dmitry Bochkov on 11.12.2015.
 */
public interface VoteForCommentUseCase extends UseCase{

    void setOnFinishedListener(OnFinishedListener onFinishedListener);

    interface OnFinishedListener extends RequestListener {

    }
}
