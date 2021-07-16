package ru.netology;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Stream;

public class Main {


    public static void main(String[] args) {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();

        HttpGet request = new
                HttpGet("https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats");
        try {
            CloseableHttpResponse response = httpClient.execute(request);
            //Arrays.stream(response.getAllHeaders()).forEach(System.out::println);

            String body = new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);
            //System.out.println(body);

            Gson gson = new Gson();
            String jsonOutput = body;
            Type listType = new TypeToken<List<Cat>>(){}.getType();
            List<Cat> cats = gson.fromJson(jsonOutput, listType);

            Stream<Cat> sorted = cats.stream()
                    .filter(x -> x.getUpvotes() != null && x.getUpvotes() > 0);
            sorted.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
