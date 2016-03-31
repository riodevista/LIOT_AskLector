package ru.mipt.asklector;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.mixpanel.android.mpmetrics.Tweak;

import java.util.Calendar;

import io.fabric.sdk.android.Fabric;
import ru.mipt.asklector.data.db.AskLectorDBHelper;
import ru.mipt.asklector.data.db.GetFromDB;
import ru.mipt.asklector.domain.Lecture;
import ru.mipt.asklector.domain.User;

/**
 * Created by Dmitry Bochkov on 16.10.2015.
 */
public class AskLector extends Application {

    private static AskLector instance;

    private static User authorizedUser = null;
    private static Lecture connectedLecture = null;

    private static final String MIXPANEL_TOKEN = "da635a26822277c6213b0f1414057d76";
    private static MixpanelAPI mixpanel = null;
    public static Tweak<Boolean> askAtAnyTime = MixpanelAPI.booleanTweak("Ask question and vote at any time", false);
    public static Tweak<Boolean> showRating = MixpanelAPI.booleanTweak("Show rating", true);
    public static Tweak<Boolean> allowComplain = MixpanelAPI.booleanTweak("Allow complain", true);
    //private static Tweak<Integer> delayBeforeStartSurveyInMin = MixpanelAPI.intTweak("Show survey after N minutes from lecture beginning", 20);

   // private static Survey survey = null;
    private static Toast toast = null;

    public AskLector(){
        instance = this;
    }

    @Override
    public void onCreate(){
        Log.d("AskLector", "Application onCreate");
        super.onCreate();

        //Crashlytics init
        Fabric.with(this, new Crashlytics());

        //Mixpanel init
        mixpanel = MixpanelAPI.getInstance(this, MIXPANEL_TOKEN);

        //Authorized user init
        GetFromDB getFromDB = new GetFromDB();
        User user = getFromDB.user();
        if(user != null){
            setAuthorizedUser(user);
        }

        //Set Lecture if exist
        Lecture lecture = GetFromDB.lecture();
        setConnectedLecture(lecture);
    }

    @Override
    public void onTerminate(){
        super.onTerminate();
        AskLectorDBHelper.getInstance().getWritableDatabase().close();
        mixpanel.flush();
    }


    public static Context provideContext(){
        return instance;
    }

    public static User getAuthorizedUser() {
        return authorizedUser;
    }

    public static void setAuthorizedUser(User _authorizedUser){
        authorizedUser = _authorizedUser;

        mixpanel.track("AskLector started by " + authorizedUser.getUsername());

        mixpanel.getPeople().identify(authorizedUser.getUsername());
        mixpanel.getPeople().set("Token", authorizedUser.getToken().string());
        Calendar now = Calendar.getInstance();
        mixpanel.getPeople().set("Sign in time", now.toString());

        mixpanel.getPeople().joinExperimentIfAvailable(); //Update Mixpanel tweaks.
    }

    public static void setConnectedLecture(Lecture lecture){
        //For test lecture only.
        /*Calendar lectureStart = lecture.getDateStart();
        lectureStart.set(Calendar.YEAR, 2015);
        lectureStart.set(Calendar.MONTH, Calendar.NOVEMBER);
        lectureStart.set(Calendar.DAY_OF_MONTH, 26);
        lectureStart.set(Calendar.HOUR_OF_DAY, 19);
        lectureStart.set(Calendar.MINUTE, 17);
        lecture.setDateStart(lectureStart);

        Calendar lectureEnd = lecture.getDateEnd();
        lectureEnd.set(Calendar.YEAR, 2015);
        lectureEnd.set(Calendar.MONTH, Calendar.NOVEMBER);
        lectureEnd.set(Calendar.DAY_OF_MONTH, 26);
        lectureEnd.set(Calendar.HOUR_OF_DAY, 19);
        lectureEnd.set(Calendar.MINUTE, 25);
        lecture.setDateEnd(lectureEnd);*/

        connectedLecture = lecture;
    }

    public static Lecture getConnectedLecture(){
        return connectedLecture;
    }

    public static boolean isItLectureTime(){

       if(askAtAnyTime.get())
            return true;

        Calendar now = Calendar.getInstance();

        Log.d("Current time", now.toString());
        Log.d("Lecture starts", connectedLecture.getDateStart().toString());
        Log.d("Lecture ends", connectedLecture.getDateEnd().toString());

        if (now.before(connectedLecture.getDateStart())) {
            if (toast != null){
                toast.cancel();
            }
            toast = Toast.makeText(instance, R.string.lecture_not_started, Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        if (now.after(connectedLecture.getDateEnd())) {
            if (toast != null){
                toast.cancel();
            }
            toast = Toast.makeText(instance, R.string.lecture_is_ended, Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        return true;
    }


    /*
    public static boolean isItSurveyTime(){

        Calendar now = Calendar.getInstance();

        Calendar lectureStart = new GregorianCalendar();
        lectureStart.setTime(getConnectedLecture().getDateStart().getTime());
        int delay = delayBeforeStartSurveyInMin.get();
        lectureStart.add(Calendar.MINUTE, delay);
        Log.d("Survey starts", lectureStart.toString());
        if (now.before(lectureStart)){
            return false;
        }

        if (survey == null)
            survey = AskLector.getMixpanel().getPeople().getSurveyIfAvailable();

        if (survey == null)
            return false;
        else
            return true;

       /* if (now.after(lectureStart)){
            return true;
        }
        else {
            if (toast != null){
                toast.cancel();
            }
            toast = Toast.makeText(instance, "Анкета станет доступной через " + String.valueOf(delay) + " минут после начала лекции.", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }


    }*/
    public static MixpanelAPI getMixpanel(){
        return mixpanel;
    }

    /*
    public static Survey getSurvey(){
        return survey;
    }
    public static void deleteSurvey(){
        survey = null;
    }*/
}
