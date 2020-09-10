package com.infomatics.oxfam.twat.view.register;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.Observable;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.infomatics.oxfam.twat.R;
import com.infomatics.oxfam.twat.model.register.RegisterModel;
import com.infomatics.oxfam.twat.model.room.entity.TeamEntity;
import com.infomatics.oxfam.twat.util.AppConstants;
import com.infomatics.oxfam.twat.util.ApplicationUtils;
import com.infomatics.oxfam.twat.util.nfc.NdefMessageParser;
import com.infomatics.oxfam.twat.util.nfc.ParsedNdefRecord;
import com.infomatics.oxfam.twat.view.BaseActivity;
import com.infomatics.oxfam.twat.viewmodel.RegistrationViewModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class RegisterBands extends BaseActivity {

    private TextView tvHeader;
    private ImageView btnBack;
    private ImageView btnSearch;
    private AutoCompleteTextView etBibNo;
    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private RegistrationViewModel registrationViewModel;
    private TextView registerTeam;
    private ArrayList<String> searchData;
    private TextView tvMapRfid;
    private String selectedTeam;
    private ProgressDialog progressDialog;
    private RelativeLayout loginLayout;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_bands);
        registrationViewModel = ViewModelProviders.of(RegisterBands.this).get(RegistrationViewModel.class);
        searchData = AppConstants.allBibNos;
        componentBinding();
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

    private void componentBinding(){
        etBibNo = findViewById(R.id.bib_no);
        btnSearch = findViewById(R.id.img_search);
        btnBack = findViewById(R.id.btn_back);
        tvHeader = findViewById(R.id.tv_header_text);
        registerTeam = findViewById(R.id.btn_register);
        tvMapRfid = findViewById(R.id.tag_message);
        loginLayout = findViewById(R.id.loginLayout);
        spinner = findViewById(R.id.spinner_team);
        loginLayout.setVisibility(View.GONE);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.team_names)); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);

        tvHeader.setText("Map RFid and BibNo");
        btnBack.setOnClickListener(v->{
            RegisterBands.this.finish();
        });
        btnBack.setVisibility(View.VISIBLE);
        spinner.setSelection(0);
        tvMapRfid.setText("Tap band on back to map after entering bib no.");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, searchData);
        etBibNo.setThreshold(1);
        etBibNo.setAdapter(adapter);
        etBibNo.setOnItemClickListener((parent, view, position, id) -> {
            selectedTeam = (String) parent.getAdapter().getItem(position);
            if(selectedTeam != null) {
                tvMapRfid.setVisibility(View.VISIBLE);
                tvMapRfid.setText(String.format(getResources().getString(R.string.map_bib_no), selectedTeam));
            }
        });

        btnSearch.setOnClickListener(v->{
            if(etBibNo.getText().toString().length() > 0){
                etBibNo.setText("");
                tvMapRfid.setVisibility(View.GONE);
            }
        });
        progressDialog = ApplicationUtils.getProgressDialog(new WeakReference<>(RegisterBands.this), "Please wait...", false);

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
        Log.e(AppConstants.TAG, tag);
        if(etBibNo.getText().toString().trim().length() == 0){
            ApplicationUtils.showToast(RegisterBands.this, "Please enter Bib No first.");
            return;
        }
        RegisterModel registerModel = new RegisterModel();
        registerModel.setBibNo(etBibNo.getText().toString()+spinner.getSelectedItem().toString());
        registerModel.setRfid(tag);
        ArrayList<RegisterModel> registerModels = new ArrayList<>();
        registerModels.add(registerModel);
        try {
            progressDialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }
        registrationViewModel.mapRfids(registerModels).observe(RegisterBands.this, response -> {
            Log.e(AppConstants.TAG, "MAPPED");
            try {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }catch (Exception e){
                e.printStackTrace();
            }
            if(response.status) {
                ApplicationUtils.showToast(RegisterBands.this, "Rfid mapped");
                spinner.setSelection(0);
            }else{
                if(response.getMessage() != null)
                    ApplicationUtils.showToast(RegisterBands.this, response.getMessage());
                else{
                    ApplicationUtils.showToast(RegisterBands.this, "Try Again!");
                }
            }
        });
        selectedTeam = null;
        tvMapRfid.setText("Tap band on back to map after entering bib no.");
        etBibNo.setText("");

    }
}
