package com.example.supachai_tong.login;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.supachai_tong.login.Modal.model_notification;
import com.example.supachai_tong.login.Modal.notifications;

import java.util.ArrayList;

public class DataAdapter_Notifi extends RecyclerView.Adapter<DataAdapter_Notifi.ViewHolder> {
    //    private List<notification> list = new ArrayList<>();
    private ArrayList<model_notification> android;
    SharedPreferences.Editor editor;
    SharedPreferences shared;
    private Context context;
    private Thread mThread;

    public DataAdapter_Notifi(ArrayList<model_notification> android) {
        this.android = android;
    }

//    public void CallView(notification row_item) {
//        list.clear();
//        list.addAll(row_item);
//        this.notifyDataSetChanged();
//    }

    @Override
    public DataAdapter_Notifi.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row_notification, viewGroup, false);
        context = viewGroup.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.txt_notifi_no_id.setText(android.get(position).getNo_id());
        holder.txt_notifi_time.setText(android.get(position).getSap_lastupdate());
        holder.txt_notifi_status.setText(android.get(position).getAppr_nextcode());


        if ((android.get(position).getAppr_nextcode()).equals("10")) {
            holder.txt_notifi_status.setText("CREATE");
        } else if ((android.get(position).getAppr_nextcode()).equals("30")) {
            holder.txt_notifi_status.setText("RELEASE");
        } else if ((android.get(position).getAppr_nextcode()).equals("31")) {
            holder.txt_notifi_status.setText("CHANGE WORK");
        } else if ((android.get(position).getAppr_nextcode()).equals("32")) {
            holder.txt_notifi_status.setText("ASSIGN");
        } else if ((android.get(position).getAppr_nextcode()).equals("33") || (android.get(position).getAppr_nextcode()).equals("41")) {
            holder.txt_notifi_status.setText("ACCEPT");
        } else if ((android.get(position).getAppr_nextcode()).equals("40")) {
            holder.txt_notifi_status.setText("CONVERT");
        } else if ((android.get(position).getAppr_nextcode()).equals("42")) {
            holder.txt_notifi_status.setText("PLANNING");
        } else if ((android.get(position).getAppr_nextcode()).equals("43")) {
            holder.txt_notifi_status.setText("CONFIRM");
        } else if ((android.get(position).getAppr_nextcode()).equals("44")) {
            holder.txt_notifi_status.setText("APPROVE");
        } else if ((android.get(position).getAppr_nextcode()).equals("45")) {
            holder.txt_notifi_status.setText("RECHECK");
        } else if ((android.get(position).getAppr_nextcode()).equals("50")) {
            holder.txt_notifi_status.setText("FINAL");
        } else if ((android.get(position).getAppr_nextcode()).equals("60")) {
            holder.txt_notifi_status.setText("CLOSE");
        } else if ((android.get(position).getAppr_nextcode()).equals("98")) {
            holder.txt_notifi_status.setText("WAIT REJECT");
        } else if ((android.get(position).getAppr_nextcode()).equals("99")) {
            holder.txt_notifi_status.setText("WAIT REJECT");
        } else if ((android.get(position).getAppr_nextcode()).equals("100")) {
            holder.txt_notifi_status.setText("REJECT");
        } else if ((android.get(position).getAppr_nextcode()).equals("101")) {
            holder.txt_notifi_status.setText("Change Work (Pre)");
        }

        holder.txt_notifi_subType.setText(android.get(position).getNoti_type().equals("mt") ? "ME" : (android.get(position).getNoti_type().toUpperCase()));
        holder.txt_notifi_title.setText(android.get(position).getS_notifi_desc());

        Log.w("Test", "Nxtcode : " + (android.get(position).getAppr_nextcode()));
        if ((android.get(position).getAppr_nextcode()).equals("30") && (android.get(position).getAssign_to().equals("true"))) {
            holder.imageView_action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context.getApplicationContext(), AssignActivity.class);
                    intent.putExtra("no_id", android.get(position).getNo_id());
                    intent.putExtra("noti_type", android.get(position).getNoti_type());
                    context.getApplicationContext().startActivity(intent);
                }
            });
        } else if ((android.get(position).getAppr_nextcode()).equals("98") && (android.get(position).getAssign_to().equals("true"))) {
            holder.imageView_action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context.getApplicationContext(), RejeckActivity.class);
                    intent.putExtra("no_id", android.get(position).getNo_id());
                    intent.putExtra("notif_no", android.get(position).getNotif_no());
                    context.getApplicationContext().startActivity(intent);
                }
            });
        } else if ((android.get(position).getAppr_nextcode()).equals("43") && (android.get(position).getAssign_to().equals("true"))) {
            holder.imageView_action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context.getApplicationContext(), ApproveActiviry.class);
                    intent.putExtra("no_id", android.get(position).getNo_id());
                    intent.putExtra("s_notifi_orderid", android.get(position).getS_notifi_orderid());
                    context.getApplicationContext().startActivity(intent);
                }
            });
        } else if ((android.get(position).getAppr_nextcode()).equals("31") && (android.get(position).getAssign_to().equals("true"))) {
            holder.imageView_action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context.getApplicationContext(), ChangeworkActivity.class);
                    intent.putExtra("no_id", android.get(position).getNo_id());
                    intent.putExtra("dev_v1", android.get(position).getDev_v1());
                    intent.putExtra("notifi_no", android.get(position).getNotif_no());
                    context.getApplicationContext().startActivity(intent);
                }
            });
        } else if ((android.get(position).getAppr_nextcode()).equals("101") && (android.get(position).getAssign_to().equals("true"))) {
            holder.imageView_action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context.getApplicationContext(), Changework_preActivity.class);
                    intent.putExtra("no_id", android.get(position).getNo_id());
                    intent.putExtra("dev_v1", android.get(position).getDev_v1());
                    intent.putExtra("notifi_no", android.get(position).getNotif_no());
                    context.getApplicationContext().startActivity(intent);

                }
            });
        }else{
            holder.imageView_action.setVisibility(View.GONE);
        }


    }


    @Override
    public int getItemCount() {
        return android.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_notifi_no_id, txt_notifi_subType, txt_notifi_status, txt_notifi_title,txt_notifi_time;
        private ImageView imageView_action;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_notifi_no_id = itemView.findViewById(R.id.txt_notifi_no_id);
            txt_notifi_subType = itemView.findViewById(R.id.txt_notifi_subType);
            txt_notifi_status = itemView.findViewById(R.id.txt_notifi_status);
            txt_notifi_title = itemView.findViewById(R.id.txt_notifi_title);
            txt_notifi_time = itemView.findViewById(R.id.txt_notifi_time);
            imageView_action = itemView.findViewById(R.id.imageView_action);
        }
    }
}

