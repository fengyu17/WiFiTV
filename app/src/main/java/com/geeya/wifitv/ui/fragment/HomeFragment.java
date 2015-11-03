/**
 * 注释部分为后续开发中，添加“连接隐藏wifi”的功能
 */
package com.geeya.wifitv.ui.fragment;

import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.geeya.wifitv.R;
import com.geeya.wifitv.adapter.HomeListViewAda;
import com.geeya.wifitv.base.BaseFragment;
import com.geeya.wifitv.presenter.AppAction;
import com.geeya.wifitv.presenter.AppActionImpl;
import com.geeya.wifitv.service.HomeReceiver;
import com.geeya.wifitv.ui.interf.IHomeFrag;
import com.geeya.wifitv.utils.DisplayUtils;
import com.geeya.wifitv.widget.PasswordDialog;

public class HomeFragment extends BaseFragment implements IHomeFrag {

    private AppAction wifiAction;
    private HomeReceiver homeReceiver;
    private HomeListViewAda adapter;

    private View rootView; // 缓存Fragment view
    private ListView lvWifiList;
    private ToggleButton tbWifiControl;
    private ImageView ibWifiControl;
    private TextView tvProgressBar;
    private ProgressBar pbProgressBar;
    private TextView tvConnectivityState;
    private ImageView ivShrinkWifiBar;
    private LinearLayout llWifiBar;
    private RelativeLayout rlWifiTitleBar; // wifi标题栏

    private boolean wifiBarState = true; // wifi状态栏状态：展开或者收起

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        wifiAction = new AppActionImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_maintab_home, container, false);
        }
        initViewId();
        initWifiList();
        setListener();
        homeReceiver = new HomeReceiver(this, mContext);
        // 缓存的rootView需要判断是否已经被加过parent，
        // 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    private void initViewId() {
        tvConnectivityState = (TextView) rootView.findViewById(R.id.tv_fragment_home_wifi_state);
        lvWifiList = (ListView) rootView.findViewById(R.id.lv_wifilist);
        tbWifiControl = (ToggleButton) rootView.findViewById(R.id.tb_fragment_home_wifi_control_toggle);
        ibWifiControl = (ImageView) rootView.findViewById(R.id.ib_fragment_home_wifi_control_button);
        tvProgressBar = (TextView) rootView.findViewById(R.id.tv_fragment_wifi_list_progresbar_title);
        pbProgressBar = (ProgressBar) rootView.findViewById(R.id.pb_fragment_wifi_list_progresbar_bar);
        ivShrinkWifiBar = (ImageView) rootView.findViewById(R.id.iv_fragment_home_wifilist_state);
        rlWifiTitleBar = (RelativeLayout) rootView.findViewById(R.id.rl_fragment_wifi_list_title_bar);
        llWifiBar = (LinearLayout) rootView.findViewById(R.id.ll_fragment_wifi_bar);
    }

    public void setListener() {
        tbWifiControl.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // isChecked 为true，则表示打开wifi
                if (isChecked) {
                    openWifi();
                } else {
                    closeWifi();
                }
            }
        });
        ibWifiControl.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                tbWifiControl.toggle();
            }
        });

        ivShrinkWifiBar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (wifiBarState) {
                    shrink();
                    wifiBarState = false;
                } else {
                    spread();
                    wifiBarState = true;
                }
            }
        });
    }

    public void initWifiList() {
        lvWifiList.setVerticalScrollBarEnabled(true);
        lvWifiList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                connect(arg2);
            }
        });
    }

    private void shrink() {
        this.wifiAction.shrinkTitleBar();
    }

    private void spread() {
        this.wifiAction.expandTitleBar();
    }

    private void openWifi() {
        tbWifiControl.setEnabled(false);
        ibWifiControl.setEnabled(false);
        this.wifiAction.openWifi();
        this.wifiAction.expandTitleBar();
    }

    private void closeWifi() {
        tbWifiControl.setEnabled(false);
        ibWifiControl.setEnabled(false);
        this.wifiAction.closeWifi();
        this.wifiAction.shrinkTitleBar();
    }

    private void connect(int arg2) {
        this.wifiAction.wifiConnection(arg2);
    }

    private IntentFilter initFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.startScan");
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        return filter;
    }

    @Override
    public void onResume() {
        super.onResume();
        wifiAction.initWifiControl();
        getActivity().getBaseContext().registerReceiver(homeReceiver, initFilter());

    }

    @Override
    public void onPause() {
        try {
            getActivity().getBaseContext().unregisterReceiver(homeReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        wifiAction = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /************************************
     * 接口实现
     *****************************************************/
    @Override
    public void updateAdapter(java.util.List<ScanResult> list) {
        if (list != null && list.size() > 0) {
            if (null != adapter) {
                adapter.setdata(list);
                reDraw(list.size());
                adapter.notifyDataSetChanged();
            } else {
                adapter = new HomeListViewAda(mContext, list);
                lvWifiList.setAdapter(adapter);
                reDraw(list.size());
            }
        } else {
            lvWifiList.setVisibility(View.GONE);
            LinearLayout.LayoutParams lv_layout = (LinearLayout.LayoutParams) lvWifiList.getLayoutParams();
            lv_layout.height = 0;
            lvWifiList.setLayoutParams(lv_layout);
        }
    }

    @Override
    public void changeToggleEnable(int value) {
        switch (value) {
            case WifiManager.WIFI_STATE_ENABLED:
                tbWifiControl.setChecked(true);
                tbWifiControl.setEnabled(true);
                ibWifiControl.setEnabled(true);
                break;
            case WifiManager.WIFI_STATE_ENABLING:
                tbWifiControl.setChecked(true);
                tbWifiControl.setEnabled(false);
                ibWifiControl.setEnabled(false);
                break;
            case WifiManager.WIFI_STATE_DISABLING:
                tbWifiControl.setChecked(false);
                tbWifiControl.setEnabled(false);
                ibWifiControl.setEnabled(false);
                break;
            case WifiManager.WIFI_STATE_DISABLED:
                tbWifiControl.setChecked(false);
                tbWifiControl.setEnabled(true);
                ibWifiControl.setEnabled(true);
                break;
            case WifiManager.WIFI_STATE_UNKNOWN:
                tbWifiControl.setEnabled(true);
                ibWifiControl.setEnabled(true);
                break;
            default:
                break;
        }
    }

    @Override
    public void setWifiListVisibility(int value) {
        lvWifiList.setVisibility(value);
    }

    @Override
    public void setToggleChecked(boolean value) {
        tbWifiControl.setChecked(value);
    }

    /**
     * 关闭WiFi时的toggle location
     */
    @Override
    public void toggleLocationClose() {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ibWifiControl.getLayoutParams();
        if (!params.equals(RelativeLayout.ALIGN_LEFT)) {
            params.addRule(RelativeLayout.ALIGN_RIGHT, -1);
            params.addRule(RelativeLayout.ALIGN_LEFT, R.id.tb_fragment_home_wifi_control_toggle);
            ibWifiControl.setLayoutParams(params);
            ibWifiControl.setImageResource(R.drawable.fragment_home_wifi_control_off);
            tbWifiControl.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
        }
        ivShrinkWifiBar.setEnabled(false);
    }

    @Override
    public void toggleLocationOpen() {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ibWifiControl.getLayoutParams();
        if (!params.equals(RelativeLayout.ALIGN_RIGHT)) {
            params.addRule(RelativeLayout.ALIGN_RIGHT, R.id.tb_fragment_home_wifi_control_toggle);
            params.addRule(RelativeLayout.ALIGN_LEFT, -1);
            ibWifiControl.setLayoutParams(params);
            ibWifiControl.setImageResource(R.drawable.fragment_home_wifi_control_on);
            tbWifiControl.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        }
        ivShrinkWifiBar.setEnabled(true);
    }

    @Override
    public void playOpenAnimation() {
        // TODO Auto-generated method stub
        TranslateAnimation animation = new TranslateAnimation(DisplayUtils.dip2px(mContext, -35), 0, 0, 0);
        animation.setDuration(200);
        ibWifiControl.setAnimation(animation);
    }

    @Override
    public void playCloseAnimation() {
        // TODO Auto-generated method stub
        TranslateAnimation animation = new TranslateAnimation(DisplayUtils.dip2px(mContext, 35), 0, 0, 0);
        animation.setDuration(200);
        ibWifiControl.setAnimation(animation);
    }

    @Override
    public ScanResult getListItem(int arg2) {
        return (ScanResult) adapter.getItem(arg2);
    }

    @Override
    public void startScan() {
        tvProgressBar.setText("正在扫描");
        pbProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void endScan() {
        tvProgressBar.setText("扫描完成");
        pbProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showConnectivityState(String message) {
        // TODO Auto-generated method stub
        if (!tvConnectivityState.getText().equals(message))
            tvConnectivityState.setText(message);
    }

    @Override
    public void expandTitleBar() {
        llWifiBar.setVisibility(View.VISIBLE);
        ivShrinkWifiBar.setImageResource(R.mipmap.fragment_home_wifilist_shrink);
    }

    @Override
    public void shrinkTitleBar() {
        llWifiBar.setVisibility(View.GONE);
        ivShrinkWifiBar.setImageResource(R.mipmap.fragment_home_wifilist_expand);
    }

    @Override
    public void setWifiBarHeightOpen() {
        LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) llWifiBar.getLayoutParams();
        params2.height = lvWifiList.getMeasuredHeight() + rlWifiTitleBar.getMeasuredHeight();// + rlAddWifiTitleBar.getMeasuredHeight();
        llWifiBar.setLayoutParams(params2);
    }

    @Override
    public void setWifiBarHeightClose() {
        LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) llWifiBar.getLayoutParams();
        params2.height = rlWifiTitleBar.getHeight();// + rlAddWifiTitleBar.getHeight();
        llWifiBar.setLayoutParams(params2);
    }

    @Override
    public void showDialog(final String SSID, final int cipherType) {
        PasswordDialog dialog = new PasswordDialog(getActivity(), new PasswordDialog.OnCustomDialogListener() {

            @Override
            public void back(String str) {
                wifiAction.wifiConnection(SSID, str, cipherType);
            }
        }, SSID, cipherType);
        dialog.show();
    }

    /**
     * 刷新wifi界面时，根据扫描到的wifi数量重新绘制界面
     *
     * @param size
     * @
     */
    private void reDraw(int size) {
        if (size < 4) {
            TypedValue value = new TypedValue();
            mContext.getResources().getValue(R.dimen.home_wifilist_item_height, value, true);
            int height = DisplayUtils.dip2px(mContext, TypedValue.complexToFloat(value.data));
            LinearLayout.LayoutParams lv_layout = (LinearLayout.LayoutParams) lvWifiList.getLayoutParams();
            lv_layout.height = size * height;
            lvWifiList.setLayoutParams(lv_layout);
        }
        LinearLayout.LayoutParams ll_layout = (LinearLayout.LayoutParams) llWifiBar.getLayoutParams();
        ll_layout.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        llWifiBar.setLayoutParams(ll_layout);
    }
}
