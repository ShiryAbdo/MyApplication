package com.example.shady.myapplication.adaptor;
//import android.support.v7.widget.CardView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shady.myapplication.R;

public class DrugsAdapter extends RecyclerView.Adapter<DrugsAdapter.DrugsViewHolder> {

    @Override
    public DrugsAdapter.DrugsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    public static class DrugsViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        public TextView txt_drugName ,text_dateTime;
        public DrugsViewHolder(View v) {
            super(v);
//            mCardView = (CardView) v.findViewById(R.id.card_view);
            txt_drugName = (TextView) v.findViewById(R.id.text_drugName);
            text_dateTime= (TextView)v.findViewById(R.id.text_dateTime);
        }
    }

    @Override
    public void onBindViewHolder(DrugsAdapter.DrugsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
//    private ArrayList<String> data_show = new ArrayList<String>();
//    private String NameMedecin ,TimeNaedecin ,DateMEDECIN ;
//    Context c;
//    ArrayList<MedicInformation> medicInformations;
//
//    public DrugsAdapter(List<LauncherActivity.ListItem> listItems, Context context) {
//
//    }
//
//    public static class DrugsViewHolder extends RecyclerView.ViewHolder {
//        public CardView mCardView;
//        public TextView txt_drugName ,text_dateTime;
//        public DrugsViewHolder(View v) {
//            super(v);
////            mCardView = (CardView) v.findViewById(R.id.card_view);
//            txt_drugName = (TextView) v.findViewById(R.id.text_drugName);
//            text_dateTime= (TextView)v.findViewById(R.id.text_dateTime);
//        }
//    }
//
//    // Provide a suitable constructor (depends on the kind of dataset)
//    public DrugsAdapter(String neme_medecin ,String time_nedecin , String data_medecin) {
//        NameMedecin= neme_medecin;
//        TimeNaedecin =time_nedecin;
//        DateMEDECIN= data_medecin;
//        data_show.add(NameMedecin);
//        data_show.add(TimeNaedecin);
//        data_show.add(DateMEDECIN);
//    }
//
//    // Create new views (invoked by the layout manager)
//    @Override
//    public DrugsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        // create a new view
//        View v = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.card_item, parent, false);
//
//
//        // set the view's size, margins, paddings and layout parameters
//        DrugsViewHolder vh = new DrugsViewHolder(v);
//        return vh;
//    }
//
//    @Override
//    public void onBindViewHolder(DrugsViewHolder holder, int position) {
//
////        holder.txt_drugName.setText(mDataset[position]);
//        holder.txt_drugName.setText(NameMedecin);
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return data_show.size();
//    }
//    public void animate(RecyclerView.ViewHolder viewHolder) {
//
//    }