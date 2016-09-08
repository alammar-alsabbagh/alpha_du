package com.procasy.dubarah_nocker;

/**
 * Created by DELL on 04/09/2016.
 */

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.procasy.dubarah_nocker.Adapter.Crime;
import com.procasy.dubarah_nocker.Adapter.CrimeExpandableAdapter;
import com.procasy.dubarah_nocker.Model.CrimeChild;

import java.util.ArrayList;
import java.util.Date;


public class TooltipWindow_apptmnts {
    private static final int MSG_DISMISS_TOOLTIP = 10000;
    private Context ctx;
    private PopupWindow tipWindow;
    private View contentView;
    private LayoutInflater inflater;
    RecyclerView recyclerView;


    public TooltipWindow_apptmnts(Context ctx) {
        this.ctx = ctx;
        tipWindow = new PopupWindow(ctx);

        inflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.tooltips_appts, null);
    }

    private ArrayList<ParentObject> generateCrimes() {


        ArrayList<ParentObject> parentObjects = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ArrayList<Object> childList = new ArrayList<>();
            childList.add(new CrimeChild(new Date(), false));
            Crime d = new Crime();
            d.setChildObjectList(childList);
            parentObjects.add(d);

        }
        return parentObjects;
    }

    void showToolTip(View anchor) {

        tipWindow.setHeight(ActionBar.LayoutParams.WRAP_CONTENT);
        tipWindow.setWidth(ActionBar.LayoutParams.MATCH_PARENT);

        tipWindow.setOutsideTouchable(true);
        tipWindow.setTouchable(true);
        tipWindow.setFocusable(true);
        tipWindow.setBackgroundDrawable(new BitmapDrawable());

        tipWindow.setContentView(contentView);
        //

        recyclerView = (RecyclerView)contentView.findViewById(R.id.all_appts);
        CrimeExpandableAdapter mCrimeExpandableAdapter = new CrimeExpandableAdapter(ctx, generateCrimes());
        //mCrimeExpandableAdapter.setCustomParentAnimationViewId(R.id.msg_parent);
        mCrimeExpandableAdapter.setParentClickableViewAnimationDefaultDuration();
        mCrimeExpandableAdapter.setParentAndIconExpandOnClick(true);
        recyclerView.setAdapter(mCrimeExpandableAdapter);


        StaggeredGridLayoutManager gaggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);

        //

        int screen_pos[] = new int[2];
// Get location of anchor view on screen
        anchor.getLocationOnScreen(screen_pos);

// Get rect for anchor view
        Rect anchor_rect = new Rect(screen_pos[0], screen_pos[1], screen_pos[0]
                + anchor.getWidth(), screen_pos[1] + anchor.getHeight());

// Call view measure to calculate how big your view should be.
        contentView.measure(ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT);

        int contentViewHeight = contentView.getMeasuredHeight();
        int contentViewWidth = contentView.getMeasuredWidth();
// In this case , i dont need much calculation for x and y position of
// tooltip
// For cases if anchor is near screen border, you need to take care of
// direction as well
// to show left, right, above or below of anchor view
        int position_x = anchor_rect.centerX() - (contentViewWidth / 2);
        int position_y = anchor_rect.bottom - (anchor_rect.height() / 2);


        tipWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, position_x,
                position_y);

// send message to handler to dismiss tipWindow after X milliseconds
        // handler.sendEmptyMessageDelayed(MSG_DISMISS_TOOLTIP, 4000);
    }

    boolean isTooltipShown() {
        if (tipWindow != null && tipWindow.isShowing()) {
            return true;
        }
        return false;
    }

    void dismissTooltip() {
        if (tipWindow != null && tipWindow.isShowing()) {
            tipWindow.dismiss();
        }
    }

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MSG_DISMISS_TOOLTIP:
                    if (tipWindow != null && tipWindow.isShowing())
                        tipWindow.dismiss();
                    break;
            }
        }

        ;
    };

}
