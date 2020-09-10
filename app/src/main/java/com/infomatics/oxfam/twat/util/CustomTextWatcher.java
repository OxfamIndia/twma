package com.infomatics.oxfam.twat.util;

import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.infomatics.oxfam.twat.interfaces.TextChangeCallback;


public class CustomTextWatcher implements TextWatcher {
    private TextInputLayout textInputLayout;
    private TextChangeCallback textChangeCallback;
    private EditText editText;
    public CustomTextWatcher(TextInputLayout textInputLayout, TextChangeCallback textChangeCallback){
        this.textInputLayout    = textInputLayout;
        this.textChangeCallback = textChangeCallback;
    }

    public CustomTextWatcher(EditText editText, TextChangeCallback textChangeCallback){
        this.editText = editText;
        this.textChangeCallback = textChangeCallback;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(s.length() > 0){
            textChangeCallback.textAdded(true);
        }else{
            textChangeCallback.textAdded(false);
        }
    }
    @Override
    public void afterTextChanged(Editable s) {
        if(textInputLayout != null) {
            textInputLayout.setError(null);
        }
    }
}
