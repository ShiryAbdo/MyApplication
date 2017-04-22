package com.example.shady.myapplication.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shady.myapplication.R;

/**
 * Created by Shiry Abdo on 4/4/2017.
 */

public class Adaptor_spinner extends ArrayAdapter<String> {
    Context cont ;
    String[] dosss ;
    int [] images ;

    public Adaptor_spinner(Context context,String[] doese, int[] image) {
        super(context, R.layout.spinner_item,doese);

        this.cont= context;
        this.dosss= doese;
        this.images= image;

    }


    @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {

        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) cont.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.spinner_item,null);
        }
        TextView doses = (TextView) convertView.findViewById(R.id.doeseid);
        ImageView dosesImage = (ImageView) convertView.findViewById(R.id.icon_doses);

        /// to see data
        doses.setText(dosss[position]);
        dosesImage.setImageResource(images[position]);

            return convertView;
        }


            @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                if(convertView==null){
                    LayoutInflater inflater = (LayoutInflater) cont.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView=inflater.inflate(R.layout.spinner_item,null);
                }
                TextView doses = (TextView) convertView.findViewById(R.id.doeseid);
                ImageView dosesImage = (ImageView) convertView.findViewById(R.id.icon_doses);

                /// to see data
                doses.setText(dosss[position]);
                dosesImage.setImageResource(images[position]);

                return convertView;

         }

}
