package com.example.supachai_tong.login;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.supachai_tong.login.Modal.Changworkpre;
import com.example.supachai_tong.login.Modal.Confirm_Changwork_pre_tag;
import com.example.supachai_tong.login.Modal.savechangepre;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Changework_preActivity extends AppCompatActivity {
    SharedPreferences.Editor editor;
    SharedPreferences shared;
    private TextView txt_no_id_pre, txt_noti_no_per, txt_appr_name_pre, txt_desc_pre, txt_before_pre, txt_after_pre;
    private Button btn_not_allowed,btn_change_pre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changework_pre);
        shared = getSharedPreferences("userLogin", Context.MODE_PRIVATE);
        editor = shared.edit();
        txt_no_id_pre = findViewById(R.id.txt_no_id_pre);
        txt_noti_no_per = findViewById(R.id.txt_noti_no_per);
        txt_appr_name_pre = findViewById(R.id.txt_appr_name_pre);
        txt_desc_pre = findViewById(R.id.txt_desc_pre);
        txt_before_pre = findViewById(R.id.txt_before_pre);
        txt_after_pre = findViewById(R.id.txt_after_pre);
        btn_not_allowed = findViewById(R.id.btn_not_allowed);
        btn_change_pre = findViewById(R.id.btn_change_pre);

        Intent intent = getIntent();
        String no_id = intent.getStringExtra("no_id");
        loadChangework_pre_appr_tag("getconfirm_change_work_pre", no_id);
    }

    private void loadChangework_pre_appr_tag(String flag, String no_id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://lionproduction.sli")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Requestlnterface_data request = retrofit.create(Requestlnterface_data.class);
        Call<List<Confirm_Changwork_pre_tag>> call = request.CallConfirm_Changwork_pre_tag(flag, no_id);
        call.enqueue(new Callback<List<Confirm_Changwork_pre_tag>>() {
            @Override
            public void onResponse(Call<List<Confirm_Changwork_pre_tag>> call, final Response<List<Confirm_Changwork_pre_tag>> response) {
                Intent intent = getIntent();
                final String no_id = intent.getStringExtra("no_id");
                final String notifi_no = intent.getStringExtra("notifi_no");
                final String appr_name_pre = new String(response.body().get(0).getAppr_name());
                final String before = new String(response.body().get(0).getAppr_tag().getBefore());
                final String after = new String(response.body().get(0).getAppr_tag().getAfter());
                final String desc = new String(response.body().get(0).getAppr_tag().getDesc());

                txt_no_id_pre.setText(no_id);
                txt_noti_no_per.setText(notifi_no);
                txt_appr_name_pre.setText(appr_name_pre);
                txt_desc_pre.setText(desc);

                if (before.equals("mt")) {
                    txt_before_pre.setText("MECHANICAL");
                } else if (before.equals("ee")) {
                    txt_before_pre.setText("ELECTRICAL");
                } else if (before.equals("cal")) {
                    txt_before_pre.setText("CALIBRATION");
                } else if (before.equals("fac")) {
                    txt_before_pre.setText("CIVIL");
                } else if (before.equals("fk")) {
                    txt_before_pre.setText("FORKLIFT");
                } else if (before.equals("cc")) {
                    txt_before_pre.setText("CCTV");
                } else if (before.equals("air")) {
                    txt_before_pre.setText("AIR CONDITION");
                } else if (before.equals("de")) {
                    txt_before_pre.setText("DEVELOP SYSTEM");
                }

                if (after.equals("mt")) {
                    txt_after_pre.setText("MECHANICAL");
                } else if (after.equals("ee")) {
                    txt_after_pre.setText("ELECTRICAL");
                } else if (after.equals("cal")) {
                    txt_after_pre.setText("CALIBRATION");
                } else if (after.equals("fac")) {
                    txt_after_pre.setText("CIVIL");
                } else if (after.equals("fk")) {
                    txt_after_pre.setText("FORKLIFT");
                } else if (after.equals("cc")) {
                    txt_after_pre.setText("CCTV");
                } else if (after.equals("air")) {
                    txt_after_pre.setText("AIR CONDITION");
                } else if (after.equals("de")) {
                    txt_after_pre.setText("DEVELOP SYSTEM");
                }

                btn_change_pre.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Changework_preActivity.this);
                        builder.setTitle("แจ้งเตือน");
                        builder.setMessage("คุณต้องการยอมรับการเปลี่ยนกลุ่มงาน ใช่หรือไม่ ?");
                        builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                change_work_pre("confirmchangeworkpre",no_id,before,after,desc,dialog);
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
                });

                btn_not_allowed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog dialog = new Dialog(Changework_preActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.popup_change_pre);
                        dialog.setCancelable(true);
                        final EditText txt_desc_pre = dialog.findViewById(R.id.txt_desc_pre);
                        Button btn_chang_pre_cancle = dialog.findViewById(R.id.btn_chang_pre_cancle);
                        Button btn_chang_pre_ok = dialog.findViewById(R.id.btn_chang_pre_ok);
                        btn_chang_pre_cancle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.cancel();
                            }
                        });

                        btn_chang_pre_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (txt_desc_pre.getText().equals("")||txt_desc_pre.getText()==null) {
                                    Toast.makeText(Changework_preActivity.this, "กรุณากรอกเหตุผล", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.w("Test",txt_desc_pre.getText().toString());
                                    londchange_pre("confirmchangeworkpre-false",no_id,txt_desc_pre.getText().toString(),dialog);
                                }
                            }
                        });
                        dialog.show();
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Confirm_Changwork_pre_tag>> call, Throwable t) {
                Log.w("error", t);
                Toast.makeText(Changework_preActivity.this, "ไม่สามารถโหลดข้อมูลปลายทางได้", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void londchange_pre(String flag, String no_id , String desc, final Dialog dialog) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://lionproduction.sli")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Requestlnterface_data request = retrofit.create(Requestlnterface_data.class);
        Call<savechangepre> call = request.Callsavechangepre(flag,no_id,desc,shared.getString("dev_v1", null),shared.getString("s_name", null),shared.getString("uname", null),shared.getString("name", null),shared.getString("position", null));
        call.enqueue(new Callback<savechangepre>() {
            @Override
            public void onResponse(Call<savechangepre> call, Response<savechangepre> response) {
                Log.w("Test", String.valueOf(response.body()));
                if (response.body().isStatus()) {
                    Log.w("Test","true");
                    Toast.makeText(Changework_preActivity.this, "บันทึกข้อมูลเรียบร้อย", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    finish();
                }else{
                    Log.w("Test","false");
                    Log.w("Test",response.body().getMessage());
                    Toast.makeText(Changework_preActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                Log.w("Test","Test1");
            }
            @Override
            public void onFailure(Call<savechangepre> call, Throwable t) {
                Toast.makeText(Changework_preActivity.this, "ไม่สามารถโหลดข้อมูลปลายทางได้", Toast.LENGTH_SHORT).show();
                Log.w("Test","ERROR");
                Log.w("Test", String.valueOf(call));
                Log.w("Test",t);
            }
        });
    }

    private void change_work_pre(String flag, String no_id ,String before ,String after, String desc,final DialogInterface dialog) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://lionproduction.sli")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Requestlnterface_data request = retrofit.create(Requestlnterface_data.class);
        Call<Changworkpre> call = request.CallChangworkpre(flag,no_id,before,after,desc,shared.getString("dev_v1", null),shared.getString("s_name", null),shared.getString("uname", null),shared.getString("name", null),shared.getString("position", null));
        call.enqueue(new Callback<Changworkpre>() {
            @Override
            public void onResponse(Call<Changworkpre> call, Response<Changworkpre> response) {
                if (response.body().isStatus()) {
                    Log.w("Test","true");
                    Toast.makeText(Changework_preActivity.this, "บันทึกข้อมูลเรียบร้อย", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    finish();
                }else{
                    Log.w("Test","false");
                    Log.w("Test",response.body().getMessage());
                }
            }
            @Override
            public void onFailure(Call<Changworkpre> call, Throwable t) {
                Log.w("Test",t);
                Toast.makeText(Changework_preActivity.this, "ไม่สามารถโหลดข้อมูลปลายทางได้", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
