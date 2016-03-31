package ru.mipt.asklector.usecase.getLecture;

import android.content.Intent;

import ru.mipt.asklector.AskLector;
import ru.mipt.asklector.data.services.GetLectureService;
import ru.mipt.asklector.domain.LectureID;
import ru.mipt.asklector.usecase.AbstractUseCase;

/**
 * Created by Dmitry Bochkov on 20.10.2015.
 */
public class GetLectureUseCaseImpl extends AbstractUseCase implements GetLectureUseCase {

    public static final int LECTURE_NOT_FOUND_ERROR = 1001;

    private OnFinishedListener onFinishedListener;

    private LectureID lectureID;

    public GetLectureUseCaseImpl(LectureID lectureID) {
        this.lectureID = lectureID;
    }


    @Override
    public void run(){
            Intent intent = new Intent(AskLector.provideContext(), GetLectureService.class);
            intent.putExtra(LectureID.OBJECT, lectureID);
            AskLector.provideContext().startService(intent);

        }

    @Override
    public void setOnFinishedListener(final OnFinishedListener onFinishedListener) {
        this.onFinishedListener = onFinishedListener;
        registerReceiver(onFinishedListener);
    }

    @Override
    protected void checkError(int errorCode) {
        switch (errorCode){
            case LECTURE_NOT_FOUND_ERROR:
                onFinishedListener.onLectureNotFoundError();
                break;
            default:
                break;
        }
    }
}
