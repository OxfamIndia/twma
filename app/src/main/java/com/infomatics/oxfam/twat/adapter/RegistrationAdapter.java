package com.infomatics.oxfam.twat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.infomatics.oxfam.twat.R;
import com.infomatics.oxfam.twat.interfaces.ItemPositionClickListener;
import com.infomatics.oxfam.twat.model.register.RegisterModel;

import java.util.ArrayList;

public class RegistrationAdapter extends RecyclerView.Adapter<RegistrationAdapter.ViewHolder>{

    private ArrayList<RegisterModel> registerModels;
    private LayoutInflater layoutInflater;
    private Context context;
    private ItemPositionClickListener itemPositionClickListener;
    public RegistrationAdapter(ArrayList<RegisterModel> registerModels, ItemPositionClickListener itemPositionClickListener){
        this.registerModels = registerModels;
        this.itemPositionClickListener = itemPositionClickListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
            context = viewGroup.getContext();
        }
        View view = layoutInflater.inflate(R.layout.card_register_member,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        RegisterModel registerModel = registerModels.get(i);

        viewHolder.tvName.setText(registerModel.getWalkerName());
        if(registerModel.getPhone() != null && registerModel.getPhone().length() > 0)
            viewHolder.phoneNumber.setText("Phone Number:   "+registerModel.getPhone());
        if(registerModel.getBibNo()!= null && registerModel.getBibNo().length() > 0)
            viewHolder.tvTagId.setText("Bib No:   "+registerModel.getBibNo());

        if( registerModel.getLastCP() > 0){
            viewHolder.lastCP.setText("Last Reported CP:   "+registerModel.getLastCP());
            viewHolder.lastCPTime.setText("Last CP Time:   "+registerModel.getLastCPTime());
        }
        else
            viewHolder.tvTagId.setText("Bib No not allocated");
        switch(i){
            case 0:
                viewHolder.colorCircle.setImageDrawable(context.getResources().getDrawable(R.drawable.red_circle));
                break;
            case 1:
                viewHolder.colorCircle.setImageDrawable(context.getResources().getDrawable(R.drawable.blue_circle));
                break;
            case 2:
                viewHolder.colorCircle.setImageDrawable(context.getResources().getDrawable(R.drawable.yellow_circle));
                break;
            case 3:
                viewHolder.colorCircle.setImageDrawable(context.getResources().getDrawable(R.drawable.green_circle));
                break;
        }
        viewHolder.itemView.setOnClickListener(v->{
            itemPositionClickListener.onItemClick(i);
        });
    }

    @Override
    public int getItemCount() {
        return registerModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName;
        private TextView tvTagId, phoneNumber, lastCP, lastCPTime;
        private ImageView colorCircle;
        private RelativeLayout card;

        public ViewHolder(View view){
            super(view);
            tvName = view.findViewById(R.id.name);
            tvTagId = view.findViewById(R.id.tag_id);
            colorCircle = view.findViewById(R.id.color_circle);
            card = view.findViewById(R.id.card_member);
            phoneNumber = view.findViewById(R.id.phone_number);
            lastCP = view.findViewById(R.id.last_cp);
            lastCPTime = view.findViewById(R.id.last_cp_time);
        }
    }

}
