package com.procasy.dubarah_nocker.Adapter;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;

/**
 * Created by DELL on 18/08/2016.
 */
public class Crime implements ParentObject {

    /* Create an instance variable for your list of children */
    private List<Object> mChildrenList;

    /**
     * Your constructor and any other accessor
     *  methods should go here.
     */

    @Override
    public List<Object> getChildObjectList() {
        return mChildrenList;
    }

    @Override
    public void setChildObjectList(List<Object> list) {
        mChildrenList = list;
    }


}
