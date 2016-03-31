package ru.mipt.asklector.ui.lectureConnection;

/**
 * Created by Dmitry Bochkov on 13.10.2015.
 */
public interface LectureConnectionView {

    void showLectureIdTooShortErrorMessage();
    void hideErrorMessage();

    void startNextActivity();

    String getEnteredLectureId();

    void showFoundLectureMessage(String lectureInfo);
    void showLectureNotFoundErrorMessage();
    void showConnectionErrorMessage();
    void showUnknownErrorMessage();

    void showLoader();
    void hideLoader();

    void hideConnectButton();
    void showConnectButton();
}
