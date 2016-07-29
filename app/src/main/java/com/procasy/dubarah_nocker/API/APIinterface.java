package com.procasy.dubarah_nocker.API;

import com.procasy.dubarah_nocker.Model.Responses.InfoNockerResponse;
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
    Call<LoginResponse> Login(@Field("user_email") String email, @Field("user_password") String password);

    @FormUrlEncoded
    @POST("get_info_nocker")
    Call<InfoNockerResponse> GetInfoNocker(@Field("user_email") String email, @Field("user_password") String password);

    @FormUrlEncoded
    @POST("get_near_by_nocker")
    Call<NearByNockerResponse> GetNearByNockers(@Field("user_email") String email, @Field("user_password") String password);


}
