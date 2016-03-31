package ru.mipt.asklector.data.services;

import android.content.Intent;
import android.util.Log;

import ru.mipt.asklector.data.api.v1.providers.AbstractProvider;
import ru.mipt.asklector.data.api.v1.providers.GetLectureProvider;
import ru.mipt.asklector.domain.LectureID;

/**
 * Created by Dmitry Bochkov on 20.10.2015.
 */
public class GetLectureService extends AbstractService{

    public GetLectureService() {
        super("GetLectureService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("GetLectureService", "onHandleIntent");
        LectureID lectureID = intent.getParcelableExtra(LectureID.OBJECT);
        AbstractProvider provider = new GetLectureProvider(lectureID);
        setUpProviderAndRun(provider);
    }

}
