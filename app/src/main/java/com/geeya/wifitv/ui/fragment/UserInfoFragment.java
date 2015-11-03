package com.geeya.wifitv.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.geeya.wifitv.R;
import com.geeya.wifitv.base.BaseFragment;
import com.geeya.wifitv.presenter.user.UserAction;
import com.geeya.wifitv.presenter.user.UserActionImpl;
import com.geeya.wifitv.ui.activity.AboutActivity;
import com.geeya.wifitv.ui.activity.BlankActivity;
import com.geeya.wifitv.ui.activity.CouponsActivity;
import com.geeya.wifitv.ui.activity.FeedbackActivity;
import com.geeya.wifitv.ui.activity.HelpActivity;
import com.geeya.wifitv.ui.activity.LoginActivity;
import com.geeya.wifitv.ui.activity.SettingActivity;
import com.geeya.wifitv.ui.activity.WealthActivity;
import com.geeya.wifitv.ui.interf.ILogout;
import com.geeya.wifitv.utils.VolleyUtil;
import com.geeya.wifitv.widget.CircleImageView;

public class UserInfoFragment extends BaseFragment implements OnClickListener, ILogout {

    private View rootView; // 缓存Fragment view

    private RelativeLayout rlBg;
    private TextView tvCoupons;
    private TextView tvWealth;
    private TextView tvCollections;
    private TextView tvSet;
    private TextView tvHelp;
    private TextView tvAbout;
    private TextView tvFeddback;

    private Button btnLogin;
    private TextView tvName;
    private TextView tvLocation;
    private CircleImageView civFace;

    private UserAction actionLogout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        actionLogout = new UserActionImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_maintab_user, container, false);
        }

//        initTitleBar();
        initViewId();

        // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        String area = null;
        area = app.getAreaInfo().getArea();
        if (area != null) {
            tvLocation.setText(area);
        }
        if (app.getUserInfo().getUserIdentify() == 1)
            btnLogin.setText(R.string.user_login);
        else {
            btnLogin.setText(R.string.user_logoff);
        }
        tvName.setText(app.getUserInfo().getUserName());
        String userFaceUrl = app.getUserInfo().getUserFace();
        if (userFaceUrl != null) {
            ImageLoader imageLoader = VolleyUtil.getImageLoader();
            imageLoader.get(userFaceUrl, ImageLoader.getImageListener(civFace, R.mipmap.userinfo_headpicture, R.mipmap.userinfo_headpicture));
        }
    }

    ;

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
    }

    private void initTitleBar() {
        if (rootView != null) {
            TextView tvTitle = (TextView) rootView.findViewById(R.id.tv_titlebar);
            tvTitle.setText(R.string.main_tab_my);
        }
    }

    private void initViewId() {
        rlBg = (RelativeLayout) rootView.findViewById(R.id.userinfo_rl_bg);
        tvCoupons = (TextView) rootView.findViewById(R.id.user_coupons);
        tvWealth = (TextView) rootView.findViewById(R.id.user_wealth);
        tvCollections = (TextView) rootView.findViewById(R.id.user_collection);
        tvHelp = (TextView) rootView.findViewById(R.id.user_help);
        tvSet = (TextView) rootView.findViewById(R.id.user_set);
        tvAbout = (TextView) rootView.findViewById(R.id.user_about);
        tvFeddback = (TextView) rootView.findViewById(R.id.user_feedback);
        rlBg.setBackgroundResource(R.mipmap.userinfo_source_background);
        tvCoupons.setOnClickListener(this);
        tvWealth.setOnClickListener(this);
        tvCollections.setOnClickListener(this);
        tvHelp.setOnClickListener(this);
        tvSet.setOnClickListener(this);
        tvAbout.setOnClickListener(this);
        tvFeddback.setOnClickListener(this);

        btnLogin = (Button) rootView.findViewById(R.id.userinfo_loginbutton);
        tvName = (TextView) rootView.findViewById(R.id.user_name);
        tvLocation = (TextView) rootView.findViewById(R.id.user_location);
        civFace = (CircleImageView) rootView.findViewById(R.id.circleimage);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        // TODO Auto-generated method stub
        super.onDestroyView();

        rootView = null;
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        civFace.clear();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.userinfo_loginbutton:
                if (app.getUserInfo().getUserIdentify() == 1) {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                } else {
                    actionLogout.userLogout();
                }
                break;
            case R.id.user_wealth:
                if (app.getUserInfo().getUserIdentify() == 0) {
                    //Intent intent = new Intent(context, LoginActivity.class);
                    Intent intent = new Intent(mContext, WealthActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(mContext, WealthActivity.class);
                    startActivity(intent);
                }
//			Intent intent = new Intent(context, BlankActivity.class);
                //startActivity(intent);
                break;
            case R.id.user_set:
                Intent intent1 = new Intent(mContext, SettingActivity.class);
                startActivity(intent1);
                break;
            case R.id.user_feedback:
                Intent intent2 = new Intent(mContext, FeedbackActivity.class);
                startActivity(intent2);
                break;
            case R.id.user_help:
                Intent intent3 = new Intent(mContext, HelpActivity.class);
                startActivity(intent3);
                break;
            case R.id.user_about:
                Intent intent4 = new Intent(mContext, AboutActivity.class);
                startActivity(intent4);
                break;
            case R.id.user_coupons:
                Intent intent5 = new Intent(mContext, CouponsActivity.class);
                //Intent intent5 = new Intent(context, BlankActivity.class);
                startActivity(intent5);
                break;
            case R.id.user_collection:
                Intent intent6 = new Intent(mContext, BlankActivity.class);
                startActivity(intent6);
            default:
                break;
        }
    }

    @Override
    public void showMessage(String message) {
        if (null != mContext) {
            Toast toast = Toast.makeText(mContext, message, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void afterLogout() {
        tvName.setText("游客");
        btnLogin.setText("登陆");
    }
}
