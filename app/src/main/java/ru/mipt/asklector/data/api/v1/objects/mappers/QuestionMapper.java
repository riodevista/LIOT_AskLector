package ru.mipt.asklector.data.api.v1.objects.mappers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import ru.mipt.asklector.data.api.v1.objects.pojos.QuestionPOJO;
import ru.mipt.asklector.domain.Question;

/**
 * Created by Dmitry Bochkov on 24.10.2015.
 */
public class QuestionMapper implements Mapper{

    public static Question toAskLectorObject(QuestionPOJO questionPOJO){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            calendar.setTime(sdf.parse(questionPOJO.getDate()));
        } catch (ParseException e){
            e.printStackTrace();
        }
        try {
            return new Question(
                    questionPOJO.getId(),
                    questionPOJO.getText(),
                    AskerMapper.toAskLectorObject(questionPOJO.getUser()),
                    questionPOJO.getVoted(),
                    Integer.parseInt(questionPOJO.getRating()),
                    calendar,
                    questionPOJO.getCommentsCount()
            );
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
        return null; //TODO: Проверять в дальнейшем на null.
    }
}
