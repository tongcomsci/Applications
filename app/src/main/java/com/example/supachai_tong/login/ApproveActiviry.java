package com.example.supachai_tong.login;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.supachai_tong.login.Modal.Detail_desc;
import com.example.supachai_tong.login.Modal.Detail_notifi_app;
import com.example.supachai_tong.login.Modal.Detail_work_cntr;
import com.example.supachai_tong.login.Modal.approve;
import com.example.supachai_tong.login.Modal.approve_true;
import com.example.supachai_tong.login.Modal.approve_false;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApproveActiviry extends AppCompatActivity {
    private TextView txt_notif_id, txt_order_id, txt_type_order,
            txt_name, txt_app_detail, txt_name_create, txt_dev,
            txt_noti_type, txt_s_notifi_func, txt_s_notifi_equip,
            txt_notifi_date, txt_appr_tel;
    private TextView txt_input_desc;
    private Button btn_cancle_approve, btn_approve;
    private RecyclerView recyclerView;
    private ArrayList<Detail_work_cntr> data;
    private DataAdapter_approve adapter;
    Calendar mCurrentDate;
    String date;
    SharedPreferences.Editor editor;
    SharedPreferences shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve);
        txt_notif_id = findViewById(R.id.txt_notif_id);
        txt_order_id = findViewById(R.id.txt_order_id);
        txt_type_order = findViewById(R.id.txt_type_order);
        txt_name = findViewById(R.id.txt_name);
        txt_app_detail = findViewById(R.id.txt_app_detail);
        txt_name_create = findViewById(R.id.txt_name_create);
        txt_dev = findViewById(R.id.txt_dev);
        txt_noti_type = findViewById(R.id.txt_noti_type);
        txt_s_notifi_func = findViewById(R.id.txt_s_notifi_func);
        txt_s_notifi_equip = findViewById(R.id.txt_s_notifi_equip);
        txt_notifi_date = findViewById(R.id.txt_notifi_date);
        txt_appr_tel = findViewById(R.id.txt_appr_tel);
        txt_input_desc = findViewById(R.id.txt_input_desc);
        btn_approve = findViewById(R.id.btn_approve);
        shared = getSharedPreferences("userLogin", Context.MODE_PRIVATE);
        editor = shared.edit();
        btn_cancle_approve = findViewById(R.id.btn_cancel_approve);
        btn_cancle_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Intent intent = getIntent();
        String no_id = intent.getStringExtra("no_id");
        String s_notifi_orderid = intent.getStringExtra("s_notifi_orderid");
        initViews();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.card_recycler_approve);
        recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        Intent intent = getIntent();
        String no_id = intent.getStringExtra("no_id");
        String s_notifi_orderid = intent.getStringExtra("s_notifi_orderid");
        loadApprove(no_id, s_notifi_orderid);
    }

    private void loadApprove(final String no_id, final String s_notifi_orderid) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://lionproduction.sli")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Requestlnterface_data request = retrofit.create(Requestlnterface_data.class);
        Call<approve> call = request.Callapprove(no_id, s_notifi_orderid);
        call.enqueue(new Callback<approve>() {
            @Override
            public void onResponse(Call<approve> call, final Response<approve> response) {

                approve jsonResponse = response.body();
                data = new ArrayList<>(Arrays.asList(jsonResponse.getDetail_work_cntr()));
                adapter = new DataAdapter_approve(data);
                recyclerView.setAdapter(adapter);

                for (Detail_notifi_app s : response.body().getDetail_notifi_app()) {
                    txt_notif_id.setText(s.getNotif_no());
                    txt_order_id.setText(s.getS_notifi_orderid());
                    if (s.getAppr_tag().getORDER_TYPE().equals("ZBMO")) {
                        txt_type_order.setText("ZBMO   Breakdown Maintenance (CCTR)");
                    } else if (s.getAppr_tag().getORDER_TYPE().equals("ZCMO")) {
                        txt_type_order.setText("ZCMO   Corrective Maintenance (CCTR)");
                    } else if (s.getAppr_tag().getORDER_TYPE().equals("ZCMA")) {
                        txt_type_order.setText("ZCMA   Corrective Maintenance (Asset)");
                    } else if (s.getAppr_tag().getORDER_TYPE().equals("ZMDO")) {
                        txt_type_order.setText("ZMDO   Modification (CCTR)");
                    } else if (s.getAppr_tag().getORDER_TYPE().equals("ZPMO")) {
                        txt_type_order.setText("ZPMO   Preventive Maintenance (CCTR)");
                    } else if (s.getAppr_tag().getORDER_TYPE().equals("ZMDA")) {
                        txt_type_order.setText("ZMDA  Modification(Asset)");
                    } else {
                        txt_type_order.setText(" - ");
                    }
                    txt_name.setText(s.getS_notifi_desc());
                    txt_app_detail.setText(s.getS_notifi_longtext());
                    txt_name_create.setText(s.getS_notifi_rep());
                    txt_dev.setText(s.getDev_v1());
                    Log.w("Test", s.getNoti_type());
                    txt_noti_type.setText(s.getNoti_type().equals("mt") ? "ME" : (s.getNoti_type().toUpperCase()));
                    txt_s_notifi_func.setText(s.getS_notifi_func());
                    if (s.getS_notifi_equip() == null || s.getS_notifi_equip().equals("")) {
                        txt_s_notifi_equip.setText(" - ");
                    } else {
                        txt_s_notifi_equip.setText(s.getS_notifi_equip());
                    }
                    String Date = (s.getS_notifi_date());
                    if (Date == null) {
                        txt_notifi_date.setText(" -");
                    } else {
                        String year = Date.substring(0, 4);
                        String month = Date.substring(5, 7);
                        String day = Date.substring(8, 10);
                        txt_notifi_date.setText(day + "-" + month + "-" + year + " ");
                    }
                    txt_appr_tel.setText(s.getAppr_tel());
                }
                String desc = "";
                for (Detail_desc s : response.body().getDetail_desc()) {
                    desc += s.getDESC() + "\n";
                }
                txt_input_desc.setText(desc);
                final RadioGroup radioGroupapprove = findViewById(R.id.radioGroupapprove);

                btn_approve.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int radioG = radioGroupapprove.getCheckedRadioButtonId();
                        String radioB = ((RadioButton) findViewById(radioG)).getText().toString();
                        Log.w("Test", String.valueOf(radioB));
                        final String string_s_name = shared.getString("s_name", null);
                        final String string_uname = shared.getString("uname", null);
                        final String string_name = shared.getString("name", null);
                        final String string_dev_v1 = shared.getString("dev_v1", null);
                        final String string_position = shared.getString("position", null);
                        if (radioB.equals("เรียบร้อย")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ApproveActiviry.this);
                            builder.setTitle("แจ้งเตือน");
                            builder.setMessage("คุณต้องการ Approve Order นี้ใช่หรือไม่ (กรุณาตรวจสอบวันเวลาแต่ละ Operation ให้เรียบร้อย)");
                            builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    Log.w("Test","approve-true"+ no_id+ string_uname+ string_s_name+ string_name+ string_dev_v1+ string_position+dialog);
                                    Approve_true("approve-true", no_id, string_uname, string_s_name, string_name, string_dev_v1, string_position, dialog);
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
                        if (radioB.equals("ไม่เรียบร้อย")) {
                            final Dialog dialog = new Dialog(ApproveActiviry.this);
                            dialog.setContentView(R.layout.dialog_detail);
                            dialog.setCancelable(true);
                            final EditText text_app = dialog.findViewById(R.id.txt_change_desc);
                            TextView txt_title_desc = dialog.findViewById(R.id.txt_title_desc);
                            txt_title_desc.setText("กรุณาระบุเหตุผลที่งานไม่เรียบร้อย");
                            Button button_cancle = dialog.findViewById(R.id.can);
                            Button button_ok = dialog.findViewById(R.id.ok);
                            button_cancle.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.cancel();
                                }
                            });
                            button_ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final String desc_app = String.valueOf(text_app.getText().toString());
//                                    Log.w("Test", "Desc" + desc_app);
                                    String string_s_name = shared.getString("s_name", null);
                                    String string_uname = shared.getString("uname", null);
                                    String string_name = shared.getString("name", null);
                                    String string_dev_v1 = shared.getString("dev_v1", null);
                                    String string_position = shared.getString("position", null);

                                    if (desc_app.equals("")) {
                                        Toast.makeText(ApproveActiviry.this, "กรุณากรอกเหตุผล", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.w("Test","approve-false"+ no_id+desc_app+s_notifi_orderid+ string_uname+ string_s_name+ string_name+ string_dev_v1+ string_position+dialog);
                                        Approve_false("approve-false", no_id,desc_app,s_notifi_orderid, string_uname, string_s_name, string_name, string_dev_v1, string_position,dialog);

                                    }
                                }
                            });
                            dialog.show();
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<approve> call, Throwable t) {
                Log.w("Test", String.valueOf("เชื่อมต่อไม่สำเร็จ" + t.getMessage()));
            }
        });
    }

    private void Approve_true(String flag, String no_id, String uname, String s_name, String name, String dev_v1, String position, final DialogInterface dialog) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://lionproduction.sli")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Requestlnterface_data request = retrofit.create(Requestlnterface_data.class);
        Call<approve_true> call = request.Callapprove_true(flag, no_id, uname, s_name, name, dev_v1, position);
        call.enqueue(new Callback<approve_true>() {
            @Override
            public void onResponse(Call<approve_true> call, Response<approve_true> response) {
                if (response.body().isStatus()) {
                    Log.w("Test", "true");
                    Toast.makeText(ApproveActiviry.this, "บันทึกข้อมูลเรียบร้อย", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    finish();
                } else {
                    Log.w("Test", "false");
                    Log.w("Test", response.body().getMessage());
                    Toast.makeText(ApproveActiviry.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<approve_true> call, Throwable t) {
                Log.w("error", t);
                Toast.makeText(ApproveActiviry.this, "ไม่สามารถโหลดข้อมูลปลายทางได้", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void Approve_false(String flag, String no_id,String description,String order_id, String uname, String s_name, String name, String dev_v1, String position,final Dialog dialog) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://lionproduction.sli")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Requestlnterface_data request = retrofit.create(Requestlnterface_data.class);
        Call<approve_false> call = request.Callapprove_false(flag, no_id,description,order_id, uname, s_name, name, dev_v1, position);
        call.enqueue(new Callback<approve_false>() {
            @Override
            public void onResponse(Call<approve_false> call, Response<approve_false> response) {
                if (response.body().isStatus()) {
                    Log.w("Test", "true");
                    Toast.makeText(ApproveActiviry.this, "บันทึกข้อมูลเรียบร้อย", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    finish();
                } else {
                    Log.w("Test", "false");
                    Log.w("Test", response.body().getMessage());
                    Toast.makeText(ApproveActiviry.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<approve_false> call, Throwable t) {
                Log.w("error", t);
                Toast.makeText(ApproveActiviry.this, "ไม่สามารถโหลดข้อมูลปลายทางได้", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
