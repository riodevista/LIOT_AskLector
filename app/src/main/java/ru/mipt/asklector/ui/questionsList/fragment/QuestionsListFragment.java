package ru.mipt.asklector.ui.questionsList.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.widget.Space;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
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
import ru.mipt.asklector.data.db.tables.QuestionsDB;
import ru.mipt.asklector.ui.question.activity.QuestionActivity;
import ru.mipt.asklector.ui.questionsList.QuestionsListPresenter;
import ru.mipt.asklector.ui.questionsList.QuestionsListPresenterImpl;
import ru.mipt.asklector.ui.questionsList.QuestionsListView;
import ru.mipt.asklector.ui.questionsList.questionsListView.QuestionsListAdapter;
import ru.mipt.asklector.ui.questionsList.questionsListView.QuestionsListLoader;


public class QuestionsListFragment extends Fragment implements QuestionsListView, View.OnClickListener {

    public static final String TAG = "QuestionsListFragment";
    public final static int LOADER_ID = 123;

    private OnFragmentInteractionListener mListener;

    private QuestionsListPresenter questionsListPresenter;

    private ProgressBar progressBar;
    private ListView questionsListView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FloatingActionButton askQuestionButton;
    private AlertDialog newQuestionADialog;


    private String newQuestionText = "";

    public static QuestionsListFragment newInstance() {
        return new QuestionsListFragment();
    }

    public QuestionsListFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRetainInstance(true);
        questionsListPresenter = new QuestionsListPresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_questions_list, container, false);

        findViews(view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpViews();
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onPause(){
        super.onPause();
        questionsListPresenter.viewOnPause();
    }

    @Override
    public void onResume(){
        super.onResume();
        questionsListPresenter.viewOnResume();
    }


    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.createNewQuestionButton:
                createNewQuestion();
                break;
            default:
                break;

        }
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


    private void showFullQuestionTextDialog(String questionText){
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setView(R.layout.dialog_show_question)
                .setPositiveButton(Html.fromHtml("<b>Закрыть</b>"), null)
                .show();
        TextView questionTextView = (TextView) alertDialog.findViewById(R.id.question_text);
        questionTextView.setText(questionText);
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTypeface(Typeface.DEFAULT, Typeface.BOLD);
    }

    private void startQuestionActivity(long questionId){
        Intent intent = new Intent(getActivity(), QuestionActivity.class);
        intent.putExtra("questionId", questionId);
        startActivity(intent);
    }


    @Override
    public void showUnknownErrorMessage() {
        Toast.makeText(getActivity(), R.string.load_questions_unknown_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAskQuestionSuccessMessage() {
        Toast.makeText(getActivity(), R.string.ask_question_success, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAskQuestionUnknownErrorMessage() {
        Toast.makeText(getActivity(), R.string.ask_question_unknown_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showEmptyQuestionErrorMessage() {
        EditText questionInput = (EditText) newQuestionADialog.findViewById(R.id.question_input);
        questionInput.setError(getResources().getString(R.string.empty_question_error));
    }

    @Override
    public void showConnectionErrorMessage() {
        Toast.makeText(getActivity(), R.string.connection_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showFullQuestionWithoutComments(String questionText) {
        showFullQuestionTextDialog(questionText);
    }

    @Override
    public void showFullQuestionWithComments(long questionId) {
        startQuestionActivity(questionId);
    }

    @Override
    public void setNewQuestionPretext(String text) {
        newQuestionText = text;
    }


    private void createNewQuestion(){
        if(!AskLector.isItLectureTime()){
            return;
        }

        // TODO: 15.12.2015 Наследовать свой диалог

        newQuestionADialog = new AlertDialog.Builder(getActivity())
                .setView(R.layout.dialog_new_question)
                .setTitle(Html.fromHtml("<b>Задать вопрос</b>"))
                .setPositiveButton(Html.fromHtml("<b>Спросить</b>"), null)
                .setNeutralButton(Html.fromHtml("<b>Передумал</b>"), null)
                .create();

        newQuestionADialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        newQuestionADialog.show();

        final EditText questionInput = (EditText) newQuestionADialog.findViewById(R.id.question_input);
        Button positiveButton = newQuestionADialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button neutralButton = newQuestionADialog.getButton(AlertDialog.BUTTON_NEUTRAL);
        positiveButton.setTextColor(Color.BLACK);
        positiveButton.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        neutralButton.setTextColor(Color.BLACK);
        neutralButton.setTypeface(Typeface.DEFAULT, Typeface.BOLD);

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable input = questionInput.getText();
                if (input != null && questionsListPresenter.checkQuestion(input.toString())) {
                    questionsListPresenter.askQuestion(input.toString());
                    newQuestionADialog.dismiss();
                }
            }
        });

        neutralButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newQuestionText = "";
                try {
                    questionInput.setText(newQuestionText);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                newQuestionADialog.dismiss();
            }
        });

        newQuestionADialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                newQuestionText = questionInput.getText().toString();
            }
        });

        questionInput.setText(newQuestionText);

    }


    private void findViews(View view){
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        questionsListView = (ListView) view.findViewById(R.id.questions_list_view);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        askQuestionButton = (FloatingActionButton) view.findViewById(R.id.createNewQuestionButton);
    }

    private void setUpViews(){

        //Add empty footer to ListView.
        Space space = new Space(questionsListView.getContext());
        space.setMinimumHeight((int) (116 * Resources.getSystem().getDisplayMetrics().density));
        questionsListView.addFooterView(space);

        initCursorLoader();

        // Say Presenter to load questions.
        questionsListPresenter.loadQuestions();

        //Set up click listener for listview's row.
        questionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = ((CursorAdapter) ((HeaderViewListAdapter) questionsListView.getAdapter()).getWrappedAdapter()).getCursor();
                //TODO: Fix this one:
                try {
                    cursor.moveToPosition(position);
                    long questionId = cursor.getLong(cursor.getColumnIndex(QuestionsDB.COLUMN_QUESTION_ID));
                    questionsListPresenter.showFullQuestion(questionId);
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        });

        questionsListPresenter.setSwipeOnRefreshListener(swipeRefreshLayout);

        askQuestionButton.setOnClickListener(this);

    }

    private void initCursorLoader(){
        //Set CursorAdapter if needed
        if(questionsListView.getAdapter() == null){
            questionsListView.setAdapter(
                    new QuestionsListAdapter(
                            getActivity(),
                            null,
                            CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
                    )
            );
        }

        //Empty loader will be notified from Provider when questions loaded.
        getLoaderManager().initLoader(
                LOADER_ID,
                new Bundle(),
                new LoaderManager.LoaderCallbacks<Cursor>() {
                    @Override
                    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
                        return new QuestionsListLoader(getActivity());
                    }

                    @Override
                    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
                        ((QuestionsListAdapter) ((HeaderViewListAdapter) questionsListView.
                                getAdapter()).getWrappedAdapter()).swapCursor(data);
                        Log.d("CursorLoader", "onLoadFinished");
                    }

                    @Override
                    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
                        questionsListView.setAdapter(null);
                    }
                }
        );
    }
}
