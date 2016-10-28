package com.procasy.dubarah_nocker;

import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ForgotPasswordActivity extends AppCompatActivity {

    Button fpass;
    TextInputLayout email ;
    TextView description ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Typeface typface = Typeface.createFromAsset(getAssets(), "fonts/font1.ttf");
        fpass = (Button) findViewById(R.id.fpassword);
        email = (TextInputLayout) findViewById(R.id.username_text_input_layout);
        description = (TextView)findViewById(R.id.description);
        email.setTypeface(typface);
        fpass.setTypeface(typface);
        description.setTypeface(typface);
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
