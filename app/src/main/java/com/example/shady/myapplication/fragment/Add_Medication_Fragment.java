package com.example.shady.myapplication.fragment;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
 import android.content.Intent;
 import android.graphics.Bitmap;
  import android.net.Uri;
 import android.os.Bundle;
 import android.os.Environment;
 import android.provider.MediaStore;
 import android.support.annotation.NonNull;
 import android.support.v4.app.Fragment;
 import android.support.v7.widget.SwitchCompat;
 import android.text.TextUtils;
 import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
 import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

 import com.example.shady.myapplication.fireBase.FirebaseHelper;
import com.example.shady.myapplication.data.MedicInformation;
import com.example.shady.myapplication.R;
import com.example.shady.myapplication.Interface.UpdateInterface;
import com.example.shady.myapplication.service.MyReceiver;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.philliphsu.bottomsheetpickers.date.DatePickerDialog;
import com.philliphsu.bottomsheetpickers.time.BottomSheetTimePickerDialog;

import java.io.File;
import java.util.Date;
import java.util.Locale;

/**
 * Created by EL.GAMAL on 3
 * /26/2017.
 */

public class Add_Medication_Fragment extends Fragment implements BottomSheetTimePickerDialog.OnTimeSetListener, UpdateInterface , DatePickerDialog.OnDateSetListener {


    TextView  textStartDate, textEndDate;
    private int tpRequestCode =8;
    ArrayList<TextView> multiAlarm ;
    LinearLayout mAlarmLayout;


    Date date ;
    private Uri fileUri;
    SwitchCompat swtAddMedic;
    Button btnSave;
    ImageView imgPreview;
    EditText editMedicName;
    TextView textSchedul, textTimePicker;
    CheckBox cbSat, cbSun, cbMon, cbTue, cbWed, cbThu, cbFri;
    com.shawnlin.numberpicker.NumberPicker numberPicker;
    private Bitmap bm;
    long dateTime;
    private String medicId;
    int hour, min ,year ,month ;
    private DatabaseReference dbRefMedicine;
    private StorageReference mStorageRef;
    private ProgressDialog mProgress;
    FirebaseHelper helper;
    DatabaseReference db;
    ArrayList<Long>timeLongList ;
    private int notification_id;
    ArrayList<MedicInformation> data;
    MedicInformation item;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_medication, container, false);
        if(getArguments() != null)
            item = (MedicInformation) getArguments().getSerializable("Med");
         db = FirebaseDatabase.getInstance().getReference();
        helper = new FirebaseHelper(db, this, null);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mProgress = new ProgressDialog(getActivity());
        timeLongList= new ArrayList<>();
        multiAlarm = new ArrayList<>();
        notification_id = (int) System.currentTimeMillis();
        data = helper.retrieve();

        mAlarmLayout = (LinearLayout) view.findViewById(R.id.alarm_repeater);
        swtAddMedic = (SwitchCompat) view.findViewById(R.id.switch_add_medic);
        imgPreview = (ImageView) view.findViewById(R.id.img_view);
        btnSave = (Button) view.findViewById(R.id.btn_save);
        textSchedul = (TextView) view.findViewById(R.id.text_reminder_time);
        editMedicName = (EditText) view.findViewById(R.id.medicineEdtTxt);
        textTimePicker = (TextView) view.findViewById(R.id.time_Picker);
        textStartDate = (TextView) view.findViewById(R.id.etStartDate);
        textEndDate = (TextView) view.findViewById(R.id.etEndDate);
        cbSat = (CheckBox) view.findViewById(R.id.checkbox_sat);
        cbSun = (CheckBox) view.findViewById(R.id.checkbox_sun);
        cbMon = (CheckBox) view.findViewById(R.id.checkbox_mon);
        cbTue = (CheckBox) view.findViewById(R.id.checkbox_tue);
        cbWed = (CheckBox) view.findViewById(R.id.checkbox_wed);
        cbThu = (CheckBox) view.findViewById(R.id.checkbox_thu);
        cbFri = (CheckBox) view.findViewById(R.id.checkbox_fri);
        numberPicker = (com.shawnlin.numberpicker.NumberPicker) view.findViewById(R.id.number_picker);
        textTimePicker.setEnabled(false);
        numberPicker.setEnabled(false);
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        editMedicName.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 editMedicName.setEnabled(true);
                                                 String medicName;
                                                 medicName = editMedicName.getText().toString();
                                                 editMedicName.setText(medicName);
                                                 Toast msg = Toast.makeText(getContext(), "please Enter Medication Name ", Toast.LENGTH_LONG);
                                                 msg.show();
                                                 Toast.makeText(getActivity(), "The file was saved at " +editMedicName.getText().toString() , Toast.LENGTH_LONG).show();


                                             }
                                         }

        );



/////////////////////////////////////////////////////////////////////////////////////////

        textTimePicker.setOnClickListener(new View.OnClickListener()

                                          {
                                              @Override
                                              public void onClick(View v) {

                                                  TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                                                      @Override
                                                      public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                                          hour = hourOfDay;
                                                          min = minute;

                                                          SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
                                                          Calendar c = Calendar.getInstance();


                                                          c.set(Calendar.HOUR_OF_DAY,hour);
                                                          c.set(Calendar.MINUTE,min);
                                                          date= c.getTime();
                                                           String strDate = timeFormat.format(date);
                                                          dateTime = date.getTime();

                                                          textTimePicker.setText(strDate);


                                                      }
                                                  };
                                                  new TimePickerDialog(getContext(), mTimeSetListener, hour, min, false).show();
                                                  Toast.makeText(getActivity(), "The file was saved at "  , Toast.LENGTH_LONG).show();



                                              }


                                          }

        );



        textStartDate.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {

                                                 tpRequestCode = 0;
                                                 Calendar now = Calendar.getInstance();
                                                 DatePickerDialog date = DatePickerDialog.newInstance(Add_Medication_Fragment.this,
                                                         now.get(Calendar.YEAR),
                                                         now.get(Calendar.MONTH),
                                                         now.get(Calendar.DAY_OF_MONTH));
                                                 date.show(getFragmentManager(), "hello");


                                                 Toast.makeText(getActivity(), "The file was saved at " +textStartDate.getText().toString() , Toast.LENGTH_LONG).show();


                                             }
                                         }

        );

        ///////////////////////////////////////////////////////////////////////////////////////////////////
        textEndDate.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {

                                               tpRequestCode = 1;
                                               Calendar now = Calendar.getInstance();
                                               DatePickerDialog date = DatePickerDialog.newInstance(Add_Medication_Fragment.this,
                                                       now.get(Calendar.YEAR),
                                                       now.get(Calendar.MONTH),
                                                       now.get(Calendar.DAY_OF_MONTH));
                                               date.show(getFragmentManager(), "hello");

                                               Toast.makeText(getActivity(), "The file was saved at " +textEndDate.getText().toString() , Toast.LENGTH_LONG).show();

                                           }
                                       }

        );

        ///////////////////////////////////////////////////////////////////////////////////////////////////

        swtAddMedic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    textTimePicker.setEnabled(true);
                    numberPicker.setEnabled(true);


                    showTextNotification("SWITCH ON *****");
                } else {

                    textTimePicker.setEnabled(false);
                    numberPicker.setEnabled(false);
                    showTextNotification("SWITCH OFF *****");

                }


            }

        });


        cbSat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()

                                         {
                                             @Override
                                             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                                 if (isChecked) {
                                                     if (buttonView == cbSat) {
                                                         numberPicker.getValue();
                                                         textTimePicker.getText();
                                                         showTextNotification("Sat");

                                                     }
                                                 }

                                             }
                                         }

        );

        cbSun.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()

                                         {

                                             @Override
                                             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                                 if (isChecked) {
                                                     if (buttonView == cbSun) {
                                                         showTextNotification("Sun");

                                                     }
                                                 }

                                             }
                                         }

        );

        cbMon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()

                                         {

                                             @Override
                                             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                                 if (isChecked) {
                                                     if (buttonView == cbMon) {
                                                         showTextNotification("Mon");

                                                     }
                                                 }

                                             }
                                         }

        );

        cbTue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()

                                         {

                                             @Override
                                             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                                 if (isChecked) {
                                                     if (buttonView == cbTue) {
                                                         showTextNotification("Tue");

                                                     }
                                                 }

                                             }
                                         }

        );
        cbWed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()

                                         {

                                             @Override
                                             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                                 if (isChecked) {
                                                     if (buttonView == cbWed) {
                                                         showTextNotification("Wed");

                                                     }
                                                 }

                                             }
                                         }

        );
        cbThu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()

                                         {

                                             @Override
                                             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                                 if (isChecked) {
                                                     if (buttonView == cbThu) {
                                                         showTextNotification("Thu");


                                                     }
                                                 }

                                             }
                                         }

        );
        cbFri.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()

                                         {

                                             @Override
                                             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                                 if (isChecked) {
                                                     if (buttonView == cbFri) {
                                                         showTextNotification("Fri");

                                                     }
                                                 }

                                             }
                                         }

        );


        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MedicInformation medicInformation = new MedicInformation();


                String medicName = editMedicName.getText().toString().trim();
                medicInformation.setMedicName(medicName);

                String medicTime = textTimePicker.getText().toString();
                timeLongList.add(dateTime);
                medicInformation.setTimelong(timeLongList);
//        String medicTime = textTimePicker.getText().toString().trim();
                medicInformation.setMedicTime(medicTime);


                Integer numberDoses = numberPicker.getValue();
                medicInformation.setNumberDoses(numberDoses);

                String medicStartDate = textStartDate.getText().toString().trim();
                medicInformation.setMedicStartDate(medicStartDate);

                String medicEndDate = textEndDate.getText().toString().trim();
                medicInformation.setMedicEndDate(medicEndDate);

                Boolean saturday = cbSat.isChecked();
                medicInformation.setSaturday(saturday);

                Boolean sunday = cbSun.isChecked();
                medicInformation.setSunday(sunday);

                Boolean monday = cbMon.isChecked();
                medicInformation.setMonday(monday);


                Boolean tuesday = cbTue.isChecked();
                medicInformation.setTuesday(tuesday);

                Boolean wednesday = cbWed.isChecked();
                medicInformation.setWednesday(wednesday);

                Boolean thursday = cbThu.isChecked();
                medicInformation.setThursday(thursday);

                Boolean friday = cbFri.isChecked();
                medicInformation.setFriday(friday);

                boolean[] days = new boolean[]{
                        medicInformation.getSaturday(),
                        medicInformation.getSunday(),
                        medicInformation.getMonday(),
                        medicInformation.getTuesday(),
                        medicInformation.getWednesday(),
                        medicInformation.getThursday(),
                        medicInformation.getFriday()
                };

                setAlarm(medicName, hour, min, days);

//                calendar.set(date.getYear(),
//                        date.getMonth(),
//                        date.getDay(),
//                        hour,
//                        min,
//                        00);



//                if (TextUtils.isEmpty(medicId)) {
                MedicInformation medicInformton = new MedicInformation(medicName,numberDoses,medicTime,timeLongList,medicStartDate,medicEndDate, saturday, sunday, monday, tuesday, wednesday, thursday, friday);
//
                helper.save(medicInformton);


// db.child("shimaa")
//                helper.save(medicInformaton);
//                } else {
//                    updateMedicine(medicName,numberDoses,medicTime,timeLongList,medicStartDate,medicEndDate, saturday, sunday, monday, tuesday, wednesday, thursday, friday);
////
//                }
//                toggleButton();
            }
        });



        imgPreview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                 intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                startActivityForResult(intent, 0);
            }
        });

        return view;
    }

    private void setAlarm (String medicName, int hour, int min , boolean[] days){
        int[] weekDays = new int[]{
                Calendar.SATURDAY,
                Calendar.SUNDAY,
                Calendar.MONDAY,
                Calendar.TUESDAY,
                Calendar.WEDNESDAY,
                Calendar.THURSDAY,
                Calendar.FRIDAY
        };
        for(int i = 0; i < days.length; i++) {
            if(days[i]) {
                Calendar calendar = Calendar.getInstance();
                calendar.setFirstDayOfWeek(Calendar.SATURDAY);
                calendar.setTime(new Date());

                if (hour < calendar.get(Calendar.HOUR)){
                    calendar.add(Calendar.DAY_OF_WEEK, 1);
                }

                while (calendar.get(Calendar.DAY_OF_WEEK) != weekDays[i]) {
                    calendar.add(Calendar.DATE, 1);
                }
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, min);
                Intent intent1 = new Intent(getContext(), MyReceiver.class);
                intent1.putExtra("name_of_medecines",medicName);
                intent1.putExtra("Hour_time",hour);
                intent1.putExtra("Mint_time",min);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        getContext(), notification_id+i, intent1,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager am = (AlarmManager) getContext()
                        .getSystemService(Context.ALARM_SERVICE);

                String s = calendar.getTime().toString();
                Log.d("RemTime", s);

                am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, pendingIntent);
            }
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        File imgFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Medicine");

        if (requestCode == 0) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                     imgPreview.setVisibility(View.VISIBLE);
                    bm = data.getExtras().getParcelable("data");
                    imgPreview.setImageBitmap(bm);
                    if (bm != null) {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                         mStorageRef.child("Medicine/test.jpg").putBytes(baos.toByteArray()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(getActivity(), "Uploaded", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("Image Upload", e.getMessage());
                            }
                        });
                    }



                    break;

                case Activity.RESULT_CANCELED:
                    Toast.makeText(getActivity(), "User cancelled image capture", Toast.LENGTH_LONG).show();
                    break;

                default:
                    Toast.makeText(getActivity(), "Sorry! Failed to capture image", Toast.LENGTH_LONG).show();
                    break;
            }

        }


    }


    private void toggleButton() {
        if (TextUtils.isEmpty(medicId)) {
            btnSave.setText("Save");
        } else {
            btnSave.setText("Update");
        }
    }


    private void updateMedicine(String medicName, Integer numberDoses, String medicTime, ArrayList<Long> timelong,  String medicStartDate, String medicEndDate ,Boolean saturday, Boolean sunday, Boolean monday, Boolean tuesday, Boolean wednesday, Boolean thursday, Boolean friday) {
        // updating the user via child nodes


//        dbRefMedicine.child(medicId).setValue(null);

//        createMedicine(medicName,numberDoses,medicTime,timelong,medicStartDate,medicEndDate, saturday, sunday, monday, tuesday, wednesday, thursday, friday);


//        if (!TextUtils.isEmpty(medicName))
//            dbRefMedicine.child(medicId).child("medicName").setValue(medicName);
//
//        if (!TextUtils.isEmpty(medicTime))
//            dbRefMedicine.child(medicId).child("medicTime").setValue(medicTime);
//
//        if (!TextUtils.isEmpty(medicStartDate))
//            dbRefMedicine.child(medicId).child("medicStartDate").setValue(medicTime);
//
//        if (!TextUtils.isEmpty(medicEndDate))
//            dbRefMedicine.child(medicId).child("medicEndDate").setValue(medicTime);
//
//        if (numberDoses != null)
//            dbRefMedicine.child(medicId).child("numberDoses").setValue(numberDoses);
//
//        if (saturday != null)
//            dbRefMedicine.child(medicId).child("saturday").setValue(saturday);
//
//        if (sunday != null)
//            dbRefMedicine.child(medicId).child("sunday").setValue(sunday);
//
//        if (monday != null)
//            dbRefMedicine.child(medicId).child("monday").setValue(monday);
//
//        if (tuesday != null)
//            dbRefMedicine.child(medicId).child("tuesday").setValue(tuesday);
//
//        if (wednesday != null)
//            dbRefMedicine.child(medicId).child("wednesday").setValue(wednesday);
//
//        if (thursday != null)
//            dbRefMedicine.child(medicId).child("thursday").setValue(thursday);
//
//        if (friday != null)
//            dbRefMedicine.child(medicId).child("friday").setValue(friday);

    }


//
//    public void saveMedicInformation() {
//        MedicInformation medicInformation = new MedicInformation();
//
//
//        String medicName = editMedicName.getText().toString().trim();
//        medicInformation.setMedicName(medicName);
//
//        String medicTime = textTimePicker.getText().toString();
//        timeLongList.add(dateTime);
//        medicInformation.setTimelong(timeLongList);
////        String medicTime = textTimePicker.getText().toString().trim();
//        medicInformation.setMedicTime(medicTime);
//
//
//        Integer numberDoses = numberPicker.getValue();
//        medicInformation.setNumberDoses(numberDoses);
//
//        String medicStartDate = textStartDate.getText().toString().trim();
//        medicInformation.setMedicStartDate(medicStartDate);
//
//        String medicEndDate = textEndDate.getText().toString().trim();
//        medicInformation.setMedicEndDate(medicEndDate);
//
//        Boolean saturday = cbSat.isChecked();
//        medicInformation.setSaturday(saturday);
//
//        Boolean sunday = cbSun.isChecked();
//        medicInformation.setSunday(sunday);
//
//        Boolean monday = cbMon.isChecked();
//        medicInformation.setMonday(monday);
//
//
//        Boolean tuesday = cbTue.isChecked();
//        medicInformation.setTuesday(tuesday);
//
//        Boolean wednesday = cbWed.isChecked();
//        medicInformation.setWednesday(wednesday);
//
//        Boolean thursday = cbThu.isChecked();
//        medicInformation.setThursday(thursday);
//
//        Boolean friday = cbFri.isChecked();
//        medicInformation.setFriday(friday);
//
//        createMedicine(medicName, numberDoses, medicTime, timeLongList,medicStartDate , medicEndDate, saturday, sunday, monday, tuesday, wednesday, thursday, friday);
//
//
//
//    }

//    private void createMedicine(String medicName, Integer numberDoses, String medicTime, ArrayList<Long> timelong,  String medicStartDate, String medicEndDate ,Boolean saturday, Boolean sunday, Boolean monday, Boolean tuesday, Boolean wednesday, Boolean thursday, Boolean friday){
//
//        MedicInformation medicInformation = new MedicInformation(medicName,numberDoses,medicTime,timelong,medicStartDate,medicEndDate, saturday, sunday, monday, tuesday, wednesday, thursday, friday);
//        helper.save(medicInformation);
//    }

    public void showTextNotification(String msgToDisplay) {
        Toast.makeText(getContext(), msgToDisplay, Toast.LENGTH_SHORT).show();
    }



    @Override
    public void updateUI(ArrayList<MedicInformation> medicInformations) {

    }

    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {

    }

    @Override
    public void onTimeSet(ViewGroup viewGroup, int hourOfDay, int minute) {

    }
}

