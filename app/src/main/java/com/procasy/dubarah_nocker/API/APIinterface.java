package com.procasy.dubarah_nocker.API;

import com.procasy.dubarah_nocker.Model.Responses.AllSkillsAndLanguageResponse;
import com.procasy.dubarah_nocker.Model.Responses.CheckResponse;
import com.procasy.dubarah_nocker.Model.Responses.InfoNockerResponse;
import com.procasy.dubarah_nocker.Model.Responses.LocationResponse;
import com.procasy.dubarah_nocker.Model.Responses.LoginResponse;
import com.procasy.dubarah_nocker.Model.Responses.NearByNockerResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Omar on 6/21/2016.
 */
public interface APIinterface {
    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> Login(@Field("user_email") String email, @Field("user_password") String password,@Field("user_ud_id") String UDID);

    @FormUrlEncoded
    @POST("get_info_nocker")
    Call<InfoNockerResponse> GetInfoNocker(@Field("user_email") String email, @Field("user_ud_id") String UDID);

    @FormUrlEncoded
    @POST("get_near_by_nocker")
    Call<NearByNockerResponse> GetNearByNockers(@Field("user_email") String email, @Field("user_ud_id") String UDID,@Field("page") int page);

    @FormUrlEncoded
    @POST("get_skills")
    Call<AllSkillsAndLanguageResponse> GetAllSkills(@Field("user_email") String email, @Field("user_ud_id") String UDID);

    @FormUrlEncoded
    @POST("update_user_location")
    Call<LocationResponse> UpdateLocation(@Field("user_email") String email,
                                          @Field("user_ud_id") String UDID ,
                                          @Field("user_lat") String lat ,
                                          @Field("user_lon") String lon
                                          );
    @FormUrlEncoded
    @POST("check_unique")
    Call<CheckResponse> CheckUnique(   @Field("table") String table,
                                       @Field("column") String column ,
                                       @Field("value") String value
    );

    @FormUrlEncoded
    @POST("check_valid")
    Call<CheckResponse> CheckUnique(   @Field("user_email") String table,
                                       @Field("social") String column
    );


}
