package com.infomatics.oxfam.twat.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.infomatics.oxfam.twat.R;
import com.infomatics.oxfam.twat.databinding.CardEntryBinding;
import com.infomatics.oxfam.twat.model.room.entity.EntryLog;

import java.util.List;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.ViewHolder>{

    private List<EntryLog> entries;
    private LayoutInflater layoutInflater;
    private Context context;
    public EntryAdapter(List<EntryLog> entries){
        this.entries = entries;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
            this.context = viewGroup.getContext();
        }
        CardEntryBinding cardEntryBinding = DataBindingUtil.inflate(layoutInflater,R.layout.card_entry , viewGroup, false);
        return new ViewHolder(cardEntryBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.cardEntryBinding.setEntry(entries.get(i));
        /*if(entries.get(i).getIsSynced() == 1)
            viewHolder.cardEntryBinding.bibNo.setTextColor(context.getResources().getColor(R.color.green));
        else
            viewHolder.cardEntryBinding.bibNo.setTextColor(context.getResources().getColor(R.color.red));*/
        if(entries.get(i).getIsSynced() == 1)
            viewHolder.cardEntryBinding.unsyncIndicator.setVisibility(View.GONE);
        else
            viewHolder.cardEntryBinding.unsyncIndicator.setVisibility(View.VISIBLE);

        if(entries.get(i).getHasRetired() == 1){
            viewHolder.cardEntryBinding.inEntry.setVisibility(View.GONE);
            viewHolder.cardEntryBinding.outEntry.setVisibility(View.GONE);
            viewHolder.cardEntryBinding.retireEntry.setVisibility(View.VISIBLE);
        }else{
            viewHolder.cardEntryBinding.inEntry.setVisibility(View.VISIBLE);
            viewHolder.cardEntryBinding.outEntry.setVisibility(View.VISIBLE);
            viewHolder.cardEntryBinding.retireEntry.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public void setData(List<EntryLog> entries){
        this.entries = entries;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        final CardEntryBinding cardEntryBinding;
        public ViewHolder(CardEntryBinding cardEntryBinding){
            super(cardEntryBinding.getRoot());
            this.cardEntryBinding = cardEntryBinding;
        }
    }
}
