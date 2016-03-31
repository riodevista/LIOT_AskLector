package ru.mipt.asklector.ui.login;

import ru.mipt.asklector.domain.User;
import ru.mipt.asklector.usecase.authorization.AuthorizationUseCase;
import ru.mipt.asklector.usecase.authorization.BackendAuthorization;

/**
 * Created by Dmitry Bochkov on 10.10.2015.
 */

public class LoginPresenterImpl implements LoginPresenter{

    private LoginView loginView;

    public LoginPresenterImpl(LoginView loginView){
        this.loginView = loginView;
    }


    @Override
    public boolean checkName() {
        String name = loginView.getEnteredName();

        if(name.length() == 0){
            loginView.hideErrorMessage();
            return false;
        }

        if(!name.matches("^[a-zA-Z0-9_.-]+$")){
            loginView.showInvalidCharactersErrorMessage();
            return false;
        }

        if (name.length() > 15){
            loginView.showTooLongNameErrorMessage();
            return false;
        }

        loginView.hideErrorMessage();
        return true;
    }

    @Override
    public void login() {

        if(checkName()) {
            loginView.showLoader();

            User newUser = new User(loginView.getEnteredName(), "", "", "");

            //Set authorization via own backend API.
            AuthorizationUseCase authorizationUseCase = new BackendAuthorization(newUser);

            authorizationUseCase.setOnFinishedListener(
                    new AuthorizationUseCase.OnFinishedListener() {
                        @Override
                        public void onSuccess() {
                            loginView.startNextActivity();
                        }

                        @Override
                        public void onConnectionError() {
                            loginView.showConnectionErrorMessage();
                            loginView.hideLoader();
                        }

                        @Override
                        public void onConflictUsernameError() {
                            loginView.showUsernameConflictErrorMessage();
                            loginView.hideLoader();
                        }

                        @Override
                        public void onUnknownError() {
                            loginView.showUnknownErrorMessage();
                            loginView.hideLoader();
                        }
                    }
            );
            authorizationUseCase.run();

        }
    }

}
