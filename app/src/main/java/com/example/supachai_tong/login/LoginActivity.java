package com.example.supachai_tong.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.supachai_tong.login.Modal.login;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private EditText ed_user, ed_pass;
    private Button btn_login;
    private ProgressBar pgsBar_login;
    SharedPreferences.Editor editor;
    SharedPreferences shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        shared = getSharedPreferences("userLogin", Context.MODE_PRIVATE);
        // Save SharedPreferences
        editor = shared.edit();
    }

    private void saveLogin() {
        editor.putString("username", ed_user.getText().toString());
        editor.putString("password", ed_pass.getText().toString());
        editor.putBoolean("booleanKey", true);
        editor.commit();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event)
    {
        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
        {
            Log.w("Test","Enter");
        }
        return super.dispatchKeyEvent(event);
    }


    @Override
    protected void onStop() {
        super.onStop();
        editor.putString("username", ed_user.getText().toString());
        editor.putString("password", ed_pass.getText().toString());
        editor.putBoolean("booleanKey", true);
        editor.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //ผูกตัวแปร
        ed_user = findViewById(R.id.ed_user);
        ed_pass = findViewById(R.id.ed_pass);
        btn_login = findViewById(R.id.btn_login);
        pgsBar_login = findViewById(R.id.pBar_login);

        /*SET shared-preferences*/
        String stringValue_user = shared.getString("username", null);
        String stringValue_pass = shared.getString("password", null);
        ed_user.setText(stringValue_user);
        ed_pass.setText(stringValue_pass);
        ed_user.setSelection(ed_user.getText().length());
        ed_pass.setSelection(ed_pass.getText().length());
        /*END shared-preferences*/
        CallLogin(ed_user.getText().toString(), ed_pass.getText().toString());
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pgsBar_login.setVisibility(View.VISIBLE);
                CallLogin(ed_user.getText().toString(), ed_pass.getText().toString());//การ get ค่า user/pass
            }
        });
    }

    boolean CallLogin(String user, String pass) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://lionproduction.sli")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<login> call = request.CallLogin(user, pass);//เรียกและส่งค่า
        call.enqueue(new Callback<login>() {//ตอบกลับ
            @Override
            public void onResponse(Call<login> call, Response<login> response) {
                Log.d("Test", String.valueOf(response.body().isStatus()));

                if (response.body().isStatus()) {
                    String s_name = new String(response.body().getS_name());
                    String uname = new String(response.body().getUname());
                    String name = new String(response.body().getName());
                    String dev_v1 = new String(response.body().getDev_v1());
                    String position = new String(response.body().getPosition());
                    String img = new String(response.body().getImg());

                    editor.putString("s_name", s_name.toString());
                    editor.putString("uname", uname.toString());
                    editor.putString("name", name.toString());
                    editor.putString("dev_v1", dev_v1.toString());
                    editor.putString("position", position.toString());
                    editor.putString("img", img.toString());
                    editor.commit();

                    String[] dev = new String[response.body().getList_dev().length + 1];
                    dev[0] = "ALL";
                    String[] noti = new String[response.body().getList_noti_type().length + 1];
                    noti[0] = "ALL";
                    String[] s_notifi_type = new String[response.body().getList_s_notifi_type().length + 1];
                    s_notifi_type[0] = "ALL";
                    String[] appr_nextcode = new String[response.body().getList_appr_nextcode().length + 1];
                    appr_nextcode[0] = "ALL";

                    Integer count = 1;
                    Integer count_noti = 1;
                    Integer count_notifi = 1;
                    Integer count_appr_nextcode = 1;

                    for (String s : response.body().getList_dev()) {
                        dev[count] = s;
                        count++;
                    }
                    for (String s : response.body().getList_noti_type()) {
                        noti[count_noti] = s;
                        count_noti++;
                    }
                    for (String s : response.body().getList_s_notifi_type()) {
                        s_notifi_type[count_notifi] = s;
                        count_notifi++;
                    }
                    for (String s : response.body().getList_appr_nextcode()) {
                        appr_nextcode[count_appr_nextcode] = s;
                        count_appr_nextcode++;
                    }


                    StringBuilder dev_s = new StringBuilder();
                    for (int i = 0; i < dev.length; i++) {
                        dev_s.append(dev[i]).append(",");
                    }
                    editor.putString("DEV_V1", dev_s.toString());

                    StringBuilder noti_s = new StringBuilder();
                    for (int i = 0; i < noti.length; i++) {
                        noti_s.append(noti[i]).append(",");
                    }
                    editor.putString("NOTI_TYPE", noti_s.toString());

                    StringBuilder s_notifi_type_s = new StringBuilder();
                    for (int i = 0; i < s_notifi_type.length; i++) {
                        s_notifi_type_s.append(s_notifi_type[i]).append(",");
                    }
                    editor.putString("S_NOTIFI", s_notifi_type_s.toString());

                    StringBuilder appr_nextcode_s = new StringBuilder();
                    for (int i = 0; i < appr_nextcode.length; i++) {
                        appr_nextcode_s.append(appr_nextcode[i]).append(",");
                    }
                    editor.putString("APPR_NEXTCODE", appr_nextcode_s.toString());
                    editor.commit();

                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    intent.putExtra("DEV_V1", dev);
                    intent.putExtra("NOTI_TYPE", noti);
                    intent.putExtra("S_NOTIFI", s_notifi_type);
                    intent.putExtra("APPR_NEXTCODE", appr_nextcode);
                    saveLogin();
                    startActivity(intent);
                    pgsBar_login.setVisibility(View.INVISIBLE);

                } else {
                    pgsBar_login.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "กรุณาตรวจสอบ ชื่อผู้ใช้และรหัสผ่าน อีกครั้ง", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<login> call, Throwable t) {

            }
        });
        return true;
    }

}

