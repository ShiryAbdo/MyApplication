package com.example.shady.myapplication.adaptor;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shady.myapplication.R;

import java.util.ArrayList;

/**
 * Created by EL.GAMAL on 4/10/2017.
 */

public class AdapterListAlarm extends BaseAdapter {

    private Activity activity;
    private ArrayList<String> arrAlarm;
    private static LayoutInflater inflater=null;

    public AdapterListAlarm(Activity a, ArrayList<String> d) {
        activity = a;
        arrAlarm=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return arrAlarm.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_item_alarm, parent, false);

        TextView textTime =(TextView)vi.findViewById(R.id.time_Picker);
        textTime.setText(arrAlarm.get(position));
        ImageView btn=(ImageView)vi.findViewById(R.id.cancel_alarm);
        btn.setTag(position);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Integer index = (Integer) v.getTag();
//                arrAlarm.remove(index.intValue());
                arrAlarm.remove(position);
                notifyDataSetChanged();

            }
        });
        return vi;
    }
}