package com.example.supachai_tong.login;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.supachai_tong.login.Modal.Changwork;
import com.example.supachai_tong.login.Modal.assign;
import com.example.supachai_tong.login.Modal.notifi;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AssignActivity extends AppCompatActivity {
    SharedPreferences.Editor editor;
    SharedPreferences shared;
    TextView ed_name, ed_dev, ed_notif_no, ed_s_notifi_func,
            ed_s_notifi_equip, ed_s_notifi_type, ed_noti_type,
            ed_s_notifi_desc, edit_11, txt_9, txt_10, txt_12, txt_13,Txt_group;
    TextInputEditText edit10, edit13;
    TextInputLayout edit_10, edit_13;
    Spinner spinner, spinner_group;
    Button btn_save, btn_dialog_ok, btn_dialog_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_assign);
        shared = getSharedPreferences("userLogin", Context.MODE_PRIVATE);
        editor = shared.edit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ed_name = findViewById(R.id.txt_name_assign);
        ed_dev = findViewById(R.id.ed_2);
        ed_notif_no = findViewById(R.id.ed_3);
        ed_s_notifi_func = findViewById(R.id.ed_4);
        ed_s_notifi_equip = findViewById(R.id.ed_5);
        ed_s_notifi_type = findViewById(R.id.ed_6);
        ed_noti_type = findViewById(R.id.ed_7);
        ed_s_notifi_desc = findViewById(R.id.ed_8);
        edit_11 = findViewById(R.id.edit_11);
        edit10 = findViewById(R.id.edit10);
        edit13 = findViewById(R.id.edit13);
        spinner = findViewById(R.id.spinner_assign);
        spinner_group = findViewById(R.id.spinner_group);
        txt_9 = findViewById(R.id.txt_9);
        txt_10 = findViewById(R.id.txt_10);
        edit_10 = findViewById(R.id.ed_10);
        txt_12 = findViewById(R.id.txt_12);
        txt_13 = findViewById(R.id.txt_13);
        edit_13 = findViewById(R.id.ed_13);
        Txt_group = findViewById(R.id.Txt_group);
        btn_save = findViewById(R.id.btn_save);

        Intent intent = getIntent();
        String no_id = intent.getStringExtra("no_id");
        String stype = intent.getStringExtra("noti_type");
        if (stype.equals("cc")) {
            stype = "ee";
        }
        loadJSON(no_id, stype);
    }

    private void loadJSON(final String no_id, final String stype) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://lionproduction.sli")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Requestlnterface_data request = retrofit.create(Requestlnterface_data.class);
        Call<notifi> call = request.Callnotifi(String.valueOf(no_id), stype);
        call.enqueue(new Callback<notifi>() {
            @Override
            public void onResponse(Call<notifi> call, final Response<notifi> response) {
                Button btn_cancle = findViewById(R.id.btn_cancel);
                btn_cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });

                ed_name.setText(response.body().getDetail_notifi().getS_notifi_rep());
                ed_dev.setText(response.body().getDetail_notifi().getDev_v1());
                ed_notif_no.setText(response.body().getDetail_notifi().getNotif_no());
                String func = response.body().getDetail_notifi().getS_notifi_func();
                String func_dsec = response.body().getDetail_notifi().getS_notifi_func_desc();
                ed_s_notifi_func.setText(func + "  ( " + func_dsec + " )");
                String equip = response.body().getDetail_notifi().getS_notifi_equip();
                String equip_desc = response.body().getDetail_notifi().getS_notifi_equip_desc();
                if (equip == null || equip.equals("")) {
                    ed_s_notifi_equip.setText(" - ");
                } else {
                    ed_s_notifi_equip.setText(equip + " ( " + equip_desc + " )");
                }
                ed_s_notifi_type.setText(response.body().getDetail_notifi().getS_notifi_type());
                if ((response.body().getDetail_notifi().getNoti_type()).equals("mt")) {
                    ed_noti_type.setText("งาน Mechanical / PM");
                    Txt_group.setText("(งาน Mechanical / PM)");
                } else if ((response.body().getDetail_notifi().getNoti_type()).equals("ee")) {
                    ed_noti_type.setText("งาน Electrical / PM");
                    Txt_group.setText(" (งาน Electrical / PM)");
                } else if ((response.body().getDetail_notifi().getNoti_type()).equals("cal")) {
                    ed_noti_type.setText("งาน Calibration / PM");
                    Txt_group.setText(" (งาน Calibration / PM)");
                } else if ((response.body().getDetail_notifi().getNoti_type()).equals("fac")) {
                    ed_noti_type.setText("งาน Civil / facility");
                    Txt_group.setText(" (งาน Civil / facility)");
                } else if ((response.body().getDetail_notifi().getNoti_type()).equals("fk")) {
                    ed_noti_type.setText("งาน Forklift");
                    Txt_group.setText(" (งาน Forklift)");
                } else if ((response.body().getDetail_notifi().getNoti_type()).equals("cc")) {
                    ed_noti_type.setText("งาน CCTV");
                    Txt_group.setText(" (งาน CCTV)");
                } else if ((response.body().getDetail_notifi().getNoti_type()).equals("air")) {
                    ed_noti_type.setText("งาน Air Condition / AMV");
                    Txt_group.setText(" (งาน Air Condition / AMV)");
                } else if ((response.body().getDetail_notifi().getNoti_type()).equals("de")) {
                    ed_noti_type.setText("งาน Develop System");
                    Txt_group.setText(" (งาน Develop System)");
                }
                ed_s_notifi_desc.setText(response.body().getDetail_notifi().getS_notifi_desc());
                edit_11.setText(response.body().getDetail_notifi().getS_notifi_longtext());

                final String[] user_assign = new String[response.body().getList_name_assign().length];
                final String[] uname_assign = new String[response.body().getList_uname_assign().length];
                Integer count_name = 0;
                for (String s : response.body().getList_name_assign()) {
                    Log.d("Ei", "Dev : " + s);
                    user_assign[count_name] = s ;
                    count_name++;
                }
                Integer count_uname = 0;
                for (String s : response.body().getList_uname_assign()) {
                    Log.d("Ei", "Dev : " + s);
                    uname_assign[count_uname] = s;
                    count_uname++;
                }
                final ArrayAdapter<String> adapter_dev = new ArrayAdapter<String>(AssignActivity.this,
                        android.R.layout.simple_dropdown_item_1line, (String[]) user_assign);
                spinner.setAdapter(adapter_dev);
                final String[] noti_type = new String[response.body().getList_noti_assign().length];
                Integer count1 = 0;
                final String[] desc_noti_type = new String[response.body().getList_noti_assign().length];
                Integer count_noti_type = 0;
                for (String s : response.body().getList_noti_assign()) {
                    //Do your stuff here
                    Log.d("Ei", "Dev : " + s);
                    noti_type[count1] = s;
                    count1++;
                }
                for (String s : response.body().getList_noti_assign()) {
                    //Do your stuff here
                    Log.d("Ei", "Dev : " + s);
//                    desc_noti_type[count_noti_type] = s.toUpperCase();
                    if (s.equals("mt")){
                        desc_noti_type[count_noti_type]=("งาน Mechanical / PM");
                    }else if (s.equals("ee")){
                        desc_noti_type[count_noti_type]=("งาน Electrical / PM");
                    }else if (s.equals("cal")){
                        desc_noti_type[count_noti_type]=("งาน Calibration / PM");
                    }else if (s.equals("fac")){
                        desc_noti_type[count_noti_type]=("งาน Civil / facility");
                    }else if (s.equals("fk")){
                        desc_noti_type[count_noti_type]=("งาน Forklift");
                    }else if (s.equals("cc")){
                        desc_noti_type[count_noti_type]=("งาน CCTV");
                    }else if (s.equals("air")){
                        desc_noti_type[count_noti_type]=("งาน Air Condition / AMV");
                    }else if (s.equals("de")){
                        desc_noti_type[count_noti_type]=("งาน Develop System");
                    }
                    count_noti_type++;
                }
                final ArrayAdapter<String> adapter_noti_type = new ArrayAdapter<String>(AssignActivity.this,
                        android.R.layout.simple_dropdown_item_1line, (String[]) desc_noti_type);
                spinner_group.setAdapter(adapter_noti_type);
                Log.w("Test", String.valueOf(adapter_noti_type));
                final RadioGroup radioGroup = findViewById(R.id.radioGroup);
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (checkedId == R.id.assign_radio) {
                            txt_9.setVisibility(View.VISIBLE);
                            txt_10.setVisibility(View.VISIBLE);
                            spinner.setVisibility(View.VISIBLE);
                            edit_10.setVisibility(View.VISIBLE);
                            edit10.setVisibility(View.VISIBLE);
                            txt_12.setVisibility(View.GONE);
                            txt_13.setVisibility(View.GONE);
                            spinner_group.setVisibility(View.GONE);
                            edit_13.setVisibility(View.GONE);
                            edit13.setVisibility(View.GONE);
                        } else if (checkedId == R.id.approve_radio) {
                            txt_9.setVisibility(View.GONE);
                            txt_10.setVisibility(View.GONE);
                            spinner.setVisibility(View.GONE);
                            edit_10.setVisibility(View.GONE);
                            edit10.setVisibility(View.GONE);
                            txt_12.setVisibility(View.VISIBLE);
                            txt_13.setVisibility(View.VISIBLE);
                            spinner_group.setVisibility(View.VISIBLE);
                            edit_13.setVisibility(View.VISIBLE);
                            edit13.setVisibility(View.VISIBLE);
                        }
                    }
                });

                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int radioG = radioGroup.getCheckedRadioButtonId();
                        String radioB = ((RadioButton) findViewById(radioG)).getText().toString();
                        Log.w("Test", String.valueOf(radioB));

                        if (radioB.equals("มอบหมายงาน")) {
                            Integer position = spinner.getSelectedItemPosition();
                            final String uname = uname_assign[position];
                            final String detail = String.valueOf(edit10.getText().toString());
                            final String string_s_name = shared.getString("s_name", null);
                            final String string_uname = shared.getString("uname", null);
                            final String string_name = shared.getString("name", null);
                            final String string_dev_v1 = shared.getString("dev_v1", null);
                            final String string_position = shared.getString("position", null);
                            AlertDialog.Builder builder = new AlertDialog.Builder(AssignActivity.this);
                            builder.setTitle("แจ้งเตือน");
                            builder.setMessage("คุณต้องการมอบหมายงานให้ " + user_assign[position] + " ใช่หรือไม่");
                            builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    loadAssign("assign", no_id, string_uname, string_s_name, string_name, string_dev_v1, string_position, detail,uname, dialog);
                                }
                            });
                            builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    // Do nothing
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }
                        if (radioB.equals("เปลี่ยนกลุ่มงาน")) {
                            Integer position = spinner_group.getSelectedItemPosition();
                            final String notifi_no = response.body().getDetail_notifi().getNotif_no();
                            final String no_id = response.body().getDetail_notifi().getNo_id();
                            final String work_default = (response.body().getDetail_notifi().getNoti_type().equals("MT")||response.body().getDetail_notifi().getNoti_type().equals("mt"))?"ME":(response.body().getDetail_notifi().getNoti_type());
                            if(work_default.equals("mt")||work_default.equals("MT")){
                                work_default.equals("ME");
                            }else {

                            }
                            final String work = (noti_type[position].equals("MT")||noti_type[position].equals("mt"))?"ME":(noti_type[position]);
                            if(work.equals("mt")||work.equals("MT")){
                                work.equals("ME");
                            }
                            Log.w("Test",work_default);
                            Log.w("Test",work);
                            final String dev_v1 = response.body().getDetail_notifi().getDev_v1();
                            final String string_s_name = shared.getString("s_name", null);
                            final String string_uname = shared.getString("uname", null);
                            final String string_name = shared.getString("name", null);
                            final String string_dev_v1 = shared.getString("dev_v1", null);
                            final String string_position = shared.getString("position", null);
                            final String desc = String.valueOf(edit13.getText().toString());

                            if (desc.equals("")) {
                                Toast.makeText(AssignActivity.this, "กรุณากรอกเหตุผล", Toast.LENGTH_SHORT).show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(AssignActivity.this);
                                builder.setTitle("แจ้งเตือน");
                                builder.setMessage("คุณต้องการเปลี่ยนประเภทงานจาก " + work_default.toUpperCase() + " เป็น " + work.toUpperCase() + " ใช่หรือไม่");
                                builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.w("Test", "notifi_no : " + notifi_no + "  no_id : " + no_id + "  work_default : " + work_default + "   work : " + work + "   desc : " + desc + "  dev_v1 : " + dev_v1);
                                        loadChangwork("change-work", "convert", notifi_no, no_id, work_default, work, desc, dev_v1, string_s_name, string_uname, string_name, string_position, dialog);
                                    }
                                });
                                builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        dialog.dismiss();
                                    }
                                });
                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<notifi> call, Throwable t) {
                Log.d("Test", String.valueOf("เชื่อมต่อไม่สำเร็จ"));
            }
        });
    }

    private void loadAssign(String flag, String no_id, String uname, String s_name, String name, String dev_v1, String position, String description,String assign, final DialogInterface dialog) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://lionproduction.sli")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Requestlnterface_data request = retrofit.create(Requestlnterface_data.class);
        Call<assign> call = request.Callassign(flag, no_id, uname, s_name, name, dev_v1, position, description,assign);
        call.enqueue(new Callback<assign>() {
            @Override
            public void onResponse(Call<assign> call, Response<assign> response) {
                if (response.body().isStatus()) {
                    Log.w("Test", "true");
                    Toast.makeText(AssignActivity.this, "บันทึกข้อมูลเรียบร้อย", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    finish();
                } else {
                    Log.w("Test", "false");
                    Log.w("Test", response.body().getMessage());
                    Toast.makeText(AssignActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<assign> call, Throwable t) {
                Log.w("error", t);
                Toast.makeText(AssignActivity.this, "ไม่สามารถโหลดข้อมูลปลายทางได้", Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void loadChangwork(String flag, String step, String notifi_no, String no_id, String work_default, String work, String desc, String dev_v1, String s_name, String uname, String name, String position, final DialogInterface dialog) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://lionproduction.sli")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Requestlnterface_data request = retrofit.create(Requestlnterface_data.class);
        Call<Changwork> call = request.CallChangwork(flag, step, notifi_no, no_id, work_default, work, desc, dev_v1, s_name, uname, name, position);
        call.enqueue(new Callback<Changwork>() {
            @Override
            public void onResponse(Call<Changwork> call, Response<Changwork> response) {
                if (response.body().isStatus()) {
                    Log.w("Test", "true");
                    Toast.makeText(AssignActivity.this, "บันทึกข้อมูลเรียบร้อย", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    finish();
                } else {
                    Log.w("Test", "false");
                    Log.w("Test", response.body().getMessage());
                    Toast.makeText(AssignActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Changwork> call, Throwable t) {
                Log.w("error", t);
                Toast.makeText(AssignActivity.this, "ไม่สามารถโหลดข้อมูลปลายทางได้", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
