package ru.mipt.asklector.data.services;

import android.content.Intent;

import ru.mipt.asklector.data.api.v1.providers.AbstractProvider;
import ru.mipt.asklector.data.api.v1.providers.ReportAsSpamProvider;

/**
 * Created by Dmitry Bochkov on 18.12.2015.
 */
public class ReportAsSpamService extends AbstractService {

    public ReportAsSpamService(){
        super("ReportAsSpamService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        long id = intent.getLongExtra("id", -1);
        AbstractProvider provider = new ReportAsSpamProvider(id);
        setUpProviderAndRun(provider);
    }
}
