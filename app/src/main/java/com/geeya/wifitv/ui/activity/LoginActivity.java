package com.geeya.wifitv.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.geeya.wifitv.R;
import com.geeya.wifitv.base.BaseActivity;
import com.geeya.wifitv.presenter.user.UserAction;
import com.geeya.wifitv.presenter.user.UserActionImpl;
import com.geeya.wifitv.ui.interf.ILogin;

public class LoginActivity extends BaseActivity implements ILogin, OnClickListener {
    private EditText metuserName;
    private EditText metuserPasswd;
    private Button mbtnLogin;
    private ImageButton ibtnSinaAuthor;
    private ImageButton ibtnWeiXinAuthor;
    private ImageButton ibtnQQAuthor;
    private UserAction userAction;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        userAction = new UserActionImpl(this);
    }

    private void login() {
        mbtnLogin.setEnabled(false);
        this.userAction.userLogin();
    }

    public void initView() {
        metuserName = (EditText) findViewById(R.id.user_login_name);
        metuserPasswd = (EditText) findViewById(R.id.user_login_password);
        mbtnLogin = (Button) findViewById(R.id.user_login_bt_loginbutton);
        ibtnSinaAuthor = (ImageButton) findViewById(R.id.login_ibtn_sina);
        ibtnWeiXinAuthor = (ImageButton) findViewById(R.id.login_ibtn_weixin);
        ibtnQQAuthor = (ImageButton) findViewById(R.id.login_ibtn_qq);
        mbtnLogin.setOnClickListener(this);
        ibtnSinaAuthor.setOnClickListener(this);
        ibtnWeiXinAuthor.setOnClickListener(this);
        ibtnQQAuthor.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_menu_register:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_login_bt_loginbutton:
                login();
                break;
            case R.id.login_ibtn_sina:
                break;
            case R.id.login_ibtn_weixin:
                break;
            case R.id.login_ibtn_qq:
                break;
            default:
                break;
        }

    }

    @Override
    public String getUserName() {
        // TODO Auto-generated method stub
        return metuserName.getText().toString().trim();
    }

    @Override
    public String getUserPassword() {
        // TODO Auto-generated method stub
        return metuserPasswd.getText().toString().trim();
    }

    @Override
    public void showToast(String message) {
        // TODO Auto-generated method stub
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
        mbtnLogin.setEnabled(true);
    }

    @Override
    public void kill() {
        // TODO Auto-generated method stub
        LoginActivity.this.finish();
    }
}
