package com.goteam.gohomerepairservicesapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;

public class SpAvailableServiceAdapter extends RecyclerView.Adapter<SpAvailableServiceAdapter.ViewHolder> {

    private OnItemClickListener cardClickListener;
    private ArrayList<Service> list;



    public interface OnItemClickListener {
        void onAddClick(int position);
    }

    public SpAvailableServiceAdapter(ArrayList<Service> list){
        this.list=list;
    }

    public void setOnCardClick(OnItemClickListener listener) {

        cardClickListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView service_name;
        public ImageView addService;

        public ViewHolder(final View itemView, final OnItemClickListener listener) {
            super(itemView);

            service_name = itemView.findViewById(R.id.service_name);
            addService = itemView.findViewById(R.id.service_add);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
            addService.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onAddClick(position);

                        }
                    }
                }
            });


        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_card_sp, parent, false);
        SpAvailableServiceAdapter.ViewHolder holder = new SpAvailableServiceAdapter.ViewHolder(v, cardClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}