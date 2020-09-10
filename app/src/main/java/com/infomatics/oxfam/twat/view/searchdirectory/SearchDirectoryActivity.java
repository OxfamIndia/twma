package com.infomatics.oxfam.twat.view.searchdirectory;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.Observable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.infomatics.oxfam.twat.R;
import com.infomatics.oxfam.twat.adapter.ContactAdapter;
import com.infomatics.oxfam.twat.adapter.ExpandableListAdapter;
import com.infomatics.oxfam.twat.interfaces.ItemPositionClickListener;
import com.infomatics.oxfam.twat.model.room.entity.ContactEntity;
import com.infomatics.oxfam.twat.model.room.entity.RoleEntity;
import com.infomatics.oxfam.twat.util.AppConstants;
import com.infomatics.oxfam.twat.util.ApplicationUtils;
import com.infomatics.oxfam.twat.view.BaseActivity;
import com.infomatics.oxfam.twat.viewmodel.ContactViewModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class SearchDirectoryActivity extends BaseActivity implements ItemPositionClickListener {

    private ImageView btnBack;
    private TextView tvHeaderText;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ContactAdapter contactAdapter;
    private ArrayList<ContactEntity> contacts;
    private ArrayList<ContactEntity> originalList;
    private ArrayList<ContactEntity> searchList;
    private EditText searchText;
    private ImageView btnSearch;
    private FloatingActionButton btnAddContact;
    private ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<ContactEntity>> expandableListDetail;
    private ContactViewModel contactViewModel;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_directory);
        contactViewModel = ViewModelProviders.of(SearchDirectoryActivity.this).get(ContactViewModel.class);
        componentBinding();
    }

    private void componentBinding(){
        btnBack = findViewById(R.id.btn_back);
        tvHeaderText = findViewById(R.id.tv_header_text);
        recyclerView = findViewById(R.id.contact_recycler);
        searchText = findViewById(R.id.et_search);
        btnSearch = findViewById(R.id.img_search);
        btnAddContact = findViewById(R.id.add_contact);
        expandableListView = findViewById(R.id.expandableListView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        contacts = new ArrayList<>();
        searchList = new ArrayList<>();
        originalList = new ArrayList<>();
        contactAdapter = new ContactAdapter(contacts, this);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setAdapter(contactAdapter);


        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(searchText.getText().toString().trim().length() == 0){
                    btnSearch.setImageDrawable(getResources().getDrawable(R.drawable.ic_search_black_24dp));
                    contacts.clear();
                    contacts.addAll(originalList);
                    contactAdapter.notifyDataSetChanged();
                    recyclerView.setVisibility(View.GONE);
                    expandableListView.setVisibility(View.VISIBLE);
                }else{
                    btnSearch.setImageDrawable(getResources().getDrawable(R.drawable.ic_close));
                    recyclerView.setVisibility(View.VISIBLE);
                    expandableListView.setVisibility(View.GONE);
                }
                searchContacts(s.toString().trim().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnSearch.setOnClickListener(v->{
            if(searchText.getText().toString().trim().length() > 0){
                searchText.setText("");
            }
        });
        btnBack.setVisibility(View.VISIBLE);
        tvHeaderText.setText("Event Directory");
        btnBack.setOnClickListener(v->SearchDirectoryActivity.this.finish());
        btnAddContact.setOnClickListener(v->{
            if(ApplicationUtils.isNetworkAvailable(SearchDirectoryActivity.this)) {
                startActivity(new Intent(SearchDirectoryActivity.this, NewContactActivity.class));
            }else{
                ApplicationUtils.showToast(SearchDirectoryActivity.this, "This functionality is not available in offline mode.");
            }
        });
        getContacts();
    }

    private void getContacts(){
        progressDialog = ApplicationUtils
                .getProgressDialog(new WeakReference<>(SearchDirectoryActivity.this), "Please wait...",false);
        if(ApplicationUtils.isNetworkAvailable(SearchDirectoryActivity.this)) {
            progressDialog.show();
            contactViewModel.getAllRoles().observe(SearchDirectoryActivity.this, res -> {
                if(progressDialog.isShowing())
                    progressDialog.dismiss();
                if (res != null) {
                    contactViewModel.addRoles(res.getDatalist());
                    contactViewModel.getAllContacts().observe(SearchDirectoryActivity.this, response -> {
                        if (response != null) {
                            contacts.clear();
                            if (response.getDatalist() != null) {
                                contactViewModel.addContacts(response.getDatalist());
                                populateFromDB();
                            }
                        }
                    });
                }else{
                    populateFromDB();
                }
            });
        }else{
            populateFromDB();
        }
    }
    private void populateFromDB(){
        populate();
    }
    private void makedataAndNotify(List<ContactEntity> contactEntities){
        if(contactEntities != null){
            List<RoleEntity> allRoles = contactViewModel.getAllRolesDromDB();
            if(allRoles != null){
                expandableListDetail = getData(allRoles);
            }
        }
    }
    private void populate(){
        contactViewModel.getAllContactsFromDB().observe(SearchDirectoryActivity.this, res->{
            if(res != null) {
                contacts.clear();
                contacts.addAll(res);
                makedataAndNotify(res);
                contactAdapter.notifyDataSetChanged();
                originalList.addAll(contacts);
                expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
                expandableListAdapter = new ExpandableListAdapter(this, expandableListTitle, expandableListDetail);
                expandableListView.setAdapter(expandableListAdapter);
                expandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
                    String number = expandableListDetail.get(expandableListTitle.get(groupPosition)).get(
                            childPosition).getNumber();
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", number, null));
                    startActivity(intent);
                    return false;
                });
            }
        });


    }

    private void searchContacts(String query){
        searchList.clear();
        for(ContactEntity contactModel : originalList){
            if(contactModel.getNumber().toLowerCase().contains(query)
            || contactModel.getName().toLowerCase().contains(query)
            || contactModel.getRole().toLowerCase().contains(query)){
                searchList.add(contactModel);
            }
        }

        if(searchList.size() > 0) {
            contacts.clear();
            contacts.addAll(searchList);
            contactAdapter.notifyDataSetChanged();
        }else{
            contacts.clear();
            contactAdapter.notifyDataSetChanged();
            Toast.makeText(SearchDirectoryActivity.this, "No Contact Found", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onItemClick(int position) {
        if(contacts.get(position).getNumber() != null) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", contacts.get(position).getNumber(), null));
            startActivity(intent);
        }
    }

    public HashMap<String, List<ContactEntity>> getData(List<RoleEntity> allRoles) {
        HashMap<String, List<ContactEntity>> expandableListDetail = new HashMap<>();

        for(RoleEntity roleEntity : allRoles){
            if(roleEntity.toString().toLowerCase().contains("admin"))
                continue;
            if(roleEntity.toString().toLowerCase().contains("super"))
                continue;
            if(roleEntity.toString().toLowerCase().contains("checkpoint"))
                continue;
            if(roleEntity.toString().toLowerCase().contains("coordinator"))
                continue;
            List<ContactEntity> contactEntities = new ArrayList<>();
            for(ContactEntity contactEntity : contacts){
                if(roleEntity.getRole().equalsIgnoreCase(contactEntity.getRole())){
                    contactEntities.add(contactEntity);
                }
            }
            Collections.sort(contactEntities, (o1, o2) -> o1.getPosting().compareTo(o2.getPosting()));
            expandableListDetail.put(roleEntity.getRole()+"("+contactEntities.size()+")", contactEntities);

        }
        return expandableListDetail;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(AppConstants.ADDED_NEW_CONTACT){
            getContacts();
        }

        isConnectedToInternet.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                runOnUiThread(() -> {
                    if(isConnectedToInternet.get()){
                        findViewById(R.id.online_offline).setVisibility(View.GONE);
                    }else{
                        findViewById(R.id.online_offline).setVisibility(View.VISIBLE);
                    }
                });
            }
        });
        runOnUiThread(() -> {
            if(isConnectedToInternet.get()){
                findViewById(R.id.online_offline).setVisibility(View.GONE);
            }else{
                findViewById(R.id.online_offline).setVisibility(View.VISIBLE);
            }
        });
    }
}
