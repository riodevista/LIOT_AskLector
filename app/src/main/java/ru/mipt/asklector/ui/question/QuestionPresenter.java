package ru.mipt.asklector.ui.question;

import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created by Dmitry Bochkov on 08.12.2015.
 */
public interface QuestionPresenter {

    void viewOnPause();
    void viewOnResume();
    void viewOnDestroy();

    void loadComments();
    void reloadComments();

    void voteForQuestion();
    void reportAsSpam();

    void setSwipeOnRefreshListener(SwipeRefreshLayout swipeRefreshLayout);

    void sendComment(String commentText);
}
