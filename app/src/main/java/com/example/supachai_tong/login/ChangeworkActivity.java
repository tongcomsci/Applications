package com.example.supachai_tong.login;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.supachai_tong.login.Modal.Cancel_Changwork;
import com.example.supachai_tong.login.Modal.Confirm_Changwork;
import com.example.supachai_tong.login.Modal.Confirm_Changwork_pre;
import com.example.supachai_tong.login.Modal.Confirm_Changwork_tag;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChangeworkActivity extends AppCompatActivity {
    SharedPreferences.Editor editor;
    SharedPreferences shared;
    private TextView txt_notif_no, txt_desc, txt_before, txt_after, txt_change_no_id, txt_change_name, txt_title_assign;
    private Button btn_accept, btn_no_accept;
    private ConstraintLayout con_name_assign;
    private Spinner spinner_assign_to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changework);
        shared = getSharedPreferences("userLogin", Context.MODE_PRIVATE);
        editor = shared.edit();
        txt_notif_no = findViewById(R.id.txt_notif_no);
        txt_desc = findViewById(R.id.txt_desc);
        txt_before = findViewById(R.id.before);
        txt_after = findViewById(R.id.after);
        btn_accept = findViewById(R.id.btn_accept);
        btn_no_accept = findViewById(R.id.btn_no_accept);
        txt_change_no_id = findViewById(R.id.txt_change_no_id);
        txt_change_name = findViewById(R.id.txt_change_name);
        con_name_assign = findViewById(R.id.con_name_assign);
        txt_title_assign = findViewById(R.id.txt_title_assign);
        spinner_assign_to = findViewById(R.id.spinner_assign_to);

        Intent intent = getIntent();
        String no_id = intent.getStringExtra("no_id");
        loadChangework_appr_tag("getconfirm_change_work", no_id);
    }

    private void loadChangework_appr_tag(String flag, String no_id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://lionproduction.sli")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Requestlnterface_data request = retrofit.create(Requestlnterface_data.class);
        Call<List<Confirm_Changwork_tag>> call = request.CallConfirm_Changwork_tag(flag, no_id);
        call.enqueue(new Callback<List<Confirm_Changwork_tag>>() {
            @Override
            public void onResponse(Call<List<Confirm_Changwork_tag>> call, final Response<List<Confirm_Changwork_tag>> response) {
                Intent intent = getIntent();
                final String no_id = intent.getStringExtra("no_id");
                final String dev_v1 = intent.getStringExtra("dev_v1");
                final String notifi_no = intent.getStringExtra("notifi_no");
                final String string_s_name = shared.getString("s_name", null);
                final String string_uname = shared.getString("uname", null);
                final String string_name = shared.getString("name", null);
                final String string_position = shared.getString("position", null);
                final String appr_name_change = response.body().get(0).getAppr_name();
                final String before = response.body().get(0).getBefore();
                final String after = response.body().get(0).getAfter();
                final String desc = response.body().get(0).getDesc();

                String[] name_assign = new String[response.body().get(0).getList_user().length];
                final String[] uname_assign = new String[response.body().get(0).getList_uname().length];
                if (response.body().get(0).getChack_status().equals("0")) {
                } else {
                    con_name_assign.setVisibility(View.VISIBLE);
                    spinner_assign_to.setVisibility(View.VISIBLE);
                    txt_title_assign.setVisibility(View.VISIBLE);

                    Integer count_name = 0;
                    for (String s : response.body().get(0).getList_user()) {
                        name_assign[count_name] = s + " (" + after.toUpperCase() + ")";
                        count_name++;
                    }
                    Integer count_uname = 0;
                    for (String s : response.body().get(0).getList_uname()) {
                        uname_assign[count_uname] = s;
                        count_uname++;
                    }
                    final ArrayAdapter<String> adapter_assign_to = new ArrayAdapter<String>(ChangeworkActivity.this,
                            android.R.layout.simple_dropdown_item_1line, (String[]) name_assign);
                    spinner_assign_to.setAdapter(adapter_assign_to);
                }

                txt_change_no_id.setText(no_id);
                txt_notif_no.setText(notifi_no);
                txt_desc.setText(desc);
                txt_change_name.setText(appr_name_change);

                if (before.equals("mt")) {
                    txt_before.setText("MECHANICAL");
                } else if (before.equals("ee")) {
                    txt_before.setText("ELECTRICAL");
                } else if (before.equals("cal")) {
                    txt_before.setText("CALIBRATION");
                } else if (before.equals("fac")) {
                    txt_before.setText("CIVIL");
                } else if (before.equals("fk")) {
                    txt_before.setText("FORKLIFT");
                } else if (before.equals("cc")) {
                    txt_before.setText("CCTV");
                } else if (before.equals("air")) {
                    txt_before.setText("AIR CONDITION");
                } else if (before.equals("de")) {
                    txt_before.setText("DEVELOP SYSTEM");
                }

                if (after.equals("mt")) {
                    txt_after.setText("MECHANICAL");
                } else if (after.equals("ee")) {
                    txt_after.setText("ELECTRICAL");
                } else if (after.equals("cal")) {
                    txt_after.setText("CALIBRATION");
                } else if (after.equals("fac")) {
                    txt_after.setText("CIVIL");
                } else if (after.equals("fk")) {
                    txt_after.setText("FORKLIFT");
                } else if (after.equals("cc")) {
                    txt_after.setText("CCTV");
                } else if (after.equals("air")) {
                    txt_after.setText("AIR CONDITION");
                } else if (after.equals("de")) {
                    txt_after.setText("DEVELOP SYSTEM");
                }

                btn_accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ChangeworkActivity.this);
                        builder.setTitle("แจ้งเตือน");
                        builder.setMessage("คุณต้องการยอมรับการเปลี่ยนกลุ่มงาน ใช่หรือไม่ ?");
                        builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (response.body().get(0).getChack_status().equals("0")) {
                                    loadChangework("confirm-change-work", no_id, dev_v1, notifi_no, before, after, desc, string_s_name, string_uname, string_name, string_position, dialog);
                                } else {
                                    Integer position = spinner_assign_to.getSelectedItemPosition();
                                    final String u_assign = uname_assign[position];
                                    Log.w("Test",u_assign);
                                    loadChangework_pre("confirm-change-work", no_id, dev_v1, notifi_no, before, after, desc, string_s_name, string_uname, string_name, string_position, u_assign, dialog);
                                }
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

                btn_no_accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final Dialog dialog = new Dialog(ChangeworkActivity.this);
                        dialog.setContentView(R.layout.dialog_detail);
                        dialog.setCancelable(true);
                        final EditText text_app = dialog.findViewById(R.id.txt_change_desc);
                        TextView txt_title_desc = dialog.findViewById(R.id.txt_title_desc);
                        txt_title_desc.setText("กรุณาระบุเหตุผลที่จะไม่ยอมรับการเปลี่ยนกลุ่มงาน ? ");
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
                                Log.w("Test", "Desc" + desc_app);
                                String string_s_name = shared.getString("s_name", null);
                                String string_uname = shared.getString("uname", null);
                                String string_name = shared.getString("name", null);
                                String string_dev_v1 = shared.getString("dev_v1", null);
                                String string_position = shared.getString("position", null);

                                if (desc_app.equals("")) {
                                    Toast.makeText(ChangeworkActivity.this, "กรุณากรอกเหตุผล", Toast.LENGTH_SHORT).show();
                                } else {
//                                    Log.w("Test", String.valueOf(text_app));
                                    Log.w("Test", desc_app);
                                    Log.w("test", ("cancel-change-work" + no_id + notifi_no + before + after + desc_app + string_s_name + string_uname + string_name + string_position));
                                    loadCancel_Changework("cancel-change-work", no_id, before, after, desc_app, string_s_name, string_uname, string_name, string_dev_v1, string_position);
                                    dialog.dismiss();
                                }
                            }
                        });
                        dialog.show();
                    }
                });
            }
            @Override
            public void onFailure(Call<List<Confirm_Changwork_tag>> call, Throwable t) {
                Log.w("error", t);
                Toast.makeText(ChangeworkActivity.this, "ไม่สามารถโหลดข้อมูลปลายทางได้", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadChangework(String flag, String no_id, String dev_v1, String notifi_no, String before, String after, String desc, String s_name, String uname, String name, String position, final DialogInterface dialog) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://lionproduction.sli")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Requestlnterface_data request = retrofit.create(Requestlnterface_data.class);
        Call<Confirm_Changwork> call = request.CallConfirm_Changwork(flag, no_id, dev_v1, notifi_no, before, after, desc, s_name, uname, name, position);
        call.enqueue(new Callback<Confirm_Changwork>() {
            @Override
            public void onResponse(Call<Confirm_Changwork> call, Response<Confirm_Changwork> response) {
                if (response.body().isStatus()) {
                    Log.w("Test", "true");
                    dialog.dismiss();
                    Toast.makeText(ChangeworkActivity.this, "บันทึกข้อมูลเรียบร้อย", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Log.w("Test", "false");
                    Log.w("Test", response.body().getMessage());
                    Toast.makeText(ChangeworkActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Confirm_Changwork> call, Throwable t) {
                Log.w("error", t);
                Toast.makeText(ChangeworkActivity.this, "ไม่สามารถโหลดข้อมูลปลายทางได้", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadChangework_pre(String flag, String no_id, String dev_v1, String notifi_no, String before, String after, String desc, String s_name, String uname, String name, String position,String assign, final DialogInterface dialog) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://lionproduction.sli")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Requestlnterface_data request = retrofit.create(Requestlnterface_data.class);
        Call<Confirm_Changwork_pre> call = request.CallConfirm_Changwork_pre(flag, no_id, dev_v1, notifi_no, before, after, desc, s_name, uname, name, position,assign);
        call.enqueue(new Callback<Confirm_Changwork_pre>() {
            @Override
            public void onResponse(Call<Confirm_Changwork_pre> call, Response<Confirm_Changwork_pre> response) {
                if (response.body().isStatus()) {
                    Log.w("Test", "true");
                    dialog.dismiss();
                    Toast.makeText(ChangeworkActivity.this, "บันทึกข้อมูลเรียบร้อย", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Log.w("Test", "false");
                    Log.w("Test", response.body().getMessage());
                    Toast.makeText(ChangeworkActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Confirm_Changwork_pre> call, Throwable t) {
                Log.w("error", t);
                Toast.makeText(ChangeworkActivity.this, "ไม่สามารถโหลดข้อมูลปลายทางได้", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void loadCancel_Changework(String flag, String no_id, String before, String after, String description, String s_name, String uname, String name, String dev_v1, String position) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://lionproduction.sli")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Requestlnterface_data request = retrofit.create(Requestlnterface_data.class);
        Call<Cancel_Changwork> call = request.CallCancel_Changwork(flag, no_id, before, after, description, s_name, uname, name, dev_v1, position);
        call.enqueue(new Callback<Cancel_Changwork>() {
            @Override
            public void onResponse(Call<Cancel_Changwork> call, Response<Cancel_Changwork> response) {
                if (response.body().isStatus()) {
                    Log.w("Test", "true");
                    Toast.makeText(ChangeworkActivity.this, "บันทึกข้อมูลเรียบร้อย", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Log.w("Test", "false");
                    Log.w("Test", response.body().getMessage());
                    Toast.makeText(ChangeworkActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Cancel_Changwork> call, Throwable t) {
                Log.w("error", t);
                Toast.makeText(ChangeworkActivity.this, "ไม่สามารถโหลดข้อมูลปลายทางได้", Toast.LENGTH_SHORT).show();
            }
        });

    }


}


