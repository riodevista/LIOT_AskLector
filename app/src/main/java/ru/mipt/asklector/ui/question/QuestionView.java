package ru.mipt.asklector.ui.question;

/**
 * Created by Dmitry Bochkov on 08.12.2015.
 */
public interface QuestionView {

    void showProgress();
    void hideProgress();

    void setQuestionText(String text);
    void setQuestionDateAndAuthor(String text);
    void setQuestionRating(String text);

    void showUnknownErrorMessage();
    void showConnectionErrorMessage();

    void setNewCommentPretext(String text);
    void showSendCommentUnknownErrorMessage();
    void showSendCommentSuccessMessage();
    void showEmptyCommentErrorMessage();
    void showReportAsSpamSuccessMessage();

    void hideKeyboard();

}
