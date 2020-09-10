package com.infomatics.oxfam.twat.view.reportincident;

import android.app.Activity;
import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.infomatics.oxfam.twat.R;
import com.infomatics.oxfam.twat.interfaces.ItemPositionClickListener;
import com.infomatics.oxfam.twat.model.reports.PostReportRequest;
import com.infomatics.oxfam.twat.util.AppConstants;
import com.infomatics.oxfam.twat.util.ApplicationUtils;
import com.infomatics.oxfam.twat.util.GetLocation;
import com.infomatics.oxfam.twat.util.ImagePicker;
import com.infomatics.oxfam.twat.view.BaseActivity;
import com.infomatics.oxfam.twat.viewmodel.ReportsViewModel;

import java.io.ByteArrayOutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecordIncident extends BaseActivity implements ItemPositionClickListener {

    private ImageView btnBack;
    private TextView tvHeader;
    private EditText etMessage;
    //    private RecyclerView recyclerImage;
    private ImageView imageView;
    private LinearLayout layout_add;
    //    private LinearLayoutManager mLayoutManager;
    private TextView btnReport;
    //    private ArrayList<ImageModel> images;
//    private PictureAdapter pictureAdapter;
    private static final int PICK_IMAGE_ID = 234;
    private static final int PERMISSION_REQUEST_CODE = 1234;
    private boolean isLostAndFound = false;
    private Bitmap image;
    private ProgressDialog progressDialog;
    private String lat = "";
    private String lng = "";
    private ReportsViewModel reportsViewModel;
    private int selectedTypePosition;
    private EditText reportType;
    private TextView addPictureTitle;
    private EditText incidentTitle;
    private EditText reportBy;

    String[] appPermissions = {
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_incident);
        reportsViewModel = ViewModelProviders.of(RecordIncident.this).get(ReportsViewModel.class);
        componentBinding();
    }

    private void componentBinding(){
        btnBack = findViewById(R.id.btn_back);
        tvHeader = findViewById(R.id.tv_header_text);
        etMessage = findViewById(R.id.report_message);
        layout_add = findViewById(R.id.layout_add);
        imageView = findViewById(R.id.image);
        reportType = findViewById(R.id.report_type);
        addPictureTitle = findViewById(R.id.add_picture_title);
        incidentTitle = findViewById(R.id.incident_title);
        reportBy = findViewById(R.id.report_by);
//        recyclerImage = findViewById(R.id.picture_recycler);
//        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        btnReport = findViewById(R.id.btn_report);
//        recyclerImage.setLayoutManager(mLayoutManager);
//        images = new ArrayList<>();
//        images.add(new ImageModel());
//        pictureAdapter = new PictureAdapter(images,this);
//        recyclerImage.setAdapter(pictureAdapter);
        isLostAndFound = getIntent().getBooleanExtra(AppConstants.IS_LOST_FOUND, false);

        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(v->{
            RecordIncident.this.finish();
        });

        layout_add.setOnClickListener(v->{
            if (hasPermissions())
                getImage();
        });
        imageView.setOnClickListener(v->{
            layout_add.callOnClick();
        });
        if(isLostAndFound){
            tvHeader.setText(getResources().getString(R.string.lostnfound));
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(RecordIncident.this,
                    R.array.lnf_report_type, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            btnReport.setText("Report");
            reportType.setVisibility(View.GONE);
            incidentTitle.setHint("Item Name");
            addPictureTitle.setText("Item Image");
        }else {
            tvHeader.setText(getResources().getString(R.string.report));
            addPictureTitle.setText("Incident Image");
            incidentTitle.setVisibility(View.VISIBLE);

            /*ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(RecordIncident.this,
                    R.array.report_severity, android.R.layout.simple_spinner_item);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);*/
        }

        btnReport.setOnClickListener(v->{
            if(!isLostAndFound){
                if(incidentTitle.getText().toString().trim().length() == 0){
                    ApplicationUtils.showToast(RecordIncident.this, "Please write the title.");
                    return;
                }
            }else{
                if(incidentTitle.getText().toString().trim().length() == 0){
                    ApplicationUtils.showToast(RecordIncident.this, "Please write the item name.");
                    return;
                }
            }
            if(etMessage.getText().toString().trim().length() == 0){
                ApplicationUtils.showToast(RecordIncident.this, "Please write the description.");
                return;
            }
            if(reportBy.getText().toString().trim().length() == 0){
                ApplicationUtils.showToast(RecordIncident.this, "Please insert reported by.");
                return;
            }
            postReport();

        });
        progressDialog = ApplicationUtils.getProgressDialog(new WeakReference<>(RecordIncident.this), "Please wait...", false);

        GetLocation getLocation = new GetLocation(RecordIncident.this, location -> {
            if(location != null){
                lat = Double.toString(location.getLatitude());
                lng = Double.toString(location.getLongitude());
                Log.e(AppConstants.TAG,"Found Location");
            }
        });
        getLocation.getLocation();
    }

    private void postReport(){
        progressDialog.show();
        PostReportRequest postReportRequest = new PostReportRequest();
        postReportRequest.setCheckpointId(AppConstants.CHECKPOINT_ID);
        if(isLostAndFound){
            postReportRequest.setDescription(
                    incidentTitle.getText().toString().trim() + "\n"
                    + etMessage.getText().toString().trim());
            postReportRequest.setReportedBy(reportBy.getText().toString().trim());
        }else {
            postReportRequest.setDescription(
                     incidentTitle.getText().toString().trim() + "\n"
                    + etMessage.getText().toString().trim());
            postReportRequest.setSeverity(reportType.getText().toString().trim());
            postReportRequest.setReportedBy(reportBy.getText().toString().trim());
        }
        if(image != null) {
            postReportRequest.setImage(getEncoded64ImageStringFromBitmap(image));
        }
        postReportRequest.setUserId(AppConstants.USER_ID);
        postReportRequest.setLatitude(lat);
        postReportRequest.setLongitude(lng);
        postReportRequest.setStatus(0);
        reportsViewModel.postReport(postReportRequest, isLostAndFound).observe(RecordIncident.this, baseResponse -> {
            if(baseResponse !=null) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                if (baseResponse.status) {
                    ApplicationUtils.showToast(RecordIncident.this, baseResponse.message);
                    RecordIncident.this.finish();
                } else {
                    ApplicationUtils.showToast(RecordIncident.this, baseResponse.message);
                }
            }else{
                ApplicationUtils.showToast(RecordIncident.this, "Something went wrong, try again!");
            }
        });
    }

    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
        return imgString;
    }

    @Override
    public void onItemClick(int position) {
       /* if(position == 0){
            if(images.size() < 3) {
                if (hasPermissions())
                    getImage();
            }else{
                ApplicationUtils.showToast(RecordIncident.this, "You cannot select more images.");
            }
        }else
        if (images.get(position1).getBitmap() != null){

        }*/
    }

    private boolean hasPermissions(){
        ArrayList<String> reqPermissions = new ArrayList<>();
        for(String perm : appPermissions){
            if(ContextCompat.checkSelfPermission(RecordIncident.this, perm) != PackageManager.PERMISSION_GRANTED){
                reqPermissions.add(perm);
            }
        }
        if(!reqPermissions.isEmpty()){
            ActivityCompat.requestPermissions(RecordIncident.this, reqPermissions.toArray(new String[reqPermissions.size()]),
                    PERMISSION_REQUEST_CODE);
            return false;
        }
        return true;
    }

    private void getImage(){
        Intent chooseImageIntent = ImagePicker.getPickImageIntent(this);
        startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PICK_IMAGE_ID:
                    image = ImagePicker.getImageFromResult(this, resultCode, data);
                    imageView.setImageBitmap(image);
                    layout_add.setVisibility(View.GONE);
                    /*ImageModel imageModel = new ImageModel();
                    imageModel.setBitmap(bitmap);
                    images.add(imageModel);
                    pictureAdapter.notifyDataSetChanged();*/
                    break;
                default:
                    super.onActivityResult(requestCode, resultCode, data);
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == PERMISSION_REQUEST_CODE){
            HashMap<String, Integer> permissionResults = new HashMap<>();
            int deniedCount = 0;
            for(int i = 0; i < grantResults.length; i++){
                if(grantResults[i] == PackageManager.PERMISSION_DENIED){
                    permissionResults.put(permissions[i], grantResults[i]);
                    deniedCount ++;
                }
            }

            if(deniedCount == 0){
                getImage();
            }else{
                for(Map.Entry<String, Integer> entry: permissionResults.entrySet()) {
                    String perName = entry.getKey();
                    int perRest = entry.getValue();

                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, perName)){
                        showDialog("","This app needs Storage and Camera permissions to work.","Yes, Grant Permissinos",
                                (dialog, which) -> {
                                    dialog.dismiss();
                                    hasPermissions();
                                },
                                "No, Exit", (dialog, which) -> dialog.dismiss(), false);
                    }else{
                        showDialog("","You have denied some permissions. Allow all permissions at [Setting] > [Permissions]","Go to Settings",
                                (dialog, which) -> {
                                    dialog.dismiss();
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                            Uri.fromParts("package", getPackageName(), null));
                                    startActivity(intent);
                                },
                                "No, Exit", (dialog, which) -> dialog.dismiss(), false);

                        break;
                    }

                }
            }
        }
    }

    private AlertDialog showDialog(String title, String msg, String positiveLabel,
                                   DialogInterface.OnClickListener positiveOnClick,
                                   String negativeLabel, DialogInterface.OnClickListener negativeClick,
                                   boolean isCacelable){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setCancelable(isCacelable);
        builder.setMessage(msg);
        builder.setPositiveButton(positiveLabel, positiveOnClick);
        builder.setNegativeButton(negativeLabel, negativeClick);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return alertDialog;

    }
}
