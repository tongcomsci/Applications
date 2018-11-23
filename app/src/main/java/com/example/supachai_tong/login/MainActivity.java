package com.example.supachai_tong.login;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.supachai_tong.login.Modal.AndroidVersion;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView recyclerView;
    private Thread mThread;
    private DataAdapter adapter;
    private TextView txt_nodata, text_namelogin;
    private Button btn;
    private ImageButton btn_main_search;
    private ConstraintLayout linearLayout_search;
    private ProgressBar pgsBar;
    private EditText ed_search;
    private Spinner dev_v1, noti_type, s_notifi, appr_nextcode, spinner_work_user;
    SharedPreferences.Editor editor;
    SharedPreferences shared;
    Calendar mCurrentDate;
    int day, month, year;
    TextView btn_date, btn_date2;
    String datebegin, dateend, stringValue_dev, stringValue_noti, stringValue_s_noti, stringValue_appr_nextcode, stringValue_datebegin, stringValue_dateend, stringassign_to;
    boolean booleanValue;
    String str_dev_v1, str_noti_type, str_s_notifi, str_appr_nextcode;
    String desc_app, string_name, string_uname, string_img;
    private Menu menu_action;
    private MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent serviceIntent = new Intent(MainActivity.this, ExampleService.class);
        stopService(serviceIntent);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        shared = getSharedPreferences("userLogin", Context.MODE_PRIVATE);
        editor = shared.edit();
        adapter = new DataAdapter();
        mCurrentDate = Calendar.getInstance();
        day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month = mCurrentDate.get(Calendar.MONTH);
        year = mCurrentDate.get(Calendar.YEAR);
        month++;
        System.out.println("Current time => " + mCurrentDate.getTime());
        SimpleDateFormat DateFormatend = new SimpleDateFormat("yyyy-MM-01");
        datebegin = DateFormatend.format(mCurrentDate.getTime());
        datebegin = year + "-" + "01" + "-" + "01";
        SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateend = DateFormat.format(mCurrentDate.getTime());
        dateend = year + "-" + month + "-" + day;

        pgsBar = findViewById(R.id.pBar);
        txt_nodata = findViewById(R.id.txt_Nodata);
        linearLayout_search = findViewById(R.id.linearLayout_search);
        ed_search = findViewById(R.id.ed_search);
        btn_main_search = findViewById(R.id.btn_main_search);


        string_name = shared.getString("name", null);
        string_uname = shared.getString("uname", null);
        string_img = shared.getString("img", null);
        Log.w("img", string_img);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getShare();

        Log.w("tt", "onCreate  " + stringassign_to);
        btn_main_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                closekeyboard();
                desc_app = String.valueOf(ed_search.getText().toString());
                if (desc_app.equals("") || desc_app == null) {
                    Log.w("Test", "Search : null");
                    loadData(stringValue_dev, stringValue_noti, stringValue_s_noti, stringValue_appr_nextcode, stringValue_datebegin, stringValue_dateend, stringassign_to, desc_app, pgsBar, recyclerView, txt_nodata, string_uname);
                } else {
                    Log.w("Test", "Search : " + desc_app);
                    loadData(stringValue_dev, stringValue_noti, stringValue_s_noti, stringValue_appr_nextcode, stringValue_datebegin, stringValue_dateend, stringassign_to, desc_app, pgsBar, recyclerView, txt_nodata, string_uname);
                }

            }
        });

        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.w("Onchang", "beforeTextChanged");
                desc_app = String.valueOf(ed_search.getText().toString());
//                if (desc_app.equals("") || desc_app == null) {
                Log.w("Test", "Search : null");
                Log.w("Test", "before : " + stringValue_dev + "   " + stringValue_noti + "   " + stringValue_s_noti + "   " + stringValue_appr_nextcode + "   " + stringValue_datebegin + "   " + stringValue_dateend);
                loadData(stringValue_dev, stringValue_noti, stringValue_s_noti, stringValue_appr_nextcode, stringValue_datebegin, stringValue_dateend, stringassign_to, desc_app, pgsBar, recyclerView, txt_nodata, string_uname);
//                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.w("Onchang", "onTextChanged");
                desc_app = String.valueOf(ed_search.getText().toString());
//                if (desc_app.equals("") || desc_app == null) {
                Log.w("Test", "Search : null");
                Log.w("Test", "Change : " + stringValue_dev + "   " + stringValue_noti + "   " + stringValue_s_noti + "   " + stringValue_appr_nextcode + "   " + stringValue_datebegin + "   " + stringValue_dateend);
                loadData(stringValue_dev, stringValue_noti, stringValue_s_noti, stringValue_appr_nextcode, stringValue_datebegin, stringValue_dateend, stringassign_to, desc_app, pgsBar, recyclerView, txt_nodata, string_uname);
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.w("Onchang", "afterTextChanged");
                desc_app = String.valueOf(ed_search.getText().toString());
//                if (desc_app.equals("") || desc_app == null) {
                Log.w("Test", "Search : null");
                Log.w("Test", "after : " + stringValue_dev + "   " + stringValue_noti + "   " + stringValue_s_noti + "   " + stringValue_appr_nextcode + "   " + stringValue_datebegin + "   " + stringValue_dateend);
                loadData(stringValue_dev, stringValue_noti, stringValue_s_noti, stringValue_appr_nextcode, stringValue_datebegin, stringValue_dateend, stringassign_to, desc_app, pgsBar, recyclerView, txt_nodata, string_uname);
//                }
            }
        });

        NavigationView navigationView = findViewById(R.id.nav_view);
        View namelogin = navigationView.getHeaderView(0);
        TextView name_user = namelogin.findViewById(R.id.text_namelogin);
        name_user.setText(string_name);
        TextView txt_uname_login = namelogin.findViewById(R.id.txt_uname_login);
        txt_uname_login.setText(string_uname);
        ImageView imageView_user = namelogin.findViewById(R.id.imageView_user);
//        Picasso.get().load("http://lionproduction.sli/pdmis/staff/leadadmin/images/misuser/3444.png").into(imageView_user);
        Picasso.get()
                .load(string_img)
                .placeholder(R.drawable.icons8_person_user)
                .error(R.drawable.icons8_person_user)
                .resize(100, 100)
                .centerCrop()
                .into(imageView_user);
        navigationView.setNavigationItemSelectedListener(this);
        initRecyclerView();
        initViews();
    }

    private void getShare() {
        stringValue_dev = shared.getString("dev_V1", "ALL");
        stringValue_noti = shared.getString("noti_type", "ALL");
        stringValue_s_noti = shared.getString("s_noti_type", "ALL");
        stringValue_appr_nextcode = shared.getString("appr_nextcode", "ALL");
        stringValue_datebegin = shared.getString("datebegin", year + "-01-01");
        stringValue_dateend = shared.getString("dateend", dateend);
        stringassign_to = shared.getString("assign_to", "1");
        booleanValue = shared.getBoolean("booleanKey", false);
    }

    public void onStart() {
        super.onStart();
        Intent serviceIntent = new Intent(MainActivity.this, ExampleService.class);
        stopService(serviceIntent);

    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    public void onStop() {
        super.onStop();
        Intent serviceIntent = new Intent(MainActivity.this, ExampleService.class);
        serviceIntent.putExtra("inputExtra", "test");
        stopService(serviceIntent);
        ContextCompat.startForegroundService(this, serviceIntent);
        mThread.interrupt();
    }

    public void onPause() {
        super.onPause();
        Intent serviceIntent = new Intent(MainActivity.this, ExampleService.class);
        serviceIntent.putExtra("inputExtra", "test");
        stopService(serviceIntent);
        ContextCompat.startForegroundService(this, serviceIntent);
        mThread.interrupt();
    }

    private void initViews() {

        mThread = new Thread() {
            public void run() {
                super.run();
                while (true) {
                    try {
                        getShare();
                        Log.w("tt", "thread   " + stringassign_to);
                        desc_app = String.valueOf(ed_search.getText().toString());
//
                        if (desc_app.equals("") || desc_app == null) {
                            Log.w("Test", "Search : null");
                            loadData(stringValue_dev, stringValue_noti, stringValue_s_noti, stringValue_appr_nextcode, stringValue_datebegin, stringValue_dateend, stringassign_to, desc_app, pgsBar, recyclerView, txt_nodata, string_uname);
                        } else {
                            Log.w("Test", "Search : " + desc_app);
                            loadData(stringValue_dev, stringValue_noti, stringValue_s_noti, stringValue_appr_nextcode, stringValue_datebegin, stringValue_dateend, stringassign_to, desc_app, pgsBar, recyclerView, txt_nodata, string_uname);
                        }

                        Log.w("Test", stringassign_to);
//                        Log.w("Test", stringValue_dev + "   " + stringValue_noti + "   " + stringValue_s_noti + "   " + stringValue_appr_nextcode + "   " + stringValue_datebegin + "   " + stringValue_dateend);
                        sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        mThread.start();
    }

    private void loadData(String dev, String noti, String s_noti, String appr_nextcode, String datestart, String dateend, String special, String text, final ProgressBar pgsBar, final RecyclerView recyclerView, final TextView txt_nodata, String uname) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://lionproduction.sli")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Requestlnterface_data request = retrofit.create(Requestlnterface_data.class);
        Call<List<AndroidVersion>> call = request.Calldata(dev, noti, s_noti, appr_nextcode, datestart, dateend, uname, special, text);
        call.enqueue(new Callback<List<AndroidVersion>>() {
            @Override
            public void onResponse(Call<List<AndroidVersion>> call, Response<List<AndroidVersion>> response) {
                if (response.isSuccess()) {
                    List<AndroidVersion> row_item = response.body();
                    adapter.CallView(row_item);

//                    if (row_item.get(0).getAlert().equals("0")){
//                        menu_action.findItem(R.id.action_notification).setIcon(R.drawable.ic_notifications_gray);
//                    }else{
//                        menu_action.findItem(R.id.action_notification).setIcon(R.drawable.ic_notification);
//                    }
                    if (row_item.size() == 0) {
                        pgsBar.setVisibility(View.GONE);
                        MainActivity.this.recyclerView.setVisibility(View.GONE);
                        MainActivity.this.txt_nodata.setVisibility(View.VISIBLE);
                    } else {
//                        Log.w("Log",row_item.get(0).getAlert());
                        pgsBar.setVisibility(View.GONE);
                        MainActivity.this.recyclerView.setVisibility(View.VISIBLE);
                        MainActivity.this.txt_nodata.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<AndroidVersion>> call, Throwable t) {
                Log.w("Test", "error" + t);

            }
        });
    }

    private void closekeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            Log.w("Test", "Enter");
            closekeyboard();
            desc_app = String.valueOf(ed_search.getText().toString());
//
            if (desc_app.equals("") || desc_app == null) {
                Log.w("Test", "Search : null");
                loadData(stringValue_dev, stringValue_noti, stringValue_s_noti, stringValue_appr_nextcode, stringValue_datebegin, stringValue_dateend, stringassign_to, desc_app, pgsBar, recyclerView, txt_nodata, string_uname);
            } else {
                Log.w("Test", "Search : " + desc_app);
                loadData(stringValue_dev, stringValue_noti, stringValue_s_noti, stringValue_appr_nextcode, stringValue_datebegin, stringValue_dateend, stringassign_to, desc_app, pgsBar, recyclerView, txt_nodata, string_uname);
            }
        }
        return super.dispatchKeyEvent(event);
    }


    @Override//ปุ่ม แบล็กย้อนกลับ
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Logout? ");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Closeall();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
//        menu_action =menu;
//
//        initViews();
        return true;
    }

    private String reFormatDate(String dateIn) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        Date date = simpleDateFormat.parse(dateIn);
        simpleDateFormat = new SimpleDateFormat("dd/mm/yyyy");
        return simpleDateFormat.format(date);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // SearchPopUp
        if (id == R.id.action_notification) {
            Intent intent = new Intent(this, NotificationActivity.class);
            startActivity(intent);

        }
        if (id == R.id.action_search) {
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.popup);
            dialog.setCancelable(true);
            getShare();
            stringValue_datebegin = shared.getString("datebegin", datebegin);
            stringValue_dateend = shared.getString("dateend", dateend);

            dev_v1 = dialog.findViewById(R.id.spinner);
            noti_type = dialog.findViewById(R.id.spinner_noti);
            s_notifi = dialog.findViewById(R.id.spinner_s_notifi);
            appr_nextcode = dialog.findViewById(R.id.spinner_status);
            spinner_work_user = dialog.findViewById(R.id.spinner_work_user);
            btn_date = dialog.findViewById(R.id.btn_date);
            btn_date2 = dialog.findViewById(R.id.btn_date2);


            Intent intent = getIntent();
            String[] user_assign = {"เกี่ยวข้อง", "ที่ต้องทำ"};
            final ArrayAdapter<String> adapter_user_assign = new ArrayAdapter<>(this,
                    R.layout.spinner_item, user_assign);
            spinner_work_user.setAdapter(adapter_user_assign);

            String[] dev = intent.getStringArrayExtra("DEV_V1");
            if (dev == null) {
                str_dev_v1 = shared.getString("DEV_V1", null);
                dev = str_dev_v1.split(",");
            }
            final ArrayAdapter<String> adapter_dev = new ArrayAdapter<>(this,
                    R.layout.spinner_item, dev);
            dev_v1.setAdapter(adapter_dev);

            String[] noti = intent.getStringArrayExtra("NOTI_TYPE");
            if (noti == null) {
                str_noti_type = shared.getString("NOTI_TYPE", null);
                noti = str_noti_type.split(",");
            }
            for (int i = 1; i < noti.length; i++) {
                noti[i] = (noti[i].toUpperCase().equals("MT") ? "ME" : noti[i].toUpperCase());
            }
            final ArrayAdapter<String> adapter_noti = new ArrayAdapter<String>(this,
                    R.layout.spinner_item, noti);
            noti_type.setAdapter(adapter_noti);


            String[] s_notifi_type = intent.getStringArrayExtra("S_NOTIFI");
            if (s_notifi_type == null) {
                str_s_notifi = shared.getString("S_NOTIFI", null);
                s_notifi_type = str_s_notifi.split(",");
            }
            final ArrayAdapter<String> adapter_s_notifi = new ArrayAdapter<String>(this,
                    R.layout.spinner_item, s_notifi_type);
            s_notifi.setAdapter(adapter_s_notifi);

            String[] appr_nextcode_ = intent.getStringArrayExtra("APPR_NEXTCODE");
            if (appr_nextcode_ == null) {
                str_appr_nextcode = shared.getString("APPR_NEXTCODE", null);
                appr_nextcode_ = str_appr_nextcode.split(",");
            }
            for (int i = 1; i < appr_nextcode_.length; i++) {
                appr_nextcode_[i] = appr_nextcode_[i].toUpperCase();
            }
            final ArrayAdapter<String> adapter_appr_nextcode = new ArrayAdapter<String>(this,
                    R.layout.spinner_item, appr_nextcode_);
            appr_nextcode.setAdapter(adapter_appr_nextcode);
            /*END Spinner appr_nextcode*/

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d = null;
            try {
                d = sdf.parse(stringValue_datebegin);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            final Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            btn_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            month++;
                            btn_date.setText(dayOfMonth + "/" + month + "/" + year);
                            datebegin = year + "-" + month + "-" + dayOfMonth;
                        }
                    }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
                    datePickerDialog.show();
                }
            });
            Date de = null;
            try {
                de = sdf.parse(stringValue_dateend);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            final Calendar cal_e = Calendar.getInstance();
            cal_e.setTime(de);
            btn_date2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            month++;
                            btn_date2.setText(dayOfMonth + "/" + month + "/" + year);
                            dateend = year + "-" + month + "-" + dayOfMonth;
                        }
                    }, cal_e.get(Calendar.YEAR), cal_e.get(Calendar.MONTH), cal_e.get(Calendar.DATE));
                    datePickerDialog.show();
                }
            });

            stringValue_dev = shared.getString("dev_V1", "ALL");
            stringValue_noti = shared.getString("noti_type", "ALL");
            stringValue_s_noti = shared.getString("s_noti_type", "ALL");
            stringValue_appr_nextcode = shared.getString("appr_nextcode", "ALL");
//            stringassign_to = shared.getString("assign_to", "1");
            booleanValue = shared.getBoolean("booleanKey", false);
            /*SET List Array */
            List<String> stringList_assign = new ArrayList<>(Arrays.asList(user_assign));
            List<String> stringList_dev = new ArrayList<>(Arrays.asList(dev));
            List<String> stringList_noti = new ArrayList<>(Arrays.asList(noti));
            List<String> stringList_s_noti = new ArrayList<>(Arrays.asList(s_notifi_type));
            List<String> stringList_appr_nextcode = new ArrayList<>(Arrays.asList(appr_nextcode_));
            /*END List Array */

            dev_v1.setSelection(stringList_dev.indexOf(stringValue_dev));
            noti_type.setSelection(stringList_noti.indexOf(stringValue_noti));
            s_notifi.setSelection(stringList_s_noti.indexOf(stringValue_s_noti));
            if (stringValue_appr_nextcode.equals("10")) {
                stringValue_appr_nextcode = "CREATE";
            } else if (stringValue_appr_nextcode.equals("30")) {
                stringValue_appr_nextcode = "RELEASE";
            } else if (stringValue_appr_nextcode.equals("32")) {
                stringValue_appr_nextcode = "ASSIGN";
            } else if (stringValue_appr_nextcode.equals("33")) {
                stringValue_appr_nextcode = "ACCEPT";
            } else if (stringValue_appr_nextcode.equals("40")) {
                stringValue_appr_nextcode = "CONVERT";
            } else if (stringValue_appr_nextcode.equals("42")) {
                stringValue_appr_nextcode = "PLANNING";
            } else if (stringValue_appr_nextcode.equals("43")) {
                stringValue_appr_nextcode = "CONFIRM";
            } else if (stringValue_appr_nextcode.equals("44")) {
                stringValue_appr_nextcode = "APPROVE";
            } else if (stringValue_appr_nextcode.equals("45")) {
                stringValue_appr_nextcode = "RECHECK";
            } else if (stringValue_appr_nextcode.equals("50")) {
                stringValue_appr_nextcode = "FINAL-CONFIRM";
            } else if (stringValue_appr_nextcode.equals("60")) {
                stringValue_appr_nextcode = "CLOSE";
            } else if (stringValue_appr_nextcode.equals("100")) {
                stringValue_appr_nextcode = "REJECT";
            }
            appr_nextcode.setSelection(stringList_appr_nextcode.indexOf(stringValue_appr_nextcode));
            spinner_work_user.setSelection(Integer.parseInt(stringassign_to));
            Log.w("tt", "spinner " + stringassign_to);

            if (stringValue_datebegin == null) {
                btn_date.setText("1" + "/" + month + "/" + year);
                btn_date2.setText(day + "/" + month + "/" + year);
            } else {
                try {
                    btn_date.setText(reFormatDate(stringValue_datebegin));
                    btn_date2.setText(reFormatDate(stringValue_dateend));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            btn = dialog.findViewById(R.id.button);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pgsBar.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    txt_nodata.setVisibility(View.GONE);

                    String appr_nextcode_ = appr_nextcode.getSelectedItem().toString();
                    if (appr_nextcode_.equals("CREATE")) {
                        appr_nextcode_ = "10";
                    } else if (appr_nextcode_.equals("RELEASE")) {
                        appr_nextcode_ = "30";
                    } else if (appr_nextcode_.equals("CHANGE WORK")) {
                        appr_nextcode_ = "31";
                    } else if (appr_nextcode_.equals("ASSIGN")) {
                        appr_nextcode_ = "32";
                    } else if (appr_nextcode_.equals("ACCEPT")) {
                        appr_nextcode_ = "33";
                    } else if (appr_nextcode_.equals("CONVERT")) {
                        appr_nextcode_ = "40";
                    } else if (appr_nextcode_.equals("PLANNING")) {
                        appr_nextcode_ = "42";
                    } else if (appr_nextcode_.equals("CONFIRM")) {
                        appr_nextcode_ = "43";
                    } else if (appr_nextcode_.equals("APPROVE")) {
                        appr_nextcode_ = "44";
                    } else if (appr_nextcode_.equals("RECHECK")) {
                        appr_nextcode_ = "45";
                    } else if (appr_nextcode_.equals("FINAL-CONFIRM")) {
                        appr_nextcode_ = "50";
                    } else if (appr_nextcode_.equals("CLOSE")) {
                        appr_nextcode_ = "60";
                    } else if (appr_nextcode_.equals("REJECT")) {
                        appr_nextcode_ = "100";
                    }

                    String work_user_assign = spinner_work_user.getSelectedItem().toString();
                    if (work_user_assign.equals("เกี่ยวข้อง")) {
                        work_user_assign = "0";
                    } else if (work_user_assign.equals("ที่ต้องทำ")) {
                        work_user_assign = "1";
                    }

                    Log.w("tt", "ITEM : " + work_user_assign);
                    /*SET shared-preferences*/
                    editor.putString("dev_V1", dev_v1.getSelectedItem().toString());
                    editor.putString("noti_type", noti_type.getSelectedItem().toString());
                    editor.putString("s_noti_type", s_notifi.getSelectedItem().toString());
                    editor.putString("appr_nextcode", appr_nextcode_);
                    editor.putString("assign_to", work_user_assign);
                    editor.putString("datebegin", datebegin);
                    editor.putString("dateend", dateend);
                    editor.putBoolean("booleanKey", true);
                    editor.commit();
                    getShare();
                    loadData(stringValue_dev, stringValue_noti, stringValue_s_noti, stringValue_appr_nextcode, stringValue_datebegin, stringValue_dateend, stringassign_to, desc_app, pgsBar, recyclerView, txt_nodata, string_uname);
                    dialog.dismiss();
                }
            });

            dialog.show();

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_manage) {
            // Handle the camera action
        } else if (id == R.id.nav_exit) {// ฟังก์ชั่น logout

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setMessage("Do you want to Logout? ");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Closeall();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void Closeall() {
        editor.clear();
        editor.commit();
        mThread.interrupt();
        Intent serviceIntent = new Intent(MainActivity.this, ExampleService.class);
        stopService(serviceIntent);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this);
        builder.setAutoCancel(true);
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
