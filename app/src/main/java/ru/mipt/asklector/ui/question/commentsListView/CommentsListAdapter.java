package ru.mipt.asklector.ui.question.commentsListView;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import ru.mipt.asklector.AskLector;
import ru.mipt.asklector.R;
import ru.mipt.asklector.data.db.tables.AskerDB;
import ru.mipt.asklector.data.db.tables.CommentsDB;
import ru.mipt.asklector.ui.Format;
import ru.mipt.asklector.usecase.voteForComment.VoteForCommentUseCase;
import ru.mipt.asklector.usecase.voteForComment.VoteForCommentUseCaseImpl;

/**
 * Created by Dmitry Bochkov on 09.12.2015.
 */
public class CommentsListAdapter extends CursorAdapter {

    private LayoutInflater cursorInflater;
    private static Toast toast = null;

    public CommentsListAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        cursorInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    static class ViewHolder{
        TextView commentText;
        TextView commentRating;
        TextView commentDate;
        ImageView rateButton;
        LinearLayout voteArea;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = cursorInflater.inflate(R.layout.comments_list_item, parent, false);

        ViewHolder holder = new ViewHolder();

        TextView commentText = (TextView) view.findViewById(R.id.comment_text);
        TextView commentRating = (TextView) view.findViewById(R.id.comment_rating);
        TextView commentDate = (TextView) view.findViewById(R.id.comment_date);
        ImageView rateButton = (ImageView) view.findViewById(R.id.rate_button);
        LinearLayout voteArea = (LinearLayout) view.findViewById(R.id.vote_area);

        holder.commentText = commentText;
        holder.commentRating = commentRating;
        holder.rateButton = rateButton;
        holder.commentDate = commentDate;
        holder.voteArea = voteArea;

        view.setTag(holder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder == null)
            return;

        /**
         * Get data from cursor.
         */

        final long commentId = cursor.getLong(cursor.getColumnIndex(CommentsDB.COLUMN_COMMENT_ID));
        String text = cursor.getString(cursor.getColumnIndex(CommentsDB.COLUMN_TEXT));
        int rating = cursor.getInt(cursor.getColumnIndex(CommentsDB.COLUMN_RATING));
        String date = cursor.getString(cursor.getColumnIndex(CommentsDB.COLUMN_DATE));
        boolean voted = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(CommentsDB.COLUMN_VOTED)));
        String askerName = cursor.getString(cursor.getColumnIndex(AskerDB.COLUMN_USERNAME));

        /**
         * Set data to row layout
         */

        holder.commentText.setText(text);

        if(AskLector.showRating.get()) {
            holder.commentRating.setText(String.valueOf(rating));
        }


        // TODO: 09.12.2015 Refactor with resourses
        if(askerName.equals(AskLector.getAuthorizedUser().getUsername())){
            holder.commentDate.setText("Вы ответили" + Format.date(date));
        }
        else {
            holder.commentDate.setText(askerName + " ответил" + Format.date(date));
        }

        //Decide which icon should used to.
        if(voted){
            holder.rateButton.setImageResource(R.drawable.heart);
            holder.commentRating.setTextColor(context.getResources().getColor(R.color.liked));
        }
        else{
            holder.rateButton.setImageResource(R.drawable.heart_outline);
            holder.commentRating.setTextColor(context.getResources().getColor(R.color.default_text));
        }


        //Set listener for rate button.
        holder.voteArea.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("CommentListViewAdapter", "Onclick, commentId: " + String.valueOf(commentId));

                    if (!AskLector.isItLectureTime()) {
                        return;
                    }

                    VoteForCommentUseCase voteForCommentUseCase = new VoteForCommentUseCaseImpl(commentId);

                    voteForCommentUseCase.setOnFinishedListener(
                            new VoteForCommentUseCase.OnFinishedListener() {
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
                    voteForCommentUseCase.run();
                }
            }
        );


    }

    public void showUnknownErrorMessage() {
        if (toast != null){
            toast.cancel();
        }
        //toast = Toast.makeText(AskLector.provideContext(), R.string.vote_for_comment_unknown_error, Toast.LENGTH_SHORT);
        //toast.show();

    }

    public void showConnectionErrorMessage() {
        if (toast != null){
            toast.cancel();
        }
        toast = Toast.makeText(AskLector.provideContext(), R.string.connection_error, Toast.LENGTH_SHORT);
        toast.show();
    }

}
