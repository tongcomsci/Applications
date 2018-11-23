package com.example.supachai_tong.login;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.supachai_tong.login.Modal.Detail_work_cntr;

import java.util.ArrayList;

public class DataAdapter_approve extends RecyclerView.Adapter<DataAdapter_approve.ViewHolder> {
    private ArrayList<Detail_work_cntr> android;
    private Context context;
    private String startDate, year, month, day, startTime,
            hour, minute, second, endDate, yearend,
            monthend, dayend, endTime, hourend, minuteend, secondend;

    public DataAdapter_approve(ArrayList<Detail_work_cntr> android) {
        this.android = android;
    }



    @Override
    public DataAdapter_approve.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row_approve, viewGroup, false);
        return new DataAdapter_approve.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter_approve.ViewHolder viewHolder, int i) {

        viewHolder.txt_opac.setText(android.get(i).getACTIVITY());
        viewHolder.txt_work_cntr.setText("(" + android.get(i).getWORK_CNTR() + ")");
        viewHolder.txt_cont_key.setText(android.get(i).getCONTROL_KEY());
        viewHolder.txt_operation.setText(android.get(i).getDESCRIPTION());
        viewHolder.s_name_approve.setText(android.get(i).getS_NAME());

        startDate = (android.get(i).getEXEC_START_DATE());
        if (startDate == null) {
            viewHolder.txt_start_date.setText("-");
        } else {
            year = startDate.substring(0, 4);
            month = startDate.substring(4, 6);
            day = startDate.substring(6, 8);
            viewHolder.txt_start_date.setText(day + "-" + month + "-" + year + " ");
        }

        startTime = android.get(i).getEXEC_START_TIME();
        if (startTime == null) {
            viewHolder.txt_start_time.setText("-");
        } else {
            hour = startTime.substring(0, 2);
            minute = startTime.substring(2, 4);
            second = startTime.substring(4, 6);
            viewHolder.txt_start_time.setText(hour + ":" + minute + ":" + second);
        }

        endDate = android.get(i).getEXEC_FIN_DATE();
        if (endDate == null) {
            viewHolder.txt_end_date.setText("-");
        } else {
            yearend = endDate.substring(0, 4);
            monthend = endDate.substring(4, 6);
            dayend = endDate.substring(6, 8);
            viewHolder.txt_end_date.setText(dayend + "-" + monthend + "-" + yearend + " ");
        }

        endTime = android.get(i).getEXEC_FIN_TIME();
        if (endTime == null) {
            viewHolder.txt_end_time.setText("-");
        } else {
            hourend = endTime.substring(0, 2);
            minuteend = endTime.substring(2, 4);
            secondend = endTime.substring(4, 6);
            viewHolder.txt_end_time.setText(hourend + ":" + minuteend + ":" + secondend);
        }

        viewHolder.txt_sta.setText(android.get(i).getCONFIRM());
        if ((android.get(i).getCONFIRM()).equals("1")) {
            viewHolder.txt_sta.setVisibility(View.VISIBLE);
            viewHolder.txt_sta2.setVisibility(View.GONE);
            viewHolder.txt_sta.setText("สรุปงานแล้ว");

        } else if ((android.get(i).getCONFIRM()).equals("0")) {
            viewHolder.txt_sta.setVisibility(View.GONE);
            viewHolder.txt_sta2.setVisibility(View.VISIBLE);
            viewHolder.txt_sta.setText("ยังไม่สรุปงาน");
        }

    }

    @Override
    public int getItemCount() {
        return android.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_opac, txt_work_cntr, txt_plant, txt_cont_key,
                txt_operation, txt_time_work, txt_start_date,
                txt_start_time, txt_end_date, txt_end_time, s_name_approve;
        private Button txt_sta, txt_sta2;

        public ViewHolder(View view) {
            super(view);

            txt_opac = view.findViewById(R.id.txt_opac);
            txt_work_cntr = view.findViewById(R.id.txt_work_cntr);
            txt_cont_key = view.findViewById(R.id.txt_cont_key);
            txt_operation = view.findViewById(R.id.txt_operation);
            txt_start_date = view.findViewById(R.id.txt_start_date);
            txt_start_time = view.findViewById(R.id.txt_start_time);
            txt_end_date = view.findViewById(R.id.txt_end_date);
            txt_end_time = view.findViewById(R.id.txt_end_time);
            txt_sta = view.findViewById(R.id.txt_sta);
            txt_sta2 = view.findViewById(R.id.txt_sta2);
            s_name_approve = view.findViewById(R.id.s_name_approve);
//            txt_plant = view.findViewById(R.id.txt_plant);
//            txt_time_work = view.findViewById(R.id.txt_time_work);

        }
    }

}

