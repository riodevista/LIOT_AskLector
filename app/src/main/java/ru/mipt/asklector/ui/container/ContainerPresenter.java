package ru.mipt.asklector.ui.container;

/**
 * Created by Dmitry Bochkov on 14.10.2015.
 */
public interface ContainerPresenter {

    void showSurveyIfAvailable();
    void checkForSurvey();
    void showAnswersPage();
}
