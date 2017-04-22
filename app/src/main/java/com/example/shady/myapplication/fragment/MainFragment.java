package com.example.shady.myapplication.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;

import com.example.shady.myapplication.adaptor.Icon_pill_Adaptor;
import com.example.shady.myapplication.data.MedicInformation;
import com.example.shady.myapplication.DividerItemDecoration;
import com.example.shady.myapplication.activities.DrugsActivity;
import com.example.shady.myapplication.fireBase.FirebaseHelper;
import com.example.shady.myapplication.R;
import com.example.shady.myapplication.Interface.UpdateInterface;
import com.example.shady.myapplication.data.UserClass;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class MainFragment extends Fragment implements UpdateInterface {

    private Icon_pill_Adaptor mAdapter;
    FirebaseHelper helper;
    DatabaseReference db;
    int hour, min;
    TextView textView;
    TextView timeText;
    ArrayList<Long> time = new ArrayList<Long>();
    EditText nameEditTxt, propTxt, descTxt;

    ArrayList<MedicInformation> morringArray = new ArrayList<>();
    ArrayList<MedicInformation> afternoonArray = new ArrayList<>();
    ArrayList<MedicInformation> eveningArray = new ArrayList<>();
    ArrayList<MedicInformation> nightArray = new ArrayList<>();

    ArrayList<MedicInformation> data;
    ArrayList<UserClass> users;
    Icon_pill_Adaptor morningAdapter;
    Icon_pill_Adaptor afternoonAdapter;
    Icon_pill_Adaptor eveningAdapter;
    Icon_pill_Adaptor nightAdapter;
     ArrayList<String> idKey;


    private RecyclerView recyclerView_morrinnig, recyclerView_afternoon, recyclerView_evening, recyclerView_night;


    LinearLayout morring_liner, afternoo_liner, evening_liner, night_liner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.data_list_row, container, false);
        //INITIALIZE FIREBASE DB
        db = FirebaseDatabase.getInstance().getReference();

        helper = new FirebaseHelper(db, this, null);
//
        data = helper.retrieve();

        mAdapter = new Icon_pill_Adaptor(getContext(), data);
        idKey= helper.getAddedItemsKeysList();

        //////////////////////////// ........ LISTENER IN LAYOUT ..........////////////////////////////////////


        morring_liner = (LinearLayout) rootView.findViewById(R.id.morring_liner);


        morring_liner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DrugsActivity.class);
                startActivity(intent);


            }
        });


        afternoo_liner = (LinearLayout) rootView.findViewById(R.id.after_noon_lanier);

        afternoo_liner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DrugsActivity.class);
                startActivity(intent);
            }
        });


        evening_liner = (LinearLayout) rootView.findViewById(R.id.eveninig_lay_liner);

        evening_liner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DrugsActivity.class);
                startActivity(intent);
            }
        });


        night_liner = (LinearLayout) rootView.findViewById(R.id.night_lay_lin);

        night_liner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DrugsActivity.class);
                startActivity(intent);
            }
        });


        ///////////////////////////............. SHOW DATE IN UI  .........../////////////////////////////////

        textView = (TextView) rootView.findViewById(R.id.textView);
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        String today = new SimpleDateFormat("EEEE", Locale.getDefault()).format(Calendar.getInstance().getTime());
        // textView is the TextView view that should display it
        textView.setText(currentDateTimeString + "                 " + today);


///////////////////////////// ............  SHOW ICON IN THE DURATION LAYOUT ......//////////////////////////////

        mAdapter = new Icon_pill_Adaptor(getContext(), helper.retrieve());




        /// insializing recycle view
        recyclerView_morrinnig = (RecyclerView) rootView.findViewById(R.id.recycler_view_morring);
        recyclerView_afternoon = (RecyclerView) rootView.findViewById(R.id.recycler_view_afternoon);
        recyclerView_evening = (RecyclerView) rootView.findViewById(R.id.recycler_view_eveninig);
        recyclerView_night = (RecyclerView) rootView.findViewById(R.id.recycler_view_night);


// morring recycle
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 4);
        RecyclerView.LayoutManager mLayoutManager1 = new GridLayoutManager(getContext(), 4);
        RecyclerView.LayoutManager mLayoutManager2 = new GridLayoutManager(getContext(), 4);
        RecyclerView.LayoutManager mLayoutManager3 = new GridLayoutManager(getContext(), 4);

        morningAdapter = new Icon_pill_Adaptor(getContext(), morringArray);
        afternoonAdapter = new Icon_pill_Adaptor(getContext(), afternoonArray);
        eveningAdapter = new Icon_pill_Adaptor(getContext(), eveningArray);
        nightAdapter = new Icon_pill_Adaptor(getContext(), nightArray);

//                new LinearLayoutManager(getContext());
        recyclerView_morrinnig.addItemDecoration(new DividerItemDecoration(MainFragment.this, GridLayoutManager.VERTICAL));
        recyclerView_morrinnig.setItemAnimator(new DefaultItemAnimator());
        recyclerView_morrinnig.setLayoutManager(mLayoutManager);
        recyclerView_morrinnig.setHasFixedSize(true);
        recyclerView_morrinnig.setAdapter(morningAdapter);


        //  afternoon recycle

        recyclerView_afternoon.addItemDecoration(new DividerItemDecoration(MainFragment.this, GridLayoutManager.VERTICAL));
        recyclerView_afternoon.setItemAnimator(new DefaultItemAnimator());
        recyclerView_afternoon.setLayoutManager(mLayoutManager1);
        recyclerView_afternoon.setHasFixedSize(true);
        recyclerView_afternoon.setAdapter(afternoonAdapter);


        //    evening  recycle
        recyclerView_evening.addItemDecoration(new DividerItemDecoration(MainFragment.this, GridLayoutManager.VERTICAL));
        recyclerView_evening.setItemAnimator(new DefaultItemAnimator());
        recyclerView_evening.setLayoutManager(mLayoutManager2);
        recyclerView_evening.setHasFixedSize(true);
        recyclerView_evening.setAdapter(eveningAdapter);


        //     night  recycle
        recyclerView_night.addItemDecoration(new DividerItemDecoration(MainFragment.this, GridLayoutManager.VERTICAL));
        recyclerView_night.setItemAnimator(new DefaultItemAnimator());
        recyclerView_night.setLayoutManager(mLayoutManager3);
        recyclerView_night.setHasFixedSize(true);
        recyclerView_night.setAdapter(nightAdapter);




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

        afternoonArray.clear();;
        eveningArray.clear();
        morringArray.clear();
        nightArray.clear();

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



                afternoonAdapter.notifyDataSetChanged();
                eveningAdapter.notifyDataSetChanged();
                morningAdapter.notifyDataSetChanged();
                nightAdapter.notifyDataSetChanged();

                if (calendar_4.get(Calendar.HOUR_OF_DAY) < 7) {
                    nightArray.add(item);
                    nightAdapter.notifyDataSetChanged();
                }
                /// from  7am to 12 pm
                else if (calendar_4.get(Calendar.HOUR_OF_DAY) >= 7 && calendar_4.get(Calendar.HOUR_OF_DAY) < 12) {
                    morringArray.add(item);
                    morningAdapter.notifyDataSetChanged();

                    /// from  12am to 7 pm
                } else if (calendar_4.get(Calendar.HOUR_OF_DAY) >= 12 && calendar_4.get(Calendar.HOUR_OF_DAY) < 19) {
                    afternoonArray.add(item);
                    afternoonAdapter.notifyDataSetChanged();

                    /// from  7am to 12 pm

                } else {
                    eveningArray.add(item);

                    eveningAdapter.notifyDataSetChanged();
                    /// from  12pm to 7 am
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

//
//    }


//    //DISPLAY INPUT DIALOG
//    private void displayInputDialog() {
//        Dialog d = new Dialog(getContext());
//        d.setTitle("Save To Firebase");
//        d.setContentView(R.layout.input_dialog);
//        timeText = (TextView) d.findViewById(R.id.timeText);
//        nameEditTxt = (EditText) d.findViewById(R.id.nameEditText);
//        propTxt = (EditText) d.findViewById(R.id.propellantEditText);
//        descTxt = (EditText) d.findViewById(R.id.descEditText);
//        Button saveBtn = (Button) d.findViewById(R.id.saveBtn);
//        Button TimeBtn = (Button) d.findViewById(R.id.TimeBtn);
//        TimeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new TimePickerDialog(getContext(), mTimeSetListener, hour, min, false).show();
//            }
//        });
//        //SAVE
//        saveBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //GET DATA
//                String time_take = timeText.getText().toString();
//                String name = nameEditTxt.getText().toString();
//                String propellant = propTxt.getText().toString();
//                String desc = descTxt.getText().toString();
//                //SET DATA
//                Spacecraft s = new Spacecraft();
//                s.setTime(time_take);
//                s.setTimelong(dateTime);
//                s.setName(name);
//                s.setPropellant(propellant);
//                s.setDescription(desc);
//
//
////                mAdapter = new Icon_pill_Adaptor(getContext(), data);
////
////
////                recyclerView_morrinnig.setAdapter(mAdapter);
//                //SIMPLE VALIDATION
////                if (name != null && name.length() > 0) {
//                //THEN SAVE
//                if (helper.save(s)) {
//                    //IF SAVED CLEAR EDITXT
//                    nameEditTxt.setText("");
//                    propTxt.setText("");
//                    descTxt.setText("");
//
//                }
////                } else {
////                    Toast.makeText(getContext(), "Name Must Not Be Empty", Toast.LENGTH_SHORT).show();
////                }
//            }
//        });
//        d.show();
//    }
//
//    long dateTime;
//
//    private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
//        @Override
//        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//            hour = hourOfDay;
//            min = minute;
//
//            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
//            Calendar c = Calendar.getInstance();
//            c.set(0, 0, 0, hour, min);
//            Date date = c.getTime();
//            String strDate = timeFormat.format(date);
//            dateTime = 0;
//            dateTime = date.getTime();
//            timeText.setText(strDate);
//
//
//        }
//    };


    //        the listener in ech itme in recycle view

//        recyclerView_morrinnig.addOnItemTouchListener(new RecyclerTouchListener( getContext(), recyclerView_morrinnig, new RecyclerTouchListener.ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                Spacecraft movie = movieList.get(position);
//                Toast.makeText(getContext(),   " is selected!", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));

//    private void prepareMovieData() {
//        DataShowHome movie = new DataShowHome("Mad Max: Fury Road");
//        movieList.add(movie);
//        mAdapter.notifyDataSetChanged();
//    }


    //        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
//
//        mAdapter = new DataAdapter(movieList);
//
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(MainFragment.this, LinearLayoutManager.VERTICAL));
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setAdapter(mAdapter);
//
//        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), mRecyclerView, new RecyclerTouchListener.ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                DataShowHome movie = movieList.get(position);
//                Toast.makeText(getContext(), movie.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));
//
//        prepareMovieData();
/////////////////////////////////////////////////////////////////////...............................................
//        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
//
//
//        mAdapter = new DataAdapter(movieList);
//
//
//////        RecyclerView.LayoutManager  mLayoutManager = new  Lin();
////        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
////        mRecyclerView.setLayoutManager(mLayoutManager);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
//
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(MainFragment.this, LinearLayoutManager.VERTICAL));
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setAdapter(mAdapter);
//
//        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), mRecyclerView, new RecyclerTouchListener.ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                DataShowHome movie = movieList.get(position);
//                Toast.makeText(getContext(), movie.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));
//
//        prepareMovieData();



//        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                displayInputDialog();
//            }
//        });
//

}