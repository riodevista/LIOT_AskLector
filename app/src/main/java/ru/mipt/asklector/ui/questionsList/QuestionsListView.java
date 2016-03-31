package ru.mipt.asklector.ui.questionsList;

/**
 * Created by Dmitry Bochkov on 15.10.2015.
 */
public interface QuestionsListView {

    void showProgress();
    void hideProgress();

    void showUnknownErrorMessage();
    void showConnectionErrorMessage();

    void showFullQuestionWithoutComments(String questionText);
    void showFullQuestionWithComments(long questionId);

    //AskQuestion dialog:
    void showAskQuestionSuccessMessage();
    void showAskQuestionUnknownErrorMessage();
    void showEmptyQuestionErrorMessage();
    void setNewQuestionPretext(String text);

}
