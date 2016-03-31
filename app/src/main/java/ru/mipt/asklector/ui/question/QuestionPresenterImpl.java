package ru.mipt.asklector.ui.question;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;

import java.text.SimpleDateFormat;

import ru.mipt.asklector.AskLector;
import ru.mipt.asklector.data.db.GetFromDB;
import ru.mipt.asklector.data.db.tables.CommentsDB;
import ru.mipt.asklector.domain.Question;
import ru.mipt.asklector.ui.Format;
import ru.mipt.asklector.usecase.askComment.AskCommentUseCase;
import ru.mipt.asklector.usecase.askComment.AskCommentUseCaseImpl;
import ru.mipt.asklector.usecase.loadComments.LoadCommentsUseCase;
import ru.mipt.asklector.usecase.loadComments.LoadCommentsUseCaseImpl;
import ru.mipt.asklector.usecase.loadQuestions.LoadQuestionsUseCase;
import ru.mipt.asklector.usecase.loadQuestions.LoadQuestionsUseCaseImpl;
import ru.mipt.asklector.usecase.reportAsSpam.ReportAsSpamUseCase;
import ru.mipt.asklector.usecase.reportAsSpam.ReportAsSpamUseCaseImpl;
import ru.mipt.asklector.utils.Utils;

/**
 * Created by Dmitry Bochkov on 08.12.2015.
 */
public class QuestionPresenterImpl implements QuestionPresenter{

    private final static int AUTO_UPDATE_DELAY_IN_MS = 10000;

    private Handler listAutoUpdateHandler = new Handler();

    private QuestionView questionView;
    private Question question;

    public QuestionPresenterImpl(QuestionView questionView, long questionId){
        this.questionView = questionView;
        question = GetFromDB.question(questionId);
    }

    @Override
    public void viewOnPause() {
        stopListAutoUpdate();
    }

    @Override
    public void viewOnResume() {
        questionView.setQuestionText(question.getText());

        String questionAuthor = question.getAsker().getUsername();
        if(questionAuthor.equals(AskLector.getAuthorizedUser().getUsername())){
            questionView.setQuestionDateAndAuthor("Вы спросили" + Format.date(question.getDate()));
        }
        else {
            questionView.setQuestionDateAndAuthor("Кто-то спросил" + Format.date(question.getDate()));
        }

        questionView.setQuestionRating(String.valueOf(question.getRating()));
        startListAutoUpdate();
    }

    @Override
    public void viewOnDestroy() {
        new CommentsDB().clear();
    }

    @Override
    public void loadComments() {
        questionView.showProgress();
        LoadCommentsUseCase loadCommentsUseCase = new LoadCommentsUseCaseImpl(question.getId());
        loadCommentsUseCase.setOnFinishedListener(new LoadCommentsUseCase.OnFinishedListener() {
            @Override
            public void onSuccess() {
                questionView.hideProgress();
            }

            @Override
            public void onConnectionError() {
                questionView.hideProgress();
                questionView.showConnectionErrorMessage();
            }

            @Override
            public void onUnknownError() {
                questionView.hideProgress();
                questionView.showUnknownErrorMessage();
            }
        });
        loadCommentsUseCase.run();
    }

    @Override
    public void reloadComments() {
        LoadCommentsUseCase loadCommentsUseCase = new LoadCommentsUseCaseImpl(question.getId());
        loadCommentsUseCase.setOnFinishedListener(new LoadCommentsUseCase.OnFinishedListener() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onConnectionError() {
                questionView.showConnectionErrorMessage();
            }

            @Override
            public void onUnknownError() {
                questionView.showUnknownErrorMessage();
            }
        });
        loadCommentsUseCase.run();
    }

    @Override
    public void voteForQuestion() {

    }

    @Override
    public void reportAsSpam() {
        ReportAsSpamUseCase reportAsSpamUseCase = new ReportAsSpamUseCaseImpl(question.getId());
        reportAsSpamUseCase.setOnFinishedListener(new ReportAsSpamUseCase.OnFinishedListener() {
            @Override
            public void onSuccess() {
                questionView.showReportAsSpamSuccessMessage();
            }

            @Override
            public void onConnectionError() {
                questionView.showConnectionErrorMessage();
            }

            @Override
            public void onUnknownError() {
                // TODO: 21.12.2015
            }
        });
        reportAsSpamUseCase.run();
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
                        questionView.showConnectionErrorMessage();
                    }

                    @Override
                    public void onUnknownError() {
                        swipeRefreshLayout.setRefreshing(false);
                        questionView.showUnknownErrorMessage();
                    }
                });
                loadQuestionsUseCase.run();
            }
        });
    }

    @Override
    public void sendComment(String commentText) {
        if(!checkComment(commentText))
            return;

        final String goodCommentText = Utils.improveText(commentText);

        AskCommentUseCase askCommentUseCase = new AskCommentUseCaseImpl(goodCommentText, question.getId());
        askCommentUseCase.setOnFinishedListener(
                new AskCommentUseCase.OnFinishedListener() {
                    @Override
                    public void onSuccess() {
                        questionView.setNewCommentPretext("");
                        questionView.showSendCommentSuccessMessage();
                        reloadComments();
                        questionView.hideProgress();
                    }

                    @Override
                    public void onConnectionError() {
                        questionView.setNewCommentPretext(goodCommentText);
                        questionView.showConnectionErrorMessage();
                    }

                    @Override
                    public void onUnknownError() {
                        questionView.setNewCommentPretext(goodCommentText);
                        questionView.showSendCommentUnknownErrorMessage();
                    }
                }
        );
        askCommentUseCase.run();
    }

    public boolean checkComment(String text){
        if (isCommentTextEmpty(text)){
            questionView.showEmptyCommentErrorMessage();
            return false;
        }

        return true;
    }

    private boolean isCommentTextEmpty(String text){
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
            new LoadCommentsUseCaseImpl(question.getId()).run();
            listAutoUpdateHandler.postDelayed(listAutoUpdate, AUTO_UPDATE_DELAY_IN_MS);
        }
    };
}
