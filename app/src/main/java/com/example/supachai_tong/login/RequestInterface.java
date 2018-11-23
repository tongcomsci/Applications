package com.example.supachai_tong.login;

import com.example.supachai_tong.login.Modal.login;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RequestInterface {
    @FormUrlEncoded
    @POST("/pdmis/include/sap/components/android/login.php")
    Call<login> CallLogin(@Field("user") String user,
                          @Field("pass") String pass);

}
