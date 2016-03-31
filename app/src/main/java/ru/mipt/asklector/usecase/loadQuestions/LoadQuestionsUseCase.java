package ru.mipt.asklector.usecase.loadQuestions;

import ru.mipt.asklector.usecase.RequestListener;
import ru.mipt.asklector.usecase.UseCase;

/**
 * Created by Dmitry Bochkov on 27.11.2015.
 */
public interface LoadQuestionsUseCase extends UseCase {

    void setOnFinishedListener(OnFinishedListener onFinishedListener);

    interface OnFinishedListener extends RequestListener {

    }
}
