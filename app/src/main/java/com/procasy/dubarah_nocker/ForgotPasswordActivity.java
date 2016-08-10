package com.procasy.dubarah_nocker;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ForgotPasswordActivity extends AppCompatActivity {

    Button fpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        fpass = (Button) findViewById(R.id.fpassword);
        fpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertdialog = new AlertDialog.Builder(ForgotPasswordActivity.this);
                alertdialog.setMessage("Please check your email address");
                alertdialog.setTitle("Success");
                alertdialog.setPositiveButton("Ok", null);
                alertdialog.show();
            }
        });
    }
}
