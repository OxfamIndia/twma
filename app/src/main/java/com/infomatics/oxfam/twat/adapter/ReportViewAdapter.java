package com.infomatics.oxfam.twat.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.infomatics.oxfam.twat.R;
import com.infomatics.oxfam.twat.databinding.CardReportBinding;
import com.infomatics.oxfam.twat.model.reports.Datalist;
import com.infomatics.oxfam.twat.view.reportincident.LostNFoundDetail;
import com.infomatics.oxfam.twat.view.reportincident.ViewReports;

import java.util.ArrayList;

public class ReportViewAdapter extends RecyclerView.Adapter<ReportViewAdapter.ViewHolder>{

    private ArrayList<Datalist> reports;
    private LayoutInflater layoutInflater;
    private Context context;


    public ReportViewAdapter(ArrayList<Datalist> reports){
        this.reports = reports;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(layoutInflater == null){
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
            context = viewGroup.getContext();
        }
        CardReportBinding cardReportBinding = DataBindingUtil.inflate(layoutInflater, R.layout.card_report, viewGroup, false);
        return new ViewHolder(cardReportBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.cardReportBinding.setReportBean(reports.get(i));
        viewHolder.cardReportBinding.checkpointId.setText("CheckPoint: "+reports.get(i).getCheckpointId());
        if(context instanceof ViewReports){
            if(((ViewReports)context).isLostnFound()){
                viewHolder.cardReportBinding.severity.setVisibility(View.GONE);
                viewHolder.cardReportBinding.status.setText("Status: "+(reports.get(i).getStatus()==1 ? "Closed" : "Active"));
            }else{
                viewHolder.cardReportBinding.severity.setVisibility(View.VISIBLE);
                viewHolder.cardReportBinding.status.setText("Status: "+(reports.get(i).getStatus()==1 ? "Closed" : "Active"));
            }
        }

        if(reports.get(i).getImage() != null){
            viewHolder.cardReportBinding.imageView.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(reports.get(i).getImage())
                    .into(viewHolder.cardReportBinding.imageView);
        }else{
            viewHolder.cardReportBinding.imageView.setVisibility(View.GONE);
        }

        viewHolder.itemView.setOnClickListener(v->{
            if(((ViewReports)context).isLostnFound()){
                context.startActivity(new Intent(context, LostNFoundDetail.class)
                .putExtra("Data", reports.get(i)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return reports.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        final CardReportBinding cardReportBinding;
        public ViewHolder(CardReportBinding cardReportBinding){
            super(cardReportBinding.getRoot());
            this.cardReportBinding = cardReportBinding;
        }
    }
}
