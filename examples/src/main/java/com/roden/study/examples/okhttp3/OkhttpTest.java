package com.roden.study.examples.okhttp3;

import okhttp3.*;
import org.junit.Test;

import java.io.IOException;

public class OkhttpTest {
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    @Test
    public void get() throws IOException {
        String response = get("https://t1cl.hlej.com/api/loan/apply");
        System.out.println(response);
    }
    @Test
    public void post() throws IOException {
        String json = "{'name':'test','sign':'3jfdis#2'}";
        String response = post("https://t1cl.hlej.com/api/loan/apply", json);
        System.out.println(response);
    }

    public String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder().url(url).post(body).build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
