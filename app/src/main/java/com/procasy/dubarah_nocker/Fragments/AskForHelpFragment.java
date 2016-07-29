package com.procasy.dubarah_nocker.Fragments;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.procasy.dubarah_nocker.API.APIinterface;
import com.procasy.dubarah_nocker.API.ApiClass;
import com.procasy.dubarah_nocker.Helper.SessionManager;
import com.procasy.dubarah_nocker.Helper.Skills;
import com.procasy.dubarah_nocker.Model.Responses.AllSkillsResponse;
import com.procasy.dubarah_nocker.Model.Responses.InfoNockerResponse;
import com.procasy.dubarah_nocker.Model.Responses.SkillsResponse;
import com.procasy.dubarah_nocker.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AskForHelpFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AskForHelpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AskForHelpFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    ArrayAdapter<String> adapter;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    SessionManager sessionManager;
    private Skills mskills;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private AutoCompleteTextView actv;
    private OnFragmentInteractionListener mListener;

    public AskForHelpFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AskForHelpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AskForHelpFragment newInstance(String param1, String param2) {
        AskForHelpFragment fragment = new AskForHelpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ask_for_help, container, false);
        sessionManager = new SessionManager(getActivity());

        actv = (AutoCompleteTextView) view.findViewById(R.id.skills_auto_complete);

        mskills = new Skills(getActivity());
        final List<String> skills = new ArrayList<>();

        mskills.open();
        Cursor cursor = mskills.getAllEntries();
        cursor.moveToFirst();
        try {


            skills.add(cursor.getString(1));
            while (cursor.moveToNext()) {
                skills.add(cursor.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mskills.close();

        }

        Log.e("list_size ", skills.size() + "");

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, skills);
        actv.setAdapter(adapter);

        final ACProgressFlower dialog = new ACProgressFlower.Builder(getActivity())
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Getting Info..")
                .fadeColor(Color.DKGRAY).build();
        dialog.show();

        Log.e("AskForHelp", "Success");

        APIinterface apiService = ApiClass.getClient().create(APIinterface.class);
        Call<AllSkillsResponse> call = apiService.GetAllSkills(sessionManager.getEmail(), sessionManager.getPassword());
        call.enqueue(new Callback<AllSkillsResponse>() {
            @Override
            public void onResponse(Call<AllSkillsResponse> call, Response<AllSkillsResponse> response) {

                mskills.open();
                Log.e("remove state ", mskills.removeAllEntry() + "");

                skills.clear();

                for (int i = 0; i < response.body().getAllSkills().size(); i++) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(Skills.COL_skillname, response.body().getAllSkills().get(i).getSkill_name());
                    contentValues.put(Skills.COL_is_hidden, response.body().getAllSkills().get(i).getIs_hidden());
                    contentValues.put(Skills.COL_SKILL_ID, response.body().getAllSkills().get(i).getSkill_id());
                    long state = mskills.insertEntry(contentValues);
                    Log.e("insert state ", state + "");

                    skills.add(response.body().getAllSkills().get(i).getSkill_name());


                }
                actv.setAdapter(adapter);
                mskills.close();

                if (dialog.isShowing())
                    dialog.dismiss();
            }

            @Override
            public void onFailure(Call<AllSkillsResponse> call, Throwable t) {
                System.out.println("ERROR 2" + t.toString());
                if (dialog.isShowing())
                    dialog.dismiss();
            }

        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
