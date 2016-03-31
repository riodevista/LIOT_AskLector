package ru.mipt.asklector.data.api.v1.requests;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import ru.mipt.asklector.data.api.v1.requests.get.GetRequest;

/**
 * Created by Dmitry Bochkov on 19.10.2015.
 */
public class GetRequester {

    OkHttpClient client = new OkHttpClient();

    public Response run(GetRequest getRequest) throws IOException {
        Request request = new Request.Builder()
                .url(getRequest.getURL())
                .build();

        Response response = client.newCall(request).execute();
        return response;
    }
}
