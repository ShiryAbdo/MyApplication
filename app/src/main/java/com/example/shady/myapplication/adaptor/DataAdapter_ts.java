package com.example.shady.myapplication.adaptor;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shady.myapplication.R;
import com.example.shady.myapplication.data.HistoryDataHelper;
import com.example.shady.myapplication.data.MedicInformation;
import com.example.shady.myapplication.fireBase.FirebaseHelper;
import com.example.shady.myapplication.fragment.Add_Medication_Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DataAdapter_ts extends RecyclerView.Adapter<DataAdapter_ts.MyViewHolder> {

    AppCompatActivity c;
    ArrayList<MedicInformation> medicInformations;
    HistoryDataHelper historyDataHelper;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nameofmedecin ,timeOfmedecin;
        public ImageView edite ,cancel;
        public CheckBox checkBox;


        public MyViewHolder(View view) {
            super(view);
            nameofmedecin = (TextView) view.findViewById(R.id.text_drugName);
            timeOfmedecin = (TextView) view.findViewById(R.id.text_dateTime);
            edite= (ImageView)view.findViewById(R.id.image_edit);
            cancel=(ImageView)view.findViewById(R.id.cancel);
            checkBox=(CheckBox)view.findViewById(R.id.check_taken);


        }
    }
    public DataAdapter_ts(AppCompatActivity c, ArrayList<MedicInformation> medicInformations) {
        this.medicInformations = medicInformations;
        this.c = c;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final MedicInformation data = medicInformations.get(position);
        holder.nameofmedecin.setText(data.getMedicName());
        holder.timeOfmedecin.setText("Doses" + data.getNumberDoses());
        holder.checkBox.setChecked(data.isDone());
        holder.edite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add_Medication_Fragment f = new Add_Medication_Fragment();
                Bundle args = new Bundle();
                args.putSerializable("Med", data);
                f.setArguments(args);
                c.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, f)
                        .commit();
            }
        });
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                data.setDone(isChecked);
                FirebaseDatabase
                        .getInstance()
                        .getReference()
                        .child("users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child("Medicines")
                        .child(data.get_id())
                        .setValue(data);
            }
        });
//        Date x = new Date(data.getTimelong());
//        Calendar calendar_4 = Calendar.getInstance();
//        holder.timeOfmedecin.setText();
//            holder.title.setText(movie.getTitle());
//////////////////            holder.genre.setText(movie.getGenre());///////////////////////////////////////////////

    }

    @Override
    public int getItemCount() {
        return medicInformations.size();
    }
}
