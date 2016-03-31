package ru.mipt.asklector.data.api.v1.objects.mappers;

import ru.mipt.asklector.data.api.v1.objects.pojos.TokenPOJO;

/**
 * Created by Dmitry Bochkov on 20.10.2015.
 */
public class TokenMapper implements Mapper{

    public static TokenPOJO toPOJO(ru.mipt.asklector.domain.Token token) {

        TokenPOJO tokenPojo = new TokenPOJO(
                token.string()
        );
        return tokenPojo;
    }

    public static ru.mipt.asklector.domain.Token toAskLectorObject(TokenPOJO tokenPojo) {

        ru.mipt.asklector.domain.Token token = new ru.mipt.asklector.domain.Token(
                tokenPojo.getToken()
        );
        return token;
    }
}
