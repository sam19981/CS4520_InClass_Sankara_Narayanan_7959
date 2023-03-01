package com.example.inclass_sankara_narayanan_002787959.InClass05;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.inclass_sankara_narayanan_002787959.R;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class InClass05 extends AppCompatActivity {

    private String setTitle = "Image Search";

    private String keyWord = "";

    private String key = "keyword";

    private String baseUrl = "http://ec2-54-164-201-39.compute-1.amazonaws.com/apis/images/retrieve";
    private String imgArray[];
    private int size = -1;

    private ImageButton next;

    private ImageButton previous;

    private Handler myHandler;

    private EditText searchBox;
    private ProgressBar spiralProgress;

    private ImageView displayImage;

    HttpUrl httpUrl;

    public static boolean internetActive = true;

    private OkHttpClient client = new OkHttpClient();

    private Button go;

    private int curIndx = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class05);
        setTitle(setTitle);

        myHandler = new Handler(Looper.getMainLooper());

        go = findViewById(R.id.go_btn);

        displayImage = findViewById(R.id.dispImg);

        searchBox = findViewById(R.id.search_box);

        next = findViewById(R.id.nextBtn);
        next.setEnabled(false);

        previous = findViewById(R.id.prevBtn);
        previous.setEnabled(false);

        spiralProgress = findViewById(R.id.spiralProgressBar);

        spiralProgress.setVisibility(View.INVISIBLE);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!internetConnectionAvailable(1000)) {
                    Toast.makeText(InClass05.this, "No Internet Connection!!", Toast.LENGTH_LONG).show();
                } else {
                    String keyWord = String.valueOf(searchBox.getText());
                    next.setEnabled(false);
                    previous.setEnabled(false);
                    if (keyWord.equals("")) {
                        Toast.makeText(InClass05.this, "No Keyword Entered!", Toast.LENGTH_SHORT).show();
                        displayImage.setImageResource(0);
                    } else {
                        setImage(keyWord);
                    }
                }
            }

        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curIndx++;
                if (curIndx > size) {
                    curIndx = (curIndx % size) - 1;
                }
                spiralProgress.setVisibility(View.VISIBLE);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(InClass05.this).load(imgArray[curIndx]).listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                Toast.makeText(InClass05.this, "Reourse could not be loaded for keyword " + keyWord, Toast.LENGTH_SHORT).show();
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                spiralProgress.setVisibility(View.INVISIBLE);
                                return false;
                            }
                        }).into(displayImage);
                    }
                });
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curIndx--;
                if (curIndx < 0) {
                    curIndx = (curIndx + size) + 1;
                }
                spiralProgress.setVisibility(View.VISIBLE);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(InClass05.this).load(imgArray[curIndx]).listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                Toast.makeText(InClass05.this, "Reourse could not be loaded for keyword " + keyWord, Toast.LENGTH_SHORT).show();
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                spiralProgress.setVisibility(View.INVISIBLE);
                                return false;
                            }
                        }).into(displayImage);
                    }
                });
            }
        });


    }

    void setImage(String keyWord) {
        httpUrl = HttpUrl.parse(baseUrl).newBuilder().addQueryParameter(key, keyWord).build();

        Request request = new Request.Builder().url(httpUrl).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    String body = responseBody.string();
                    if (!(body.equals(""))) {
                        imgArray = body.split("\n");
                        curIndx = 0;
                        size = imgArray.length - 1;
                    } else {
                        size = -1;
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (size == -1) {
                                Toast.makeText(InClass05.this, "No Images Found", Toast.LENGTH_SHORT).show();
                                displayImage.setImageResource(0);
                            } else {
                                Glide.with(InClass05.this).load(imgArray[curIndx]).listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        Toast.makeText(InClass05.this, "Resource could not be loaded for keyword " + keyWord, Toast.LENGTH_SHORT).show();
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        if (size > 1) {
                                            next.setEnabled(true);
                                            previous.setEnabled(true);
                                        }
                                        return false;
                                    }
                                }).into(displayImage);
                            }
                        }
                    });
                } else {
                    ResponseBody responseBody = response.body();
                    String body = responseBody.string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(InClass05.this, body, Toast.LENGTH_SHORT).show();
                            displayImage.setImageResource(0);
                        }
                    });
                }
            }
        });

    }


    private boolean internetConnectionAvailable(int timeOut) {
        InetAddress inetAddress = null;
        try {
            Future<InetAddress> future = Executors.newSingleThreadExecutor().submit(new Callable<InetAddress>() {
                @Override
                public InetAddress call() {
                    try {
                        return InetAddress.getByName("google.com");
                    } catch (UnknownHostException e) {
                        return null;
                    }
                }
            });
            inetAddress = future.get(timeOut, TimeUnit.MILLISECONDS);
            future.cancel(true);
        } catch (InterruptedException e) {
        } catch (ExecutionException e) {
        } catch (TimeoutException e) {
        }
        return inetAddress!=null && !inetAddress.equals("");
    }


}