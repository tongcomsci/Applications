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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.supachai_tong.login.Modal.Changwork;
import com.example.supachai_tong.login.Modal.Changworkpre;
import com.example.supachai_tong.login.Modal.noreject;
import com.example.supachai_tong.login.Modal.reject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RejeckActivity extends AppCompatActivity {
    SharedPreferences.Editor editor;
    SharedPreferences shared;
    private TextView txt_reject_notif_no, txt_reject_appr_name, txt_reject_longtxt;
    private Button btn_no_reject, btn_reject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejeck);
        shared = getSharedPreferences("userLogin", Context.MODE_PRIVATE);
        editor = shared.edit();

    }

    @Override
    protected void onStart() {
        super.onStart();
        txt_reject_notif_no = findViewById(R.id.txt_reject_notif_no);
        txt_reject_appr_name = findViewById(R.id.txt_reject_appr_name);
        txt_reject_longtxt = findViewById(R.id.txt_reject_longtxt);
        btn_reject = findViewById(R.id.btn_reject);
        btn_no_reject = findViewById(R.id.btn_no_reject);

        Intent intent = getIntent();
        String no_id = intent.getStringExtra("no_id");
        String notif_no = intent.getStringExtra("notif_no");
        londReject("getlond_reject",no_id,notif_no);
    }


    private void londReject(String flag, final String no_id, final String notif_no) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://lionproduction.sli")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Requestlnterface_data request = retrofit.create(Requestlnterface_data.class);
        final Call<List<reject>> call = request.Calllondreject(flag, no_id);
        call.enqueue(new Callback<List<reject>>() {
            @Override
            public void onResponse(Call<List<reject>> call, Response<List<reject>> response) {
                txt_reject_notif_no.setText(notif_no);
                txt_reject_appr_name.setText(response.body().get(0).getAppr_name());
                txt_reject_longtxt.setText(response.body().get(0).getAppr_tag().getDescription());
                Log.w("Test",notif_no);
                Log.w("Test",response.body().get(0).getAppr_name());
                Log.w("Test",response.body().get(0).getAppr_tag().getDescription());
                final String string_s_name = shared.getString("s_name", null);
                final String string_uname = shared.getString("uname", null);
                final String string_name = shared.getString("name", null);
                final String string_dev_v1 = shared.getString("dev_v1", null);
                final String string_position = shared.getString("position", null);


                btn_reject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(RejeckActivity.this);
                        builder.setTitle("แจ้งเตือน");
                        builder.setMessage("คุณต้องการยอมรับการ Reject ใช่หรือไม่");
                        builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {

                                Log.w("Test","confirm-reject"+" "+ no_id+" "+ "1"+" "+ notif_no+" "+ string_dev_v1+" "+ string_s_name+" "+ string_uname+" "+ string_name+" "+ string_position);
                                loadConfirmReject("confirm-reject", no_id, "1", notif_no, string_dev_v1, string_s_name, string_uname, string_name, string_position,dialog);


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
                });

                btn_no_reject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog dialog = new Dialog(RejeckActivity.this);
                        dialog.setContentView(R.layout.dialog_detail);
                        dialog.setCancelable(true);
                        final EditText text_app = dialog.findViewById(R.id.txt_change_desc);

                        TextView txt_title_desc = dialog.findViewById(R.id.txt_title_desc);
                        txt_title_desc.setText("กรุณาระบุเหตุผลที่จะไม่ยอมรับการ Reject ? ");
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
                                Log.w("Test", "Desc  " + desc_app);


                                if (desc_app.equals("")) {
                                    Toast.makeText(RejeckActivity.this, "กรุณากรอกเหตุผล", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.w("Test", desc_app);
                                    Log.w("Test","confirm-reject"+" "+ no_id+" "+ "0"+" "+ notif_no+" "+ string_s_name+" "+ string_uname+" "+ string_name+" "+ string_dev_v1+" "+ string_position);
                                    loadNoReject("confirm-reject", no_id, "0", notif_no, string_dev_v1, string_s_name, string_uname, string_name, string_position,dialog);
                                    dialog.dismiss();
                                }
                            }
                        });
                        dialog.show();

                    }
                });



            }

            @Override
            public void onFailure(Call<List<reject>> call, Throwable t) {
                Log.w("Test", t);

            }
        });
    }

    private void loadNoReject(String flag ,String no_id,String act , String notifi_no ,String dev_v1, String s_name, String uname, String name, String position, final Dialog dialog) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://lionproduction.sli")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Requestlnterface_data request = retrofit.create(Requestlnterface_data.class);
        Call<noreject> call = request.CallNoReject(flag,no_id , act , notifi_no, dev_v1, s_name, uname, name, position);
        call.enqueue(new Callback<noreject>() {
            @Override
            public void onResponse(Call<noreject> call, Response<noreject> response) {
                if (response.body().isStatus()) {
                    Log.w("Test", "true");
                    Toast.makeText(RejeckActivity.this, "บันทึกข้อมูลเรียบร้อย", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    finish();
                } else {
                    Log.w("Test", "false");
                    Log.w("Test", response.body().getMessage());
                    Toast.makeText(RejeckActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<noreject> call, Throwable t) {
                Log.w("error", t);
                Toast.makeText(RejeckActivity.this, "ไม่สามารถโหลดข้อมูลปลายทางได้", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadConfirmReject(String flag ,String no_id,String act , String notifi_no ,String dev_v1, String s_name, String uname, String name, String position,final DialogInterface dialog) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://lionproduction.sli")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Requestlnterface_data request = retrofit.create(Requestlnterface_data.class);
        Call<noreject> call = request.CallNoReject(flag,no_id , act , notifi_no, dev_v1, s_name, uname, name, position);
        call.enqueue(new Callback<noreject>() {
            @Override
            public void onResponse(Call<noreject> call, Response<noreject> response) {
                if (response.body().isStatus()) {
                    Log.w("Test", "true");
                    Toast.makeText(RejeckActivity.this, "บันทึกข้อมูลเรียบร้อย", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    finish();
                } else {
                    Log.w("Test", "false");
                    Log.w("Test", response.body().getMessage());
                    Toast.makeText(RejeckActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<noreject> call, Throwable t) {
                Log.w("error", t);
                Toast.makeText(RejeckActivity.this, "ไม่สามารถโหลดข้อมูลปลายทางได้", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
