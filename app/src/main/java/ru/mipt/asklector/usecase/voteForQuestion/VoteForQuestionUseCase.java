package ru.mipt.asklector.usecase.voteForQuestion;

import ru.mipt.asklector.usecase.RequestListener;
import ru.mipt.asklector.usecase.UseCase;

/**
 * Created by Dmitry Bochkov on 27.11.2015.
 */
public interface VoteForQuestionUseCase extends UseCase {

    void setOnFinishedListener(OnFinishedListener onFinishedListener);

    interface OnFinishedListener extends RequestListener {

    }
}
