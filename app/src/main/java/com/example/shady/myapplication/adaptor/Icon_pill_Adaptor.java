package com.example.shady.myapplication.adaptor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.shady.myapplication.data.MedicInformation;
import com.example.shady.myapplication.R;

import java.util.ArrayList;

public class Icon_pill_Adaptor extends RecyclerView.Adapter<Icon_pill_Adaptor.MyViewHolder> {
    Context c;
    ArrayList<MedicInformation> medicInformations;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imagIcon;

        public MyViewHolder(View view) {
            super(view);

              imagIcon = (ImageView) view.findViewById(R.id.pill_row);
        }
    }


    public Icon_pill_Adaptor(Context c, ArrayList<MedicInformation>  medicInformations) {
        this.c = c;
        this.medicInformations = medicInformations;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.model, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
         holder.imagIcon.setImageResource(R.drawable.show_pill);

    }

    @Override
    public int getItemCount() {

        return medicInformations.size();
    }
}
