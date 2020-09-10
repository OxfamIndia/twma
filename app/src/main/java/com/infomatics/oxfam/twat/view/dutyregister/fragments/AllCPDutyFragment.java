package com.infomatics.oxfam.twat.view.dutyregister.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.infomatics.oxfam.twat.R;
import com.infomatics.oxfam.twat.adapter.ExpandableDutyAdapter;
import com.infomatics.oxfam.twat.view.dutyregister.DutyRegisterAcitivity;


public class AllCPDutyFragment extends Fragment {
    private ExpandableListView expandableListView;
    ExpandableDutyAdapter expandableListAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_duties, container, false);
        expandableListView = view.findViewById(R.id.expandableListView);
        expandableListAdapter = new ExpandableDutyAdapter(getActivity(),
                ((DutyRegisterAcitivity)getActivity()).getExpandableListTitle(),
                ((DutyRegisterAcitivity)getActivity()).getExpandableListDetail());
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            String number = ((DutyRegisterAcitivity)getActivity()).getExpandableListDetail().get(
                    ((DutyRegisterAcitivity)getActivity()).getExpandableListTitle().get(groupPosition)).get(
                    childPosition).getPhone();
            if(number != null && number.length() > 0) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", number, null));
                startActivity(intent);
            }
            return false;
        });
        return view;
    }
}
