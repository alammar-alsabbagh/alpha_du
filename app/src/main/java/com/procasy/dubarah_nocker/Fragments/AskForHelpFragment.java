package com.procasy.dubarah_nocker.Fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.procasy.dubarah_nocker.API.APIinterface;
import com.procasy.dubarah_nocker.API.ApiClass;
import com.procasy.dubarah_nocker.Helper.Language;
import com.procasy.dubarah_nocker.Helper.SessionManager;
import com.procasy.dubarah_nocker.Helper.Skills;
import com.procasy.dubarah_nocker.Model.Responses.AllSkillsAndLanguageResponse;
import com.procasy.dubarah_nocker.R;
import com.procasy.dubarah_nocker.Services.LocationService;

import java.io.IOException;
import java.util.ArrayList;
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

    ImageView reord, start, stop;
    private MediaRecorder myAudioRecorder;
    ProgressBar progressBar;
    private String outputFile = null;
    Button where;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;
    List<String> skills_list;
    List<String> language_list;
    private int PLACE_PICKER_REQUEST = 1;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    SessionManager sessionManager;
    private Skills mskills;
    private Language mlanguage;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private AutoCompleteTextView actv, language;
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 5 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ask_for_help, container, false);
        sessionManager = new SessionManager(getActivity());

        language = (AutoCompleteTextView) view.findViewById(R.id.language);
        actv = (AutoCompleteTextView) view.findViewById(R.id.skills_auto_complete);
        mlanguage = new Language(getActivity());
        where = (Button) view.findViewById(R.id.where);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        progressBar.setVisibility(View.INVISIBLE);

        reord = (ImageView) view.findViewById(R.id.record_sound);
        start = (ImageView) view.findViewById(R.id.start_record);
        stop = (ImageView) view.findViewById(R.id.stop_record);

        stop.setEnabled(false);
        start.setEnabled(false);

        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";


        reord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {


                    requestPermissions(
                            new String[]{Manifest.permission.RECORD_AUDIO,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            5);

                    myAudioRecorder = new MediaRecorder();
                    myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                    myAudioRecorder.setOutputFile(outputFile);


                    myAudioRecorder.prepare();
                    myAudioRecorder.start();

                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                progressBar.setVisibility(View.VISIBLE);
                reord.setEnabled(false);
                stop.setEnabled(true);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAudioRecorder.stop();
                myAudioRecorder.release();
                myAudioRecorder = null;

                stop.setEnabled(false);
                start.setEnabled(true);
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getActivity(), "Audio recorded successfully", Toast.LENGTH_LONG).show();
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) throws IllegalArgumentException, SecurityException, IllegalStateException {
                MediaPlayer m = new MediaPlayer();

                try {
                    m.setDataSource(outputFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    m.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                m.start();
                progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "Playing audio", Toast.LENGTH_LONG).show();
            }
        });

        where.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                    startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);


                } catch (Exception e) {
                    e.printStackTrace();
                }

                //startActivityForResult(new Intent(getActivity(), MapsActivity.class),1);

            }
        });
        mskills = new Skills(getActivity());

        skills_list = new ArrayList<>();
        language_list = new ArrayList<>();

        mskills.open();
        Cursor cursor = mskills.getAllEntries();
        cursor.moveToFirst();
        try {


            skills_list.add(cursor.getString(1));
            while (cursor.moveToNext()) {
                skills_list.add(cursor.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mskills.close();

        }

        mlanguage.open();
        Cursor cursor_language = mlanguage.getAllEntries();
        cursor_language.moveToFirst();
        try {


            language_list.add(cursor_language.getString(1));
            while (cursor_language.moveToNext()) {
                language_list.add(cursor_language.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mlanguage.close();
        }


        Log.e("list_size ", skills_list.size() + "");

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, skills_list);
        adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, language_list);
        language.setAdapter(adapter2);

        final ACProgressFlower dialog = new ACProgressFlower.Builder(getActivity())
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Getting Info..")
                .fadeColor(Color.DKGRAY).build();
        dialog.show();

        Log.e("AskForHelp", "Success");

        APIinterface apiService = ApiClass.getClient().create(APIinterface.class);
        Call<AllSkillsAndLanguageResponse> call = apiService.GetAllSkills(sessionManager.getEmail(), sessionManager.getUDID());
        call.enqueue(new Callback<AllSkillsAndLanguageResponse>() {
            @Override
            public void onResponse(Call<AllSkillsAndLanguageResponse> call, Response<AllSkillsAndLanguageResponse> response) {

                mskills.open();
                Log.e("remove state ", mskills.removeAllEntry() + "");
                mlanguage.open();
                mlanguage.removeAllEntry();
                skills_list.clear();
                language_list.clear();
                try {
                    for (int i = 0; i < response.body().getAllSkills().size(); i++) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(Skills.COL_skillname, response.body().getAllSkills().get(i).getSkill_name());
                        contentValues.put(Skills.COL_is_hidden, response.body().getAllSkills().get(i).getIs_hidden());
                        contentValues.put(Skills.COL_SKILL_ID, response.body().getAllSkills().get(i).getSkill_id());
                        long state = mskills.insertEntry(contentValues);
                        Log.e("insert state ", state + "");
                        skills_list.add(response.body().getAllSkills().get(i).getSkill_name());
                    }

                    for (int i = 0; i < response.body().getAllLanguage().size(); i++) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(Language.COL_language_code, response.body().getAllLanguage().get(i).getLang_code());
                        contentValues.put(Language.COL_language_name, response.body().getAllLanguage().get(i).getLang_name());
                        contentValues.put(Language.COL_language_Id, response.body().getAllLanguage().get(i).getlang_id());

                        long state = mlanguage.insertEntry(contentValues);
                        Log.e("insert state language ", state + "");
                        language_list.add(response.body().getAllLanguage().get(i).getLang_name());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                actv.setAdapter(adapter);
                language.setAdapter(adapter2);
                mskills.close();
                mlanguage.close();

                if (dialog.isShowing())
                    dialog.dismiss();
            }

            @Override
            public void onFailure(Call<AllSkillsAndLanguageResponse> call, Throwable t) {
                System.out.println("ERROR 2" + t.toString());
                if (dialog.isShowing())
                    dialog.dismiss();
            }

        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == getActivity().RESULT_OK) {
                Place place = PlacePicker.getPlace(data, getActivity());
                //String toastMsg = String.format("Place: %s", place.getName());
                where.setText(place.getName());
                //Toast.makeText(getActivity(), toastMsg, Toast.LENGTH_LONG).show();
            }
        }
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
