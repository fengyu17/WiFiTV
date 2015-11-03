package com.geeya.wifitv.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.geeya.wifitv.AppConfig;
import com.geeya.wifitv.R;
import com.geeya.wifitv.base.BaseFragment;
import com.geeya.wifitv.ui.interf.IJsCall;

public class LifeFragment extends BaseFragment {

    private View rootView; // 缓存Fragment view
    private WebView webView;
    private SwipeRefreshLayout srlRefreshWeb;
    private DownloadManager downloadManager;
    private List<Long> downloadId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_maintab_life, container, false);
        }
//        initTitleBar();
        init();
        setWebView();
        setRefreshListener();
        addJsInterface();
        // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        // TODO Auto-generated method stub
        super.onDestroyView();
        rootView = null;
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init() {
        srlRefreshWeb = (SwipeRefreshLayout) rootView.findViewById(R.id.srl_life_refreshweb);
        srlRefreshWeb.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        webView = (WebView) rootView.findViewById(R.id.webview);
        webView.loadUrl(AppConfig.LIFE_URL);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        downloadId = new ArrayList<Long>();

    }

    private void initTitleBar() {
        if (rootView != null) {
            TextView tvTitlebar = (TextView) rootView.findViewById(R.id.tv_titlebar);
            tvTitlebar.setText(R.string.main_tab_life);
        }
    }

    private void addJsInterface() {
        webView.addJavascriptInterface(new IJsCall(), "WiFiTvWebViewFunc");
    }

    private void setWebView() {
        // 返回键监听
        webView.setOnKeyListener(new OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
                    webView.goBack();
                    return true;
                }
                return false;
            }
        });

        // 加载url监听
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                super.onPageStarted(view, url, favicon);
                srlRefreshWeb.setRefreshing(true);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);
                srlRefreshWeb.setRefreshing(false);
                testMethod(view);
            }
        });

        webView.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                long id = download(url);
                if (-1 != id)
                    downloadId.add(id);
            }
        });
    }

    // 下拉刷新监听
    private void setRefreshListener() {
        srlRefreshWeb.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.loadUrl(webView.getUrl());
                srlRefreshWeb.setRefreshing(false);
            }
        });
    }

    private long download(String url) {
        if (null == url)
            return -1;
        String[] str = url.split("\\/");
        String fileName = str[str.length - 1];
        Request request = new Request(Uri.parse(url));
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
        request.setNotificationVisibility(Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        return downloadManager.enqueue(request);
    }

    /**
     * JS验证
     */
    private void testMethod(WebView webView) {
        String call = "javascript:sayHello()";
        call = "javascript:sumToJava(1,2)";
        webView.loadUrl(call);

    }

}
