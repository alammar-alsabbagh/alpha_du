package com.procasy.dubarah_nocker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.transition.Fade;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.procasy.dubarah_nocker.API.APIinterface;
import com.procasy.dubarah_nocker.API.ApiClass;
import com.procasy.dubarah_nocker.Activity.SignUpActivities.MainInfoSignUp;
import com.procasy.dubarah_nocker.Model.Responses.LoginResponse;
import com.procasy.dubarah_nocker.Model.UserLoginModel;
import com.shawnlin.preferencesmanager.PreferencesManager;

import java.util.List;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements Validator.ValidationListener{

    RelativeLayout relativeLayout;
    Button create_nocker,login;

    @NotEmpty
    @Email
    EditText Email;

    @com.mobsandgeeks.saripaar.annotation.Password(min = 6,scheme = com.mobsandgeeks.saripaar.annotation.Password.Scheme.ALPHA_NUMERIC_MIXED_CASE)
    EditText Password;

Validator validator;
    APIinterface apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LinkIDwithViews();
        new PreferencesManager(this)
                .setName("user")
                .init();

        apiService =
                ApiClass.getClient().create(APIinterface.class);

        validator = new Validator(this);
        validator.setValidationListener(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesManager.putInt("register",0);
                validator.validate();
            }
        });
        create_nocker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesManager.putInt("register",1);
                validator.validate();

            }
        });
        setupWindowAnimations();
        SpannableStringBuilder builder = new SpannableStringBuilder();

        String text = "Create New ";
        SpannableString white= new SpannableString(text);
        white.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)), 0, text.length(), 0);
        builder.append(white);
        String text2 = "nocker";
        SpannableString yellow= new SpannableString(text2);
        yellow.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), 0, text2.length(), 0);
        builder.append(yellow);
        String text3 = " Account";
        SpannableString white2= new SpannableString(text3);
        white2.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)), 0, text3.length(), 0);
        builder.append(white2);


        create_nocker.setText(builder, TextView.BufferType.SPANNABLE);
    }
    private void setupWindowAnimations() {
        Fade slide = new Fade();
        slide.setDuration(10000);
        getWindow().setExitTransition(slide);
    }

    private void LinkIDwithViews(){
        Email = (EditText) findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.password);
        create_nocker = (Button) findViewById(R.id.create_nocker);
        login = (Button) findViewById(R.id.login);
    }


    @Override
    public void onValidationSucceeded() {
        final ACProgressFlower dialog = new ACProgressFlower.Builder(LoginActivity.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("logging in ...")
                .fadeColor(Color.DKGRAY).build();
        dialog.show();
    if(PreferencesManager.getInt("register") == 0) {
        //login
        PreferencesManager.putString("email", Email.getText().toString());
        PreferencesManager.putString("password", Password.getText().toString());
        System.out.println( Email.getText().toString()+"   "+ Password.getText().toString());
        UserLoginModel user = new UserLoginModel( Email.getText().toString(),Password.getText().toString());
        Call<LoginResponse> call = apiService.Login( Email.getText().toString(),Password.getText().toString());
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (dialog.isShowing())
                    dialog.dismiss();
                if(response.body().getStatus()==1)
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                else
                {
                    AlertDialog.Builder alertdialog = new AlertDialog.Builder(LoginActivity.this);
                    alertdialog.setMessage("Wrong Authentication!");
                    alertdialog.setTitle("Fail");
                    alertdialog.setPositiveButton("Ok",null);
                    alertdialog.show();
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                System.out.println("here 2"+t.toString());
                if (dialog.isShowing())
                    dialog.dismiss();
            }

        });
    }else {
        //register
        PreferencesManager.putString("email",Email.getText().toString());
        PreferencesManager.putString("password",Password.getText().toString());
        startActivity(new Intent(getApplicationContext(), MainInfoSignUp.class));
    }


    }


    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }



}