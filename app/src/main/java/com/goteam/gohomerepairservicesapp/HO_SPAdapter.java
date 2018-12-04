package com.goteam.gohomerepairservicesapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;

public class HO_SPAdapter extends RecyclerView.Adapter<HO_SPAdapter.ViewHolder> {

    private OnItemClickListener cardClickListener;
    private LinkedList<ServiceProvider> list;



    public interface OnItemClickListener {
        void onItemClick(int position);

    }

    public HO_SPAdapter(LinkedList<ServiceProvider> list){
        this.list=list;
    }

    public void setOnCardClick(OnItemClickListener listener) {

        cardClickListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView sp_name;

        public ViewHolder(final View itemView, final OnItemClickListener listener) {
            super(itemView);

            sp_name = itemView.findViewById(R.id.sp_name);
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ho_sp_card, parent, false);
        ViewHolder holder = new ViewHolder(v, cardClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ServiceProvider currentSp = list.get(position);
        holder.sp_name.setText(currentSp.getCompanyName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}