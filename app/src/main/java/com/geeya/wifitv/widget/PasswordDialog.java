package com.geeya.wifitv.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.geeya.wifitv.R;

public class PasswordDialog extends Dialog {
    private Button btCertainButton;
    private Button btCancelButton;
    private EditText etPassword;
    private TextView tvTitle;
    private String title;
    private int cipherType;
    public OnCustomDialogListener customDialogListener;

    public interface OnCustomDialogListener {
        void back(String str);
    }

    public PasswordDialog(Context context, OnCustomDialogListener customDialogListener, String title, int cipherType) {
        super(context, R.style.DialogStyle);
        this.customDialogListener = customDialogListener;
        this.title = title;
        this.cipherType = cipherType;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        setContentView(R.layout.dialog_wifi_password);
        //getActionBar().hide();
        tvTitle = (TextView) findViewById(R.id.dialog_wifi_title);
        btCertainButton = (Button) findViewById(R.id.dialog_wifi_certain);
        btCancelButton = (Button) findViewById(R.id.dialog_wifi_cancel);
        etPassword = (EditText) findViewById(R.id.dialog_wifi_psw);
        btCertainButton.setOnClickListener(listener);
        btCancelButton.setOnClickListener(listener);
        etPassword.addTextChangedListener(watcher);
        tvTitle.setText(title);
        super.onCreate(savedInstanceState);
    }

    TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (1 == cipherType) {
                if (etPassword.getText().toString().length() > 7)
                    btCertainButton.setEnabled(true);
                else
                    btCertainButton.setEnabled(false);
            } else {
                if (etPassword.getText().toString().length() > 1)
                    btCertainButton.setEnabled(true);
                else
                    btCertainButton.setEnabled(false);
            }
        }
    };

    private View.OnClickListener listener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.dialog_wifi_certain) {
                customDialogListener.back(etPassword.getText().toString());
                dismiss();
            } else {
                etPassword = null;
                customDialogListener.back(null);
                cancel();
            }
        }
    };
}
