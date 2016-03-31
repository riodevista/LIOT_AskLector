package ru.mipt.asklector.data.api.v1.providers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Response;

import java.io.IOException;

import ru.mipt.asklector.AskLector;
import ru.mipt.asklector.data.api.v1.objects.mappers.LectureMapper;
import ru.mipt.asklector.data.api.v1.objects.pojos.LecturePOJO;
import ru.mipt.asklector.data.api.v1.requests.GetRequester;
import ru.mipt.asklector.data.api.v1.requests.get.GetLectureRequest;
import ru.mipt.asklector.data.db.GetFromDB;
import ru.mipt.asklector.data.db.InsertIntoDB;
import ru.mipt.asklector.data.db.tables.AskerDB;
import ru.mipt.asklector.data.db.tables.LectureDB;
import ru.mipt.asklector.data.db.tables.QuestionsDB;
import ru.mipt.asklector.domain.Lecture;
import ru.mipt.asklector.domain.LectureID;
import ru.mipt.asklector.domain.Token;
import ru.mipt.asklector.usecase.getLecture.GetLectureUseCaseImpl;

/**
 * Created by Dmitry Bochkov on 20.10.2015.
 */
public class GetLectureProvider extends AbstractProvider {

    private LectureID lectureID;

    public  GetLectureProvider(LectureID lectureID){
        this.lectureID = lectureID;
    }

    @Override
    public void run() {

        Token token = GetFromDB.user().getToken();

        GetRequester getRequester = new GetRequester();
        try {
            Response response = getRequester.run(new GetLectureRequest(lectureID, token));

            if(response.code() == 404){
                if (onFinishedListener != null)
                    onFinishedListener.onFailure(GetLectureUseCaseImpl.LECTURE_NOT_FOUND_ERROR);
                return;
            }
            if(response.code() != 200){
                if (onFinishedListener != null)
                    onFinishedListener.onFailure(GetLectureUseCaseImpl.UNKNOWN_ERROR);
                return;
            }

            Gson gson = new GsonBuilder().create();
            String responseJSON = response.body().string();
            LecturePOJO lecturePOJO = gson.fromJson(responseJSON, LecturePOJO.class);
            Lecture lecture = LectureMapper.toAskLectorObject(lecturePOJO);

            new InsertIntoDB().lecture(lecture);

            AskLector.setConnectedLecture(lecture);

            new QuestionsDB().clear();
            new AskerDB().clear();

            if (onFinishedListener != null)
                onFinishedListener.onSuccess();
        }
        catch (IOException e){
            if (onFinishedListener != null)
                onFinishedListener.onFailure(GetLectureUseCaseImpl.CONNECTION_ERROR);
            e.printStackTrace();
        }
    }

}
