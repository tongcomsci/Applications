package com.example.supachai_tong.login;

import android.util.Log;
import android.widget.TextView;
import com.example.supachai_tong.login.Modal.core;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Core {
    public void CoreLog(String flag, String uname, final TextView text_desc1,final TextView text_1_desc, final TextView text_desc2,final TextView text_2_desc, final String description) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://lionproduction.sli")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Requestlnterface_data request = retrofit.create(Requestlnterface_data.class);
        Call<List<core>> call = request.Callcore(flag, uname);
        call.enqueue(new Callback<List<core>>() {
            @Override
            public void onResponse(Call<List<core>> call, Response<List<core>> response) {
                if (response.isSuccess()) {
                    if (description == "") {
                        text_1_desc.setText("มอบหมายงานให้ : ");
                        text_desc1.setText(response.body().get(0).getName());
                        text_2_desc.setText("รายละเอียด : ");
                        text_desc2.setText(" - ");
                    } else {
                        text_1_desc.setText("มอบหมายงานให้ : ");
                        text_desc1.setText(response.body().get(0).getName() );
                        text_2_desc.setText("รายละเอียด : ");
                        text_desc2.setText(description);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<core>> call, Throwable t) {
                Log.w("error", t);
            }
        });

    }

}
