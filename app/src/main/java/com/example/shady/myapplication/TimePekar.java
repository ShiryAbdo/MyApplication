package com.example.shady.myapplication;

 import android.support.v7.app.AppCompatActivity;
 import android.os.Bundle;
 import android.view.View;
 import android.widget.AdapterView;
 import android.widget.Spinner;
 import android.widget.Toast;

 import com.example.shady.myapplication.adaptor.Adaptor_spinner;


public class TimePekar extends AppCompatActivity {

    Spinner spinner;


    String[] TypeOFDoses = {"Pill", "Injectiones", "Liquid"};
    int[] imageOfDoses = {R.drawable.pill_color, R.drawable.injection, R.drawable.liqued};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_pekar);


        // Spinner element of days
        spinner = (Spinner) findViewById(R.id.spinner);

        Adaptor_spinner adaptor_spinner = new Adaptor_spinner(this, TypeOFDoses, imageOfDoses);
        spinner.setAdapter(adaptor_spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                // Showing selected spinner item
                Toast.makeText(parent.getContext(), "Selected: " + TypeOFDoses[position], Toast.LENGTH_LONG).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}