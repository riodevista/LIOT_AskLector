package ru.mipt.asklector.ui.login.activity;

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

import ru.mipt.asklector.R;
import ru.mipt.asklector.ui.lectureConnection.activity.LectureConnectionActivity;
import ru.mipt.asklector.ui.login.LoginPresenter;
import ru.mipt.asklector.ui.login.LoginPresenterImpl;
import ru.mipt.asklector.ui.login.LoginView;

public class LoginActivity extends AppCompatActivity implements LoginView{

    private LoginPresenter loginPresenter;

    private TextView infoLabel;
    private EditText userNameTextField;
    private TextView errorLabel;
    private Button sendNameButton;
    private ProgressBar loader;
    private TextView infoLabel2;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginPresenter = new LoginPresenterImpl(this); //Set presenter for this view.

        findViews();
        setUpViews();
    }

    /**
     * LoginView's methods
     */

    @Override
    public void showTooLongNameErrorMessage() {
        userNameTextField.setError(getResources().getString(R.string.auth_error_too_long_name));
        //errorLabel.setText(R.string.auth_error_too_long_name);
        //errorLabel.setVisibility(View.VISIBLE);
    }

    @Override
    public void showInvalidCharactersErrorMessage() {
        userNameTextField.setError(getResources().getString(R.string.auth_error_invalid_characters));
        //errorLabel.setText(R.string.auth_error_invalid_characters);
        //errorLabel.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideErrorMessage() {
        //errorLabel.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showUsernameConflictErrorMessage() {
        userNameTextField.setError(getResources().getString(R.string.auth_error_conflict_username));
        //Toast.makeText(this, R.string.auth_error_conflict_username, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showConnectionErrorMessage() {
        Toast.makeText(LoginActivity.this, R.string.connection_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUnknownErrorMessage() {
        Toast.makeText(LoginActivity.this, R.string.auth_error_unknown, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void showLoader() {
        hideAllViews();
        loader.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoader() {
        loader.setVisibility(View.INVISIBLE);
        showAllViews();
    }

    @Override
    public void startNextActivity() {
        Intent intent = new Intent(this, LectureConnectionActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public String getEnteredName() {
        return userNameTextField.getText().toString();
    }

    /**
     * End of LoginView's methods
     */

    private void hideAllViews(){
        sendNameButton.setVisibility(View.INVISIBLE);
    }

    private void showAllViews(){
        sendNameButton.setVisibility(View.VISIBLE);
    }


    //sendNameButton listener
    public void login(View view){
        loginPresenter.login();
    }

    private void findViews(){
        infoLabel = (TextView) findViewById(R.id.infoLabel);
        userNameTextField = (EditText) findViewById(R.id.userNameTextField);
        errorLabel = (TextView) findViewById(R.id.errorLabel);
        sendNameButton = (Button) findViewById(R.id.sendNameButton);
        loader = (ProgressBar) findViewById(R.id.loader);
        infoLabel2 = (TextView) findViewById(R.id.infoLabel2);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    private void setUpViews(){

        //Set up toolbar.
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle(R.string.LoginActivity_title);

        //Check userNameTextField text in real-time.
        userNameTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginPresenter.checkName();
            }
        });

        //Set up Enter button to login action.
        userNameTextField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                switch (actionId) {

                    case EditorInfo.IME_ACTION_DONE:
                        loginPresenter.login();
                        return true;

                    default:
                        return false;
                }
            }
        });
    }
}
