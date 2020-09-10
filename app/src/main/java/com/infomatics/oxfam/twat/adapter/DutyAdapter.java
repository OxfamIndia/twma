package com.infomatics.oxfam.twat.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.infomatics.oxfam.twat.R;
import com.infomatics.oxfam.twat.databinding.CardDutyBinding;
import com.infomatics.oxfam.twat.interfaces.ItemPositionClickListener;
import com.infomatics.oxfam.twat.model.dutyregister.User;

import java.util.List;

public class DutyAdapter extends RecyclerView.Adapter<DutyAdapter.ViewHolder>{

    private List<User> allDuties;
    private LayoutInflater layoutInflater;
    private ItemPositionClickListener itemPositionClickListener;

    public DutyAdapter(List<User> allDuties, ItemPositionClickListener itemPositionClickListener){
        this.allDuties = allDuties;
        this.itemPositionClickListener = itemPositionClickListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(layoutInflater == null)
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        CardDutyBinding cardDutyBinding = DataBindingUtil.inflate(layoutInflater
        , R.layout.card_duty, viewGroup, false);
        return new ViewHolder(cardDutyBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.cardDutyBinding.setUser(allDuties.get(i));
        viewHolder.cardDutyBinding.cbDutyUser.setOnClickListener(v->{
           itemPositionClickListener.onItemClick(i);
        });
        if(allDuties.get(i).isChecked())
            viewHolder.cardDutyBinding.cbDutyUser.setChecked(true);
        else
            viewHolder.cardDutyBinding.cbDutyUser.setChecked(false);
    }

    @Override
    public int getItemCount() {
        return allDuties.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        final CardDutyBinding cardDutyBinding;
        public ViewHolder(CardDutyBinding cardDutyBinding){
            super(cardDutyBinding.getRoot());
            this.cardDutyBinding = cardDutyBinding;
        }
    }
}
