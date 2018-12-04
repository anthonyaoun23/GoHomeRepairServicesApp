package com.goteam.gohomerepairservicesapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyBookingsAdapter extends RecyclerView.Adapter<MyBookingsAdapter.ViewHolder> {

    private MyBookingsAdapter.OnItemClickListener cardClickListener;
    private ArrayList<Booking> list;

    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }

    public MyBookingsAdapter(ArrayList<Booking> list){
        this.list=list;
    }

    public void setOnCardClick(OnItemClickListener listener) {
        cardClickListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView serviceProviderName;
        public TextView bookingTime;
        public ImageView deleteBooking;

        public ViewHolder(final View itemView, final OnItemClickListener listener) {
            super(itemView);

            serviceProviderName = itemView.findViewById(R.id.serviceProvider);
            bookingTime = itemView.findViewById(R.id.bookingTime);
            deleteBooking = itemView.findViewById(R.id.booking_delete);
            deleteBooking.setOnClickListener(new View.OnClickListener() {
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_booking_card, parent, false);
        ViewHolder holder = new ViewHolder(v, cardClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Booking booking = list.get(position);
        holder.serviceProviderName.setText(booking.getServiceProvider().getCompanyName());
        holder.bookingTime.setText(String.format("%d:%d", booking.getDateTime().getHourStart(),booking.getDateTime().getMinuteStart()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
