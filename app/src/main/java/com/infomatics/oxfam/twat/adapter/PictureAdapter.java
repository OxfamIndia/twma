package com.infomatics.oxfam.twat.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.infomatics.oxfam.twat.R;
import com.infomatics.oxfam.twat.interfaces.ItemPositionClickListener;
import com.infomatics.oxfam.twat.model.image.ImageModel;

import java.util.ArrayList;

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.ViewHolder>{

    private ArrayList<ImageModel> images;
    private ItemPositionClickListener listener;
    private LayoutInflater layoutInflater;
    public PictureAdapter(ArrayList<ImageModel> images, ItemPositionClickListener listener){
        this.images = images;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(layoutInflater == null)
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.card_add_picture, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.itemView.setOnClickListener(v->{
            listener.onItemClick(i);
        });
        viewHolder.addLayout.setVisibility(View.VISIBLE);
        try {
            if (images.size() > 1) {
                try {
                    if (images.get(i).getBitmap() != null) {
                        viewHolder.image.setImageBitmap(images.get(i).getBitmap());
                        viewHolder.addLayout.setVisibility(View.GONE);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        LinearLayout addLayout;
        public ViewHolder(View view){
            super(view);
            addLayout = view.findViewById(R.id.layout_add);
            image = view.findViewById(R.id.image);
        }
    }
}
