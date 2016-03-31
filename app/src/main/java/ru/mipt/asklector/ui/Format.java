package ru.mipt.asklector.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import ru.mipt.asklector.AskLector;
import ru.mipt.asklector.data.db.tables.AskerDB;
import ru.mipt.asklector.domain.Asker;

/**
 * Created by Dmitry Bochkov on 16.12.2015.
 */
public abstract class Format {

    public static String date(String date){
        Calendar dateToFormat = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            dateToFormat.setTime(sdf.parse(date));
        } catch (ParseException e){
            e.printStackTrace();
        }

        return date(dateToFormat);
    }

    public static String date(Calendar dateToFormat){
        Calendar now = Calendar.getInstance();
        int today = now.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM");
        String day = "";
        if (today - 1 == dateToFormat.get(Calendar.DAY_OF_MONTH)){
            day = " вчера";
        } else if (today > dateToFormat.get(Calendar.DAY_OF_MONTH)){
            day = " " + dateFormat.format(dateToFormat.getTime());
        }

        dateFormat = new SimpleDateFormat("HH:mm");
        String time = dateFormat.format(dateToFormat.getTime());

        return day + " в " + time;
    }
}
