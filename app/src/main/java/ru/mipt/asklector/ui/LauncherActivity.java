package ru.mipt.asklector.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import ru.mipt.asklector.AskLector;
import ru.mipt.asklector.ui.lectureConnection.activity.LectureConnectionActivity;
import ru.mipt.asklector.ui.login.activity.LoginActivity;

/**
 * Created by Dmitry Bochkov on 24.10.2015.
 */
public class LauncherActivity extends Activity {

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);

        Intent intent;

        if(isUserAuthorized()){
            intent = new Intent(this, LectureConnectionActivity.class);
        }
        else{
            intent = new Intent(this, LoginActivity.class);
        }

        startActivity(intent);
        finish();
    }

    private boolean isUserAuthorized(){
        if(AskLector.getAuthorizedUser() == null)
            return false;
        else
            return true;

    }
}
