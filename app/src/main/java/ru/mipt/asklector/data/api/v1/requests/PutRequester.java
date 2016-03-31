package ru.mipt.asklector.data.api.v1.requests;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

import ru.mipt.asklector.data.api.v1.requests.put.PutRequest;

/**
 * Created by Dmitry Bochkov on 28.10.2015.
 */
public class PutRequester {

    OkHttpClient client = new OkHttpClient();

    public Response run(PutRequest putRequest) throws IOException {
        Request request = new Request.Builder()
                .url(putRequest.getURL())
                .put(RequestBody.create(MediaType.parse("application/json"), ""))
                .build();

        Response response = client.newCall(request).
                execute();
        return response;
    }
}
