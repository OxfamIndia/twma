package com.infomatics.oxfam.twat.view.login;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.infomatics.oxfam.twat.R;
import com.infomatics.oxfam.twat.model.login.ForgotPasswordRequest;
import com.infomatics.oxfam.twat.util.ApplicationUtils;
import com.infomatics.oxfam.twat.view.BaseActivity;
import com.infomatics.oxfam.twat.viewmodel.LoginViewModel;

import java.lang.ref.WeakReference;

public class ForgotPassword extends BaseActivity {

    private TextView btnForgotPassword;
    private EditText etUserId;
    private LoginViewModel loginViewModel;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        loginViewModel = ViewModelProviders.of(ForgotPassword.this).get(LoginViewModel.class);
        componentBinding();
    }

    private void componentBinding(){
        btnForgotPassword = findViewById(R.id.btn_forgot_password);
        etUserId = findViewById(R.id.txt_user_id);
        progressDialog = ApplicationUtils.getProgressDialog(new WeakReference<>(ForgotPassword.this),"Please wait..." , false);
        btnForgotPassword.setOnClickListener(v->{
            if(etUserId.getText().toString().trim().length() == 0){
                ApplicationUtils.showToast(ForgotPassword.this, getResources().getString(R.string.error_userid));
                return;
            }
            progressDialog.show();
            ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();
            forgotPasswordRequest.setUserid(etUserId.getText().toString().trim());
            loginViewModel.forgotPassword(forgotPasswordRequest).observe(ForgotPassword.this,response->{
                if(progressDialog.isShowing())
                    progressDialog.cancel();
                if(response != null){
                    if(response.getMessage()!= null)
                        ApplicationUtils.showToast(ForgotPassword.this,response.getMessage());
                    else
                        ApplicationUtils.showToast(ForgotPassword.this,getResources().getString(R.string.password_changed));
                    ForgotPassword.this.finish();
                }
            } );
        });

    }
}
