package ru.mipt.asklector.ui.lectureConnection.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import ru.mipt.asklector.AskLector;
import ru.mipt.asklector.R;
import ru.mipt.asklector.ui.container.activity.ContainerActivity;
import ru.mipt.asklector.ui.lectureConnection.LectureConnection;
import ru.mipt.asklector.ui.lectureConnection.LectureConnectionImpl;
import ru.mipt.asklector.ui.lectureConnection.LectureConnectionView;

public class LectureConnectionActivity extends AppCompatActivity implements LectureConnectionView{

    private LectureConnection lectureConnectionPresenter;

    private TextView infoLabel;
    private EditText lectureIdTextField;
    private TextView errorLabel;
    private Button connectToLectureButton;
    private ProgressBar loader;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_connection);

        lectureConnectionPresenter = new LectureConnectionImpl(this);

        findViews();
        setUpViews();
    }

    @Override
    public void onStart(){
        super.onStart();
        lectureConnectionPresenter.findLecture();
    }


    /**
     * LectureConnection's methods
     */

    @Override
    public void showLectureIdTooShortErrorMessage() {
        errorLabel.setText(R.string.error_too_short_lecture_id);
        errorLabel.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideErrorMessage() {
        errorLabel.setVisibility(View.INVISIBLE);
    }

    @Override
    public void startNextActivity() {
        Intent intent = new Intent(LectureConnectionActivity.this, ContainerActivity.class);
        startActivity(intent);
    }

    @Override
    public String getEnteredLectureId() {
        return lectureIdTextField.getText().toString();
    }

    @Override
    public void showFoundLectureMessage(String lectureInfo) {
        errorLabel.setText(lectureInfo);
        errorLabel.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLectureNotFoundErrorMessage() {
        errorLabel.setText(R.string.lecture_not_found);
        errorLabel.setVisibility(View.VISIBLE);
    }

    @Override
    public void showConnectionErrorMessage() {
        errorLabel.setText(R.string.connection_error);
        errorLabel.setVisibility(View.VISIBLE);
    }

    @Override
    public void showUnknownErrorMessage() {
        Toast.makeText(this, R.string.lecture_connection_error_unknown, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoader() {
        errorLabel.setVisibility(View.INVISIBLE);
        loader.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoader() {
        loader.setVisibility(View.INVISIBLE);
        errorLabel.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideConnectButton() {
        connectToLectureButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showConnectButton() {
        connectToLectureButton.setVisibility(View.VISIBLE);

        lectureIdTextField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                switch (actionId) {
                    case EditorInfo.IME_ACTION_DONE:
                        lectureConnectionPresenter.connectToLectureWithId();
                        return true;

                    default:
                        return false;
                }
            }
        });

    }

    /**
     * End of LectureConnection's methods
     */


    //ConnectToLectureWithIdButton listener
    public void connectToLectureWithId(View view){
        lectureConnectionPresenter.connectToLectureWithId();
    }

    private void findViews(){
        infoLabel = (TextView) findViewById(R.id.infoLabel);
        lectureIdTextField = (EditText) findViewById(R.id.lectureIdTextField);
        errorLabel = (TextView) findViewById(R.id.errorLabel);
        connectToLectureButton = (Button) findViewById(R.id.connectToLectureWithIdButton);
        loader = (ProgressBar) findViewById(R.id.loader);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    private void setUpViews(){

        //Set up toolbar.
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle(R.string.LectureConnectionActivity_title);


        //Set up infoLabel with username.
        infoLabel.setText(getResources().getString(
                R.string.enter_lecture_id,
                AskLector.getAuthorizedUser().getUsername()
        ));

        //Check lecture ID in real-time.
        lectureIdTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                lectureConnectionPresenter.findLecture();
            }
        });

        hideConnectButton();
    }
}
