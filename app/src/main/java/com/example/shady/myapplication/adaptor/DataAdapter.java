package com.example.shady.myapplication.adaptor;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shady.myapplication.R;
import com.example.shady.myapplication.Spacecraft;

import java.util.List;

    public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {

            private List<Spacecraft> spacecrafts;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView title ;
            public ImageView icon_pill;

            public MyViewHolder(View view) {
                super(view);
//                title = (TextView) view.findViewById(R.id.pill_name);
//                icon_pill=(ImageView)view.findViewById(R.id.icon_pill);

            }
        }
        public DataAdapter(List<Spacecraft> moviesList) {
            this.spacecrafts = moviesList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.model, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            Spacecraft movie = spacecrafts.get(position);
//            holder.title.setText(movie.getTitle());
//////////////////            holder.genre.setText(movie.getGenre());///////////////////////////////////////////////

        }

        @Override
        public int getItemCount() {
            return spacecrafts.size();
        }
    }
