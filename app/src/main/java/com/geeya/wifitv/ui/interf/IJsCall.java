package com.geeya.wifitv.ui.interf;

import android.webkit.JavascriptInterface;

public class IJsCall {

    //在SDK17及以上时，不加注解“@JavascriptInterface”可能导致调用失效
    @JavascriptInterface
    public String getUserId() {
        return "user";
    }

    ;

}
