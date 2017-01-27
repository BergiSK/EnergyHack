package com.example.ideapad.testapp;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.example.ideapad.testapp.rest.ApiClient;
import com.example.ideapad.testapp.rest.ApiInterface;
import com.example.ideapad.testapp.rest.TimeMapObject;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by IdeaPad on 27.1.2017.
 */

public class MyService extends Service {

    private static final String TAG = "com.example.ideapad.testapp";
    private static final Logger log = Logger.getLogger(MyService.class.getName());

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        log.info("onStartCommand");

        final Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<TimeMapObject> call = apiService.getTimeObject();
                call.enqueue(new Callback<TimeMapObject>() {
                    @Override
                    public void onResponse(Call<TimeMapObject> call, Response<TimeMapObject> response) {
                        TimeMapObject object = response.body();
                        Intent local = new Intent();
                        local.putExtra("time", object.getDate() + ";" + object.getTime() + ";" + object.getMillis());
                        local.setAction(TAG);
                        getApplicationContext().sendBroadcast(local);
                    }

                    @Override
                    public void onFailure(Call<TimeMapObject> call, Throwable t) {
                        log.severe(t.getMessage());
                        log.severe(t.getStackTrace().toString());
                        log.severe(call.toString());
                    }
                });
                handler.postDelayed(this, 20000);
            }
        });

        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        log.info("onDestroy()");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
