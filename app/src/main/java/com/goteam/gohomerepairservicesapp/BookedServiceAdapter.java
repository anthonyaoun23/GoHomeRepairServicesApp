package com.goteam.gohomerepairservicesapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class BookedServiceAdapter extends RecyclerView.Adapter<BookedServiceAdapter.ViewHolder> {

    private BookedServiceAdapter.OnItemClickListener cardClickListener;
    private ArrayList<Service> list;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public BookedServiceAdapter(ArrayList<Service> list){
        this.list=list;
    }

    public void setOnCardClick(BookedServiceAdapter.OnItemClickListener listener) {

        cardClickListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView service_name;
        public TextView service_rate;

        public ViewHolder(final View itemView, final OnItemClickListener listener) {
            super(itemView);

            service_name = itemView.findViewById(R.id.service_title);
            service_rate = itemView.findViewById(R.id.service_rate);
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
    public BookedServiceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sp_services_card, parent, false);
        BookedServiceAdapter.ViewHolder holder = new BookedServiceAdapter.ViewHolder(v, cardClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(BookedServiceAdapter.ViewHolder holder, int position) {
        Service service = list.get(position);
        holder.service_name.setText(service.getServiceName());
        holder.service_rate.setText(String.valueOf(service.getRate()));


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
