package ru.mipt.asklector.data.api.v1.requests;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

import ru.mipt.asklector.data.api.v1.requests.post.PostRequest;

/**
 * Created by Dmitry Bochkov on 19.10.2015.
 */
public class PostRequester {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private OkHttpClient client = new OkHttpClient();

    //public PostRequester(PostRequest postRequest)

    public Response post(PostRequest postRequest) throws IOException {
        RequestBody body = RequestBody.create(JSON, postRequest.getJSON());
        Request request = new Request.Builder()
                .url(postRequest.getURL())
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }
}
