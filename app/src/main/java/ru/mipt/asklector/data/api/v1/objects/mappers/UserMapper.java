package ru.mipt.asklector.data.api.v1.objects.mappers;

import ru.mipt.asklector.data.api.v1.objects.pojos.BasePOJO;
import ru.mipt.asklector.data.api.v1.objects.pojos.UserPOJO;
import ru.mipt.asklector.domain.AskLectorObject;
import ru.mipt.asklector.domain.User;

/**
 * Created by Dmitry Bochkov on 20.10.2015.
 */
public class UserMapper implements Mapper{


    public static UserPOJO toPOJO(AskLectorObject askLectorObject) {
        User user = (User) askLectorObject;

        UserPOJO userPOJO = new UserPOJO(
                user.getUsername(),
                user.getFirstName(),
                user.getMidName(),
                user.getLastName()
        );
        return userPOJO;
    }

    public static User toAskLectorObject(BasePOJO basePOJO) {
        UserPOJO userPOJO = (UserPOJO) basePOJO;

        User user = new User(
                userPOJO.getNickname(),
                userPOJO.getFirst_name(),
                userPOJO.getMid_name(),
                userPOJO.getLast_name()
        );

        return user;
    }
}
