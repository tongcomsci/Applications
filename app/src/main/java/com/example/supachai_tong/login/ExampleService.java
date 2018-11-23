package com.example.supachai_tong.login;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.supachai_tong.login.Modal.notification;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.supachai_tong.login.App.CHANNEL_ID;

public class ExampleService extends Service {
    private Thread mThread;
    private DataAdapter_Notifi adapter;
    SharedPreferences.Editor editor;
    SharedPreferences shared;

    @Override
    public void onCreate() {
        super.onCreate();
        shared = getSharedPreferences("userLogin", Context.MODE_PRIVATE);
        editor = shared.edit();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        final String input = intent.getStringExtra("inputExtra");

        Intent notificationIntent = new Intent(getApplicationContext(), NotificationActivity.class);
        final PendingIntent pendingIntent = PendingIntent.getActivities(getApplicationContext(),
                0, new Intent[]{notificationIntent}, 0);
        mThread = new Thread() {
            public void run() {
                super.run();
                while (true) {
                    try {
                        String string_uname = shared.getString("uname", null);

//                        Log.w("Test", "ashgdashd");
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://lionproduction.sli")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        Requestlnterface_data request = retrofit.create(Requestlnterface_data.class);
                        Call<notification> call = request.Callnotificationservice("getnotification",string_uname,"1");
                        call.enqueue(new Callback<notification>() {
                            @Override
                            public void onResponse(Call<notification> call, Response<notification> response) {
                                if (response.isSuccess()) {
                                    notification row_item = response.body();

//                                    Log.w("Test", "size = 1");
                                    Notification notification_load = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                                            .setContentTitle("Example Service")
                                            .setContentText("มีใบแจ้งซ่อมใหม่")
                                            .setSmallIcon(R.drawable.ic_android)
                                            .setContentIntent(pendingIntent)
                                            .build();

                                    startForeground(1, notification_load);
////                                stopSelf();

                                }
                            }

                            @Override
                            public void onFailure(Call<notification> call, Throwable t) {
                                Log.w("Test", "error Service");

                            }
                        });
                        sleep(60000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        mThread.start();


        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
