package com.procasy.dubarah_nocker.Activity.BeANocker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.procasy.dubarah_nocker.R;

public class BeAnockerAcitivty extends AppCompatActivity {
    String[] DayOfWeek = {"Sunday", "Monday", "Tuesday",
            "Wednesday", "Thursday", "Friday", "Saturday"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_be_anocker_acitivty);
        final TextView btnOpenPopup = (TextView) findViewById(R.id.link);
        btnOpenPopup.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                LayoutInflater layoutInflater =
                        (LayoutInflater)getBaseContext()
                                .getSystemService(LAYOUT_INFLATER_SERVICE);
                final View popupView = layoutInflater.inflate(R.layout.activity_disclaimer, null);
                final PopupWindow popupWindow = new PopupWindow(
                        popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                Button next = (Button) popupView.findViewById(R.id.next);
                Button dismiss = (Button) popupView.findViewById(R.id.dismiss);

                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                dismiss.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        popupWindow.dismiss();
                        }
                    });

                popupWindow.showAtLocation(btnOpenPopup, Gravity.TOP, 0, 0);
            }

        });
    }
}
