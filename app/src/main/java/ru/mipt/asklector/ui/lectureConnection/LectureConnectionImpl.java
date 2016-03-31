package ru.mipt.asklector.ui.lectureConnection;

import android.os.Handler;

import ru.mipt.asklector.AskLector;
import ru.mipt.asklector.domain.Lecture;
import ru.mipt.asklector.domain.LectureID;
import ru.mipt.asklector.usecase.getLecture.GetLectureUseCase;
import ru.mipt.asklector.usecase.getLecture.GetLectureUseCaseImpl;

/**
 * Created by Dmitry Bochkov on 13.10.2015.
 */
public class LectureConnectionImpl implements LectureConnection {

    private LectureConnectionView lectureConnectionView;

    public LectureConnectionImpl(LectureConnectionView lectureConnectionView){
        this.lectureConnectionView = lectureConnectionView;
    }

    @Override
    public boolean checkLectureId() {
        String lectureId = lectureConnectionView.getEnteredLectureId();

        if(lectureId.length() == 0){
            lectureConnectionView.hideErrorMessage();
            return false;
        }

        if (lectureId.length() < 4){
            lectureConnectionView.showLectureIdTooShortErrorMessage();
            return false;
        }

        lectureConnectionView.hideErrorMessage();
        return true;
    }

    @Override
    public void connectToLectureWithId() {
            lectureConnectionView.startNextActivity();
    }

    @Override
    public void findLecture() {
        lectureConnectionView.hideConnectButton();
        String lectureID = lectureConnectionView.getEnteredLectureId();

        if(!checkLectureId())
            return;

        lectureConnectionView.showLoader();

        GetLectureUseCase getLectureUseCase = new GetLectureUseCaseImpl(new LectureID(lectureID));
        getLectureUseCase.setOnFinishedListener(
                new GetLectureUseCase.OnFinishedListener() {
                        @Override
                        public void onSuccess() {
                            lectureConnectionView.hideLoader();

                            Lecture lecture = AskLector.getConnectedLecture();

                            lectureConnectionView.showFoundLectureMessage(
                                    lecture.getSubject().getTitle() + "\n"
                                            + lecture.getLecturer().getTitle() + "."
                            );
                            lectureConnectionView.showConnectButton();
                        }

                        @Override
                        public void onConnectionError() {
                            lectureConnectionView.hideLoader();
                            lectureConnectionView.showConnectionErrorMessage();
                            tryToFindLectureAgain();
                        }

                        @Override
                        public void onLectureNotFoundError () {
                            lectureConnectionView.hideLoader();
                            lectureConnectionView.showLectureNotFoundErrorMessage();
                        }

                        @Override
                        public void onUnknownError () {
                            lectureConnectionView.hideLoader();
                            lectureConnectionView.showUnknownErrorMessage();
                        }
                }

        );
        getLectureUseCase.run();

    }

    private void tryToFindLectureAgain(){
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        findLecture();
                    }
                },
                3000
        );
    }

}
