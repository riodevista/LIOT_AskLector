package ru.mipt.asklector.usecase.askQuestion;

import ru.mipt.asklector.usecase.RequestListener;
import ru.mipt.asklector.usecase.UseCase;

/**
 * Created by Dmitry Bochkov on 27.11.2015.
 */
public interface AskQuestionUseCase extends UseCase {

    void setOnFinishedListener(OnFinishedListener onFinishedListener);

    interface OnFinishedListener extends RequestListener {

    }
}
