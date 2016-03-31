package ru.mipt.asklector.ui.questionsList;

import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created by Dmitry Bochkov on 15.10.2015.
 */
public interface QuestionsListPresenter {

    void viewOnPause();
    void viewOnResume();

    void loadQuestions();
    void reloadQuestions();
    void setSwipeOnRefreshListener(final SwipeRefreshLayout swipeRefreshLayout);
    void showFullQuestion(long questionId);

    void askQuestion(String text);
    boolean checkQuestion(String text);

}
