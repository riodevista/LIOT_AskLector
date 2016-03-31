package ru.mipt.asklector.data.api.v1.objects.mappers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import ru.mipt.asklector.data.api.v1.objects.pojos.CommentPOJO;
import ru.mipt.asklector.domain.Comment;

/**
 * Created by Dmitry Bochkov on 09.12.2015.
 */
public class CommentMapper implements Mapper {

    public static Comment toAskLectorObject(CommentPOJO commentPOJO){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            calendar.setTime(sdf.parse(commentPOJO.getDate()));
        } catch (ParseException e){
            e.printStackTrace();
        }
        try {
            return new Comment(
                    AskerMapper.toAskLectorObject(commentPOJO.getUser()),
                    calendar,
                    commentPOJO.getId(),
                    commentPOJO.getRating(),
                    commentPOJO.getText(),
                    commentPOJO.getVoted()
            );
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
        return null; //TODO: Проверять в дальнейшем на null.
    }
}
