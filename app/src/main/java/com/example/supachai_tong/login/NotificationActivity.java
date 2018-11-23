package com.example.supachai_tong.login;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.supachai_tong.login.Modal.AndroidVersion;
import com.example.supachai_tong.login.Modal.Cancel_Changwork;
import com.example.supachai_tong.login.Modal.JSONResponse;
import com.example.supachai_tong.login.Modal.approve;
import com.example.supachai_tong.login.Modal.notification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.supachai_tong.login.App.CHANNEL_ID;

public class NotificationActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<notification> data;
    private DataAdapter_Notifi adapter;
    SharedPreferences.Editor editor;
    SharedPreferences shared;
    private Thread mThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        shared = getSharedPreferences("userLogin", Context.MODE_PRIVATE);
        editor = shared.edit();
        initRecyclerView();
        initViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manu_notification, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_menu) {
            Intent intent = new Intent(this, MainActivity.class);
            finish();
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        finish();
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.card_notification);
        recyclerView.setHasFixedSize(true);
        recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    public void onStop() {
        super.onStop();
        mThread.interrupt();
    }

    public void onPause() {
        super.onPause();
        mThread.interrupt();
    }

    private void initViews() {
        mThread = new Thread() {
            public void run() {
                super.run();
                while (true) {
                    try {
                        String string_s_name = shared.getString("s_name", null);
                        String string_uname = shared.getString("uname", null);
                        String string_name = shared.getString("name", null);
                        Log.w("Test","uname : "+string_uname);
                        loadDataNotification(string_uname);
                        sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        mThread.start();
    }

    private void loadDataNotification(String uname) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://lionproduction.sli")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Requestlnterface_data request = retrofit.create(Requestlnterface_data.class);
        Call<JSONResponse> call = request.Callnotification("getnotification",uname,"1");
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                Log.w("Test", "true");
                JSONResponse jsonResponse = response.body();
                data = new ArrayList<>(Arrays.asList(jsonResponse.getNotification()));
                adapter = new DataAdapter_Notifi(data);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.w("Test", "error Notifi" +t);

            }
        });

}

}
