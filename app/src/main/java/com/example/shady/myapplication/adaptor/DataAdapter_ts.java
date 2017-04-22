package com.example.shady.myapplication.adaptor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shady.myapplication.R;
import com.example.shady.myapplication.data.HistoryDataHelper;
import com.example.shady.myapplication.data.MedicInformation;

import java.util.ArrayList;

public class DataAdapter_ts extends RecyclerView.Adapter<DataAdapter_ts.MyViewHolder> {

    Context c;
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
    public DataAdapter_ts(Context c, ArrayList<MedicInformation> medicInformations) {
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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MedicInformation data = medicInformations.get(position);
        holder.nameofmedecin.setText(data.getMedicName());
        holder.timeOfmedecin.setText("Doses" + data.getNumberDoses());
        holder.edite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
