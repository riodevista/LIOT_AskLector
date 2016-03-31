package ru.mipt.asklector.data.api.v1.providers;

import android.util.Log;

import com.squareup.okhttp.Response;

import java.io.IOException;

import ru.mipt.asklector.data.api.v1.requests.PutRequester;
import ru.mipt.asklector.data.api.v1.requests.put.VoteForQuestionRequest;
import ru.mipt.asklector.data.db.GetFromDB;
import ru.mipt.asklector.data.db.tables.QuestionsDB;
import ru.mipt.asklector.domain.Question;
import ru.mipt.asklector.usecase.voteForQuestion.VoteForQuestionUseCaseImpl;

/**
 * Created by Dmitry Bochkov on 28.10.2015.
 */
public class VoteForQuestionProvider extends AbstractProvider {

    private long questionId;

    public  VoteForQuestionProvider(long questionId){
        this.questionId = questionId;
    }

    @Override
    public void run() {
        Log.d("VoteForQuestionProvider", "run");
        String token = GetFromDB.user().getToken().string();
        Question question = GetFromDB.question(questionId);

        String voteValue;

        //TODO: Fix NullPointerException
        if(question == null){
            Log.d("VoteForQuestionProvider", "Question is NULL");
            if (onFinishedListener != null)
                onFinishedListener.onFailure(VoteForQuestionUseCaseImpl.UNKNOWN_ERROR);
            return;
        }

        //update list before request.
        question.setVoted(!question.isVoted());
        question.setRating(question.getRating() + ((question.isVoted()) ? 1 : (-1)));
        new QuestionsDB().updateVotedAndRating(
                question.getId(),
                question.isVoted(),
                question.getRating()
        );


        if (question.isVoted()){
            voteValue = "plus";
        }
        else{
            voteValue = "minus";
        }
        //Log.d("VoteForQuestionProvider", "Vote value is :" + voteValue);
        PutRequester putRequester = new PutRequester();
        try {
            Response response = putRequester.run(
                    new VoteForQuestionRequest(
                            question.getId(),
                            token,
                            voteValue
                            )
            );

            Log.d("Asklector", String.valueOf(response.code()));
            if(response.code() != 200){
                new QuestionsDB().updateVotedAndRating(
                        question.getId(),
                        !question.isVoted(),
                        question.getRating() + ((question.isVoted()) ? (-1) : 1)
                );

                Log.d("VoteForQuestionProvider", "Response != 200");
                if (onFinishedListener != null)
                    onFinishedListener.onFailure(VoteForQuestionUseCaseImpl.UNKNOWN_ERROR);
                return;
            }


            Log.d("VoteForQuestionProvider", "Response 200");
            if (onFinishedListener != null)
                onFinishedListener.onSuccess();
        }
        catch (IOException e){
            new QuestionsDB().updateVotedAndRating(
                    question.getId(),
                    !question.isVoted(),
                    question.getRating() + ((question.isVoted()) ? (-1) : 1)
            );


            Log.d("VoteForQuestionProvider", "Exception");
            if (onFinishedListener != null)
                onFinishedListener.onFailure(VoteForQuestionUseCaseImpl.CONNECTION_ERROR);
            e.printStackTrace();
        }
    }
}
