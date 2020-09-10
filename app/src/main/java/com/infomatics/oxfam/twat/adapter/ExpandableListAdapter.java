package com.infomatics.oxfam.twat.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.infomatics.oxfam.twat.R;
import com.infomatics.oxfam.twat.model.contact.ContactModel;
import com.infomatics.oxfam.twat.model.room.entity.ContactEntity;
import com.infomatics.oxfam.twat.util.circletextview.CircleTextView;

import java.util.HashMap;
import java.util.List;


public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<ContactEntity>> expandableListDetail;

    public ExpandableListAdapter(Context context, List<String> expandableListTitle,
                                 HashMap<String, List<ContactEntity>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    public void setData(List<String> expandableListTitle,
                        HashMap<String, List<ContactEntity>> expandableListDetail){
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
        notifyDataSetChanged();
    }
    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String listTitle = (String) getGroup(i);
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) viewGroup.getContext().
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.group, null);
        }
        TextView listTitleTextView = (TextView) view
                .findViewById(R.id.listTitle);
        listTitleTextView.setText(listTitle);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        final ContactEntity contactEntity = (ContactEntity) getChild(i, i1);
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) viewGroup.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            try {
                view = layoutInflater.inflate(R.layout.child, null);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        CircleTextView ctv = view.findViewById(R.id.ctv);
        TextView name = view.findViewById(R.id.contact_name);
        TextView contact = view.findViewById(R.id.contact_number);
        ctv.setCustomText(contactEntity.getName().toUpperCase());
        ctv.setSolidColor(i);
        ctv.setTextColor(Color.WHITE);
        name.setText(contactEntity.getName() + " (CP:"+contactEntity.getPosting()+")");
        contact.setText(contactEntity.getNumber());
        return view;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
