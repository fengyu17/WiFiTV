package com.geeya.wifitv.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geeya.wifitv.R;
import com.geeya.wifitv.presenter.AppAction;
import com.geeya.wifitv.presenter.AppActionImpl;
import com.geeya.wifitv.ui.interf.IJoin;

public class JoinActivity extends Activity implements OnFocusChangeListener, IJoin {

    private Button btnCommit;
    private EditText etName;
    private EditText etTel;
    private EditText etLeave;
    private AppAction actionJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        actionJoin = new AppActionImpl(this);
        initTitleBar();
        initView();
        setListener();
        addTextWatcher();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.et_join_name:
                if (hasFocus)
                    etName.setBackgroundResource(R.drawable.activity_join_et_bg_select);
                else
                    etName.setBackgroundResource(R.drawable.activity_join_et_bg_non_select);
                break;
            case R.id.et_join_tel:
                if (hasFocus)
                    etTel.setBackgroundResource(R.drawable.activity_join_et_bg_select);
                else
                    etTel.setBackgroundResource(R.drawable.activity_join_et_bg_non_select);
                break;
            case R.id.et_join_leave:
                if (hasFocus)
                    etLeave.setBackgroundResource(R.drawable.activity_join_et_bg_select);
                else
                    etLeave.setBackgroundResource(R.drawable.activity_join_et_bg_non_select);
                break;
            default:
                break;
        }
    }

    @Override
    public void setEnable(boolean enable) {
        btnCommit.setEnabled(enable);
    }

    @Override
    public String getName() {
        return etName.getText().toString().trim();
    }

    @Override
    public String getTel() {
        return etTel.getText().toString().trim();
    }

    @Override
    public String getLeave() {
        return etLeave.getText().toString().trim();
    }

    private void initTitleBar() {
        LinearLayout ll_back = (LinearLayout) findViewById(R.id.back);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText("加入我们");
        ll_back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        etName = (EditText) findViewById(R.id.et_join_name);
        etTel = (EditText) findViewById(R.id.et_join_tel);
        etLeave = (EditText) findViewById(R.id.et_join_leave);
        btnCommit = (Button) findViewById(R.id.bt_join_commit);
    }

    private void setListener() {
        etName.setOnFocusChangeListener(this);
        etTel.setOnFocusChangeListener(this);
        etLeave.setOnFocusChangeListener(this);
        btnCommit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addTextWatcher() {
        etName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                actionJoin.setEnabled();
            }
        });

        etLeave.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                actionJoin.setEnabled();
            }
        });

        etTel.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                actionJoin.setEnabled();
            }
        });
    }
}
