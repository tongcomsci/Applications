package com.example.supachai_tong.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.supachai_tong.login.Modal.alert;
import com.example.supachai_tong.login.Modal.model_notification;
import com.example.supachai_tong.login.Modal.notifications;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotificationActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<model_notification> data;
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
            load_alert();
            Intent intent = new Intent(this, MainActivity.class);
            finish();
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        load_alert();
        finish();
    }


    private void load_alert(){
        String string_uname = shared.getString("uname", null);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://lionproduction.sli")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Requestlnterface_data request = retrofit.create(Requestlnterface_data.class);
        Call<alert> call = request.Callalert("getupdate_alert",string_uname);
        call.enqueue(new Callback<alert>() {
            @Override
            public void onResponse(Call<alert> call, Response<alert> response) {
                Log.w("Test", "true");
            }
            @Override
            public void onFailure(Call<alert> call, Throwable t) {
                Log.w("Test", "error " +t);
            }
        });

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
        load_alert();
    }

    public void onPause() {
        super.onPause();
        mThread.interrupt();
        load_alert();
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
        Call<notifications> call = request.Callnotification("getnotification",uname,"1");
        call.enqueue(new Callback<notifications>() {
            @Override
            public void onResponse(Call<notifications> call, Response<notifications> response) {
                Log.w("Test", "true");
                notifications jsonResponse = response.body();
                data = new ArrayList<>(Arrays.asList(jsonResponse.getNotification()));
                adapter = new DataAdapter_Notifi(data);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<notifications> call, Throwable t) {
                Log.w("Test", "error Notifi" +t);

            }
        });

}

}
