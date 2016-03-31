package ru.mipt.asklector.ui.questionsList;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;

import ru.mipt.asklector.AskLector;
import ru.mipt.asklector.data.db.GetFromDB;
import ru.mipt.asklector.usecase.askQuestion.AskQuestionUseCase;
import ru.mipt.asklector.usecase.askQuestion.AskQuestionUseCaseImpl;
import ru.mipt.asklector.usecase.loadQuestions.LoadQuestionsUseCase;
import ru.mipt.asklector.usecase.loadQuestions.LoadQuestionsUseCaseImpl;
import ru.mipt.asklector.utils.Utils;

/**
 * Created by Dmitry Bochkov on 15.10.2015.
 */

public class QuestionsListPresenterImpl implements QuestionsListPresenter{

    private final static int AUTO_UPDATE_DELAY_IN_MS = 10000;

    private Handler listAutoUpdateHandler = new Handler();

    private QuestionsListView questionsListView;

    public QuestionsListPresenterImpl(QuestionsListView questionsListView){
        this.questionsListView = questionsListView;
    }

    @Override
    public void viewOnPause() {
        stopListAutoUpdate();
    }

    @Override
    public void viewOnResume() {
        startListAutoUpdate();
    }

    @Override
    public void loadQuestions() {
        questionsListView.showProgress();

        LoadQuestionsUseCase loadQuestionsUseCase = new LoadQuestionsUseCaseImpl();
        loadQuestionsUseCase.setOnFinishedListener(new LoadQuestionsUseCase.OnFinishedListener() {
            @Override
            public void onSuccess() {
                questionsListView.hideProgress();
            }

            @Override
            public void onConnectionError() {
                questionsListView.hideProgress();
                questionsListView.showConnectionErrorMessage();

            }

            @Override
            public void onUnknownError() {
                questionsListView.hideProgress();
                questionsListView.showUnknownErrorMessage();

            }
        });
        loadQuestionsUseCase.run();
    }

    @Override
    public void reloadQuestions() {
        LoadQuestionsUseCase loadQuestionsUseCase = new LoadQuestionsUseCaseImpl();
        loadQuestionsUseCase.setOnFinishedListener(new LoadQuestionsUseCase.OnFinishedListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onConnectionError() {
                questionsListView.showConnectionErrorMessage();
            }

            @Override
            public void onUnknownError() {
                questionsListView.showUnknownErrorMessage();
            }
        });
        loadQuestionsUseCase.run();
    }

    @Override
    public void setSwipeOnRefreshListener(final SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                LoadQuestionsUseCase loadQuestionsUseCase = new LoadQuestionsUseCaseImpl();
                loadQuestionsUseCase.setOnFinishedListener(new LoadQuestionsUseCase.OnFinishedListener() {
                    @Override
                    public void onSuccess() {
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onConnectionError() {
                        swipeRefreshLayout.setRefreshing(false);
                        questionsListView.showConnectionErrorMessage();
                    }

                    @Override
                    public void onUnknownError() {
                        swipeRefreshLayout.setRefreshing(false);
                        questionsListView.showUnknownErrorMessage();
                    }
                });
                loadQuestionsUseCase.run();
            }
        });
    }

    @Override
    public void showFullQuestion(long questionId) {
        if (AskLector.getConnectedLecture().getSettings().areCommentsAvailable()){
            questionsListView.showFullQuestionWithComments(questionId);
        } else {
            try {
                String questionText = GetFromDB.question(questionId).getText();
                questionsListView.showFullQuestionWithoutComments(questionText);
            } catch(NullPointerException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void askQuestion(final String questionText) {
        final String goodQuestionText = Utils.improveText(questionText);

        AskQuestionUseCase askQuestionUseCase = new AskQuestionUseCaseImpl(goodQuestionText);
        askQuestionUseCase.setOnFinishedListener(
                new AskQuestionUseCase.OnFinishedListener() {
                    @Override
                    public void onSuccess() {
                        questionsListView.setNewQuestionPretext("");
                        questionsListView.showAskQuestionSuccessMessage();
                        reloadQuestions();
                    }

                    @Override
                    public void onConnectionError() {
                        questionsListView.setNewQuestionPretext(goodQuestionText);
                        questionsListView.showConnectionErrorMessage();
                    }

                    @Override
                    public void onUnknownError() {
                        questionsListView.setNewQuestionPretext(goodQuestionText);
                        questionsListView.showAskQuestionUnknownErrorMessage();
                    }
                }
        );
        askQuestionUseCase.run();
    }

    @Override
    public boolean checkQuestion(String text){
        if (isQuestionTextEmpty(text)){
            questionsListView.showEmptyQuestionErrorMessage();
            return false;
        }

        return true;
    }

    private boolean isQuestionTextEmpty(String text){
        if (text.trim().length() == 0 )
            return true;
        return false;
    }

    private void startListAutoUpdate(){
        listAutoUpdateHandler.postDelayed(listAutoUpdate, AUTO_UPDATE_DELAY_IN_MS);
    }

    private void stopListAutoUpdate(){
        listAutoUpdateHandler.removeCallbacks(listAutoUpdate);
    }

    private Runnable listAutoUpdate = new Runnable() {
        @Override
        public void run() {
            new LoadQuestionsUseCaseImpl().run();
            listAutoUpdateHandler.postDelayed(listAutoUpdate, AUTO_UPDATE_DELAY_IN_MS);
        }
    };

}
