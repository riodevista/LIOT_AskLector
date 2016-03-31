package ru.mipt.asklector.data.services;

import android.content.Intent;

import ru.mipt.asklector.data.api.v1.providers.AbstractProvider;
import ru.mipt.asklector.data.api.v1.providers.VoteForCommentProvider;

/**
 * Created by Dmitry Bochkov on 11.12.2015.
 */
public class VoteForCommentService extends AbstractService {

    public VoteForCommentService(){
        super("VoteForCommentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        long commentId = intent.getLongExtra("commentId", -1);
        AbstractProvider provider = new VoteForCommentProvider(commentId);
        setUpProviderAndRun(provider);
    }
}
