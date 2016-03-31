package ru.mipt.asklector.data.api.v1.providers;

import android.util.Log;

import com.squareup.okhttp.Response;

import java.io.IOException;

import ru.mipt.asklector.data.api.v1.requests.PutRequester;
import ru.mipt.asklector.data.api.v1.requests.put.VoteForCommentRequest;
import ru.mipt.asklector.data.db.GetFromDB;
import ru.mipt.asklector.data.db.tables.CommentsDB;
import ru.mipt.asklector.domain.Comment;
import ru.mipt.asklector.usecase.voteForComment.VoteForCommentUseCaseImpl;

/**
 * Created by Dmitry Bochkov on 11.12.2015.
 */
public class VoteForCommentProvider extends AbstractProvider {

    private long commentId;

    public VoteForCommentProvider(long commentId){
        this.commentId = commentId;
    }
    @Override
    public void run() {
        Log.d("VoteForCommentProvider", "run");
        String token = GetFromDB.user().getToken().string();
        Comment comment = GetFromDB.comment(commentId);

        String voteValue;

        //TODO: Fix NullPointerException
        if(comment == null){
            Log.d("VoteForCommentProvider", "Comment is NULL");
            if (onFinishedListener != null)
                onFinishedListener.onFailure(VoteForCommentUseCaseImpl.UNKNOWN_ERROR);
            return;
        }

        //update list before request.
        comment.setVoted(!comment.isVoted());
        comment.setRating(comment.getRating() + ((comment.isVoted()) ? 1 : (-1)));
        new CommentsDB().updateVotedAndRating(
                comment.getId(),
                comment.isVoted(),
                comment.getRating()
        );


        if (comment.isVoted()){
            voteValue = "plus";
        }
        else{
            voteValue = "minus";
        }
        //Log.d("VoteForCommentProvider", "Vote value is :" + voteValue);
        PutRequester putRequester = new PutRequester();
        try {
            Response response = putRequester.run(
                    new VoteForCommentRequest(
                            comment.getId(),
                            token,
                            voteValue
                    )
            );

            Log.d("VoteForCommentProvider", "Response " + String.valueOf(response.code()));
            if(response.code() != 200){
                new CommentsDB().updateVotedAndRating(
                        comment.getId(),
                        !comment.isVoted(),
                        comment.getRating() + ((comment.isVoted()) ? (-1) : 1)
                );

                if (onFinishedListener != null)
                    onFinishedListener.onFailure(VoteForCommentUseCaseImpl.UNKNOWN_ERROR);
                return;
            }

            if (onFinishedListener != null)
                onFinishedListener.onSuccess();
        }
        catch (IOException e){
            new CommentsDB().updateVotedAndRating(
                    comment.getId(),
                    !comment.isVoted(),
                    comment.getRating() + ((comment.isVoted()) ? (-1) : 1)
            );


            Log.d("VoteForCommentProvider", "Exception");
            if (onFinishedListener != null)
                onFinishedListener.onFailure(VoteForCommentUseCaseImpl.CONNECTION_ERROR);
            e.printStackTrace();
        }

    }
}
