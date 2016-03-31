package ru.mipt.asklector.data.api.v1.objects.mappers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ru.mipt.asklector.data.api.v1.objects.pojos.GroupPOJO;
import ru.mipt.asklector.data.api.v1.objects.pojos.LecturePOJO;
import ru.mipt.asklector.domain.Department;
import ru.mipt.asklector.domain.GUID;
import ru.mipt.asklector.domain.Group;
import ru.mipt.asklector.domain.Lecture;
import ru.mipt.asklector.domain.LectureSettings;
import ru.mipt.asklector.domain.Lecturer;
import ru.mipt.asklector.domain.Subject;
import ru.mipt.asklector.domain.enums.ModerationType;

/**
 * Created by Dmitry Bochkov on 21.10.2015.
 */
public class LectureMapper implements Mapper {

    public static Lecture toAskLectorObject(LecturePOJO lecturePOJO){

        List<Group> groups = new ArrayList<>();
        List<GroupPOJO> pojoGroups = lecturePOJO.getGroups();

        for (GroupPOJO pojoGroup: pojoGroups) {
            groups.add(
                    new Group(
                        new GUID(pojoGroup.getGuid()),
                        pojoGroup.getTitle()
                    )
            );
        }



        // TODO: Боже, это такой костыль, лол прост.
        // TODO: 21.12.2015 Я уже не помню, почему это костыль, но когда-нибудь от него избавлюсь, правда.
        if(groups.isEmpty()){
            groups.add(new Group(new GUID("EMPTY"), "No groups"));
        }

        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            start.setTime(sdf.parse(lecturePOJO.getDateStart()));
            end.setTime(sdf.parse(lecturePOJO.getDateEnd()));
        } catch (ParseException e){
            e.printStackTrace();
        }

        Lecture lecture = new Lecture(
                new GUID(lecturePOJO.getGuid()),
                new Subject(
                        new GUID(lecturePOJO.getSubject().getGuid()),
                        lecturePOJO.getSubject().getTitle(),
                        new Department(
                                new GUID(lecturePOJO.getSubject().getDepartment().getGuid()),
                                lecturePOJO.getSubject().getDepartment().getName(),
                                lecturePOJO.getSubject().getDepartment().getDescription()
                        )
                ),
                new Lecturer(
                        new GUID(lecturePOJO.getLecturer().getGuid()),
                        lecturePOJO.getLecturer().getTitle()
                ),
                lecturePOJO.getLocation(),
                start,
                end,
                groups,
                new LectureSettings(
                        (lecturePOJO.getLectureSettingsPOJO().getModeration() == 1) ?
                                ModerationType.POST : ModerationType.PRE,
                        lecturePOJO.getLectureSettingsPOJO().getComments() == 1 //Таков бэкенд, я не виноват.
                )
        );

        return lecture;
    }
}
