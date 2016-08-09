package com.procasy.dubarah_nocker;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.transition.Fade;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.procasy.dubarah_nocker.API.APIinterface;
import com.procasy.dubarah_nocker.API.ApiClass;
import com.procasy.dubarah_nocker.Activity.BeANocker.BeAnockerAcitivty;
import com.procasy.dubarah_nocker.Activity.JobRequestActivity;
import com.procasy.dubarah_nocker.Activity.SignUpActivities.MainInfoSignUp;
import com.procasy.dubarah_nocker.Helper.SessionManager;
import com.procasy.dubarah_nocker.Model.Responses.CheckResponse;
import com.procasy.dubarah_nocker.Model.Responses.InfoNockerResponse;
import com.procasy.dubarah_nocker.Model.Responses.LoginResponse;
import com.procasy.dubarah_nocker.Model.Responses.SocialSignupResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements Validator.ValidationListener, View.OnClickListener {

    Button login;
    TextView description;

    @NotEmpty
    @Email
    EditText Email;

    @com.mobsandgeeks.saripaar.annotation.Password(min = 6, scheme = com.mobsandgeeks.saripaar.annotation.Password.Scheme.ANY)
    EditText Password;
    SessionManager sessionManager;
    Validator validator;
    APIinterface apiService;
    LinearLayout linearLayout;

    ImageView linkedIn, facebook, twitter, googleplus;
    int RC_SIGN_IN = 1;
    String UDID;
    GoogleSignInOptions gso;
    GoogleApiClient mGoogleApiClient;
    private CallbackManager callbackManager;
    private ProfileTracker profileTracker;

    TextView fpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LinkIDwithViews();

        sessionManager = new SessionManager(this);
        if (sessionManager.isLoggedIn())
            startActivity(new Intent(getApplicationContext(), MainActivity.class));

        apiService =
                ApiClass.getClient().create(APIinterface.class);

        validator = new Validator(this);
        validator.setValidationListener(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });


        setupWindowAnimations();
        printKeyHash(LoginActivity.this);

        marshmallowPhoneStatePremissionCheck();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */,
                        new GoogleApiClient.OnConnectionFailedListener() {
                            @Override
                            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                            }
                        } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        Log.v("LoginActivity", response.toString());
                                        JSONObject objectFace = response.getJSONObject();
                                        try {
                                            String Email = objectFace.getString("email");
                                            String first_name = objectFace.getString("first_name");
                                            String last_name = objectFace.getString("last_name");
                                            String gender = objectFace.getString("gender");
                                            String birthday = objectFace.getString("birthday");
                                            JSONObject picture = objectFace.getJSONObject("picture");
                                            JSONObject data = picture.getJSONObject("data");
                                            String pic_url = data.getString("url");
                                            Call<SocialSignupResponse> call = apiService.SocialSignup(Email, first_name, last_name, UDID, "facebook", gender, pic_url, birthday);
                                            call.enqueue(new Callback<SocialSignupResponse>() {
                                                @Override
                                                public void onResponse(Call<SocialSignupResponse> call, Response<SocialSignupResponse> response) {
                                                    System.out.println(response.body().getMessage());
                                                }

                                                @Override
                                                public void onFailure(Call<SocialSignupResponse> call, Throwable t) {

                                                }
                                            });


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        // Application code
                                        try {
                                            String email = object.getString("email");
                                            String birthday = object.getString("birthday");
                                            // 01/31/1980 format

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,first_name,last_name,email,gender,birthday,picture");
                        request.setParameters(parameters);
                        request.executeAsync();


                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(LoginActivity.this, "Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(LoginActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


    }

    private void setupWindowAnimations() {
        Fade slide = new Fade();
        slide.setDuration(10000);
        getWindow().setExitTransition(slide);
    }

    private void LinkIDwithViews() {
        Email = (EditText) findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        linearLayout = (LinearLayout) findViewById(R.id.linear);
        linkedIn = (ImageView) findViewById(R.id.linkedin);
        googleplus = (ImageView) findViewById(R.id.googleplus);
        description = (TextView) findViewById(R.id.description);
        facebook = (ImageView) findViewById(R.id.facebook);
        twitter = (ImageView) findViewById(R.id.twitter);
        fpassword = (TextView) findViewById(R.id.fpassword);

        linkedIn.setOnClickListener(this);
        googleplus.setOnClickListener(this);
        facebook.setOnClickListener(this);
        twitter.setOnClickListener(this);
        fpassword.setOnClickListener(this);
        Typeface typface = Typeface.createFromAsset(getAssets(), "fonts/font1.ttf");

        login.setTypeface(typface);
        description.setTypeface(typface);
        fpassword.setTypeface(typface);
        String htmlString = "<u>Forgot Your Password ?</u>";
        fpassword.setText(Html.fromHtml(htmlString));
    }


    @Override
    public void onValidationSucceeded() {
        final ACProgressFlower dialog = new ACProgressFlower.Builder(LoginActivity.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Logging in ...")
                .fadeColor(Color.DKGRAY).build();
        dialog.show();
        sessionManager.setEmail(Email.getText().toString());
        sessionManager.setPassword(Password.getText().toString());
        Call<LoginResponse> call = apiService.Login(Email.getText().toString(), Password.getText().toString(), UDID);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (dialog.isShowing())
                    dialog.dismiss();
                switch (response.body().getStatus()) {
                    case 1: {
                        sessionManager.setLogin(true);
                        sessionManager.setUDID(UDID);
                        final ACProgressFlower dialog = new ACProgressFlower.Builder(LoginActivity.this)
                                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                                .themeColor(Color.WHITE)
                                .text("Getting Info..")
                                .fadeColor(Color.DKGRAY).build();
                        dialog.show();
                        APIinterface apiService = ApiClass.getClient().create(APIinterface.class);
                        Call<InfoNockerResponse> call2 = apiService.GetInfoNocker(sessionManager.getEmail(), UDID);
                        call2.enqueue(new Callback<InfoNockerResponse>() {
                            @Override
                            public void onResponse(Call<InfoNockerResponse> call, Response<InfoNockerResponse> response) {
                                System.out.println(response.body().getUser().toString());
                                sessionManager.setEmail(response.body().getUser().getUser_email());
                                sessionManager.setFName(response.body().getUser().getUser_fname());
                                sessionManager.setLName(response.body().getUser().getUser_lname());
                                sessionManager.setPP(response.body().getUser().getUser_img());
                                sessionManager.setAVG(response.body().getAvg_charge());
                                sessionManager.setKeyIsNocker(response.body().getUser().is_nocker());
                                Log.d("nocker_data", response.body().getUser().toString() + "");
                                Log.e("user_img", response.body().getUser().getUser_img() + " ff");
                                sessionManager.setSocialType(response.body().getUser().getUser_social_type());
                                Log.e("user_img", sessionManager.getPP() + " ff");

                                if (dialog.isShowing())
                                    dialog.dismiss();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            }

                            @Override
                            public void onFailure(Call<InfoNockerResponse> call, Throwable t) {
                                System.out.println("here 2" + t.toString());
                                if (dialog.isShowing())
                                    dialog.dismiss();
                            }
                        });
                        break;
                    }
                    case 0: {
                        AlertDialog.Builder alertdialog = new AlertDialog.Builder(LoginActivity.this);
                        alertdialog.setMessage("Wrong Authentication");
                        alertdialog.setTitle("Fail");
                        alertdialog.setPositiveButton("Ok", null);
                        alertdialog.show();
                        break;
                    }
                    case 2: {
                        startActivity(new Intent(getApplicationContext(), MainInfoSignUp.class));
                        break;
                    }
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                System.out.println("here 2" + t.toString());
                if (dialog.isShowing())
                    dialog.dismiss();
            }

        });
    }


    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            if (Password.getText().toString().length() < 6)
                Snackbar.make(linearLayout, "Your Password Is Short", Snackbar.LENGTH_SHORT).show();
            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (android.content.pm.Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }

    private static Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.R_EMAILADDRESS);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linkedin: {
                LISessionManager.getInstance(getApplicationContext()).init(LoginActivity.this, buildScope(), new AuthListener() {
                    @Override
                    public void onAuthSuccess() {
                        String url = "https://api.linkedin.com/v1/people/~:(id,first-name,last-name,email-address,picture-url)?format=json";
                        APIHelper apiHelper = APIHelper.getInstance(getApplicationContext());
                        apiHelper.getRequest(getApplicationContext(), url, new ApiListener() {
                            @Override
                            public void onApiSuccess(ApiResponse apiResponse) {
                                final JSONObject object = apiResponse.getResponseDataAsJson();
                                try {
                                    final String EmailAddress = object.getString("emailAddress");
                                    Call<CheckResponse> call = apiService.CheckUnique("user", "user_email", object.getString("emailAddress"));
                                    call.enqueue(new Callback<CheckResponse>() {
                                        @Override
                                        public void onResponse(Call<CheckResponse> call, Response<CheckResponse> response) {
                                            if (response.body().getStatus()) // new user
                                            {

                                            } else /// old user
                                            {

                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<CheckResponse> call, Throwable t) {

                                        }
                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onApiError(LIApiError liApiError) {
                                // Error making GET request!
                            }
                        });
                    }

                    @Override
                    public void onAuthError(LIAuthError error) {
                        // Handle authentication errors
                        System.out.println(error.toString());

                    }
                }, true);
                break;
            }
            case R.id.googleplus: {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
                break;
            }
            case R.id.facebook: {
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends", "email", "user_birthday"));
                break;
            }
            case R.id.twitter: {
                Intent i = new Intent(getApplicationContext(), JobRequestActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(i);
                break;
            }
            case R.id.fpassword: {
                Intent i = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(i);
                break;
            }
        }
    }


    private void marshmallowPhoneStatePremissionCheck() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getApplicationContext().checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    new String[]{Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.READ_PHONE_STATE},
                    5);
        } else {
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            UDID = telephonyManager.getDeviceId();
            Log.e("UDID", UDID);
            //   gps functions.
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 5 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            UDID = telephonyManager.getDeviceId();
            Log.e("UDID Marshmelo :D ", UDID);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            int statusCode = result.getStatus().getStatusCode();
            Log.e("Google Plus : ", statusCode + "");
            handleSignInResult(result);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("Google Plus", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            final GoogleSignInAccount acct = result.getSignInAccount();
            Log.e("Googel Plus Data : ", acct.getEmail() + "  " + acct.getPhotoUrl() + "   " + acct.getFamilyName() + " " + acct.getGivenName());
            Call<SocialSignupResponse> call = apiService.SocialSignup(acct.getEmail(), acct.getGivenName(), acct.getFamilyName(), UDID, "googleplus", "other", acct.getPhotoUrl().toString(), "");
            call.enqueue(new Callback<SocialSignupResponse>() {

                @Override
                public void onResponse(Call<SocialSignupResponse> call, Response<SocialSignupResponse> response) {

                    Log.d("response :", response.body().getStatus() + "");
                    if (response.body().getStatus() == 1) {
                        sessionManager.setEmail(acct.getEmail());
                        sessionManager.setFName(acct.getGivenName());
                        sessionManager.setLName(acct.getFamilyName());
                        sessionManager.setLogin(true);
                        sessionManager.setPassword(null);
                        sessionManager.setPP(acct.getPhotoUrl().toString());
                        sessionManager.setUDID(UDID);
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    } else if (response.body().getStatus() == 2) {
                        sessionManager.setEmail(acct.getEmail());
                        sessionManager.setFName(acct.getGivenName());
                        sessionManager.setLName(acct.getFamilyName());
                        sessionManager.setLogin(true);
                        sessionManager.setPassword(null);
                        sessionManager.setPP(acct.getPhotoUrl().toString());
                        sessionManager.setUDID(UDID);
                        startActivity(new Intent(getApplicationContext(), BeAnockerAcitivty.class));
                    } else {
                        AlertDialog.Builder alertdialog = new AlertDialog.Builder(LoginActivity.this);
                        alertdialog.setMessage("Wrong Authentication");
                        alertdialog.setTitle("Fail");
                        alertdialog.setPositiveButton("Ok", null);
                        alertdialog.show();
                    }
                }

                @Override
                public void onFailure(Call<SocialSignupResponse> call, Throwable t) {

                }
            });
           /* updateUI(true);*/
        } else {
            // Signed out, show unauthenticated UI.
/*
            updateUI(false);
*/
        }
    }


}
