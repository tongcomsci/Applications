package com.example.supachai_tong.login;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.supachai_tong.login.Modal.Appr_tag;
import com.example.supachai_tong.login.Modal.Viewinfo;

import java.util.ArrayList;
import java.util.List;

public class DataAdapter_viewinfo extends RecyclerView.Adapter<DataAdapter_viewinfo.ViewHolder> {
    private Context context;
    SharedPreferences.Editor editor;
    SharedPreferences shared;
    private Core getcore;
    private List<Viewinfo> list = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row_dialog, viewGroup, false);
        getcore = new Core();
        context = viewGroup.getContext();
        shared = context.getSharedPreferences("userLogin", Context.MODE_PRIVATE);
        editor = shared.edit();
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        if (list.get(i).getAppr_name().equals("") || list.get(i).getAppr_name() == null) {
            viewHolder.txt_appr_name.setVisibility(View.GONE);
            viewHolder.appr_name.setVisibility(View.GONE);
        } else {
            viewHolder.appr_name.setText(list.get(i).getAppr_name());
        }
        viewHolder.result.setText(list.get(i).getAppr_date());

        if ((list.get(i).getAppr_status()).equals("create")) {
            viewHolder.txt_status_view.setText("Create");
            viewHolder.txt_status_view.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.buttonblueright));
            viewHolder.txt_sta_view.setText("สร้างใบแจ้งซ่อม (Create)");
            String Notif_id = "";
            String Func = "";
            String Equi = "";
            String detail = "";
            for (Appr_tag s : list.get(i).getAppr_tag()) {
                Notif_id += s.getNOTIF_NO();
                Func += s.getFUNCT_LOC();
                Equi += s.getEQUIPMENT();
                detail += s.getDESCRIPTION();
            }
//            viewHolder.txt_view1.setText(Notif_id + Func + Equi + detail);
            viewHolder.text_desc1.setVisibility(View.VISIBLE);
            viewHolder.text_desc2.setVisibility(View.VISIBLE);
            viewHolder.text_desc3.setVisibility(View.VISIBLE);
            viewHolder.text_desc4.setVisibility(View.VISIBLE);
            viewHolder.text_1_desc.setVisibility(View.VISIBLE);
            viewHolder.text_2_desc.setVisibility(View.VISIBLE);
            viewHolder.text_3_desc.setVisibility(View.VISIBLE);
            viewHolder.text_4_desc.setVisibility(View.VISIBLE);
            viewHolder.txt_gone.setVisibility(View.VISIBLE);

            viewHolder.text_1_desc.setText("ใบแจ้งซ่อมเลขที่ : ");
            viewHolder.text_2_desc.setText("Func Location : ");
            viewHolder.text_3_desc.setText("Equipment : ");
            viewHolder.text_4_desc.setText("ชื่อเรื่อง : ");
            viewHolder.text_desc1.setText(Notif_id);
            viewHolder.text_desc2.setText(Func);
            viewHolder.text_desc3.setText((Equi.equals("") || Equi == null) ? " - " : Equi);
            viewHolder.text_desc4.setText(detail);
        } else if ((list.get(i).getAppr_status()).equals("release")) {
            viewHolder.txt_status_view.setText("Release");
            viewHolder.txt_status_view.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.buttonoregneright));
            viewHolder.txt_sta_view.setText("อนุมัติใบแจ้งซ่อม (Release)");
            String e_status = "";
            for (Appr_tag s : list.get(i).getAppr_tag()) {
                e_status += s.getE_STATUS();
            }
//            viewHolder.txt_view1.setText(e_status);
            viewHolder.text_desc1.setVisibility(View.VISIBLE);
            viewHolder.text_desc2.setVisibility(View.GONE);
            viewHolder.text_desc3.setVisibility(View.GONE);
            viewHolder.text_desc4.setVisibility(View.GONE);
            viewHolder.text_1_desc.setVisibility(View.VISIBLE);
            viewHolder.text_2_desc.setVisibility(View.GONE);
            viewHolder.text_3_desc.setVisibility(View.GONE);
            viewHolder.text_4_desc.setVisibility(View.GONE);
            viewHolder.text_1_desc.setText("สถานะการอนุมัติ : ");
            viewHolder.text_desc1.setText((e_status.equals("") || e_status == null) ? " - " : e_status);
        } else if ((list.get(i).getAppr_status()).equals("assign")) {
            viewHolder.txt_status_view.setText("Assign");
            viewHolder.txt_status_view.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.buttonoregneright));
            viewHolder.txt_sta_view.setText("มอบหมายงาน (Assign)");
            viewHolder.text_desc1.setVisibility(View.VISIBLE);
            viewHolder.text_desc2.setVisibility(View.VISIBLE);
            viewHolder.text_desc3.setVisibility(View.GONE);
            viewHolder.text_desc4.setVisibility(View.GONE);
            viewHolder.text_1_desc.setVisibility(View.VISIBLE);
            viewHolder.text_2_desc.setVisibility(View.VISIBLE);
            viewHolder.text_3_desc.setVisibility(View.GONE);
            viewHolder.text_4_desc.setVisibility(View.GONE);
            for (Appr_tag s : list.get(i).getAppr_tag()) {
                getcore.CoreLog("getname", s.getAssign(), viewHolder.text_desc1, viewHolder.text_1_desc, viewHolder.text_desc2, viewHolder.text_2_desc, s.getDescription());
            }
        } else if ((list.get(i).getAppr_status()).equals("change-assign")) {
            viewHolder.txt_status_view.setText("Transfer");
            viewHolder.txt_status_view.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.buttonoregneright));
            viewHolder.txt_sta_view.setText("โอนย้ายงาน (มาจากสถานะ Assign)");
            viewHolder.text_desc1.setVisibility(View.VISIBLE);
            viewHolder.text_desc2.setVisibility(View.VISIBLE);
            viewHolder.text_desc3.setVisibility(View.GONE);
            viewHolder.text_desc4.setVisibility(View.GONE);
            viewHolder.text_1_desc.setVisibility(View.VISIBLE);
            viewHolder.text_2_desc.setVisibility(View.VISIBLE);
            viewHolder.text_3_desc.setVisibility(View.GONE);
            viewHolder.text_4_desc.setVisibility(View.GONE);
            for (Appr_tag s : list.get(i).getAppr_tag()) {
                getcore.CoreLog("getname", s.getAssign(), viewHolder.text_desc1, viewHolder.text_1_desc, viewHolder.text_desc2, viewHolder.text_2_desc, s.getDescription());
            }
        } else if ((list.get(i).getAppr_status()).equals("accept")) {
            viewHolder.txt_status_view.setText("Accept");
            viewHolder.txt_status_view.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.buttonoregneright));
            viewHolder.txt_sta_view.setText("รับงาน (Accept)");
            viewHolder.text_desc1.setVisibility(View.GONE);
            viewHolder.text_desc2.setVisibility(View.GONE);
            viewHolder.text_desc3.setVisibility(View.GONE);
            viewHolder.text_desc4.setVisibility(View.GONE);
            viewHolder.text_1_desc.setVisibility(View.VISIBLE);
            viewHolder.text_2_desc.setVisibility(View.GONE);
            viewHolder.text_3_desc.setVisibility(View.GONE);
            viewHolder.text_4_desc.setVisibility(View.GONE);
            viewHolder.text_1_desc.setText("รับงานเรียบร้อย");
//            viewHolder.txt_view1.setText("รับงานเรียบร้อย");
        } else if ((list.get(i).getAppr_status()).equals("convert")) {
            viewHolder.txt_status_view.setText("Convert");
            viewHolder.txt_status_view.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.buttonoregneright));
            viewHolder.txt_sta_view.setText("เปลี่ยนใบแจ้งซ่อมเป็นออเดอร์ (Convert)");
            String order_id = "";
            String order_type = "";
            for (Appr_tag s : list.get(i).getAppr_tag()) {
                order_id += s.getORDER_ID();
                order_type += s.getORDER_TYPE();
            }
//            viewHolder.txt_view1.setText(order_id + order_type);
            viewHolder.text_desc1.setVisibility(View.VISIBLE);
            viewHolder.text_desc2.setVisibility(View.VISIBLE);
            viewHolder.text_desc3.setVisibility(View.GONE);
            viewHolder.text_desc4.setVisibility(View.GONE);
            viewHolder.text_1_desc.setVisibility(View.VISIBLE);
            viewHolder.text_2_desc.setVisibility(View.VISIBLE);
            viewHolder.text_3_desc.setVisibility(View.GONE);
            viewHolder.text_4_desc.setVisibility(View.GONE);
            viewHolder.text_1_desc.setText("ใบแจ้งซ่อมเป็นออเดอร์เลขที่ : ");
            viewHolder.text_2_desc.setText("ชนิดออเดอร์ : ");
            viewHolder.text_desc1.setText(order_id);
            viewHolder.text_desc2.setText(order_type);
        } else if ((list.get(i).getAppr_status()).equals("planning")) {
            viewHolder.txt_status_view.setText("Planning");
            viewHolder.txt_status_view.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.buttonoregneright));
            viewHolder.txt_sta_view.setText("วางแผน (Planning)");
            String order_id = "";
            String status = "";
            for (Appr_tag s : list.get(i).getAppr_tag()) {
                order_id += s.getOrder_id();
                status += s.getStatus();
            }
//            viewHolder.txt_view1.setText(order_id + status);
            viewHolder.text_desc1.setVisibility(View.VISIBLE);
            viewHolder.text_desc2.setVisibility(View.VISIBLE);
            viewHolder.text_1_desc.setVisibility(View.VISIBLE);
            viewHolder.text_2_desc.setVisibility(View.VISIBLE);
            viewHolder.text_1_desc.setText("ออเดอร์เลขที่ : ");
            viewHolder.text_2_desc.setText("สถานะ : ");
            viewHolder.text_desc1.setText(order_id);
            viewHolder.text_desc2.setText(status);
        } else if ((list.get(i).getAppr_status()).equals("confirm")) {
            viewHolder.txt_status_view.setText("Confirm");
            viewHolder.txt_status_view.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.buttongreenright));
            viewHolder.txt_sta_view.setText("ช่างยืนยันข้อมูลครบถ้วน (Confirm)");
            String TEXT = "";
            for (Appr_tag s : list.get(i).getAppr_tag()) {
                TEXT += s.getTEXT();
            }
//            viewHolder.txt_view1.setText(TEXT);
            viewHolder.text_desc1.setVisibility(View.VISIBLE);
            viewHolder.text_1_desc.setVisibility(View.VISIBLE);
            viewHolder.text_1_desc.setText("อธิบาย : ");
            viewHolder.text_desc1.setText(TEXT);
        } else if ((list.get(i).getAppr_status()).equals("approve")) {
            viewHolder.txt_status_view.setText("Approve");
            viewHolder.txt_status_view.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.buttongreenright));
            viewHolder.txt_sta_view.setText("รับรู้ผลการยืนยันเวลาการทำงานของช่าง (Approve)");
            viewHolder.text_1_desc.setVisibility(View.VISIBLE);
            viewHolder.text_1_desc.setText("-");
        } else if ((list.get(i).getAppr_status()).equals("approve-false")) {
            viewHolder.txt_status_view.setText("Send back");
            viewHolder.txt_status_view.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.buttongrayright));
            viewHolder.txt_sta_view.setText("");
        } else if ((list.get(i).getAppr_status()).equals("recheck")) {
            viewHolder.txt_status_view.setText("Recheck");
            viewHolder.text_1_desc.setVisibility(View.VISIBLE);
            viewHolder.text_1_desc.setText("-");
            viewHolder.txt_status_view.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.buttongreenright));
            viewHolder.txt_sta_view.setText("ตรวจสอบงานอีกครั้ง (Recheck)");
        } else if ((list.get(i).getAppr_status()).equals("final-confirm")) {
            viewHolder.txt_status_view.setText("Final");
            viewHolder.txt_status_view.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.buttongreenright));
            viewHolder.txt_sta_view.setText("ยืนยันผลการซ่อม (Final Confirm)");
        } else if ((list.get(i).getAppr_status()).equals("close")) {
            viewHolder.txt_status_view.setText("Close");
            viewHolder.txt_status_view.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.buttongreenright));
            viewHolder.txt_sta_view.setText("จบงาน (Close)");
        } else if ((list.get(i).getAppr_status()).equals("change-work")) {
            viewHolder.txt_status_view.setText("Change");
            viewHolder.txt_status_view.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.buttongrayright));
            viewHolder.txt_sta_view.setText("แจ้งเปลี่ยนกลุ่มงาน (Change Work)");
            String before = "";
            String after = "";
            String desc = "";
            for (Appr_tag s : list.get(i).getAppr_tag()) {
                before += s.getBefore();
                after += s.getAfter();
                desc += s.getDesc();
            }
            viewHolder.txt_view_after.setVisibility(View.VISIBLE);
            viewHolder.txt_alt26.setVisibility(View.VISIBLE);
            viewHolder.text_desc1.setVisibility(View.VISIBLE);
            viewHolder.text_desc2.setVisibility(View.VISIBLE);
            viewHolder.text_1_desc.setVisibility(View.VISIBLE);
            viewHolder.text_2_desc.setVisibility(View.VISIBLE);
            viewHolder.text_1_desc.setText("แจ้งเปลี่ยนงานจาก : ");
            viewHolder.text_2_desc.setText("หมายเหตุ : ");
            if (before.equals("mt")) {
                viewHolder.text_desc1.setText("MECHANICAL");
            } else if (before.equals("ee")) {
                viewHolder.text_desc1.setText("ELECTRICAL");
            } else if (before.equals("cal")) {
                viewHolder.text_desc1.setText("CALIBRATION");
            } else if (before.equals("fac")) {
                viewHolder.text_desc1.setText("CIVIL");
            } else if (before.equals("fk")) {
                viewHolder.text_desc1.setText("FORKLIFT");
            } else if (before.equals("cc")) {
                viewHolder.text_desc1.setText("CCTV");
            } else if (before.equals("air")) {
                viewHolder.text_desc1.setText("AIR CONDITION");
            } else if (before.equals("de")) {
                viewHolder.text_desc1.setText("DEVELOP SYSTEM");
            }
            if (after.equals("mt")) {
                viewHolder.txt_view_after.setText("MECHANICAL");
            } else if (after.equals("ee")) {
                viewHolder.txt_view_after.setText("ELECTRICAL");
            } else if (after.equals("cal")) {
                viewHolder.txt_view_after.setText("CALIBRATION");
            } else if (after.equals("fac")) {
                viewHolder.txt_view_after.setText("CIVIL");
            } else if (after.equals("fk")) {
                viewHolder.txt_view_after.setText("FORKLIFT");
            } else if (after.equals("cc")) {
                viewHolder.txt_view_after.setText("CCTV");
            } else if (after.equals("air")) {
                viewHolder.txt_view_after.setText("AIR CONDITION");
            } else if (after.equals("de")) {
                viewHolder.txt_view_after.setText("DEVELOP SYSTEM");
            }
            viewHolder.text_desc2.setText(desc);
        } else if ((list.get(i).getAppr_status()).equals("confirm-change-work")) {
            viewHolder.txt_status_view.setText("Change");
            viewHolder.txt_status_view.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.buttongrayright));
            viewHolder.txt_sta_view.setText("ยอมรับการเปลี่ยนกลุ่มงาน (Confirm Change Work)");
            String after = "";
            for (Appr_tag s : list.get(i).getAppr_tag()) {
                after += s.getAfter();
            }
            viewHolder.text_desc1.setVisibility(View.VISIBLE);
            viewHolder.text_1_desc.setVisibility(View.VISIBLE);
            viewHolder.text_1_desc.setText("เปลี่ยนกลุ่มงานกลุ่มเป็น : ");
            if (after.equals("mt")) {
                viewHolder.text_desc1.setText("MECHANICAL");
            } else if (after.equals("ee")) {
                viewHolder.text_desc1.setText("ELECTRICAL");
            } else if (after.equals("cal")) {
                viewHolder.text_desc1.setText("CALIBRATION");
            } else if (after.equals("fac")) {
                viewHolder.text_desc1.setText("CIVIL");
            } else if (after.equals("fk")) {
                viewHolder.text_desc1.setText("FORKLIFT");
            } else if (after.equals("cc")) {
                viewHolder.text_desc1.setText("CCTV");
            } else if (after.equals("air")) {
                viewHolder.text_desc1.setText("AIR CONDITION");
            } else if (after.equals("de")) {
                viewHolder.text_desc1.setText("DEVELOP SYSTEM");
            }
        } else if ((list.get(i).getAppr_status()).equals("reject") && (list.get(i).getAppr_nextcode()).equals("98")) {
            viewHolder.txt_status_view.setText("Wait Rej");
            viewHolder.txt_status_view.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.buttongrayright));
            viewHolder.txt_sta_view.setText("แจ้งยกเลิกใบแจ้งซ่อม (Reject)");
            String description = "";
            for (Appr_tag s : list.get(i).getAppr_tag()) {
                description += s.getDescription();
            }
            viewHolder.txt_wait_re.setVisibility(View.VISIBLE);
            viewHolder.text_1_desc.setVisibility(View.VISIBLE);
            viewHolder.text_1_desc.setText("เหตุผลการยกเลิก : ");
            viewHolder.txt_wait_re.setText(description);


        } else if ((list.get(i).getAppr_status()).equals("cancel-accept-reject") && (list.get(i).getAppr_nextcode()).equals("32")) {
            viewHolder.txt_status_view.setText("Canc Rej");
            viewHolder.txt_status_view.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.buttonredright));
            viewHolder.txt_sta_view.setText("ปฎิเสธการยกเลิกใบแจ้งซ่อม (Cancel Reject)");
            final String string_name = shared.getString("name", null);
//            viewHolder.text_desc1.setVisibility(View.VISIBLE);
            viewHolder.text_1_desc.setVisibility(View.VISIBLE);
            viewHolder.text_1_desc.setText("คุณ "+string_name+" ได้ปฎิเสธการ Reject ใบแจ้งซ่อม และส่งกลับไปยังขั้นตอน Assign");
//            viewHolder.text_desc1.setText(string_name);


        }else if ((list.get(i).getAppr_status()).equals("confirm-reject")) {
            viewHolder.txt_status_view.setText("Conf Rej");
            viewHolder.txt_status_view.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.buttonredright));
            viewHolder.txt_sta_view.setText("อนุมัติ Reject ขั้นตอนแรก (Confirm Reject)");
        } else if ((list.get(i).getAppr_status()).equals("confirm-assign-reject") && (list.get(i).getAppr_nextcode()).equals("98")) {
            viewHolder.txt_status_view.setText("Conf Rej");
            viewHolder.txt_status_view.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.buttonredright));
            viewHolder.txt_sta_view.setText("อนุมัติ Reject ขั้นตอนสุดท้าย (Confirm Reject)");
        } else if ((list.get(i).getAppr_status()).equals("reject") && (list.get(i).getAppr_nextcode()).equals("99")) {
            viewHolder.txt_status_view.setText("Wait Rej");
            viewHolder.txt_status_view.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.buttongrayright));
            viewHolder.txt_sta_view.setText("แจ้ง ยกเลิกใบแจ้งซ่อม (Reject)");
        } else if ((list.get(i).getAppr_status()).equals("reject") && (list.get(i).getAppr_nextcode()).equals("98")) {
            viewHolder.txt_status_view.setText("Wait Rej");
            viewHolder.txt_status_view.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.buttongrayright));
            viewHolder.txt_sta_view.setText("แจ้ง ยกเลิกใบแจ้งซ่อม (Reject)");
            String description = "";
            for (Appr_tag s : list.get(i).getAppr_tag()) {
                description += s.getDescription();
            }
            viewHolder.txt_wait_re.setVisibility(View.VISIBLE);
            viewHolder.text_1_desc.setVisibility(View.VISIBLE);
            viewHolder.text_1_desc.setText("เหตุผลการยกเลิก : ");
            viewHolder.txt_wait_re.setText(description);

        } else if ((list.get(i).getAppr_status()).equals("cancel-change-work")) {
            viewHolder.txt_status_view.setText("Change");
            viewHolder.txt_status_view.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.buttongrayright));
            viewHolder.txt_sta_view.setText("ยกเลิกการเปลี่ยนกลุ่มงาน (Cancel Change Work)");
            String description = "";
            for (Appr_tag s : list.get(i).getAppr_tag()) {
                description += s.getDescription();
            }
//            viewHolder.txt_view1.setText(order_id + status);
            viewHolder.text_desc1.setVisibility(View.VISIBLE);
            viewHolder.text_1_desc.setVisibility(View.VISIBLE);
            viewHolder.text_1_desc.setText("ปฏิเสธการเปลี่ยนกลุ่มงานด้วยเหตุผล : ");
            viewHolder.text_desc1.setText(description);
        }else if ((list.get(i).getAppr_status()).equals("sap-reject") ) {
            viewHolder.txt_status_view.setText("Sap Reject");
            viewHolder.txt_status_view.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.buttonredright));
            viewHolder.txt_sta_view.setText("ยกเลิกใบแจ้งซ่อมจากระบบ SAP (SAP Reject)");
            String CAUSE = "";
            for (Appr_tag s : list.get(i).getAppr_tag()) {
                CAUSE += s.getCAUSE();
            }

            viewHolder.text_1_desc.setTextColor(R.color.blue);
            viewHolder.text_1_desc.setVisibility(View.VISIBLE);
            viewHolder.text_1_desc.setText(CAUSE);

        }else if ((list.get(i).getAppr_status()).equals("change-work-pre") && (list.get(i).getAppr_nextcode()).equals("101")) {
            viewHolder.txt_status_view.setText("change");
            viewHolder.txt_status_view.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.buttongrayright));
            viewHolder.txt_sta_view.setText("แจ้งเปลี่ยนกลุ่มงาน (Change Work) รอหัวหน้างานอนุมัติ (ขั้นแรก)");
        }else if ((list.get(i).getAppr_status()).equals("confirm-change-work-pre") && (list.get(i).getAppr_nextcode()).equals("31")) {
            viewHolder.txt_status_view.setText("change");
            viewHolder.txt_status_view.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.buttongrayright));
            viewHolder.txt_sta_view.setText("แจ้งเปลี่ยนกลุ่มงาน (Change Work)");
            String before = "";
            String after = "";
            String desc = "";
            for (Appr_tag s : list.get(i).getAppr_tag()) {
                before += s.getBefore();
                after += s.getAfter();
                desc += s.getDesc();
            }
            viewHolder.txt_view_after.setVisibility(View.VISIBLE);
            viewHolder.txt_alt26.setVisibility(View.VISIBLE);
            viewHolder.text_desc1.setVisibility(View.VISIBLE);
            viewHolder.text_desc2.setVisibility(View.VISIBLE);
            viewHolder.text_1_desc.setVisibility(View.VISIBLE);
            viewHolder.text_2_desc.setVisibility(View.VISIBLE);
            viewHolder.text_1_desc.setText("แจ้งเปลี่ยนงานจาก : ");
            viewHolder.text_2_desc.setText("หมายเหตุ : ");
            if (before.equals("mt")) {
                viewHolder.text_desc1.setText("MECHANICAL");
            } else if (before.equals("ee")) {
                viewHolder.text_desc1.setText("ELECTRICAL");
            } else if (before.equals("cal")) {
                viewHolder.text_desc1.setText("CALIBRATION");
            } else if (before.equals("fac")) {
                viewHolder.text_desc1.setText("CIVIL");
            } else if (before.equals("fk")) {
                viewHolder.text_desc1.setText("FORKLIFT");
            } else if (before.equals("cc")) {
                viewHolder.text_desc1.setText("CCTV");
            } else if (before.equals("air")) {
                viewHolder.text_desc1.setText("AIR CONDITION");
            } else if (before.equals("de")) {
                viewHolder.text_desc1.setText("DEVELOP SYSTEM");
            }
            if (after.equals("mt")) {
                viewHolder.txt_view_after.setText("MECHANICAL");
            } else if (after.equals("ee")) {
                viewHolder.txt_view_after.setText("ELECTRICAL");
            } else if (after.equals("cal")) {
                viewHolder.txt_view_after.setText("CALIBRATION");
            } else if (after.equals("fac")) {
                viewHolder.txt_view_after.setText("CIVIL");
            } else if (after.equals("fk")) {
                viewHolder.txt_view_after.setText("FORKLIFT");
            } else if (after.equals("cc")) {
                viewHolder.txt_view_after.setText("CCTV");
            } else if (after.equals("air")) {
                viewHolder.txt_view_after.setText("AIR CONDITION");
            } else if (after.equals("de")) {
                viewHolder.txt_view_after.setText("DEVELOP SYSTEM");
            }
            viewHolder.text_desc2.setText(desc);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void CallView(List<Viewinfo> row_item) {
        list.clear();
        list.addAll(row_item);
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_time, appr_nextcode, txt_appr_name, result, appr_name, text_desc1, text_desc2, text_desc3, text_desc4, text_1_desc,
                text_2_desc, text_3_desc, text_4_desc, txt_gone, txt_sta_view, txt_alt26, txt_view_after,txt_wait_re;
        private ConstraintLayout con_dialog;
        private LinearLayout linearLayout_status;
        private Button txt_status_view;

        public ViewHolder(View view) {
            super(view);
            result = view.findViewById(R.id.txtResult);
            appr_name = view.findViewById(R.id.appr_name);
//            txt_view1 = view.findViewById(R.id.txt_view1);
            txt_status_view = view.findViewById(R.id.txt_status_info);
            text_desc1 = view.findViewById(R.id.text_desc1);
            text_desc2 = view.findViewById(R.id.text_desc2);
            text_desc3 = view.findViewById(R.id.text_desc3);
            text_desc4 = view.findViewById(R.id.text_desc4);
            text_1_desc = view.findViewById(R.id.text_1_desc);
            text_2_desc = view.findViewById(R.id.text_2_desc);
            text_3_desc = view.findViewById(R.id.text_3_desc);
            text_4_desc = view.findViewById(R.id.text_4_desc);
            txt_gone = view.findViewById(R.id.txt_gone);
            txt_alt26 = view.findViewById(R.id.txt_alt26);
            txt_view_after = view.findViewById(R.id.txt_view_after);
            txt_sta_view = view.findViewById(R.id.txt_sta_view);
            txt_wait_re = view.findViewById(R.id.txt_wait_re);
//            txt_time = view.findViewById(R.id.txt_time);
            txt_appr_name = view.findViewById(R.id.txt_appr_name);
//            appr_nextcode = view.findViewById(R.id.appr_nextcode);
//            con_dialog = view.findViewById(R.id.con_dialog);
//            linearLayout_status= view.findViewById(R.id.linearLayout_status);

        }
    }

}

