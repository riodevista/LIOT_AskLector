package ru.mipt.asklector.data.api.v1.providers;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;

import ru.mipt.asklector.data.api.v1.objects.mappers.CommentMapper;
import ru.mipt.asklector.data.api.v1.objects.pojos.CommentPOJO;
import ru.mipt.asklector.data.api.v1.requests.GetRequester;
import ru.mipt.asklector.data.api.v1.requests.get.LoadCommentsRequest;
import ru.mipt.asklector.data.db.GetFromDB;
import ru.mipt.asklector.data.db.tables.CommentsDB;
import ru.mipt.asklector.domain.Comment;
import ru.mipt.asklector.domain.Token;
import ru.mipt.asklector.usecase.AbstractUseCase;
import ru.mipt.asklector.utils.Utils;

/**
 * Created by Dmitry Bochkov on 24.10.2015.
 */
public class LoadCommentsProvider extends AbstractProvider {

    private int questionId;

    public LoadCommentsProvider(int questionId) {
        this.questionId = questionId;
    }

    @Override
    public void run() {
        Log.d("LoadQuestionsProvider", "run");

        Token token = new GetFromDB().user().getToken();

        GetRequester getRequester = new GetRequester();

        int page = 1;
        ArrayList<Comment> comments = new ArrayList<>();
        try {
            while(true) {
                Response response = getRequester.run(new LoadCommentsRequest(questionId, token, page++));

                if (response.code() != 200) {
                    Log.d("LoadCommentsProvider", "Response != 200");
                    if (onFinishedListener != null)
                        onFinishedListener.onFailure(AbstractUseCase.UNKNOWN_ERROR);
                    return;
                }

                Gson gson = new GsonBuilder().create();
                String responseJSON = response.body().string();
                CommentPOJO[] commentPOJOs = gson.fromJson(responseJSON, CommentPOJO[].class);

                for (CommentPOJO commentPOJO : commentPOJOs) {
                    Comment newComment = CommentMapper.toAskLectorObject(commentPOJO);
                    newComment.setQuestionId(questionId);
                    improveComment(newComment);
                    comments.add(newComment);
                }



                if (page > Integer.parseInt(response.header("X-Pagination-Page-Count"))) {
                    new CommentsDB().insert(comments);
                    Log.d("LoadCommentsProvider", "Success");
                    if (onFinishedListener != null)
                        onFinishedListener.onSuccess();
                    return;
                }
            }

        } catch (IOException e) {
            Log.d("LoadCommentsProvider", "Exception");
            if (onFinishedListener != null)
                onFinishedListener.onFailure(AbstractUseCase.CONNECTION_ERROR);
            e.printStackTrace();
        }


    }

    private void improveComment(Comment comment){
        comment.setText(Utils.improveText(comment.getText()));
    }
}
