package com.infomatics.oxfam.twat.view.tagscan;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.Observable;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.infomatics.oxfam.twat.R;
import com.infomatics.oxfam.twat.model.checkincheckout.CheckInCheckOutBean;
import com.infomatics.oxfam.twat.model.room.entity.EntryLog;
import com.infomatics.oxfam.twat.model.room.entity.MemberEntity;
import com.infomatics.oxfam.twat.model.room.entity.TeamEntity;
import com.infomatics.oxfam.twat.model.team.Walker;
import com.infomatics.oxfam.twat.util.AppConstants;
import com.infomatics.oxfam.twat.util.ApplicationUtils;
import com.infomatics.oxfam.twat.util.nfc.NdefMessageParser;
import com.infomatics.oxfam.twat.util.nfc.ParsedNdefRecord;
import com.infomatics.oxfam.twat.view.BaseActivity;
import com.infomatics.oxfam.twat.view.register.RegisterTeam;
import com.infomatics.oxfam.twat.viewmodel.CheckInCheckOutViewModel;
import com.skyfishjy.library.RippleBackground;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class TagScanActivity extends BaseActivity {

    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private ImageView btnBack;
    private TextView headerText;
    private RippleBackground rippleBackground;
    private CheckInCheckOutViewModel viewModel;
    private boolean tagDetailScreen;
    private RelativeLayout retirementScreen;
    private RippleBackground animationView;
    private TextView tagDetailsMessage;
    private RelativeLayout tagdetailLayout;
    private TextView tagDetails;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_scan);

        viewModel = ViewModelProviders.of(TagScanActivity.this).get(CheckInCheckOutViewModel.class);
        progressDialog = ApplicationUtils.getProgressDialog(new WeakReference<>(TagScanActivity.this), "Please wait...", false);
        componentBinding();
    }

    private void componentBinding(){
        rippleBackground=findViewById(R.id.content);
        btnBack = findViewById(R.id.btn_back);
        headerText = findViewById(R.id.tv_header_text);
        retirementScreen = findViewById(R.id.retirement_screen);
        animationView = findViewById(R.id.animation_view);
        tagDetailsMessage = findViewById(R.id.tag_details_message);
        tagdetailLayout = findViewById(R.id.tag_detail_screen);
        tagDetails = findViewById(R.id.tagDetails);
        new Handler().postDelayed(() -> {
            rippleBackground.startRippleAnimation();
            animationView.startRippleAnimation();
        },200);
        TextView textView = findViewById(R.id.tag);
        String screenName = getIntent().getExtras().getString(AppConstants.SCREEN_NAME);
        textView.setText(String.format(getResources().getString(R.string.tap_message), screenName));
        headerText.setText(screenName);
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(v->{
            rippleBackground.stopRippleAnimation();
            animationView.stopRippleAnimation();
            TagScanActivity.this.finish();
        });
        tagDetailScreen = getIntent().getBooleanExtra(AppConstants.TAG_DETAILS, false);

        if(tagDetailScreen){
            retirementScreen.setVisibility(View.GONE);
            tagdetailLayout.setVisibility(View.VISIBLE);
            tagDetailsMessage.setText(String.format(getResources().getString(R.string.tap_message), "details"));
        }

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (nfcAdapter == null) {
            ApplicationUtils.showToast(this, "No NFC");
//            finish();
            return;
        }


        pendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, this.getClass())
                        .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
    }

    @Override
    public void onBackPressed() {
        rippleBackground.stopRippleAnimation();
        super.onBackPressed();
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

    @Override
    protected void onResume() {
        super.onResume();

        if (nfcAdapter != null) {
            if (!nfcAdapter.isEnabled())
                showWirelessSettings();

            nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
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

    private void displayMsgs(NdefMessage[] msgs)
    {
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
        if(memberEntity!= null){
            bibNo = memberEntity.getBibno();
        }

        if(tagDetailScreen){
            if(bibNo!= null && bibNo.length() > 0){
            animationView.setVisibility(View.GONE);
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("\nBIB No: "+ bibNo);
            if(memberEntity.getLeaderId().length() > 0) {
                try {
                    TeamEntity teamEntity = viewModel.getTeamWithLeaderId(Integer.parseInt(memberEntity.getLeaderId()));
                    for (Walker walker : teamEntity.getWalker()) {
                        if (walker.getBibNo().equalsIgnoreCase(bibNo)) {
                            stringBuffer.append("\nTeam Name: " + teamEntity.getTeamTitle());
                            stringBuffer.append("\nWalker Name: " + walker.getFirstName() + " " + walker.getLastName());
                            stringBuffer.append("\nWalker Mobile: " + walker.getMobile());
                            stringBuffer.append("\nWalker Desc: " + walker.getDescription());
                            break;
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            tagDetails.setText(stringBuffer.toString());
            }else{
                ApplicationUtils.showToast(TagScanActivity.this, "Rfid is not mapped with bib no, please map rfid first.");
            }
        }else {
            long timeInMillis = System.currentTimeMillis();
            String activityTime = ApplicationUtils.getDate(timeInMillis);
            boolean isNew= false;
            if (bibNo != null) {
                if(entry == null){
                    entry = new EntryLog();
                    isNew = true;
                }
                entry.setCheckOutTime(activityTime);
                entry.setActivityTime(activityTime);
                entry.setCheckInMillis(Long.toString(timeInMillis));
                entry.setHasCheckedOut(0);
                entry.setHasRetired(1);
                entry.setBibNo(bibNo);
                entry.setIsSynced(0);
                entry.setCheckpoint_id(AppConstants.CHECKPOINT_ID);
                if(isNew)
                    viewModel.insert(entry);
                else
                    viewModel.update(entry);
                Log.e(AppConstants.TAG, "Updated");

                if (ApplicationUtils.isNetworkAvailable(TagScanActivity.this)) {
                    ArrayList<CheckInCheckOutBean> checkInCheckOutBeans = new ArrayList<>();
                    CheckInCheckOutBean checkInCheckOutBean = new CheckInCheckOutBean();
                    checkInCheckOutBean.setCheckin_time(entry.getCheckInMillis());
                    checkInCheckOutBean.setCheckout_time(entry.getCheckOutMillis());
                    checkInCheckOutBean.setBibno(entry.getBibNo());
                    checkInCheckOutBean.setCheckpoint_id(AppConstants.CHECKPOINT_ID);
                    checkInCheckOutBean.setIs_retire(entry.getHasRetired());
                    checkInCheckOutBeans.add(checkInCheckOutBean);

                    viewModel.updateAllUnsyncedEntries(checkInCheckOutBeans).observe(TagScanActivity.this, response -> {
                        if (response != null) {
                            if (response.getDatalist() != null) {
                                for (CheckInCheckOutBean checkInCheckOutBean2 : response.getDatalist()) {
                                    ApplicationUtils.showToast(TagScanActivity.this, "Bib No: " + checkInCheckOutBean2.getBibno() + " Retired");
                                    EntryLog e = viewModel.findByBibNo(checkInCheckOutBean2.getBibno());
                                    if (e != null) {
                                        e.setIsSynced(1);
                                        viewModel.update(e);
                                    }
                                }
                            }else{
                                ApplicationUtils.showToast(TagScanActivity.this, "Something went wrong, try again.");
                            }
                        } else {
                            Log.e(AppConstants.TAG, "FAILED TO SYNC ENTRIES");
                        }
                    });


                }
            }
            else{
                ApplicationUtils.showToast(TagScanActivity.this, "Entry of this Rfid is not present, Please check him in then retire.");
            }
        }
    }
}
