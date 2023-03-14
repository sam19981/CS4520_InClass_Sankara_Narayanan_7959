package com.example.inclass_sankara_narayanan_002787959.InClass06;

import android.os.Handler;
import android.os.Looper;


import com.example.inclass_sankara_narayanan_002787959.InClass06.DataModels.NetworkResponseListner;
import com.example.inclass_sankara_narayanan_002787959.InClass06.DataModels.NewsCollection;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoadData {

    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private Handler handler = new Handler(Looper.getMainLooper());
    private String country;
    private String category;
    private NetworkResponseListner networkResponseListner;
    private NewsCollection allNews;
    private OkHttpClient client;
    private String baseUrl = "https://newsapi.org/v2/top-headlines";
    final private String categoryKey ="category";
    final private String countryKey ="country";
    final private String apiKeykey = "apiKey";
    final private String apiKey = "2495120fa3aa4e1b8a80e065d4b3e776";
    private HttpUrl httpUrl;

    public LoadData(String Country,String Category,NetworkResponseListner listner) {
        this.country = Country;
        this.category = Category;
        this.networkResponseListner = listner;
    }

    public void execute()
    {
        executor.execute(new Runnable() {
            @Override
            public void run() {

                //Background work here

                Reader response = getNews();

                if (response==null)
                {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //UI Thread work here
                            networkResponseListner.FailedData();
                        }
                    });
                }
                else {
                    Gson gsonParser = new Gson();
                    allNews = gsonParser.fromJson(response,NewsCollection.class);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //UI Thread work here
                            networkResponseListner.SuccessData(allNews.getArticles());
                        }
                    });
                }
            }
        });
    }

    HttpUrl urlBuilder()
    {
        HttpUrl.Builder httpUrlBuilder = HttpUrl.parse(baseUrl).newBuilder();

        if(!category.equals("") && !country.equals(""))
        {
            httpUrlBuilder.addQueryParameter(categoryKey,category).addQueryParameter(countryKey,country);
        }
        else if(!category.equals(""))
        {
            httpUrlBuilder.addQueryParameter(categoryKey,category);
        }
        else if (!country.equals(""))
        {
            httpUrlBuilder.addQueryParameter(countryKey,country);
        }
        else{
            return null;
        }

        httpUrlBuilder.addQueryParameter(apiKeykey,apiKey);
        return httpUrlBuilder.build();
    }


    Reader getNews()
    {
        client = new OkHttpClient();

        httpUrl = urlBuilder();

        if (httpUrl!=null) {
            Request request = new Request.Builder().url(httpUrl).build();

            Response response;

            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (response != null && response.isSuccessful()) {
                try {
                    if (response.body() != null) {
                        return response.body().charStream();
                    } else {
                        return null;
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;

        //.enqueue(new Callback() {
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                System.out.println("here");
////                Toast.makeText(InClass06.this, "Unable to connect get the data!", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//
//                if(response.isSuccessful())
//                {
//                    Gson gsonParser = new Gson();
//                    allNews = gsonParser.fromJson(response.body().charStream(),NewsCollection.class);
//
//                }
//                else {
//
//                }
//            }
//        });


    }

}
