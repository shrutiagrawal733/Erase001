package com.example.erase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ArrayList<DataModel> data;

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView date,dropbox,gadget,points;

        public MyViewHolder(View v)
        {
            super(v);
            this.date = v.findViewById(R.id.date);
            this.dropbox = v.findViewById(R.id.dropbox);
            this.gadget = v.findViewById(R.id.gadget);
            this.points = v.findViewById(R.id.points);
        }
    }

    public CustomAdapter(ArrayList<DataModel> data){
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_layout,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView date = holder.date;
        TextView dropbox = holder.dropbox;
        TextView item = holder.gadget;
        TextView points = holder.points;

        date.setText(data.get(listPosition).getDate());
        dropbox.setText(data.get(listPosition).getDropbox());
        item.setText(data.get(listPosition).getGadget());
        points.setText(String.valueOf(data.get(listPosition).getPoints()));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

