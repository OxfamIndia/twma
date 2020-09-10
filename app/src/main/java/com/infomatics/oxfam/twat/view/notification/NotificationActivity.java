package com.infomatics.oxfam.twat.view.notification;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.infomatics.oxfam.twat.R;
import com.infomatics.oxfam.twat.adapter.NotificationAdapter;
import com.infomatics.oxfam.twat.model.room.entity.NotificationEntity;
import com.infomatics.oxfam.twat.view.BaseActivity;
import com.infomatics.oxfam.twat.viewmodel.NotificationViewModel;

import java.util.ArrayList;

public class NotificationActivity extends BaseActivity {

    private ImageView btnBack;
    private TextView tvHeaderText;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private NotificationAdapter notificationAdapter;
    private ArrayList<NotificationEntity> allNotifications;
    private NotificationViewModel notificationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        notificationViewModel = ViewModelProviders.of(NotificationActivity.this).get(NotificationViewModel.class);
        componentBinding();
    }

    private void componentBinding(){
        btnBack = findViewById(R.id.btn_back);
        tvHeaderText = findViewById(R.id.tv_header_text);
        recyclerView = findViewById(R.id.notification_recycler);

        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(v->{
            NotificationActivity.this.finish();
        });

        tvHeaderText.setText(getResources().getString(R.string.notification_activity));
        allNotifications = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        notificationViewModel.getAllNotifications().observe(NotificationActivity.this, res->{
            if(res != null && res.size() > 0){
                allNotifications.clear();
                allNotifications.addAll(new ArrayList<>(res));
                notificationAdapter = new NotificationAdapter(allNotifications);
                recyclerView.setAdapter(notificationAdapter);
            }
        });




    }
}
