package com.example.inclass_sankara_narayanan_002787959.InClass04;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class HeavyWork implements Runnable{
    public final static int STATUS_START = 0x001;
    public final static int STATUS_PROGRESS = 0x002;
    public final static int STATUS_END = 0x003;
    public final static String KEY_PROGRESS = "0x004";

    public final static String GENERATED_VALUE = "0x005";

    private Handler messageQueue;

    private int complexcity;

    public HeavyWork(Handler messageQueue,int complexcity){
        this.messageQueue = messageQueue;
        this.complexcity =complexcity;
    }
    static final int COUNT = 9000000;
    void getArrayNumbers(int n){
        Message startMessage = new Message();
        startMessage.what = STATUS_START;
        messageQueue.sendMessage(startMessage);

        for (int i=0; i<n; i++){
            Message progressMessage = new Message();
            progressMessage.what = STATUS_PROGRESS;
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_PROGRESS,(100/n)*(i+1));
            bundle.putDouble(GENERATED_VALUE,getNumber());
            progressMessage.setData(bundle);
            messageQueue.sendMessage(progressMessage);
        }
        Message endMessage = new Message();
        endMessage.what = STATUS_END;
        messageQueue.sendMessage(endMessage);

    }

    static double getNumber(){
        double num = 0;
        Random ran = new Random();
        for(int i=0;i<COUNT; i++){
            num = num + (Math.random()*ran.nextDouble()*100+ran.nextInt(50))*1000;
        }
        return num / ((double) COUNT);
    }

    @Override
    public void run() {
        getArrayNumbers(complexcity);
    }

}