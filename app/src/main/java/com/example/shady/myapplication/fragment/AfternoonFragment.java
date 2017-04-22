package com.example.shady.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;


import com.example.shady.myapplication.Interface.UpdateInterface;
import com.example.shady.myapplication.R;
import com.example.shady.myapplication.adaptor.DataAdapter_ts;
import com.example.shady.myapplication.adaptor.Icon_pill_Adaptor;
import com.example.shady.myapplication.data.HistoryDataHelper;
import com.example.shady.myapplication.data.MedicInformation;
import com.example.shady.myapplication.data.UserClass;
import com.example.shady.myapplication.fireBase.FirebaseHelper;
import com.example.shady.myapplication.m_UI.DividerItemDecoration;
import com.example.shady.myapplication.m_UI.RecyclerTouchListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class AfternoonFragment extends Fragment implements UpdateInterface {

    FirebaseHelper helper;
    DatabaseReference db;
    ArrayList<MedicInformation> afternonoArray ;
     DataAdapter_ts adapter;
    ArrayList<UserClass> users;
    ArrayList<MedicInformation> data;
    ArrayList<String> idKey;

    HistoryDataHelper historyDataHelper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        //INITIALIZE FIREBASE DB
        db = FirebaseDatabase.getInstance().getReference();
        helper = new FirebaseHelper(db, this, null);
        afternonoArray = new ArrayList<>();
        users = new ArrayList<>();
        data = helper.retrieve();
        idKey= helper.getAddedItemsKeysList();
//        historyDataHelper.setHistoryList(afternonoArray);

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_afternoon, container, false);



        //////////////////////////////////////////////////////////////////////////////
        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_drugs_list);
        adapter= new DataAdapter_ts(getContext(),afternonoArray);

        rv.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(mLayoutManager);
        rv.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        rv.setItemAnimator(itemAnimator);
        Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(getContext(), R.anim.bounce_interpolator);
        rv.setAnimation(animAnticipateOvershoot);


        rv.addOnItemTouchListener(new RecyclerTouchListener(this, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getActivity(), "Single click on position : " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getActivity(), "Long press on position : " + position, Toast.LENGTH_SHORT).show();
            }
        }));

        return rootView;
    }



    public void update() {
        if (data != null && !data.isEmpty()) {
            try {

                isTimeWithinInterval(data);


            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
        }
    }


    public void updateUser() {
        if (!users.isEmpty()) {
            Toast.makeText(getActivity(), "Hello " + users.get(0).getUsername(), Toast.LENGTH_SHORT).show();
        }
    }

    public void isTimeWithinInterval(ArrayList<MedicInformation> data) throws Exception {

        afternonoArray.clear();


        String[] daysUS = new String[]{"Sat", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri"};

        String dayOfWeek = new SimpleDateFormat("EEE", Locale.US).format(Calendar.getInstance().getTime());
        for (int i = 0; i < daysUS.length; i++){
            if(daysUS[i].equals(dayOfWeek)){
                for(int j = 0; j < data.size(); j++){
                    boolean[] days = new boolean[]{
                            data.get(j).getSaturday(),
                            data.get(j).getSunday(),
                            data.get(j).getMonday(),
                            data.get(j).getTuesday(),
                            data.get(j).getWednesday(),
                            data.get(j).getThursday(),
                            data.get(j).getFriday()
                    };
                    if (!days[i])
                        data.remove(j);
                }
                break;
            }
        }


        for (int i = 0; i < data.size(); i++) {
            MedicInformation item = data.get(i);

            // Time in String parsing - to time long
            for (long time : item.getTimelong()) {
                Date x = new Date(time);
                Calendar calendar_4 = Calendar.getInstance();


                calendar_4.setTime(x);



                adapter.notifyDataSetChanged();


            if (calendar_4.get(Calendar.HOUR_OF_DAY) >= 12 && calendar_4.get(Calendar.HOUR_OF_DAY) < 19) {
                afternonoArray.add(item);
                adapter.notifyDataSetChanged();

                    /// from  7am to 12 pm

                } else {

                }

            }


        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        (getActivity()).setTitle("Time line");
    }

    @Override
    public void updateUI(ArrayList<MedicInformation> medicInformations) {

        if (medicInformations != null && !medicInformations.isEmpty()) {
            try {
                isTimeWithinInterval(medicInformations);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
