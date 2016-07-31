package com.procasy.dubarah_nocker.Activity.SignUpActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.procasy.dubarah_nocker.R;

import java.util.List;

public class LocationInfoSignUp extends AppCompatActivity implements Validator.ValidationListener{

    LinearLayout next_btn;
    @NotEmpty
    EditText address1, postalCode;
    EditText address2;
    Validator validator;
    String FirstName , Lastname , Country , City , Region , Phonenumber,Email,Password,BirthDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_info_sign_up);
        next_btn = (LinearLayout) findViewById(R.id.next_btn);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
validator.validate();            }
        });
        linkIDwithView();
        validator = new Validator(this);
        validator.setValidationListener(this);
        FirstName = getIntent().getExtras().getString("firstName");
        Lastname = getIntent().getExtras().getString("lastName");
        Country = getIntent().getExtras().getString("country");
        City = getIntent().getExtras().getString("city");
        Region = getIntent().getExtras().getString("region");
        Phonenumber = getIntent().getExtras().getString("phoneNumber");
        Email = getIntent().getExtras().getString("email");
        Password = getIntent().getExtras().getString("password");
        BirthDate = getIntent().getExtras().getString("birthDate");

    }

    void linkIDwithView() {
        address1 = (EditText) findViewById(R.id.address1);
        postalCode = (EditText) findViewById(R.id.postalCode);
        address2 = (EditText) findViewById(R.id.address2);
    }

    @Override
    public void onValidationSucceeded() {
        Bundle bundle = new Bundle();
        bundle.putString("firstName",FirstName);
        bundle.putString("lastName",Lastname);
        bundle.putString("country",Country);
        bundle.putString("city",City);
        bundle.putString("region",Region);
        bundle.putString("birthDate",BirthDate);
        bundle.putString("phoneNumber",Phonenumber);
        bundle.putString("email",Email);
        bundle.putString("password",Password);
        bundle.putString("address1",address1.getText().toString());
        bundle.putString("postalCode",postalCode.getText().toString());
        bundle.putString("address2",postalCode.getText().toString());
        Intent intent = new Intent(getApplicationContext(), PhotoSignUp.class);
        intent.putExtras(bundle);
        startActivity(intent);
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
