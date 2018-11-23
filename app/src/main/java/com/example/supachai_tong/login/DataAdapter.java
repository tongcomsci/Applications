package com.example.supachai_tong.login;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.supachai_tong.login.Modal.AndroidVersion;
import com.example.supachai_tong.login.Modal.Viewinfo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private Context context;
    private RecyclerView recyclerView;
    private DataAdapter_viewinfo adapter;
    private Core core;
    private ArrayList<Viewinfo> data;
    private List<AndroidVersion> list = new ArrayList<>();
    SharedPreferences.Editor editor;
    SharedPreferences shared;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row, viewGroup, false);
        context = viewGroup.getContext();
        adapter = new DataAdapter_viewinfo();
        core = new Core();
        shared = context.getSharedPreferences("userLogin", Context.MODE_PRIVATE);
        editor = shared.edit();
        return new ViewHolder(view);
    }

    private void initViews(Dialog dialog, String no_id) {
        recyclerView = dialog.findViewById(R.id.card_recycler_view_info);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext().getApplicationContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setHasFixedSize(true);
        recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        recyclerView.setLayoutManager(new LinearLayoutManager(context.getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        loadJSON(no_id);
    }

    private void loadJSON(String flag_id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://lionproduction.sli")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Requestlnterface_data request = retrofit.create(Requestlnterface_data.class);
        Call<List<Viewinfo>> call = request.Callviewinfo(String.valueOf(flag_id));
        call.enqueue(new Callback<List<Viewinfo>>() {
            @Override
            public void onResponse(Call<List<Viewinfo>> call, Response<List<Viewinfo>> response) {
                if (response.isSuccess()) {
                    List<Viewinfo> row_item = response.body();
                    adapter.CallView(row_item);
                }
                Log.w("Error", "เชื่อมต่อสำเร็จ");
            }
            @Override
            public void onFailure(Call<List<Viewinfo>> call, Throwable t) {
                Log.w("Error", t.getMessage());
            }
        });
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final DataAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.constraint_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _Dialog(list, i);
            }
        });
        viewHolder.btn_txt_no_id.setText(list.get(i).getNo_id());
        viewHolder.notifi_no_txt.setText(list.get(i).getNotif_no());
        viewHolder.dev_v1_txt.setText(list.get(i).getDev_v1());
        viewHolder.order_txt.setText((list.get(i).getS_notifi_orderid().equals("") || list.get(i).getS_notifi_orderid() == null) ? " -" : (list.get(i).getS_notifi_orderid()));

        if (list.get(i).getS_notifi_desc().length() >= 45) {
            String detail = list.get(i).getS_notifi_desc();
            String sub_detail = detail.substring(0, 45);
            viewHolder.detail_txt.setText(sub_detail + "...");
        } else {
            viewHolder.detail_txt.setText(list.get(i).getS_notifi_desc());
        }

        if (list.get(i).getS_notifi_longtext().length() >= 40) {
            String detail = list.get(i).getS_notifi_longtext();
            String sub_detail = detail.substring(0, 40);
            viewHolder.txt_desc_main.setText(sub_detail + "...");
        } else {
            viewHolder.txt_desc_main.setText(list.get(i).getS_notifi_longtext());
        }
        viewHolder.group_txt.setText(list.get(i).getNoti_type().equals("mt") ? "ME" : (list.get(i).getNoti_type().toUpperCase()));
        viewHolder.btn_txt_status.setText(list.get(i).getAppr_nextcode());
        if ((list.get(i).getAppr_nextcode()).equals("10")) {
            viewHolder.btn_txt_status.setText("CREATE");
            viewHolder.view_status.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.buttonblue));
        } else if ((list.get(i).getAppr_nextcode()).equals("30")) {
            viewHolder.btn_txt_status.setText("RELEASE");
            viewHolder.view_status.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.buttonorange));
        } else if ((list.get(i).getAppr_nextcode()).equals("31")) {
            viewHolder.btn_txt_status.setText("CHANGE WORK");
            viewHolder.view_status.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.buttonwaitreject));
        } else if ((list.get(i).getAppr_nextcode()).equals("32")) {
            viewHolder.btn_txt_status.setText("ASSIGN");
            viewHolder.view_status.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.buttonorange));
        } else if ((list.get(i).getAppr_nextcode()).equals("33") || (list.get(i).getAppr_nextcode()).equals("41")) {
            viewHolder.btn_txt_status.setText("ACCEPT");
            viewHolder.view_status.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.buttonorange));
        } else if ((list.get(i).getAppr_nextcode()).equals("40")) {
            viewHolder.btn_txt_status.setText("CONVERT");
            viewHolder.view_status.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.buttonorange));
        } else if ((list.get(i).getAppr_nextcode()).equals("42")) {
            viewHolder.btn_txt_status.setText("PLANNING");
            viewHolder.view_status.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.buttonorange));
        } else if ((list.get(i).getAppr_nextcode()).equals("43")) {
            viewHolder.btn_txt_status.setText("CONFIRM");
            viewHolder.view_status.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.buttongreen));
        } else if ((list.get(i).getAppr_nextcode()).equals("44")) {
            viewHolder.btn_txt_status.setText("APPROVE");
            viewHolder.view_status.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.buttongreen));
        } else if ((list.get(i).getAppr_nextcode()).equals("45")) {
            viewHolder.btn_txt_status.setText("RECHECK");
            viewHolder.view_status.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.buttongreen));
        } else if ((list.get(i).getAppr_nextcode()).equals("50")) {
            viewHolder.btn_txt_status.setText("FINAL");
            viewHolder.view_status.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.buttongreen));
        } else if ((list.get(i).getAppr_nextcode()).equals("60")) {
            viewHolder.btn_txt_status.setText("CLOSE");
            viewHolder.view_status.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.buttongreen));
        } else if ((list.get(i).getAppr_nextcode()).equals("98")) {
            viewHolder.btn_txt_status.setText("WAIT REJECT");
            viewHolder.view_status.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.buttonwaitreject));
        } else if ((list.get(i).getAppr_nextcode()).equals("99")) {
            viewHolder.btn_txt_status.setText("WAIT REJECT");
            viewHolder.view_status.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.buttonwaitreject));
        } else if ((list.get(i).getAppr_nextcode()).equals("100")) {
            viewHolder.btn_txt_status.setText("REJECT");
            viewHolder.view_status.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.buttonred));
        }else if ((list.get(i).getAppr_nextcode()).equals("101")) {
            viewHolder.btn_txt_status.setText("Change Work (Pre)");
            viewHolder.view_status.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.buttonwaitreject));
        }

        if ((list.get(i).getAppr_nextcode()).equals("30")&&(list.get(i).getAssign_to().equals("true"))) {
            viewHolder.img_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu pm = new PopupMenu(context, viewHolder.img_btn);
                    pm.getMenuInflater().inflate(R.menu.popup_menu_assign, pm.getMenu());
                    pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            int id = item.getItemId();

                            if (id == R.id.assign_menu) {
                                Intent intent = new Intent(context.getApplicationContext(), AssignActivity.class);
                                intent.putExtra("no_id", list.get(i).getNo_id());
                                intent.putExtra("noti_type", list.get(i).getNoti_type());
                                context.getApplicationContext().startActivity(intent);
                                return true;
                            }
                            if (id == R.id.view_info_menu) {
                                final Dialog dialog = new Dialog(context);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.popup_viewinfo);
                                dialog.setCancelable(true);
                                initViews(dialog, String.valueOf(list.get(i).getNo_id()));
                                dialog.show();
                                return true;
                            }
                            if (id == R.id.view) {
                                _Dialog(list, i);
                                return true;
                            }
                            return true;
                        }
                    });
                    pm.show();
                }
            });
        } else if ((list.get(i).getAppr_nextcode()).equals("98")&&(list.get(i).getAssign_to().equals("true"))) {
            viewHolder.img_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu pm = new PopupMenu(context, viewHolder.img_btn);
                    pm.getMenuInflater().inflate(R.menu.popup_menu_reject, pm.getMenu());
                    pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            int id = item.getItemId();

                            if (id == R.id.reject_menu) {
                                Intent intent = new Intent(context.getApplicationContext(),RejeckActivity.class);
                                intent.putExtra("no_id", list.get(i).getNo_id());
                                intent.putExtra("notif_no", list.get(i).getNotif_no());
                                context.getApplicationContext().startActivity(intent);
                                return true;
                            }
                            if (id == R.id.view_info_menu) {
                                final Dialog dialog = new Dialog(context);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.popup_viewinfo);
                                dialog.setCancelable(true);
                                initViews(dialog, String.valueOf(list.get(i).getNo_id()));
                                dialog.show();
                                return true;
                            }
                            if (id == R.id.view) {
                                _Dialog(list, i);
                                return true;
                            }
                            return true;
                        }
                    });
                    pm.show();
                }
            });
        }else if ((list.get(i).getAppr_nextcode()).equals("43")&&(list.get(i).getAssign_to().equals("true"))) {
            viewHolder.img_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final PopupMenu pm = new PopupMenu(context, viewHolder.img_btn);
                    pm.getMenuInflater().inflate(R.menu.popup_menu_approve, pm.getMenu());
                    pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            int id = item.getItemId();
                            if (id == R.id.approve_menu) {

                                Intent intent = new Intent(context.getApplicationContext(), ApproveActiviry.class);
                                intent.putExtra("no_id", list.get(i).getNo_id());
                                intent.putExtra("s_notifi_orderid", list.get(i).getS_notifi_orderid());
                                context.getApplicationContext().startActivity(intent);

                                return true;
                            }
                            if (id == R.id.view_info_menu) {
                                final Dialog dialog = new Dialog(context);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.popup_viewinfo);
                                dialog.setCancelable(true);
                                initViews(dialog, String.valueOf(list.get(i).getNo_id()));
                                dialog.show();
                                return true;
                            }
                            if (id == R.id.view) {
                                _Dialog(list, i);
                                return true;
                            }

                            return true;
                        }
                    });
                    pm.show();
                }
            });
        } else if ((list.get(i).getAppr_nextcode()).equals("31")&&(list.get(i).getAssign_to().equals("true"))) {
            viewHolder.img_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu pm = new PopupMenu(context, viewHolder.img_btn);
                    pm.getMenuInflater().inflate(R.menu.popup_menu_change_work, pm.getMenu());
                    pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            int id = item.getItemId();
                            if (id == R.id.Change_work) {
                                Intent intent = new Intent(context.getApplicationContext(), ChangeworkActivity.class);
                                intent.putExtra("no_id", list.get(i).getNo_id());
                                intent.putExtra("dev_v1", list.get(i).getDev_v1());
                                intent.putExtra("notifi_no", list.get(i).getNotif_no());
                                context.getApplicationContext().startActivity(intent);
                                return true;
                            }
                            if (id == R.id.view_info_menu) {
                                final Dialog dialog = new Dialog(context);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.popup_viewinfo);
                                dialog.setCancelable(true);
                                initViews(dialog, String.valueOf(list.get(i).getNo_id()));
                                dialog.show();
                                return true;
                            }
                            if (id == R.id.view) {
                                _Dialog(list, i);
                                return true;
                            }
                            return true;
                        }
                    });
                    pm.show();
                }
            });
        }else if ((list.get(i).getAppr_nextcode()).equals("101")&&(list.get(i).getAssign_to().equals("true"))) {
            viewHolder.img_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu pm = new PopupMenu(context, viewHolder.img_btn);
                    pm.getMenuInflater().inflate(R.menu.popup_menu_change_work_pre, pm.getMenu());
                    pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            int id = item.getItemId();
                            if (id == R.id.Change_work_pre) {
                                Intent intent = new Intent(context.getApplicationContext(), Changework_preActivity.class);
                                intent.putExtra("no_id", list.get(i).getNo_id());
                                intent.putExtra("dev_v1", list.get(i).getDev_v1());
                                intent.putExtra("notifi_no", list.get(i).getNotif_no());
                                context.getApplicationContext().startActivity(intent);
                                return true;
                            }
                            if (id == R.id.view_info_menu) {
                                final Dialog dialog = new Dialog(context);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.popup_viewinfo);
                                dialog.setCancelable(true);
                                initViews(dialog, String.valueOf(list.get(i).getNo_id()));
                                dialog.show();
                                return true;
                            }
                            if (id == R.id.view) {
                                _Dialog(list, i);
                                return true;
                            }
                            return true;
                        }
                    });
                    pm.show();
                }
            });
        } else {
            viewHolder.img_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu pm = new PopupMenu(context, viewHolder.img_btn);
                    pm.getMenuInflater().inflate(R.menu.popup_menu_viewinfo, pm.getMenu());
                    pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            int id = item.getItemId();

                            if (id == R.id.view_info_menu) {
                                final Dialog dialog = new Dialog(context);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.popup_viewinfo);
                                dialog.setCancelable(true);
                                initViews(dialog, String.valueOf(list.get(i).getNo_id()));
                                dialog.show();
                                return true;
                            }
                            if (id == R.id.view) {
                                _Dialog(list, i);
                                return true;
                            }
                            return true;
                        }
                    });
                    pm.show();
                }
            });
        }
    }

    @SuppressLint("ResourceAsColor")
    private void _Dialog(List<AndroidVersion> list, int i) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_view);
        dialog.setCancelable(true);
        TextView txt_view_no_id, txt_view_eq, txt_view_func, txt_view_head, txt_view_desc, txt_view_dev,
                txt_view_noti_type, txt_view_s_notifi_type, txt_view_notif_no, txt_func_view, txt_eq_view,txt_name_rap;

        txt_view_no_id = dialog.findViewById(R.id.txt_view_no_id);
        txt_view_eq = dialog.findViewById(R.id.txt_view_eq);
        txt_view_func = dialog.findViewById(R.id.txt_view_func);
        txt_view_head = dialog.findViewById(R.id.txt_view_head);
        txt_view_desc = dialog.findViewById(R.id.txt_view_desc);
        txt_view_dev = dialog.findViewById(R.id.txt_view_dev);
        txt_view_noti_type = dialog.findViewById(R.id.txt_view_noti_type);
        txt_view_s_notifi_type = dialog.findViewById(R.id.txt_view_s_notifi_type);
        txt_view_notif_no = dialog.findViewById(R.id.txt_view_notif_no);
        txt_func_view = dialog.findViewById(R.id.txt_func_view);
        txt_eq_view = dialog.findViewById(R.id.txt_eq_view);
        txt_name_rap = dialog.findViewById(R.id.txt_name_rap);

        txt_view_no_id.setText(list.get(i).getNo_id());
        txt_view_head.setText(list.get(i).getS_notifi_desc());
        txt_name_rap.setText(list.get(i).getS_notifi_rep());
        txt_view_desc.setText((list.get(i).getS_notifi_longtext().equals("") || list.get(i).getS_notifi_longtext() == null) ? " - " : (list.get(i).getS_notifi_longtext()));
        txt_view_dev.setText(list.get(i).getDev_v1());
        txt_view_noti_type.setText(list.get(i).getNoti_type().equals("mt") ? "ME" : (list.get(i).getNoti_type().toUpperCase()));
        txt_view_s_notifi_type.setText(list.get(i).getS_notifi_type());
        if(list.get(i).getS_notifi_type().equals("N1 - งานซ่อมเครื่องจักร หรือ อุปกรณ์สนับสนุนการผลิตที่มีการหยุดฉุกเฉิน (Breakdown)")){
//            txt_view_s_notifi_type.setTextColor(R.color.red);
            txt_view_s_notifi_type.setTextColor(Color.parseColor("#e53a36"));
        }
        txt_view_notif_no.setText(list.get(i).getNotif_no());

        if (list.get(i).getS_notifi_equip_desc().equals("") || list.get(i).getS_notifi_equip_desc() == null) {
            txt_view_eq.setText(" - ");
            txt_eq_view.setVisibility(View.GONE);
        } else {
            txt_view_eq.setText(list.get(i).getS_notifi_equip_desc());
            txt_eq_view.setVisibility(View.VISIBLE);
            txt_eq_view.setText( (list.get(i).getS_notifi_equip().equals("") || list.get(i).getS_notifi_equip() ==null ) ? " - " : (" ("+list.get(i).getS_notifi_equip()+")"));
        }
        if (list.get(i).getS_notifi_func_desc().equals("") || list.get(i).getS_notifi_func_desc() == null) {
            txt_view_func.setText(" - ");
            txt_func_view.setVisibility(View.GONE);
        } else {
            txt_view_func.setText(list.get(i).getS_notifi_func_desc());
            txt_func_view.setVisibility(View.VISIBLE);
            txt_func_view.setText( (list.get(i).getS_notifi_func().equals("") || list.get(i).getS_notifi_func() ==null ) ? " - " : (" ("+list.get(i).getS_notifi_func()+")"));

        }
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void CallView(List<AndroidVersion> row_item) {
        list.clear();
        list.addAll(row_item);
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView group_txt, detail_txt, txt_desc_main, btn_txt_status, btn_txt_no_id, notifi_no_txt, dev_v1_txt, order_txt, text_viewdecs, text_viewdecs2;
        private ConstraintLayout constraint_main,cons_h_viewinfo;
        private ImageView img_btn;
        private CardView view_status;
        private RecyclerView card_recycler_view_info;
        private ProgressBar pgsBar;

        public ViewHolder(View view) {
            super(view);
            btn_txt_no_id = view.findViewById(R.id.btn_txt_no_id);
            btn_txt_status = view.findViewById(R.id.btn_txt_status);

            view_status = view.findViewById(R.id.view_status);
            img_btn = view.findViewById(R.id.img_btn);
            group_txt = view.findViewById(R.id.group_txt);
            detail_txt = view.findViewById(R.id.detail_txt);
            txt_desc_main = view.findViewById(R.id.txt_desc_main);
            notifi_no_txt = view.findViewById(R.id.notifi_txt);
            dev_v1_txt = view.findViewById(R.id.dev_v1_txt);
            order_txt = view.findViewById(R.id.order_txt);
            constraint_main = view.findViewById(R.id.constraint_main);
            pgsBar = view.findViewById(R.id.pbar_viewinfo);
            card_recycler_view_info = view.findViewById(R.id.card_recycler_view_info);
            cons_h_viewinfo = view.findViewById(R.id.cons_h_viewinfo);
        }
    }
}
