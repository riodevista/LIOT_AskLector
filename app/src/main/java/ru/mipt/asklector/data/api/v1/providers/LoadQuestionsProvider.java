package ru.mipt.asklector.data.api.v1.providers;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;

import ru.mipt.asklector.data.api.v1.objects.mappers.QuestionMapper;
import ru.mipt.asklector.data.api.v1.objects.pojos.QuestionPOJO;
import ru.mipt.asklector.data.api.v1.requests.GetRequester;
import ru.mipt.asklector.data.api.v1.requests.get.LoadQuestionsRequest;
import ru.mipt.asklector.data.db.GetFromDB;
import ru.mipt.asklector.data.db.tables.QuestionsDB;
import ru.mipt.asklector.domain.GUID;
import ru.mipt.asklector.domain.Question;
import ru.mipt.asklector.domain.Token;
import ru.mipt.asklector.usecase.loadQuestions.LoadQuestionsUseCaseImpl;
import ru.mipt.asklector.utils.Utils;

/**
 * Created by Dmitry Bochkov on 24.10.2015.
 */
public class LoadQuestionsProvider extends AbstractProvider {

    @Override
    public void run() {

        Log.d("LoadQuestionsProvider", "run");

        Token token = new GetFromDB().user().getToken();
        GUID guid = new GetFromDB().lecture().getGuid();

        GetRequester getRequester = new GetRequester();

        int page = 1;
        ArrayList<Question> questions = new ArrayList<>();
            try {
                while(true) {
                    Response response = getRequester.run(new LoadQuestionsRequest(guid, token, page++));

                    if (response.code() != 200) {
                        Log.d("LoadQuestionsProvider", "Response != 200");
                        if (onFinishedListener != null)
                            onFinishedListener.onFailure(LoadQuestionsUseCaseImpl.UNKNOWN_ERROR);
                        return;
                    }

                    Gson gson = new GsonBuilder().create();
                    String responseJSON = response.body().string();
                    QuestionPOJO[] questionPOJOs = gson.fromJson(responseJSON, QuestionPOJO[].class);

                    for (QuestionPOJO questionPOJO : questionPOJOs) {
                        Question newQuestion = QuestionMapper.toAskLectorObject(questionPOJO);
                        improveQuestion(newQuestion);
                        questions.add(newQuestion);
                    }



                    if (page > Integer.parseInt(response.header("X-Pagination-Page-Count"))) {
                        QuestionsDB questionsDB = new QuestionsDB();
                        questionsDB.insert(questions);
                        Log.d("LoadQuestionsProvider", "Success");
                        if (onFinishedListener != null)
                            onFinishedListener.onSuccess();
                        return;
                    }
                }

            } catch (IOException e) {
                Log.d("LoadQuestionsProvider", "Exception");
                if (onFinishedListener != null)
                    onFinishedListener.onFailure(LoadQuestionsUseCaseImpl.CONNECTION_ERROR);
                e.printStackTrace();
            }


    }

    private void improveQuestion(Question question){
        question.setText(Utils.improveText(question.getText()));
    }
}
