package ru.mipt.asklector.usecase.reportAsSpam;

import android.content.Intent;
import android.util.Log;

import ru.mipt.asklector.AskLector;
import ru.mipt.asklector.data.services.ReportAsSpamService;
import ru.mipt.asklector.usecase.AbstractUseCase;

/**
 * Created by Dmitry Bochkov on 18.12.2015.
 */
public class ReportAsSpamUseCaseImpl extends AbstractUseCase implements ReportAsSpamUseCase {

    private long id;

    public ReportAsSpamUseCaseImpl(long id){
        this.id = id;
    }

    @Override
    public void run(){
        Log.d("ReportAsSpamUseCase", "run");
        Intent intent = new Intent(AskLector.provideContext(), ReportAsSpamService.class);
        intent.putExtra("id", id);
        AskLector.provideContext().startService(intent);

    }

    @Override
    public void setOnFinishedListener(final OnFinishedListener onFinishedListener) {
        registerReceiver(onFinishedListener);
    }

    @Override
    protected void checkError(int errorCode) {

    }
}
