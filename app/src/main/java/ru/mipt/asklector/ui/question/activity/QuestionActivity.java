package ru.mipt.asklector.ui.question.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import ru.mipt.asklector.AskLector;
import ru.mipt.asklector.R;
import ru.mipt.asklector.ui.question.QuestionPresenter;
import ru.mipt.asklector.ui.question.QuestionPresenterImpl;
import ru.mipt.asklector.ui.question.QuestionView;
import ru.mipt.asklector.ui.question.commentsListView.CommentsListAdapter;
import ru.mipt.asklector.ui.question.commentsListView.CommentsListLoader;

/**
 * Created by Dmitry Bochkov on 08.12.2015.
 */
public class QuestionActivity extends AppCompatActivity implements QuestionView {

    public static final int LOADER_ID = 234;
    private static final long DEFAULT_QUESTION_ID = 0;

    private QuestionPresenter questionPresenter;

    private Toolbar toolbar;
    private TextView questionText;
    private TextView questionRating;
    private TextView questionDateAndAuthor;
    private EditText commentInput;
    private Button sendCommentButton;
    private ListView commentsList;
    private ProgressBar progress;
    private View questionHeader;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MenuItem complainButton;

    private String newCommentText = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        long questionId = getIntent().getLongExtra("questionId", DEFAULT_QUESTION_ID);
        questionPresenter = new QuestionPresenterImpl(this, questionId);

        findViews();
        setUpViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        questionPresenter.viewOnDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.question_activity, menu);
        complainButton = menu.getItem(R.id.action_complain);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.action_complain:
                questionPresenter.reportAsSpam();
                break;
            case android.R.id.home:
                finish();
                break;
            default:
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume(){
        super.onResume();
        questionPresenter.viewOnResume();
    }

    @Override
    public void onPause(){
        super.onPause();
        questionPresenter.viewOnPause();
    }

    private void findViews() {
        questionHeader = View.inflate(this, R.layout.comments_list_header, null);
        questionText = (TextView) questionHeader.findViewById(R.id.question_text);
        //questionRating = (TextView) findViewById(R.id.question_rating);
        questionDateAndAuthor = (TextView) questionHeader.findViewById(R.id.question_date_and_author);
        commentInput = (EditText) findViewById(R.id.comment_input);
        sendCommentButton = (Button) findViewById(R.id.send_comment_button);
        commentsList = (ListView) findViewById(R.id.comments_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        progress = (ProgressBar) findViewById(R.id.progress);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
    }

    private void setUpViews() {
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle(R.string.question_activity_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (!AskLector.allowComplain.get())
            complainButton.setVisible(false);

        addHeaderToListView();
        initCursorLoader();

        questionPresenter.loadComments();

        questionPresenter.setSwipeOnRefreshListener(swipeRefreshLayout);

        sendCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionPresenter.sendComment(commentInput.getText().toString());
            }
        });

    }



    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void setQuestionText(String text) {
        questionText.setText(text);
    }

    @Override
    public void setQuestionDateAndAuthor(String text) {
        questionDateAndAuthor.setText(text);
    }

    @Override
    public void setQuestionRating(String text) {
        //questionRating.setText(text);
    }

    @Override
    public void showUnknownErrorMessage() {
        Toast.makeText(this, R.string.comments_unknown_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showConnectionErrorMessage() {
        Toast.makeText(this, R.string.connection_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setNewCommentPretext(String text) {
        newCommentText = text;
        commentInput.setText(text);
    }

    @Override
    public void showSendCommentUnknownErrorMessage() {
        Toast.makeText(this, R.string.send_comment_unknown_error, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showSendCommentSuccessMessage() {
        Toast.makeText(this, R.string.send_comment_success, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showEmptyCommentErrorMessage() {
        commentInput.setError(getResources().getString(R.string.send_comment_empty_error));
    }

    @Override
    public void showReportAsSpamSuccessMessage() {
        Toast.makeText(this, R.string.report_question_as_spam_success, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideKeyboard() {
        // TODO: 11.12.2015
    }

    private void addHeaderToListView(){
        commentsList.addHeaderView(questionHeader);
    }

    private void initCursorLoader(){
        if(commentsList.getAdapter() == null){
            commentsList.setAdapter(
                    new CommentsListAdapter(
                            this,
                            null,
                            CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
                    )
            );
        }

        //Empty loader will be notified from Provider when questions loaded.
        getSupportLoaderManager().initLoader(
                LOADER_ID,
                new Bundle(),
                new LoaderManager.LoaderCallbacks<Cursor>() {
                    @Override
                    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
                        return new CommentsListLoader(QuestionActivity.this);
                    }

                    @Override
                    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
                        ((CommentsListAdapter) ((HeaderViewListAdapter) commentsList.
                                getAdapter()).getWrappedAdapter()).swapCursor(data);
                        Log.d("CommentsCursorLoader", "onLoadFinished");
                    }

                    @Override
                    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
                        commentsList.setAdapter(null);
                    }
                }
        );
    }
}
