package com.infomatics.oxfam.twat.view.searchdirectory;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.infomatics.oxfam.twat.R;
import com.infomatics.oxfam.twat.model.contact.ContactModel;
import com.infomatics.oxfam.twat.model.room.entity.ContactEntity;
import com.infomatics.oxfam.twat.model.room.entity.RoleEntity;
import com.infomatics.oxfam.twat.util.AppConstants;
import com.infomatics.oxfam.twat.util.ApplicationUtils;
import com.infomatics.oxfam.twat.view.BaseActivity;
import com.infomatics.oxfam.twat.viewmodel.ContactViewModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class NewContactActivity extends BaseActivity {

    private EditText etFirstName;
    private EditText etLastName;
    private EditText etContactnumber;
    private Spinner roleSpinner;
    private TextView saveContact;

    private ImageView imgBack;
    private TextView headerText;
    private int selectedPosition;
    private List<RoleEntity> allRoles;
    private ContactViewModel contactViewModel;
    private ProgressDialog progressDialog;
    private TextView posting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
        contactViewModel = ViewModelProviders.of(NewContactActivity.this).get(ContactViewModel.class);
        AppConstants.ADDED_NEW_CONTACT = false;
        componentBinding();
    }
    private void componentBinding(){
        etFirstName =findViewById(R.id.txt_first_name);
        etLastName = findViewById(R.id.txt_last_name);
        etContactnumber = findViewById(R.id.txt_contact_number);
        roleSpinner = findViewById(R.id.spinner_role);
        saveContact = findViewById(R.id.btn_save_contact);
        imgBack = findViewById(R.id.btn_back);
        posting = findViewById(R.id.posting);
        headerText = findViewById(R.id.tv_header_text);
        selectedPosition = 0;
        headerText.setText("Add New Contact");
        imgBack.setVisibility(View.VISIBLE);
        allRoles = new ArrayList<>();
        allRoles = contactViewModel.getAllRolesDromDB();
        ArrayList<RoleEntity> tempList = new ArrayList<>();
        for(RoleEntity roleEntity : allRoles){
            if(roleEntity.toString().toLowerCase().contains("admin"))
                continue;
            if(roleEntity.toString().toLowerCase().contains("super"))
                continue;
            if(roleEntity.toString().toLowerCase().contains("checkpoint"))
                continue;

             if(roleEntity.toString().toLowerCase().contains("coordinator"))
                continue;
            tempList.add(roleEntity);
        }
        allRoles.clear();
        allRoles.addAll(tempList);
        imgBack.setOnClickListener(v->NewContactActivity.this.finish());
        if(allRoles != null) {
            ArrayAdapter<RoleEntity> dataAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, allRoles);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            roleSpinner.setAdapter(dataAdapter);
            roleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedPosition = position;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        posting.setText("Posting: "+AppConstants.CP_NAME);
        saveContact.setOnClickListener(v->{

            if(etFirstName.getText().toString().trim().length() == 0)
            {
                ApplicationUtils.showToast(NewContactActivity.this, getResources().getString(R.string.error_f_name));
                etFirstName.requestFocus();
                return;
            }

            if(etLastName.getText().toString().trim().length() == 0){
                ApplicationUtils.showToast(NewContactActivity.this, getResources().getString(R.string.error_l_name));
                etLastName.requestFocus();
                return;
            }

            if(etContactnumber.getText().toString().trim().length() == 0) {
                ApplicationUtils.showToast(NewContactActivity.this, getResources().getString(R.string.error_contact_number));
                etContactnumber.requestFocus();
                return;
            }

            if(etContactnumber.getText().toString().trim().length() < 10){
                ApplicationUtils.showToast(NewContactActivity.this, getResources().getString(R.string.error_invalid_number));
                etContactnumber.requestFocus();
                return;
            }

            if(selectedPosition == 0){
                ApplicationUtils.showToast(NewContactActivity.this, getResources().getString(R.string.error_role));
                roleSpinner.requestFocus();
                return;
            }

            ContactModel contactModel = new ContactModel();
            contactModel.setName(etFirstName.getText().toString()+" "+etLastName.getText().toString());
            contactModel.setNumber(etContactnumber.getText().toString());
            contactModel.setRole(allRoles.get(selectedPosition).getRole());
            contactModel.setPosting(Integer.toString(AppConstants.CHECKPOINT_ID));

            progressDialog = ApplicationUtils.getProgressDialog(new WeakReference<>(NewContactActivity.this), "Please wait...", false);
            progressDialog.show();
            contactViewModel.saveContact(contactModel).observe(NewContactActivity.this, response->{
                if(progressDialog.isShowing())
                    progressDialog.dismiss();
                if(response != null){
                    if(response.getMessage() != null) {
                        AppConstants.ADDED_NEW_CONTACT = true;
                        ApplicationUtils.showToast(NewContactActivity.this, response.getMessage());
                        etFirstName.setText("");
                        etLastName.setText("");
                        etContactnumber.setText("");
                        roleSpinner.setSelection(0);
                    }
                    else
                        ApplicationUtils.showToast(NewContactActivity.this, "Something snapped! Please try again");
                }
            });
        });
    }
}
