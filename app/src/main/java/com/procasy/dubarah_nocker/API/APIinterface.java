package com.procasy.dubarah_nocker.API;

import com.procasy.dubarah_nocker.Model.Responses.AllSkillsAndLanguageResponse;
import com.procasy.dubarah_nocker.Model.Responses.CheckResponse;
import com.procasy.dubarah_nocker.Model.Responses.InfoNockerResponse;
import com.procasy.dubarah_nocker.Model.Responses.LocationResponse;
import com.procasy.dubarah_nocker.Model.Responses.LoginResponse;
import com.procasy.dubarah_nocker.Model.Responses.NearByNockerResponse;
import com.procasy.dubarah_nocker.Model.Responses.NormalResponse;
import com.procasy.dubarah_nocker.Model.Responses.ResponseJob;
import com.procasy.dubarah_nocker.Model.Responses.SocialSignupResponse;
import com.procasy.dubarah_nocker.Model.Responses.UserInfoResponse;
import com.procasy.dubarah_nocker.Model.Responses.UserQoutesResponse;
import com.procasy.dubarah_nocker.Model.UploadModel;

import org.json.JSONArray;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
    @POST("update_gcm_code")
    Call<NormalResponse> Update_GCM(@Field("user_email") String email, @Field("user_ud_id") String UDID,
                                    @Field("user_gcm_code") String GCM);


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
    Call<NormalResponse> AskForHelp
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


    @Multipart
    @POST("update_user_img")
    Call<UploadModel> UpdatePicture
            (@Part MultipartBody.Part img
                    , @Part("user_email") RequestBody user_email,
             @Part("user_ud_id") RequestBody user_ud_id);


    @FormUrlEncoded
    @POST("get_other_info_nocker")
    Call<UserInfoResponse> GetUserInfo(@Field("user_email") String user_email
    );

    @FormUrlEncoded
    @POST("get_appointments")
    Call<String> Get_user_appoitement(@Field("user_email") String user_email, @Field("user_ud_id") String user_ud_id
    );

    @FormUrlEncoded
    @POST("get_top_nocker")
    Call<String> Get_top_nocker(@Field("user_email") String user_email
    );


    @FormUrlEncoded
    @POST("speedy_sign")
    Call<CheckResponse> speedy_sign(@Field("his_name") String his_name,
                                    @Field("his_skill") String his_skill,
                                    @Field("his_phone") String his_phone,
                                    @Field("his_email") String his_email,
                                    @Field("his_lang") String his_lang,
                                    @Field("his_rating") String his_rating,
                                    @Field("his_rating_text") String his_rating_text,
                                    @Field("his_subscribtion") String his_subscribtion,
                                    @Field("fname") String fname,
                                    @Field("lname") String lname,
                                    @Field("country") String country,
                                    @Field("city") String city,
                                    @Field("province") String province,
                                    @Field("birth") String birth,
                                    @Field("email") String email,
                                    @Field("phone") String phone,
                                    @Field("skill") String skill,
                                    @Field("subscribtion") String subscribtion,
                                    @Field("language") String langauge,
                                    @Field("gender") String gender
    );



    @FormUrlEncoded
    @POST("get_user_qoutes")
    Call<UserQoutesResponse> get_user_qoutes(@Field("user_email") String user_email, @Field("user_ud_id") String user_ud_id);

    @FormUrlEncoded
    @POST("accept_qoute_from_user")
    Call<NormalResponse> accept_qoute_from_user(@Field("user_email") String user_email, @Field("user_ud_id") String user_ud_id , @Field("qoute_id") int qoute_id);
}
