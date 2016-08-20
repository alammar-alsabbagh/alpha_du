package com.procasy.dubarah_nocker.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.procasy.dubarah_nocker.R;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by DELL on 18/08/2016.
 */
public class CrimeExpandableAdapter extends ExpandableRecyclerAdapter<CrimeParentViewHolder, CrimeChildViewHolder> {

    Context mContext;
    List<ParentObject> mparentobj;
    LayoutInflater mInflater ;

    public CrimeExpandableAdapter(Context mContext, List<ParentObject> mparentobj) {
        super(mContext, mparentobj);
        this.mContext = mContext;
        this.mparentobj = mparentobj;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public CrimeParentViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {

        View view = mInflater.inflate(R.layout.list_item_crime_parent, viewGroup, false);
        return new CrimeParentViewHolder(view);

    }

    @Override
    public CrimeChildViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {
        View view = mInflater.inflate(R.layout.activity_job_request, viewGroup, false);
        return new CrimeChildViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(CrimeParentViewHolder crimeParentViewHolder, int i, Object o) {
        Crime crime = (Crime) o;
//        crimeParentViewHolder.mCrimeTitleTextView.setText("lol");
    }

    @Override
    public void onBindChildViewHolder(CrimeChildViewHolder crimeChildViewHolder, int i, Object o) {

    }
}
