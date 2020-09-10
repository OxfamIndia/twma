package com.infomatics.oxfam.twat.view.dutyregister.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.infomatics.oxfam.twat.R;
import com.infomatics.oxfam.twat.adapter.DutyAdapter;
import com.infomatics.oxfam.twat.interfaces.ItemPositionClickListener;
import com.infomatics.oxfam.twat.model.dutyregister.Datalist;
import com.infomatics.oxfam.twat.model.dutyregister.DutyList;
import com.infomatics.oxfam.twat.model.dutyregister.User;
import com.infomatics.oxfam.twat.util.AppConstants;
import com.infomatics.oxfam.twat.view.dutyregister.DutyRegisterAcitivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DutyFragment  extends Fragment implements ItemPositionClickListener {

    private Spinner spinner;
    private ArrayList<User> userList = new ArrayList<>();
    private User selectedUser;
    private ArrayList<String> users;
    private RecyclerView dutiesRecycler;
    private LinearLayoutManager mLinearLayoutManager;
    private DutyAdapter dutyAdapter;
    private TextView dutyButton;
    SharedPreferences sharedPreferences;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_duty, container, false);
        spinner = view.findViewById(R.id.spinnerDuties);
        dutiesRecycler = view.findViewById(R.id.duties_recycler);
        dutyButton = view.findViewById(R.id.duty_on_off);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dutiesRecycler.setLayoutManager(mLinearLayoutManager);
        dutyAdapter = new DutyAdapter(userList, this);
        dutiesRecycler.setAdapter(dutyAdapter);
        ((DutyRegisterAcitivity)getActivity()).hasFetched.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if(((DutyRegisterAcitivity)getActivity()).hasFetched.get()){
                    updateUsers();
                    List<DutyList> dutyList = ((DutyRegisterAcitivity)getActivity()).getCurrentCp();
                    if(dutyList != null) {
                        ArrayAdapter<DutyList> adapter =
                                new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, dutyList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(adapter);
                    }
                }
            }
        });
        if(((DutyRegisterAcitivity)getActivity()).hasFetched.get()){
            updateUsers();
            List<DutyList> dutyList = ((DutyRegisterAcitivity)getActivity()).getCurrentCp();
            if(dutyList != null) {
                ArrayAdapter<DutyList> adapter =
                        new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, dutyList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
            }
        }



        sharedPreferences = getActivity().getSharedPreferences(AppConstants.APP_PREF, Context.MODE_PRIVATE);
        dutyButton.setText("Check In");

        dutyButton.setOnClickListener(v->{
            if(dutyButton.getText().toString().equalsIgnoreCase("Check In")){
                ((DutyRegisterAcitivity) getActivity()).changeuserDuty(((DutyList)spinner.getSelectedItem()).getId());
            }else {
                if (selectedUser != null)
                    ((DutyRegisterAcitivity) getActivity()).changeuserDuty(Integer.toString(selectedUser.getId()));
            }

        });
        return view;
    }

    public void updateUsers(){
        if(((DutyRegisterAcitivity)getActivity()) != null &&
                ((DutyRegisterAcitivity)getActivity()).getAllDuties() != null){
            userList.clear();
            userList.addAll(((DutyRegisterAcitivity)getActivity()).getAllDuties());
            dutyAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public void onItemClick(int position) {
        if(userList.get(position).isChecked()) {
            userList.get(position).setChecked(false);
            selectedUser = null;
            dutyButton.setText("Check In");
        }
        else {
            userList.get(position).setChecked(true);
            selectedUser = userList.get(position);
            dutyButton.setText("Check Out");
        }
        for(int i =0; i < userList.size(); i++){
            if(i==position)
                continue;
            else
                userList.get(i).setChecked(false);
        }
        dutyAdapter.notifyDataSetChanged();
    }
}
