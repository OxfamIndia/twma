package com.infomatics.oxfam.twat.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.infomatics.oxfam.twat.R;
import com.infomatics.oxfam.twat.databinding.ActionCardBinding;
import com.infomatics.oxfam.twat.interfaces.ItemPositionClickListener;
import com.infomatics.oxfam.twat.model.dashboardhome.DashboardItemModel;

import java.util.ArrayList;

public class ActionAdapter  extends RecyclerView.Adapter<ActionAdapter.ViewHolder> {

    private ArrayList<DashboardItemModel> actions;
    private LayoutInflater layoutInflater;
    private ItemPositionClickListener listener;
    public ActionAdapter(ArrayList<DashboardItemModel> actions, ItemPositionClickListener listener){
        this.actions = actions;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(layoutInflater == null)
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        ActionCardBinding actionCardBinding = DataBindingUtil.inflate(layoutInflater, R.layout.action_card,
                viewGroup, false);
        return new ViewHolder(actionCardBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.actionCardBinding.setAction(actions.get(i));
        viewHolder.itemView.setOnClickListener(v-> listener.onItemClick(i));
    }

    @Override
    public int getItemCount() {
        return actions.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ActionCardBinding actionCardBinding;
        public ViewHolder(ActionCardBinding actionCardBinding){
            super(actionCardBinding.getRoot());
            this.actionCardBinding = actionCardBinding;
        }
    }
}
