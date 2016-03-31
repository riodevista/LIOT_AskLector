package ru.mipt.asklector.ui.questionsList.questionsListView;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import ru.mipt.asklector.AskLector;
import ru.mipt.asklector.R;
import ru.mipt.asklector.data.db.tables.AskerDB;
import ru.mipt.asklector.data.db.tables.QuestionsDB;
import ru.mipt.asklector.ui.Format;
import ru.mipt.asklector.usecase.reportAsSpam.ReportAsSpamUseCase;
import ru.mipt.asklector.usecase.reportAsSpam.ReportAsSpamUseCaseImpl;
import ru.mipt.asklector.usecase.voteForQuestion.VoteForQuestionUseCase;
import ru.mipt.asklector.usecase.voteForQuestion.VoteForQuestionUseCaseImpl;

/**
 * Created by Dmitry Bochkov on 17.10.2015.
 */

/**
 * This class is custom adapter for questions list in QuestionsListFragment.
 */

public class QuestionsListAdapter extends CursorAdapter{

    private LayoutInflater cursorInflater;
    private static Toast toast = null;

    public QuestionsListAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        cursorInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    static class ViewHolder{
        TextView questionText;
        TextView questionRating;
        TextView questionDate;
        ImageView rateButton;
        LinearLayout voteArea;
        ImageView popupMenu;
        TextView commentsCount;
        ImageView commentsButton;
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = cursorInflater.inflate(R.layout.questions_list_item, parent, false);

        ViewHolder holder = new ViewHolder();

        TextView questionText = (TextView) view.findViewById(R.id.question_text);
        TextView questionRating = (TextView) view.findViewById(R.id.question_rating);
        TextView questionDate = (TextView) view.findViewById(R.id.question_date);
        ImageView rateButton = (ImageView) view.findViewById(R.id.rate_button);
        LinearLayout voteArea = (LinearLayout) view.findViewById(R.id.vote_area);
        ImageView popupMenu = (ImageView) view.findViewById(R.id.popup_menu);
        TextView commentsCount = (TextView) view.findViewById(R.id.question_comments_count);
        ImageView commentsButton = (ImageView) view.findViewById(R.id.comments_button);

        holder.questionText = questionText;
        holder.questionRating = questionRating;
        holder.rateButton = rateButton;
        holder.questionDate = questionDate;
        holder.popupMenu = popupMenu;
        holder.commentsCount = commentsCount;
        holder.voteArea = voteArea;
        holder.commentsButton = commentsButton;

        view.setTag(holder);

        return view;
    }


    @Override
    public void bindView(final View view, final Context context, final Cursor cursor) {

        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder == null)
            return;

        /**
         * Get data from cursor.
         */

        final long questionId = cursor.getLong(cursor.getColumnIndex(QuestionsDB.COLUMN_QUESTION_ID));
        String text = cursor.getString(cursor.getColumnIndex(QuestionsDB.COLUMN_TEXT));
        int rating = cursor.getInt(cursor.getColumnIndex(QuestionsDB.COLUMN_RATING));
        String date = cursor.getString(cursor.getColumnIndex(QuestionsDB.COLUMN_DATE));
        boolean voted = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(QuestionsDB.COLUMN_VOTED)));
        int commentsCount = cursor.getInt(cursor.getColumnIndex(QuestionsDB.COLUMN_COMMENTS_COUNT));

        /**
         * Prepare data
         */

        // Cut too long question.
        if(text.length() > 120)
            text = text.substring(0, 120) + "…";


        /**
         * Set data to row layout
         */

        holder.questionText.setText(text);
        holder.commentsCount.setText(String.valueOf(commentsCount));

        if(AskLector.showRating.get()) {
            holder.questionRating.setText(String.valueOf(rating));
        }


        // TODO: 09.12.2015 Refactor with resourses
        String askerName = cursor.getString(cursor.getColumnIndex(AskerDB.COLUMN_USERNAME));
        if(askerName.equals(AskLector.getAuthorizedUser().getUsername())){
            holder.questionDate.setText("Вы спросили" + Format.date(date));
        } else {
            holder.questionDate.setText("Кто-то спросил" + Format.date(date));
        }

        //Decide which icon should used to.
        if (voted){
            holder.rateButton.setImageResource(R.drawable.heart);
            holder.questionRating.setTextColor(context.getResources().getColor(R.color.liked));
        } else{
            holder.rateButton.setImageResource(R.drawable.heart_outline);
            holder.questionRating.setTextColor(context.getResources().getColor(R.color.default_text));
        }

        //Check if comments are available for this Lecture.
        if (!AskLector.getConnectedLecture().getSettings().areCommentsAvailable()){
            holder.commentsButton.setVisibility(View.GONE);
            holder.commentsCount.setVisibility(View.GONE);
        } else {
            if (commentsCount > 0) {
                holder.commentsButton.setImageResource(R.drawable.ic_comment);
            } else {
                holder.commentsButton.setImageResource(R.drawable.ic_comment_outline);
            }
        }



        //Set listener for rate button.
        holder.voteArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("QuestionListViewAdapter", "Onclick, questionId: " + String.valueOf(questionId));

                if (!AskLector.isItLectureTime()) {
                    return;
                }

                VoteForQuestionUseCase voteForQuestionUseCase = new VoteForQuestionUseCaseImpl(questionId);

                voteForQuestionUseCase.setOnFinishedListener(
                        new VoteForQuestionUseCase.OnFinishedListener() {
                            @Override
                            public void onSuccess() {
                            }

                            @Override
                            public void onConnectionError() {
                                showConnectionErrorMessage();
                            }

                            @Override
                            public void onUnknownError() {
                                //showUnknownErrorMessage();
                            }
                        }
                );
                voteForQuestionUseCase.run();
            }
        });

        if (!AskLector.allowComplain.get()){
            holder.popupMenu.setVisibility(View.GONE);
        } else{
            holder.popupMenu.setVisibility(View.VISIBLE);

            holder.popupMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(context, v);
                    popupMenu.inflate(R.menu.questions_list_item_popup);
                    popupMenu.show();
                    // TODO: 21.12.2015 How to use menu item with id not position?
                    popupMenu.getMenu().getItem(0).setOnMenuItemClickListener(
                            new MenuItem.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {
                                    ReportAsSpamUseCase reportAsSpamUseCase =
                                            new ReportAsSpamUseCaseImpl(questionId);
                                    reportAsSpamUseCase.setOnFinishedListener(
                                            new ReportAsSpamUseCase.OnFinishedListener() {
                                                @Override
                                                public void onSuccess() {
                                                    showReportAsSpamSuccess();
                                                }

                                                @Override
                                                public void onConnectionError() {
                                                    showConnectionErrorMessage();
                                                }

                                                @Override
                                                public void onUnknownError() {
                                                    // TODO: 21.12.2015
                                                }
                                            }
                                    );
                                    reportAsSpamUseCase.run();

                                    return false;
                                }
                            }
                    );
                }
            });
        }

    }

    public void showUnknownErrorMessage() {
        if (toast != null){
            toast.cancel();
        }
        toast = Toast.makeText(AskLector.provideContext(), R.string.vote_for_question_unknown_error, Toast.LENGTH_SHORT);
        toast.show();

    }

    public void showConnectionErrorMessage() {
        if (toast != null){
            toast.cancel();
        }
        toast = Toast.makeText(AskLector.provideContext(), R.string.connection_error, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void showReportAsSpamSuccess() {
        if (toast != null){
            toast.cancel();
        }
        toast = Toast.makeText(AskLector.provideContext(), R.string.report_question_as_spam_success, Toast.LENGTH_SHORT);
        toast.show();
    }
}
