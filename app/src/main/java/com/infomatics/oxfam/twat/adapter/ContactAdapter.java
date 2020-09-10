package com.infomatics.oxfam.twat.adapter;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.infomatics.oxfam.twat.R;
import com.infomatics.oxfam.twat.databinding.CardContactBinding;
import com.infomatics.oxfam.twat.interfaces.ItemPositionClickListener;
import com.infomatics.oxfam.twat.model.contact.ContactModel;
import com.infomatics.oxfam.twat.model.room.entity.ContactEntity;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder>{

    private ArrayList<ContactEntity> contacts;
    private ItemPositionClickListener listener;
    private LayoutInflater layoutInflater;

    public ContactAdapter(ArrayList<ContactEntity> contacts, ItemPositionClickListener listener){
        this.contacts = contacts;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(layoutInflater == null){
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        CardContactBinding cardContactBinding = DataBindingUtil.inflate(layoutInflater,
                R.layout.card_contact, viewGroup, false);
        return new ViewHolder(cardContactBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.cardContactBinding.setContactModel(contacts.get(i));
        viewHolder.cardContactBinding.ctv.setCustomText(contacts.get(i).getName());
        viewHolder.cardContactBinding.ctv.setSolidColor(i);
        viewHolder.cardContactBinding.ctv.setTextColor(Color.WHITE);
        viewHolder.cardContactBinding.ctv.setCustomTextSize(22);
        viewHolder.itemView.setOnClickListener(v->listener.onItemClick(i));
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        final CardContactBinding cardContactBinding;
        public ViewHolder(CardContactBinding cardContactBinding){
            super(cardContactBinding.getRoot());
            this.cardContactBinding = cardContactBinding;
        }
    }
}
