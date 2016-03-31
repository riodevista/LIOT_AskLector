package ru.mipt.asklector.ui.login;

/**
 * Created by Dmitry Bochkov on 10.10.2015.
 */
public interface LoginView {

    void showTooLongNameErrorMessage();
    void showInvalidCharactersErrorMessage();
    void hideErrorMessage();

    //void showUserAuthorizationErrorMessage();
    void showUsernameConflictErrorMessage();
    void showConnectionErrorMessage();
    void showUnknownErrorMessage();

    void showLoader();
    void hideLoader();

    void startNextActivity();

    String getEnteredName();
}
