package com.infomatics.oxfam.twat.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.infomatics.oxfam.twat.R;
import com.infomatics.oxfam.twat.databinding.CardTeamProgressBinding;
import com.infomatics.oxfam.twat.model.ReportList;

import java.util.ArrayList;

public class TeamProgressAdapter extends RecyclerView.Adapter<TeamProgressAdapter.ViewHolder>{

    private ArrayList<ReportList> reports;
    private LayoutInflater layoutInflater;

    public TeamProgressAdapter(ArrayList<ReportList> reports){
        this.reports = reports;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(layoutInflater == null)
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        CardTeamProgressBinding cardTeamProgressBinding = DataBindingUtil.inflate(layoutInflater,
                R.layout.card_team_progress, viewGroup, false);
        return new ViewHolder(cardTeamProgressBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.cardTeamProgressBinding.setDataModel(reports.get(i));
        viewHolder.cardTeamProgressBinding.sNo.setText(""+(i+1));
    }

    public void setList(ArrayList<ReportList> reports){
        this.reports = reports;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return reports.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        final CardTeamProgressBinding cardTeamProgressBinding;
        public ViewHolder(CardTeamProgressBinding cardTeamProgressBinding){
            super(cardTeamProgressBinding.getRoot());
            this.cardTeamProgressBinding = cardTeamProgressBinding;
        }
    }
}
