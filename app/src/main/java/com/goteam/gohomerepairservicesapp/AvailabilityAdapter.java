package com.goteam.gohomerepairservicesapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Time;
import java.util.ArrayList;
import java.util.LinkedList;

public class AvailabilityAdapter extends RecyclerView.Adapter<AvailabilityAdapter.ViewHolder> {

    private OnItemClickListener cardClickListener;
    private ArrayList<TimeOfAvailability> list;



    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public AvailabilityAdapter(ArrayList<TimeOfAvailability> list){
        this.list=list;
    }

    public void setOnCardClick(OnItemClickListener listener) {

        cardClickListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView startTime;
        public TextView endTime;

        public ViewHolder(final View itemView, final OnItemClickListener listener) {
            super(itemView);

            startTime = itemView.findViewById(R.id.begining_time);
            endTime = itemView.findViewById(R.id.end_time);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sp_availability_card, parent, false);
        ViewHolder holder = new ViewHolder(v, cardClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TimeOfAvailability currentTime = list.get(position);
        holder.startTime.setText(currentTime.getHourStart());
        holder.endTime.setText(currentTime.getHourEnd());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}