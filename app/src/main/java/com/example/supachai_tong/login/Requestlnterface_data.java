package com.example.supachai_tong.login;

import com.example.supachai_tong.login.Modal.AndroidVersion;
import com.example.supachai_tong.login.Modal.Cancel_Changwork;
import com.example.supachai_tong.login.Modal.Changwork;
import com.example.supachai_tong.login.Modal.Changworkpre;
import com.example.supachai_tong.login.Modal.Confirm_Changwork;
import com.example.supachai_tong.login.Modal.Confirm_Changwork_pre;
import com.example.supachai_tong.login.Modal.Confirm_Changwork_pre_tag;
import com.example.supachai_tong.login.Modal.Confirm_Changwork_tag;
import com.example.supachai_tong.login.Modal.JSONResponse;
import com.example.supachai_tong.login.Modal.Viewinfo;
import com.example.supachai_tong.login.Modal.approve;
import com.example.supachai_tong.login.Modal.approve_true;
import com.example.supachai_tong.login.Modal.assign;
import com.example.supachai_tong.login.Modal.core;
import com.example.supachai_tong.login.Modal.noreject;
import com.example.supachai_tong.login.Modal.notifi;
import com.example.supachai_tong.login.Modal.approve_false;
import com.example.supachai_tong.login.Modal.notification;
import com.example.supachai_tong.login.Modal.reject;
import com.example.supachai_tong.login.Modal.savechangepre;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Requestlnterface_data {
    @FormUrlEncoded
    @POST("/pdmis/include/sap/components/android/data.php")
    Call<List<AndroidVersion>> Calldata(@Field("dev_v1") String dev,
                                        @Field("noti_type") String noti,
                                        @Field("s_notifi_type") String s_noti,
                                        @Field("appr_nextcode") String appr_nextcode,
                                        @Field("datestart") String datestart,
                                        @Field("dateend") String dateend,
                                        @Field("uname") String uname,
                                        @Field("special") String special,
                                        @Field("text") String text);

    @FormUrlEncoded
    @POST("/pdmis/include/sap/components/android/notifi.php")
    Call<notifi> Callnotifi(@Field("no_id") String no_id,
                            @Field("stype") String stype);

    @FormUrlEncoded
    @POST("/pdmis/include/sap/components/android/core.php")
    Call<JSONResponse> Callnotification(@Field("flag") String flag,
                                        @Field("uname") String uname,
                                        @Field("special") String special);

    @FormUrlEncoded
    @POST("/pdmis/include/sap/components/android/core.php")
    Call<notification> Callnotificationservice(@Field("flag") String flag,
                                               @Field("uname") String uname,
                                               @Field("special") String special);


    @FormUrlEncoded
    @POST("/pdmis/include/sap/components/android/core.php")
    Call<List<reject>> Calllondreject(@Field("flag") String flag,
                                @Field("no_id") String no_id);


    @FormUrlEncoded
    @POST("/pdmis/include/sap/components/android/viewinfo.php")
    Call<List<Viewinfo>> Callviewinfo(@Field("no_id") String no_id);

    @FormUrlEncoded
    @POST("/pdmis/include/sap/components/android/approve.php")
    Call<approve> Callapprove(@Field("no_id") String no_id,
                              @Field("s_notifi_orderid") String s_notifi_orderid);

    @FormUrlEncoded
    @POST("/pdmis/include/sap/components/android/core.php")
    Call<List<core>> Callcore(@Field("flag") String flag,
                              @Field("uname") String uname);

    @FormUrlEncoded
    @POST("/pdmis/include/model-mtadmin/components/service/ajax_mtworkorder.php")
    Call<assign> Callassign(@Field("flag") String flag,
                                  @Field("no_id") String on_id,
                                  @Field("uname") String uname,
                                  @Field("s_name") String s_name,
                                  @Field("name") String name,
                                  @Field("dev_v1") String dev_v1,
                                  @Field("position") String position,
                                  @Field("description") String description,
                                @Field("assign") String assign);

    @FormUrlEncoded
    @POST("/pdmis/include/model-mtadmin/components/service/ajax_mtworkorder.php")
    Call<approve_false> Callapprove_false(@Field("flag") String flag,
                                                @Field("no_id") String on_id,
                                                @Field("description") String description,
                                                @Field("order_id") String order_id,
                                                @Field("uname") String uname,
                                                @Field("s_name") String s_name,
                                                @Field("name") String name,
                                                @Field("dev_v1") String dev_v1,
                                                @Field("position") String position);

    @FormUrlEncoded
    @POST("/pdmis/include/model-mtadmin/components/service/ajax_mtworkorder.php")
    Call<Changwork> CallChangwork(@Field("flag") String flag,
                                        @Field("step") String step,
                                        @Field("notifi_no") String notifi_no,
                                        @Field("no_id") String on_id,
                                        @Field("before") String work_default,
                                        @Field("after") String work,
                                        @Field("desc") String desc,
                                        @Field("dev_v1") String dev_v1,
                                        @Field("s_name") String s_name,
                                        @Field("uname") String uname,
                                        @Field("name") String name,
                                        @Field("position") String position);


    @FormUrlEncoded
    @POST("/pdmis/include/model-mtadmin/components/service/ajax_mtworkorder.php")
    Call<Confirm_Changwork> CallConfirm_Changwork(@Field("flag") String flag,
                                                        @Field("no_id") String on_id,
                                                        @Field("dev_v1") String dev_v1,
                                                        @Field("notifi_no") String notifi_no,
                                                        @Field("before") String before,
                                                        @Field("after") String after,
                                                        @Field("desc") String desc,
                                                        @Field("s_name") String s_name,
                                                        @Field("uname") String uname,
                                                        @Field("name") String name,
                                                        @Field("position") String position);


    @FormUrlEncoded
    @POST("/pdmis/include/model-mtadmin/components/service/ajax_mtworkorder.php")
    Call<Cancel_Changwork> CallCancel_Changwork(@Field("flag") String flag,
                                                @Field("no_id") String on_id,
                                                @Field("before") String before,
                                                @Field("after") String after,
                                                @Field("description") String description,
                                                @Field("s_name") String s_name,
                                                @Field("uname") String uname,
                                                @Field("name") String name,
                                                @Field("dev_v1") String dev_v1,
                                                @Field("position") String position);


    @FormUrlEncoded
    @POST("/pdmis/include/sap/components/android/core.php")
    Call<List<Confirm_Changwork_tag>> CallConfirm_Changwork_tag(@Field("flag") String flag,
                                                                @Field("no_id") String on_id);


    @FormUrlEncoded
    @POST("/pdmis/include/sap/components/android/core.php")
    Call<List<Confirm_Changwork_pre_tag>> CallConfirm_Changwork_pre_tag(@Field("flag") String flag,
                                                                        @Field("no_id") String on_id);


    @FormUrlEncoded
    @POST("/pdmis/include/model-mtadmin/components/service/ajax_mtworkorder.php")
    Call<savechangepre> Callsavechangepre(@Field("flag") String flag,
                                          @Field("no_id") String no_id,
                                          @Field("desc") String desc,
                                          @Field("dev_v1") String dev_v1,
                                          @Field("s_name") String s_name,
                                          @Field("uname") String uname,
                                          @Field("name") String name,
                                          @Field("position") String position);



    @FormUrlEncoded
    @POST("/pdmis/include/model-mtadmin/components/service/ajax_mtworkorder.php")
    Call<Changworkpre> CallChangworkpre(@Field("flag") String flag,
                                        @Field("no_id") String on_id,
                                        @Field("before") String before,
                                        @Field("after") String after,
                                        @Field("desc") String desc,
                                        @Field("dev_v1") String dev_v1,
                                        @Field("s_name") String s_name,
                                        @Field("uname") String uname,
                                        @Field("name") String name,
                                        @Field("position") String position);



    @FormUrlEncoded
    @POST("/pdmis/include/model-mtadmin/components/service/ajax_mtworkorder.php")
    Call<Confirm_Changwork_pre> CallConfirm_Changwork_pre(@Field("flag") String flag,
                                                          @Field("no_id") String on_id,
                                                          @Field("dev_v1") String dev_v1,
                                                          @Field("notifi_no") String notifi_no,
                                                          @Field("before") String before,
                                                          @Field("after") String after,
                                                          @Field("desc") String desc,
                                                          @Field("s_name") String s_name,
                                                          @Field("uname") String uname,
                                                          @Field("name") String name,
                                                          @Field("position") String position,
                                                          @Field("assign") String assign);


    @FormUrlEncoded
    @POST("/pdmis/include/model-mtadmin/components/service/ajax_mtworkorder.php")
    Call<approve_true> Callapprove_true(@Field("flag") String flag,
                                        @Field("no_id") String on_id,
                                        @Field("uname") String uname,
                                        @Field("s_name") String s_name,
                                        @Field("name") String name,
                                        @Field("dev_v1") String dev_v1,
                                        @Field("position") String position);



    @FormUrlEncoded
    @POST("/pdmis/include/model-mtadmin/components/service/ajax_mtworkorder.php")
    Call<noreject> CallNoReject(@Field("flag") String flag,
                                @Field("no_id") String on_id,
                                @Field("act")String act,
                                @Field("notifi_no")String notifi_no,
                                @Field("uname") String uname,
                                @Field("s_name") String s_name,
                                @Field("name") String name,
                                @Field("dev_v1") String dev_v1,
                                @Field("position") String position);

}
