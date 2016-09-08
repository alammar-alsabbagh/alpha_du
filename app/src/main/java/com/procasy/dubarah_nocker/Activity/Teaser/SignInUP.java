package com.procasy.dubarah_nocker.Activity.Teaser;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;
import com.procasy.dubarah_nocker.Helper.SessionManager;
import com.procasy.dubarah_nocker.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class SignInUP extends AppCompatActivity {

    LinearLayout signup,signupG,signupFB,signupLinkedin;
    int RC_SIGN_IN = 1;
    String UDID;
    GoogleSignInOptions gso;
    GoogleApiClient mGoogleApiClient;
    private CallbackManager callbackManager;
    SessionManager sessionManager;
    TextView t1,t6,t9,t7,textView3,textView4,t8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_up);
        signup = (LinearLayout) findViewById(R.id.signup);
        signupG = (LinearLayout) findViewById(R.id.signupG);
        signupFB = (LinearLayout) findViewById(R.id.signupFb);
        sessionManager = new SessionManager(this);

        //  signupTwitter = (LinearLayout) findViewById(R.id.signupTwitter);
       signupLinkedin = (LinearLayout) findViewById(R.id.signupLinkedin);
        marshmallowPhoneStatePremissionCheck();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */,
                        new GoogleApiClient.OnConnectionFailedListener() {
                            @Override
                            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                            }
                        } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(Plus.API)
                .build();
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
                                            final String Email = objectFace.getString("email");
                                            final String first_name = objectFace.getString("first_name");
                                            final String last_name = objectFace.getString("last_name");
                                            String gender = objectFace.getString("gender");
                                            String birthday = objectFace.getString("birthday");
                                            JSONObject picture = objectFace.getJSONObject("picture");
                                            JSONObject data = picture.getJSONObject("data");
                                            final String pic_url = data.getString("url");
                                            sessionManager.setFName(first_name);
                                            sessionManager.setLName(last_name);
                                            sessionManager.setEmail(Email);
                                            sessionManager.setPP(pic_url);
                                            startActivity(new Intent(getApplicationContext(),MainInfoActivity.class));
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
                        Toast.makeText(SignInUP.this, "Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(SignInUP.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        t1 = (TextView) findViewById(R.id.t1);
        t6 = (TextView) findViewById(R.id.t6);
        t9 = (TextView) findViewById(R.id.textView);
        t7 = (TextView) findViewById(R.id.t7);
        t8 = (TextView) findViewById(R.id.t8);

        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);
        Typeface typface = Typeface.createFromAsset(getAssets(), "fonts/font1.ttf");
        Typeface typface2 = Typeface.createFromAsset(getAssets(), "fonts/font2.ttf");
        Typeface bold = Typeface.createFromAsset(getAssets(), "fonts/font3.ttf");
        Typeface link = Typeface.createFromAsset(getAssets(), "fonts/linkedin.ttf");
        Typeface google = Typeface.createFromAsset(getAssets(), "fonts/font4.ttf");

        t1.setTypeface(typface);
        t6.setTypeface(typface2);
        t9.setTypeface(typface);
        t8.setTypeface(google);
        textView3.setTypeface(bold);
        textView4.setTypeface(bold);
        t7.setTypeface(link);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainInfoActivity.class));
            }
        });
        signupFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(SignInUP.this, Arrays.asList("public_profile", "user_friends", "email", "user_birthday"));
            }
        });
        signupLinkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("here");
                LISessionManager.getInstance(getApplicationContext()).init(SignInUP.this, buildScope(), new AuthListener() {
                    @Override
                    public void onAuthSuccess() {
                        String url = "https://api.linkedin.com/v1/people/~:(id,first-name,last-name,email-address,picture-url)?format=json";
                        System.out.println("here");
                        APIHelper apiHelper = APIHelper.getInstance(getApplicationContext());
                        apiHelper.getRequest(getApplicationContext(), url, new ApiListener() {
                            @Override
                            public void onApiSuccess(ApiResponse apiResponse) {
                                System.out.println(apiResponse.toString());
                                JSONObject object = apiResponse.getResponseDataAsJson();
                                try {
                                    sessionManager.setFName(object.getString("firstName"));
                                    sessionManager.setLName(object.getString("lastName"));
                                    sessionManager.setEmail(object.getString("emailAddress"));
                                    startActivity(new Intent(getApplicationContext(),MainInfoActivity.class));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onApiError(LIApiError liApiError) {
                                // Error making GET request!
                                System.out.println("error");

                            }
                        });
                    }
                    @Override
                    public void onAuthError(LIAuthError error) {
                        // Handle authentication errors
                        System.out.println(error.toString());
                        System.out.println("here 2");
                    }
                }, true);
            }
        });
       /* signupLinkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        signupTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

*/
        signupG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
    }


    private static Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.R_EMAILADDRESS);
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
        LISessionManager.getInstance(getApplicationContext()).onActivityResult(this, requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            int statusCode = result.getStatus().getStatusCode();
            Log.e("Google Plus : ",statusCode+"");
            handleSignInResult(result);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("Google Plus", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            Person person = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
            String firstName = person.getName().getGivenName();
            String lastName = person.getName().getFamilyName();
            String Email = result.getSignInAccount().getEmail();
            sessionManager.setEmail(Email);
            sessionManager.setFName(firstName);
            sessionManager.setLName(lastName);
            startActivity(new Intent(getApplicationContext(),MainInfoActivity.class));
            // Signed in successfully, show authenticated UI.
            }
            else {

        }
    }
}
