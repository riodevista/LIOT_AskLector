package ru.mipt.asklector.ui.container;

import com.mixpanel.android.mpmetrics.Survey;

/**
 * Created by Dmitry Bochkov on 14.10.2015.
 */
public interface ContainerView {

    void setQuestionsListFragment();

    void showSurveyNotAvailableMessage();
    void showSurvey(Survey survey);
    void showGoogleSurvey();
    void showAnswersPage();

    void showSurveyButton();
    void hideSurveyButton();
}
