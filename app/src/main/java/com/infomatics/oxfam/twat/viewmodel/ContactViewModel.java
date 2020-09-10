package com.infomatics.oxfam.twat.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.infomatics.oxfam.twat.model.BaseResponse;
import com.infomatics.oxfam.twat.model.RoleResponse;
import com.infomatics.oxfam.twat.model.contact.ContactModel;
import com.infomatics.oxfam.twat.model.contact.SearchDirectoryResponse;
import com.infomatics.oxfam.twat.model.room.dao.ContactDao;
import com.infomatics.oxfam.twat.model.room.dao.RoleDao;
import com.infomatics.oxfam.twat.model.room.entity.ContactEntity;
import com.infomatics.oxfam.twat.model.room.entity.RoleEntity;
import com.infomatics.oxfam.twat.repository.ApiRepository;
import com.infomatics.oxfam.twat.repository.AppDatabase;

import java.util.ArrayList;
import java.util.List;

public class ContactViewModel extends AndroidViewModel {

    private MutableLiveData<BaseResponse> saveContactResponse;
    private MutableLiveData<SearchDirectoryResponse> contactResponse;
    private MutableLiveData<RoleResponse> rolesResponse;
    private MutableLiveData<List<RoleEntity>> allRoles;
    private MutableLiveData<List<ContactEntity>> allContacts;
    private ContactDao contactDao;
    private RoleDao roleDao;
    private AppDatabase appDatabase;
    private ApiRepository apiRepository;
    public ContactViewModel(@NonNull Application application) {
        super(application);
        if(saveContactResponse != null ||
                contactResponse != null ||
                rolesResponse !=null)
            return;
        apiRepository = ApiRepository.getInstance();
        appDatabase = AppDatabase.getAppDatabase(application);
        contactDao = appDatabase.contactDao();
        roleDao = appDatabase.roleDao();
    }

    public LiveData<SearchDirectoryResponse> getAllContacts(){
        contactResponse = apiRepository.getAllContacts();
        return contactResponse;
    }

    public LiveData<BaseResponse> saveContact(ContactModel contactModel){
        saveContactResponse = apiRepository.saveContact(contactModel);
        return saveContactResponse;
    }

    public LiveData<RoleResponse> getAllRoles(){
        rolesResponse = apiRepository.getAllRoles();
        return rolesResponse;
    }

    public LiveData<List<ContactEntity>> getAllContactsFromDB(){
        return contactDao.getAllContacts();
    }

    public List<RoleEntity> getAllRolesDromDB(){
        return roleDao.getAllRoles();
    }

    public void addRoles(ArrayList<RoleEntity> roles){
        roleDao.insertAll(roles.toArray(new RoleEntity[roles.size()]));
    }

    public void addContacts(ArrayList<ContactEntity> contacts){
        contactDao.insertAll(contacts.toArray(new ContactEntity[contacts.size()]));
    }
}
