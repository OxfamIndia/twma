package com.infomatics.oxfam.twat.view;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.Observable;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.infomatics.oxfam.twat.R;
import com.infomatics.oxfam.twat.adapter.EntryAdapter;
import com.infomatics.oxfam.twat.interfaces.DashboardTaskComplete;
import com.infomatics.oxfam.twat.model.checkincheckout.ChangeCPStatusRequest;
import com.infomatics.oxfam.twat.model.checkincheckout.CheckInCheckOutBean;
import com.infomatics.oxfam.twat.model.room.entity.CheckpointEntity;
import com.infomatics.oxfam.twat.model.room.entity.DashboardEntity;
import com.infomatics.oxfam.twat.model.room.entity.EntryLog;
import com.infomatics.oxfam.twat.model.room.entity.MemberEntity;
import com.infomatics.oxfam.twat.model.room.entity.RetirementEntity;
import com.infomatics.oxfam.twat.model.room.entity.TeamEntity;
import com.infomatics.oxfam.twat.model.team.Datalist;
import com.infomatics.oxfam.twat.repository.DashboardBackgroundTask;
import com.infomatics.oxfam.twat.repository.backgroundtask.InsertCPDataInBackground;
import com.infomatics.oxfam.twat.util.AppConstants;
import com.infomatics.oxfam.twat.util.ApplicationUtils;
import com.infomatics.oxfam.twat.util.nfc.NdefMessageParser;
import com.infomatics.oxfam.twat.util.nfc.ParsedNdefRecord;
import com.infomatics.oxfam.twat.view.notification.NotificationActivity;
import com.infomatics.oxfam.twat.viewmodel.CheckInCheckOutViewModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class CheckInCheckOutActivity extends BaseActivity {

    private FloatingActionButton fabClose;
    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private RecyclerView entriesRecycler;
    private LinearLayoutManager linearLayoutManager;
    private EntryAdapter entryAdapter;
    private CheckInCheckOutViewModel viewModel;
    private List<EntryLog> entries = new ArrayList<>();
    private List<EntryLog> filteredEntries = new ArrayList<>();
    private EditText etSearch;
    private ImageView imgSearch;
    private TextView totalCheckIns, totalCheckOuts, totalRetirements, totalTeams, totalTeamCheckIns, totalTeamCheckOuts;
    private ImageView btnBack, btnNotification;
    private TextView tvHeader;
    private int syncCounter = 0;
    private ProgressDialog progressDialog;
    private boolean isCpClosed;
    private TextView offlineOnlineView;
    int teamCheckInCount, teamCheckOutCount,  totalTeamRetirements;
    private DashboardEntity dashboardEntity;
    private boolean isNew;
    private List<Integer> teamBibsCheckIns = new ArrayList<>();
    private List<Integer> teamBibsCheckOuts = new ArrayList<>();
    private List<Integer> teamBibsRetirements = new ArrayList<>();
    private LinearLayout unSyncLayout;
    private TextView tvUnSyncCount;
    private TextView tvTotalWalkerCI, tvTotalWalkerCO, tvTotalWalkerR, tvTotalTeamsCI, tvTotalTeamsCO, tvTotalTeamsR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in_check_out);
        try {
            isCpClosed = AppConstants.CP_STATUS.equalsIgnoreCase("closed");
            viewModel = ViewModelProviders.of(CheckInCheckOutActivity.this).get(CheckInCheckOutViewModel.class);
            progressDialog = ApplicationUtils.getProgressDialog(new WeakReference<>(CheckInCheckOutActivity.this), "Please wait...", false);
            sharedPreferences = getSharedPreferences(AppConstants.APP_PREF, MODE_PRIVATE);
            componentBinding();

            if (ApplicationUtils.isNetworkAvailable(CheckInCheckOutActivity.this)) {
//            progressDialog.show();
                if (AppConstants.hasFetchedAllData.get()) {
                    getAndUpdatedata();
                }
                AppConstants.hasFetchedAllData.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
                    @Override
                    public void onPropertyChanged(Observable sender, int propertyId) {
                        if (AppConstants.hasFetchedAllData.get()) {
                            getAndUpdatedata();
                        }
                    }
                });

            } else {
                getAndUpdatedata();
                ApplicationUtils.showToast(CheckInCheckOutActivity.this, "You will be working in offline mode.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getAndUpdatedata(){
        runOnUiThread(() -> {
            viewModel.getAllTeamNames().observe(CheckInCheckOutActivity.this, list->{
                if(progressDialog.isShowing())
                    progressDialog.dismiss();
                Log.e(AppConstants.TAG, "All Team Data fetched");
            });
            tvTotalTeamsR.setText(""+AppConstants.TEAM_COUNT);
            tvTotalTeamsCO.setText(""+AppConstants.TEAM_COUNT);
            tvTotalTeamsCI.setText(""+AppConstants.TEAM_COUNT);
            tvTotalWalkerR.setText(""+AppConstants.WALKER_COUNT);
            tvTotalWalkerCO.setText(""+AppConstants.WALKER_COUNT);
            tvTotalWalkerCI.setText(""+AppConstants.WALKER_COUNT);
        });

    }

    private DashboardBackgroundTask dashboardBackgroundTask;

    private void componentBinding(){
        tvHeader = findViewById(R.id.tv_header_text);
        btnBack = findViewById(R.id.btn_back);
        btnNotification = findViewById(R.id.action_two);
        entriesRecycler = findViewById(R.id.recyclerView);
        totalCheckIns = findViewById(R.id.total_check_ins);
        totalCheckOuts = findViewById(R.id.total_check_outs);
        totalRetirements = findViewById(R.id.total_retirements);
        totalTeams = findViewById(R.id.totalTeams);
        totalTeamCheckIns = findViewById(R.id.total_checkin_teams);
        totalTeamCheckOuts = findViewById(R.id.total_team_check_outs);
        tvUnSyncCount  =findViewById(R.id.unsyncCount);
        unSyncLayout = findViewById(R.id.unsyncView);
        etSearch = findViewById(R.id.et_search);
        imgSearch = findViewById(R.id.img_search);
        fabClose = findViewById(R.id.fab_close);
        tvTotalWalkerCI = findViewById(R.id.total_walkers);
        tvTotalWalkerCO = findViewById(R.id.total_walkers_co);
        tvTotalWalkerR = findViewById(R.id.total_walkers_r);
        tvTotalTeamsCI = findViewById(R.id.total_teams_ci);
        tvTotalTeamsCO = findViewById(R.id.total_teams_co);
        tvTotalTeamsR = findViewById(R.id.total_teams_r);
        offlineOnlineView = findViewById(R.id.online_offline);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        entriesRecycler.setLayoutManager(linearLayoutManager);
        entries = new ArrayList<>();
        entryAdapter = new EntryAdapter(entries);
        entriesRecycler.setAdapter(entryAdapter);

        if(isCpClosed)
            tvHeader.setText("Checkpoint "+AppConstants.CHECKPOINT_ID+" (Closed)");
        else
            tvHeader.setText("Checkpoint "+AppConstants.CHECKPOINT_ID);
        btnBack.setVisibility(View.VISIBLE);
        btnNotification.setVisibility(View.VISIBLE);
        btnBack.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_white));
        btnNotification.setImageDrawable(getResources().getDrawable(R.drawable.ic_notifications_white));

        btnBack.setOnClickListener(v->{
            startActivity(new Intent(CheckInCheckOutActivity.this, DashboardActivity.class));
        });
        btnNotification.setOnClickListener(v->{
            startActivity(new Intent(CheckInCheckOutActivity.this, NotificationActivity.class));
        });

        pendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, this.getClass())
                        .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        dashboardEntity = viewModel.getDashboardData();
        if(dashboardEntity == null) {
            dashboardEntity = new DashboardEntity();
            isNew = true;
        }
        else {
            isNew = false;
            teamCheckInCount  = dashboardEntity.getTotalTeamCheckIns();
            teamCheckOutCount = dashboardEntity.getTotalTeamCheckOuts();
            totalTeamCheckOuts.setText(""+dashboardEntity.getTotalTeamCheckOuts());
            totalTeamCheckIns.setText(""+dashboardEntity.getTotalTeamCheckIns());
            teamBibsCheckIns = dashboardEntity.getTeamBibCheckIns();
            teamBibsCheckOuts = dashboardEntity.getTeamBibCheckOuts();
            teamBibsRetirements = dashboardEntity.getTeamBibRetirements();
            if(teamBibsCheckIns == null){
                teamBibsCheckIns = new ArrayList<>();
            }
            if(teamBibsCheckOuts == null)
                teamBibsCheckOuts = new ArrayList<>();
            if(teamBibsRetirements == null)
                teamBibsRetirements = new ArrayList<>();
        }

        viewModel.getAllEntries().observe(CheckInCheckOutActivity.this, entryList->{
            if(entryList != null){
                entryAdapter.setData(entryList);
                totalRetirements.setText(Integer.toString(viewModel.getTotalRetirements()));
                totalCheckOuts.setText(Integer.toString(viewModel.getTotalCheckOuts()));
                totalCheckIns.setText(Integer.toString(viewModel.getTotalCheckIns()));
                unSyncCount = viewModel.getUnSyncedEntries().size();
                tvUnSyncCount.setText("UnSync Count: "+unSyncCount);
                entries.clear();
                entries.addAll(entryList);
                dashboardBackgroundTask = new DashboardBackgroundTask(viewModel, entries, dashboardEntity -> {
                    if(dashboardEntity != null) {
                        if (dashboardEntity.getTeamBibCheckOuts() == null) {
                            dashboardEntity.setTeamBibCheckOuts(new ArrayList<>());
                        }
                        if (dashboardEntity.getTeamBibCheckIns() == null)
                            dashboardEntity.setTeamBibCheckIns(new ArrayList<>());
                        this.teamBibsCheckOuts = dashboardEntity.getTeamBibCheckOuts();
                        this.teamBibsCheckIns = dashboardEntity.getTeamBibCheckIns();
                        this.teamBibsRetirements = dashboardEntity.getTeamBibRetirements();
                        this.teamCheckOutCount = dashboardEntity.getTotalTeamCheckOuts();
                        this.teamCheckInCount = dashboardEntity.getTotalTeamCheckIns();
                        this.totalTeamRetirements = dashboardEntity.getTotalTeamRetirements();
                        this.totalTeamCheckIns.setText("" + this.teamCheckInCount);
                        this.totalTeamCheckOuts.setText("" + this.teamCheckOutCount);
                        this.totalTeams.setText("" + this.totalTeamRetirements);
                    }
                });
                dashboardBackgroundTask.execute();
            }
        });

        etSearch.setText("");
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(etSearch.getText().toString().trim().length() == 0){
                    imgSearch.setImageDrawable(getResources().getDrawable(R.drawable.ic_search_black_24dp));
                    entryAdapter.setData(entries);
                }else{
                    imgSearch.setImageDrawable(getResources().getDrawable(R.drawable.ic_close));
                    searchEntries();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        imgSearch.setOnClickListener(v->{
            if(etSearch.getText().toString().trim().length() > 0){
                etSearch.setText("");
            }
        });
        unSyncCount = viewModel.getUnSyncedEntries().size();
        tvUnSyncCount.setText("UnSync Count: "+unSyncCount);

        unSyncLayout.setOnClickListener(v->{
            if(unSyncCount > 0) {
                if (!progressDialog.isShowing())
                    progressDialog.show();
                syncData();
            }else{
                ApplicationUtils.showToast(CheckInCheckOutActivity.this,"All data already synced.");
            }
        });
        fabClose.setOnClickListener(v->{
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    CheckInCheckOutActivity.this);

            // set title
            alertDialogBuilder.setTitle("Close CP?");

            // set dialog message
            alertDialogBuilder
                    .setMessage("Click yes to close this CheckPoint. You will not be able to receive any further checkins and checkouts! \n Are you sure?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialog, id) -> {
                        dialog.cancel();
                        closeCP();
                    })
                    .setNegativeButton("No", (dialog, id) -> {
                        dialog.cancel();
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        });

        if(AppConstants.CP_STATUS.equalsIgnoreCase("closed"))
            fabClose.setVisibility(View.GONE);
    }

    private void closeCP(){
        try {
            if (!progressDialog.isShowing()) {
                progressDialog.show();
            }

            ChangeCPStatusRequest changeCPStatusRequest = new ChangeCPStatusRequest();
            changeCPStatusRequest.setId(Integer.toString(AppConstants.CHECKPOINT_ID));
            changeCPStatusRequest.setStatus("CLOSED");
            viewModel.changeCpStatus(changeCPStatusRequest).observe(CheckInCheckOutActivity.this, response -> {
                if (progressDialog.isShowing()) {
                    progressDialog.cancel();
                }
                CheckpointEntity checkpointEntity = viewModel.getCPData(AppConstants.CHECKPOINT_ID);
                if (checkpointEntity != null) {
                    checkpointEntity.setCpStatus("CLOSED");
                    viewModel.updateCPData(checkpointEntity);
                }
                fabClose.setVisibility(View.GONE);
                nfcAdapter = null;
                ApplicationUtils.showToast(CheckInCheckOutActivity.this, "This CheckPoint has been closed.");
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void searchEntries(){
        try {
            filteredEntries.clear();
            for (EntryLog entryLog : entries) {
                if (entryLog.getBibNo().toLowerCase().contains(etSearch.getText().toString().toLowerCase())) {
                    filteredEntries.add(entryLog);
                }
            }
            if (filteredEntries.size() > 0) {
                entryAdapter.setData(filteredEntries);
            } else {
                ApplicationUtils.showToast(CheckInCheckOutActivity.this, "No Such BIBNo Exists.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String dumpTagData(Tag tag) {
        StringBuilder sb = new StringBuilder();
        byte[] id = tag.getId();
        sb.append(toDec(id));
        /*sb.append("ID (hex): ").append(toHex(id)).append('\n');
        sb.append("ID (reversed hex): ").append(toReversedHex(id)).append('\n');
        sb.append("ID (dec): ").append(toDec(id)).append('\n');
        sb.append("ID (reversed dec): ").append(toReversedDec(id)).append('\n');

        String prefix = "android.nfc.tech.";
        sb.append("Technologies: ");
        for (String tech : tag.getTechList()) {
            sb.append(tech.substring(prefix.length()));
            sb.append(", ");
        }

        sb.delete(sb.length() - 2, sb.length());

        for (String tech : tag.getTechList()) {
            if (tech.equals(MifareClassic.class.getName())) {
                sb.append('\n');
                String type = "Unknown";

                try {
                    MifareClassic mifareTag = MifareClassic.get(tag);

                    switch (mifareTag.getType()) {
                        case MifareClassic.TYPE_CLASSIC:
                            type = "Classic";
                            break;
                        case MifareClassic.TYPE_PLUS:
                            type = "Plus";
                            break;
                        case MifareClassic.TYPE_PRO:
                            type = "Pro";
                            break;
                    }
                    sb.append("Mifare Classic type: ");
                    sb.append(type);
                    sb.append('\n');

                    sb.append("Mifare size: ");
                    sb.append(mifareTag.getSize() + " bytes");
                    sb.append('\n');

                    sb.append("Mifare sectors: ");
                    sb.append(mifareTag.getSectorCount());
                    sb.append('\n');

                    sb.append("Mifare blocks: ");
                    sb.append(mifareTag.getBlockCount());
                } catch (Exception e) {
                    sb.append("Mifare classic error: " + e.getMessage());
                }
            }

            if (tech.equals(MifareUltralight.class.getName())) {
                sb.append('\n');
                MifareUltralight mifareUlTag = MifareUltralight.get(tag);
                String type = "Unknown";
                switch (mifareUlTag.getType()) {
                    case MifareUltralight.TYPE_ULTRALIGHT:
                        type = "Ultralight";
                        break;
                    case MifareUltralight.TYPE_ULTRALIGHT_C:
                        type = "Ultralight C";
                        break;
                }
                sb.append("Mifare Ultralight type: ");
                sb.append(type);
            }
        }
*/
        return sb.toString();
    }

    private String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = bytes.length - 1; i >= 0; --i) {
            int b = bytes[i] & 0xff;
            if (b < 0x10)
                sb.append('0');
            sb.append(Integer.toHexString(b));
            if (i > 0) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    private String toReversedHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; ++i) {
            if (i > 0) {
                sb.append(" ");
            }
            int b = bytes[i] & 0xff;
            if (b < 0x10)
                sb.append('0');
            sb.append(Integer.toHexString(b));
        }
        return sb.toString();
    }

    private long toDec(byte[] bytes) {
        long result = 0;
        long factor = 1;
        for (int i = 0; i < bytes.length; ++i) {
            long value = bytes[i] & 0xffl;
            result += value * factor;
            factor *= 256l;
        }
        return result;
    }

    private long toReversedDec(byte[] bytes) {
        long result = 0;
        long factor = 1;
        for (int i = bytes.length - 1; i >= 0; --i) {
            long value = bytes[i] & 0xffl;
            result += value * factor;
            factor *= 256l;
        }
        return result;
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            nfcAdapter = NfcAdapter.getDefaultAdapter(this);
            CheckpointEntity checkpointEntity = viewModel.getCPData(AppConstants.CHECKPOINT_ID);
            if (checkpointEntity != null) {
                if (checkpointEntity.getCpStatus().equalsIgnoreCase("CLOSED")) {
                    nfcAdapter = null;
                    ApplicationUtils.showToast(this, "CP Closed");
                    return;
                }
            }
            if (nfcAdapter == null) {
                ApplicationUtils.showToast(this, "No NFC");
//            finish();
                return;
            }
            if (nfcAdapter != null && !isCpClosed) {
                if (!nfcAdapter.isEnabled())
                    showWirelessSettings();

                nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
            }
            if (viewModel != null) {
                if (totalRetirements != null) {
                    int count = viewModel.getTotalRetirements();
                    if (count > 0) {
                        totalRetirements.setText("" + count);
                    }
                }
            }
            isConnectedToInternet.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
                @Override
                public void onPropertyChanged(Observable sender, int propertyId) {
                    runOnUiThread(() -> {
                        if (isConnectedToInternet.get()) {
                            offlineOnlineView.setVisibility(View.GONE);
                        } else {
                            offlineOnlineView.setVisibility(View.VISIBLE);
                        }
                    });
                }
            });
            runOnUiThread(() -> {
                if (isConnectedToInternet.get()) {
                    offlineOnlineView.setVisibility(View.GONE);
                } else {
                    offlineOnlineView.setVisibility(View.VISIBLE);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void showWirelessSettings() {
        ApplicationUtils.showToast(this, "You need to enable NFC");
        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
        startActivity(intent);
    }
    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        resolveIntent(intent);
    }

    private void resolveIntent(Intent intent) {
        String action = intent.getAction();

        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs;

            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];

                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }

            } else {
                byte[] empty = new byte[0];
                byte[] id = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
                Tag tag = (Tag) intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                byte[] payload = dumpTagData(tag).getBytes();
                NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN, empty, id, payload);
                NdefMessage msg = new NdefMessage(new NdefRecord[] {record});
                msgs = new NdefMessage[] {msg};
            }

            displayMsgs(msgs);
        }
    }

    private void displayMsgs(NdefMessage[] msgs) {
        try {
            if (isCpClosed) {
                ApplicationUtils.showToast(CheckInCheckOutActivity.this, AppConstants.CP_NAME + " is closed.");
                return;
            }

            if (msgs == null || msgs.length == 0)
                return;

            StringBuilder builder = new StringBuilder();
            List<ParsedNdefRecord> records = NdefMessageParser.parse(msgs[0]);
            final int size = records.size();

            for (int i = 0; i < size; i++) {
                ParsedNdefRecord record = records.get(i);
                String str = record.str();
                builder.append(str);
            }
            String tag = builder.toString();
            String bibNo = "";
            Log.e(AppConstants.TAG, tag);
            EntryLog entry = viewModel.findByTagId(tag);
            MemberEntity memberEntity = viewModel.getbibNo(tag);
            if (memberEntity != null) {
                bibNo = memberEntity.getBibno();
            }
            if (bibNo == null || bibNo.length() < 2) {
                ApplicationUtils.showToast(CheckInCheckOutActivity.this, "Band is not mapped. Considering TAG ID.");
                bibNo = tag;
            }


           /* if ((AppConstants.CHECKPOINT_ID != 1)) {
                if(AppConstants.CHECKPOINT_ID == 6){

                }else {
                    EntryLog tempLog = viewModel.getEntryWithbib(bibNo, AppConstants.CHECKPOINT_ID - 1);
                    if (tempLog == null) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                CheckInCheckOutActivity.this);

                        // set title
                        alertDialogBuilder.setTitle("Walker has skipped Previous CP");

                        // set dialog message
                        alertDialogBuilder
                                .setMessage("Click yes if you are allowing Walker to continue")
                                .setCancelable(false)
                                .setPositiveButton("Yes", (dialog, id) -> {
                                    dialog.cancel();
                                    checkInCheckOut(entry, memberEntity.getBibno(), tag);

                                })
                                .setNegativeButton("No", (dialog, id) -> {
                                    dialog.cancel();

                                });

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();
                        return;
                    }
                }
            }*/

            checkInCheckOut(entry, bibNo, tag);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void checkInCheckOut(EntryLog entry, String bibNo, String tag){
        long timeInMillis = System.currentTimeMillis();
        int count = 0;
        int countCheckOut = 0;
        String activityTime = ApplicationUtils.getDate(timeInMillis);
        if (entry != null) {
            if(entry.getHasRetired() == 1){
                ApplicationUtils.showToast(CheckInCheckOutActivity.this, "Walker has Retired. Cannot be checked In");
                return;
            }
            entry.setCheckOutTime(activityTime);
            entry.setActivityTime(activityTime);
            entry.setCheckOutMillis(Long.toString(timeInMillis));
            entry.setHasCheckedOut(1);
            entry.setBibNo(bibNo);
            entry.setIsSynced(0);
            entry.setCheckpoint_id(AppConstants.CHECKPOINT_ID);
            viewModel.update(entry);
            countCheckOut = viewModel.findCountOfTeamMembersCheckedOut(entry.getBibNo().substring(0, entry.getBibNo().length()-1)+"%");
            Log.e(AppConstants.TAG, "Updated");
        } else {
            RetirementEntity r = viewModel.getRetirementIfExists(bibNo);
            if(r != null && r.getCpName() != null){
                ApplicationUtils.showToast(CheckInCheckOutActivity.this, "Walker has Retired. Cannot be checked In");
                return;
            }
            entry = new EntryLog();
            entry.setActivityTime(activityTime);
            entry.setCheckInTime(activityTime);
            entry.setCheckInMillis(Long.toString(timeInMillis));
            entry.setCheckOutTime("");
            entry.setHasRetired(0);
            entry.setHasCheckedOut(0);
            entry.setBibNo(bibNo);
            entry.setCheckpoint_id(AppConstants.CHECKPOINT_ID);
            entry.setIsSynced(0);
            entry.setTagId(tag);
            viewModel.insert(entry);
            count = viewModel.findCountOfTeamMembersCheckedIn(entry.getBibNo().substring(0, entry.getBibNo().length()-1)+"%");
            Log.e(AppConstants.TAG, "Inserted");
        }

        checkForDashboardChanges(entry, count, countCheckOut);
        dashboardEntity.setCheckpointId(AppConstants.CHECKPOINT_ID);

        if(isNew) {
            viewModel.insertDashboardData(dashboardEntity);
            isNew = false;
        }
        else
            viewModel.updateDashboardData(dashboardEntity);
        Log.e(AppConstants.TAG,"CheckedIn: "+count);
        Log.e(AppConstants.TAG,"CheckedOut: "+countCheckOut);

        syncCounter++;
        unSyncCount = viewModel.getUnSyncedEntries().size();
        updateUI();
        if(syncCounter>=AppConstants.SYNC_COUNTER){
            syncCounter = 0;
            syncData();
        }
    }

    private void checkForDashboardChanges(EntryLog entry, int count, int countCheckOut) {
        int TEAM_SIZE = viewModel.getTeamSizeWithBibNo(entry.getBibNo().substring(0, entry.getBibNo().length() - 1)+"%");
        if(AppConstants.CHECKPOINT_ID > 1) {
            int retireCount = viewModel.getRetirementCountOfBib(entry.getBibNo().substring(0, entry.getBibNo().length() - 1) + "%");
            TEAM_SIZE -= retireCount;
        }
        if(entry.getHasCheckedOut() == 0){
            if(count < TEAM_SIZE){
                ApplicationUtils.showToast(CheckInCheckOutActivity.this,
                        "Walkers Remaining : "+(TEAM_SIZE - count));
            }
        }
        if (count == TEAM_SIZE) {
            if (isNew) {
                teamCheckInCount++;
                totalTeamCheckIns.setText("" + teamCheckInCount);
                dashboardEntity.setTotalTeamCheckIns(teamCheckInCount);
            }
            int teamBib = Integer.parseInt(entry.getBibNo().substring(0, entry.getBibNo().length() - 1));
            if (!teamBibsCheckIns.contains(teamBib)) {
                teamCheckInCount++;
                teamBibsCheckIns.add(teamBib);
                totalTeamCheckIns.setText("" + teamCheckInCount);
                dashboardEntity.setTeamBibCheckIns(teamBibsCheckIns);
                dashboardEntity.setTotalTeamCheckIns(teamCheckInCount);
            }
        }
        if (countCheckOut == TEAM_SIZE) {
            if (isNew) {
                teamCheckOutCount++;
                totalTeamCheckOuts.setText("" + teamCheckOutCount);
                dashboardEntity.setTotalTeamCheckOuts(teamCheckOutCount);
            } else {
                int teamBib = Integer.parseInt(entry.getBibNo().substring(0, entry.getBibNo().length() - 1));
                if (!teamBibsCheckOuts.contains(teamBib)) {
                    teamCheckOutCount++;
                    teamBibsCheckOuts.add(teamBib);
                    totalTeamCheckOuts.setText("" + teamCheckOutCount);
                    dashboardEntity.setTotalTeamCheckOuts(teamCheckOutCount);
                    dashboardEntity.setTeamBibCheckOuts(teamBibsCheckOuts);
                }
            }
        }
    }

    int unSyncCount = 0;
    private void updateUI(){
        tvUnSyncCount.setText("UnSync Count: "+unSyncCount);
    }
    private SharedPreferences sharedPreferences;
    private void syncData(){
        if(!dashboardBackgroundTask.isCancelled())
            dashboardBackgroundTask.cancel(true);
        /*if(sharedPreferences.contains(AppConstants.NETWORK_KEY))
            if(!sharedPreferences.getBoolean(AppConstants.NETWORK_KEY, false))
                return;*/
        if(ApplicationUtils.isNetworkAvailable(CheckInCheckOutActivity.this)) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    List<EntryLog> entries = viewModel.getUnSyncedEntries();
                    if (entries != null && entries.size() > 0) {
                        ArrayList<CheckInCheckOutBean> checkInCheckOutBeans = new ArrayList<>();
                        for (EntryLog entryLog : entries) {
                            CheckInCheckOutBean checkInCheckOutBean = new CheckInCheckOutBean();
                            checkInCheckOutBean.setCheckin_time(entryLog.getCheckInMillis());
                            checkInCheckOutBean.setCheckout_time(entryLog.getCheckOutMillis());
                            checkInCheckOutBean.setBibno(entryLog.getBibNo());
                            checkInCheckOutBean.setCheckpoint_id(AppConstants.CHECKPOINT_ID);
                            checkInCheckOutBean.setIs_retire(entryLog.getHasRetired());
                            checkInCheckOutBeans.add(checkInCheckOutBean);
                        }
                        viewModel.updateAllUnsyncedEntries(checkInCheckOutBeans).observe(CheckInCheckOutActivity.this, response -> {
                            if (response != null) {
                                if (response.getDatalist() != null) {
                                    for (CheckInCheckOutBean checkInCheckOutBean : response.getDatalist()) {
                                        EntryLog e = viewModel.findByBibNo(checkInCheckOutBean.getBibno());
                                        if (e != null) {
                                            e.setIsSynced(1);
                                            viewModel.update(e);
                                            Log.e(AppConstants.TAG, "UPDATED SYNC STATE");
                                        }
                                    }
                                }
                            } else {
                                Log.e(AppConstants.TAG, "FAILED TO SYNC ENTRIES");
                            }
                        });
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                    unSyncCount = viewModel.getUnSyncedEntries().size();
                    tvUnSyncCount.setText("UnSync Count: "+unSyncCount);

                }
            }.execute();
        }
    }

    @Override
    public void onBackPressed() {

    }
}
