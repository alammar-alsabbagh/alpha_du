package com.procasy.dubarah_nocker.API;

import com.procasy.dubarah_nocker.Model.JobModel;
import com.procasy.dubarah_nocker.Model.Responses.AllSkillsAndLanguageResponse;
import com.procasy.dubarah_nocker.Model.Responses.CheckResponse;
import com.procasy.dubarah_nocker.Model.Responses.InfoNockerResponse;
import com.procasy.dubarah_nocker.Model.Responses.LocationResponse;
import com.procasy.dubarah_nocker.Model.Responses.LoginResponse;
import com.procasy.dubarah_nocker.Model.Responses.NearByNockerResponse;
import com.procasy.dubarah_nocker.Model.Responses.ResponseJob;
import com.procasy.dubarah_nocker.Model.Responses.SocialSignupResponse;
import com.procasy.dubarah_nocker.Model.Responses.UserInfoResponse;

import org.json.JSONArray;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Omar on 6/21/2016.
 */
public interface APIinterface {
    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> Login(@Field("user_email") String email, @Field("user_password") String password,
                              @Field("user_ud_id") String UDID);

    @FormUrlEncoded
    @POST("get_info_nocker")
    Call<InfoNockerResponse> GetInfoNocker(@Field("user_email") String email, @Field("user_ud_id") String UDID);


    @FormUrlEncoded
    @POST("near_jobs")
    Call<ResponseJob> GetJobs(@Field("user_email") String email,
                              @Field("user_ud_id") String UDID, @Field("page") int page);

    @FormUrlEncoded
    @POST("get_near_by_nocker")
    Call<NearByNockerResponse> GetNearByNockers(@Field("user_email") String email,
                                                @Field("user_ud_id") String UDID, @Field("page") int page);

    @FormUrlEncoded
    @POST("get_skills")
    Call<AllSkillsAndLanguageResponse> GetAllSkills(@Field("user_email") String email, @Field("user_ud_id") String UDID);

    @FormUrlEncoded
    @POST("update_user_location")
    Call<LocationResponse> UpdateLocation(@Field("user_email") String email,
                                          @Field("user_ud_id") String UDID,
                                          @Field("user_lat") String lat,
                                          @Field("user_lon") String lon
    );

    @FormUrlEncoded
    @POST("check_unique")
    Call<CheckResponse> CheckUnique(@Field("table") String table,
                                    @Field("column") String column,
                                    @Field("value") String value
    );

    @FormUrlEncoded
    @POST("check_valid")
    Call<CheckResponse> CheckUnique(@Field("user_email") String table,
                                    @Field("social") String column
    );

    @FormUrlEncoded
    @POST("social_signup")
    Call<SocialSignupResponse> SocialSignup(@Field("user_email") String user_email,
                                            @Field("user_fname") String user_fname,
                                            @Field("user_lname") String user_lname,
                                            @Field("user_ud_id") String user_ud_id,
                                            @Field("user_social_type") String user_social_type,
                                            @Field("user_gender") String user_gender,
                                            @Field("user_img") String user_img,
                                            @Field("user_birthday") String user_birthday
    );

    @FormUrlEncoded
    @POST("add_user_skill")
    Call<SocialSignupResponse> AddUserSkill(@Field("user_email") String user_email,
                                            @Field("user_ud_id") String user_ud_id,
                                            @Field("skills") JSONArray skills
    );


    @FormUrlEncoded
    @POST("add_user_language")
    Call<SocialSignupResponse> AddUserLanguages(@Field("user_email") String user_email,
                                                @Field("user_ud_id") String user_ud_id,
                                                @Field("langs") JSONArray skills
    );


    @Multipart
    @POST("ask_for_help")
    Call<ResponseBody> AskForHelp
            (@Part MultipartBody.Part hr_voice_record,
             @Part("user_email") RequestBody user_email,
             @Part("user_ud_id") RequestBody user_ud_id,
             @Part("hr_skill_id") RequestBody hr_skill_id,
             @Part("hr_language") RequestBody hr_language,
             @Part("hr_description") RequestBody hr_description,
             @Part("hr_est_date") RequestBody hr_est_date,
             @Part("hr_est_time") RequestBody hr_est_time,
             @Part MultipartBody.Part img1,
             @Part MultipartBody.Part img2,
             @Part MultipartBody.Part img3
            );


    @FormUrlEncoded
    @POST("get_other_info_nocker")
    Call<UserInfoResponse> GetUserInfo(@Field("user_email") String user_email
    );


}
