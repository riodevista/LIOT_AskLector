package ru.mipt.asklector.usecase.authorization;

import android.content.Intent;

import ru.mipt.asklector.AskLector;
import ru.mipt.asklector.data.services.UserAuthorizationService;
import ru.mipt.asklector.domain.User;
import ru.mipt.asklector.usecase.AbstractUseCase;

/**
 * Created by Dmitry Bochkov on 19.10.2015.
 */
public class BackendAuthorization extends AbstractUseCase implements AuthorizationUseCase {

    public static final int CONFLICT_USERNAME_ERROR = 1001;

    OnFinishedListener onFinishedListener;

    private User user;

    public BackendAuthorization(User user){
        this.user = user;
    }
    
    @Override
    public void run() {
        Intent intent = new Intent(AskLector.provideContext(), UserAuthorizationService.class);
        intent.putExtra(User.OBJECT, user);
        AskLector.provideContext().startService(intent);
    }

    @Override
    public void setOnFinishedListener(final OnFinishedListener onFinishedListener) {
        this.onFinishedListener = onFinishedListener;
        registerReceiver(onFinishedListener);
    }

    @Override
    protected void checkError(int errorCode) {
        switch (errorCode) {
            case CONFLICT_USERNAME_ERROR:
                onFinishedListener.onConflictUsernameError();
                break;
            default:
                break;
        }
    }
}
