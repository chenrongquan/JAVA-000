package http.demo;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * @description: 使用OkHttp 访问http://localhost:8801
 * @author: chenrq
 * @date: 2020年10月28日 18时25分
 */
public class OkHttpClientDemo {
    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient();
        String testUrl = "http://localhost:8801";
        Request request = new Request.Builder().url(testUrl).build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            Headers headers = response.headers();
            String value = response.body().string();
            System.out.print(headers);
            System.out.print(value);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (response != null) {
                response.close();
            }
        }

    }
}