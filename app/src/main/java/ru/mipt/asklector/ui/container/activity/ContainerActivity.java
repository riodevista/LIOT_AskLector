package ru.mipt.asklector.ui.container.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mixpanel.android.mpmetrics.Survey;

import ru.mipt.asklector.AskLector;
import ru.mipt.asklector.R;
import ru.mipt.asklector.ui.container.ContainerPresenter;
import ru.mipt.asklector.ui.container.ContainerPresenterImpl;
import ru.mipt.asklector.ui.container.ContainerView;
import ru.mipt.asklector.ui.questionsList.fragment.QuestionsListFragment;


public class ContainerActivity extends AppCompatActivity implements ContainerView, QuestionsListFragment.OnFragmentInteractionListener {

    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;
    private ContainerPresenter containerPresenter;
    //private QuestionsListFragment questionsListFragment;

    MenuItem startSurveyButton;
    Toast toast = null;
    //Handler checkForSurveyHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        containerPresenter = new ContainerPresenterImpl(this);

        findViews();
        setUpViews();

        setQuestionsListFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.questions_list, menu);
        startSurveyButton = menu.findItem(R.id.action_show_survey);

        //checkForSurveyHandler.post(checkForSurvey);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

       /* if (toggle.onOptionsItemSelected(item)) {
            return false;
        }*/

        switch (id){
            case R.id.action_show_answers:
                containerPresenter.showAnswersPage();
                break;
            case R.id.action_show_survey:
                containerPresenter.showSurveyIfAvailable();
                break;
            case R.id.action_about_app:
                showAboutAppPage();
                break;
            case R.id.action_disconnect_from_lecture:
                disconnectFromLecture();
                break;
            default:
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void setQuestionsListFragment() {
        Fragment fragment = QuestionsListFragment.newInstance();
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.content_frame, fragment, QuestionsListFragment.TAG).
                commit();

        /*FragmentManager fm = getSupportFragmentManager();

        questionsListFragment =
                (QuestionsListFragment) fm.findFragmentByTag(QuestionsListFragment.TAG);

        if (questionsListFragment == null){
            questionsListFragment = QuestionsListFragment.newInstance();
            fm.beginTransactioR.id.content_frame, n().add(questionsListment.TAG).commit();
        }*/

    }

    @Override
    public void showSurveyNotAvailableMessage() {
        if (toast != null){
            toast.cancel();
        }
        toast = Toast.makeText(this, R.string.survey_not_available, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void showSurvey(Survey survey) {
        AskLector.getMixpanel().getPeople().showSurvey(survey, this);
    }

    @Override
    public void showGoogleSurvey() {
        Intent browserIntent = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse(getResources().getString(R.string.google_survey_link))
        );
        startActivity(browserIntent);
    }

    @Override
    public void showAnswersPage() {
        Intent browserIntent = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse(getResources().getString(R.string.answers_link))
        );
        startActivity(browserIntent);
    }

    @Override
    public void showSurveyButton() {
        startSurveyButton.setVisible(true);
    }

    @Override
    public void hideSurveyButton() {
        startSurveyButton.setVisible(false);
    }


    @Override
    public void onBackPressed(){
        disconnectFromLecture();
    }
/*
    Runnable checkForSurvey = new Runnable() {
        @Override
        public void run() {
            containerPresenter.checkForSurvey();
            checkForSurveyHandler.postDelayed(checkForSurvey, 100000);
        }
    };
    */

    public void findViews(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    public void setUpViews(){
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle(R.string.QuestionsListFragment_title);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(toggle);

        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    private void showAboutAppPage(){
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(R.layout.dialog_about_app)
                .setPositiveButton("Ок", null)
                .show();

        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setTextColor(Color.BLACK);
        positiveButton.setTypeface(Typeface.DEFAULT, Typeface.BOLD);

        TextView title = (TextView) dialog.findViewById(R.id.about_app_title);
        title.setText(getResources().getString(R.string.app_name) + " Beta");
        TextView version = (TextView) dialog.findViewById(R.id.app_version);
        try {
            version.setText(getResources().getString(
                            R.string.app_version,
                            getPackageManager().getPackageInfo(getPackageName(), 0).versionName
                    )
            );
        } catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }

    }
    private void disconnectFromLecture(){
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(R.layout.dialog_disconnect_from_lecture)
                .setPositiveButton("Уверен", null)
                .setNeutralButton("Нет", null)
                .show();

        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setTextColor(Color.BLACK);
        positiveButton.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        Button neutralButton = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);
        neutralButton.setTextColor(Color.BLACK);
        neutralButton.setTypeface(Typeface.DEFAULT, Typeface.BOLD);

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });
    }
}
