package com.goteam.gohomerepairservicesapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;

public class SpCurrentServiceAdapter extends RecyclerView.Adapter<SpCurrentServiceAdapter.ViewHolder> {

    private OnItemClickListener cardClickListener;
    private LinkedList<Service> list;



    public interface OnItemClickListener {
        void onItemClick(int position);

        void onDeleteClick(int position);
    }

    public SpCurrentServiceAdapter(LinkedList<Service> list){
        this.list=list;
    }

    public void setOnCardClick(OnItemClickListener listener) {

        cardClickListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView service_name;
        public TextView rate_value;
        public ImageView deleteService;

        public ViewHolder(final View itemView, final OnItemClickListener listener) {
            super(itemView);

            service_name = itemView.findViewById(R.id.service_name);
            deleteService = itemView.findViewById(R.id.service_delete);
            rate_value = itemView.findViewById(R.id.rateValue);
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
            deleteService.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);

                        }
                    }
                }
            });


        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_card, parent, false);
        ViewHolder holder = new ViewHolder(v, cardClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Service currentService = list.get(position);
        holder.service_name.setText(currentService.getServiceName());
        holder.rate_value.setText(currentService.getRate()+"$/hr");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}