package com.infomatics.oxfam.twat.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.infomatics.oxfam.twat.R;
import com.infomatics.oxfam.twat.databinding.CardRetirementBinding;
import com.infomatics.oxfam.twat.model.retirement.Datalist;

import java.util.ArrayList;

public class RetirementAdapter extends RecyclerView.Adapter<RetirementAdapter.ViewHolder>{

    private ArrayList<Datalist> retirements;
    private LayoutInflater layoutInflater;

    public RetirementAdapter(ArrayList<Datalist> retirements){
        this.retirements =retirements;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(layoutInflater == null)
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        CardRetirementBinding cardRetirementBinding = DataBindingUtil.inflate(layoutInflater
        , R.layout.card_retirement, viewGroup, false);
        return new ViewHolder(cardRetirementBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.cardRetirementBinding.setRetirementBean(retirements.get(i));
    }

    @Override
    public int getItemCount() {
        return retirements!= null ? retirements.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        final CardRetirementBinding cardRetirementBinding;
        public ViewHolder(CardRetirementBinding cardRetirementBinding){
            super(cardRetirementBinding.getRoot());
            this.cardRetirementBinding = cardRetirementBinding;
        }

    }
}
