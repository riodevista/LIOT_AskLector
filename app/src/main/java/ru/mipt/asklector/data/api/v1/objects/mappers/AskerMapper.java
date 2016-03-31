package ru.mipt.asklector.data.api.v1.objects.mappers;

import ru.mipt.asklector.data.api.v1.objects.pojos.AskerPOJO;
import ru.mipt.asklector.domain.Asker;

/**
 * Created by Dmitry Bochkov on 24.10.2015.
 */
public class AskerMapper implements Mapper{

    public static Asker toAskLectorObject(AskerPOJO askerPOJO){
        return new Asker(
                askerPOJO.getId(),
                askerPOJO.getNickname(),
                askerPOJO.getFirstName(),
                askerPOJO.getMidName(),
                askerPOJO.getLastName()
        );
    }

    public static AskerPOJO toPOJO(Asker asker){
        return new AskerPOJO(
                asker.getId(),
                asker.getUsername(),
                asker.getFirstName(),
                asker.getMidName(),
                asker.getLastName()
        );
    }
}

