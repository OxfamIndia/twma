package com.infomatics.oxfam.twat.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.infomatics.oxfam.twat.R;
import com.infomatics.oxfam.twat.databinding.CardNotificationBinding;
import com.infomatics.oxfam.twat.model.room.entity.NotificationEntity;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{

    private ArrayList<NotificationEntity> reports;
    private LayoutInflater layoutInflater;

    public NotificationAdapter(ArrayList<NotificationEntity> reports){
        this.reports = reports;
        System.out.println("Size"+reports.size());

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(layoutInflater == null)
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        CardNotificationBinding CardNotificationBinding = DataBindingUtil.inflate(layoutInflater,
                R.layout.card_notification, viewGroup, false);
        return new ViewHolder(CardNotificationBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.CardNotificationBinding.setDataModel(reports.get(i));
    }

    public void setList(ArrayList<NotificationEntity> r) {
       reports.clear();
        reports.addAll(r);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return reports.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        final CardNotificationBinding CardNotificationBinding;
        public ViewHolder(CardNotificationBinding CardNotificationBinding){
            super(CardNotificationBinding.getRoot());
            this.CardNotificationBinding = CardNotificationBinding;
        }
    }
}
