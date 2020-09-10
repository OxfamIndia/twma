package com.infomatics.oxfam.twat.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.infomatics.oxfam.twat.R;
import com.infomatics.oxfam.twat.databinding.CardNavigationItemBinding;
import com.infomatics.oxfam.twat.interfaces.ItemPositionClickListener;
import com.infomatics.oxfam.twat.model.navigation.NavigationIconModel;

import java.util.ArrayList;

public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.ViewHolder>{

    private ArrayList<NavigationIconModel> navActions;
    private ItemPositionClickListener listener;
    private LayoutInflater layoutInflater;

    public NavigationAdapter(ArrayList<NavigationIconModel> navActions, ItemPositionClickListener listener){
        this.navActions = navActions;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(layoutInflater == null)
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        CardNavigationItemBinding cardNavigationItemBinding = DataBindingUtil.inflate(layoutInflater,
                R.layout.card_navigation_item, viewGroup, false);
        return new ViewHolder(cardNavigationItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.cardNavigationItemBinding.setNavigationModel(navActions.get(i));
        viewHolder.itemView.setOnClickListener(v->listener.onItemClick(i));
    }

    @Override
    public int getItemCount() {
        return navActions.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        CardNavigationItemBinding cardNavigationItemBinding;
        public ViewHolder(CardNavigationItemBinding cardNavigationItemBinding){
            super(cardNavigationItemBinding.getRoot());
            this.cardNavigationItemBinding  = cardNavigationItemBinding;
        }
    }
}
