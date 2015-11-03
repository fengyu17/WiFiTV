package com.geeya.wifitv;

public class AppConfig {

    // 云平台接口
    public static final String CLOUD_IP = "http://192.168.0.136:8008";

    // 位置信息接口
    public static final String URL_LOCATION = "/geeyayun/app/base/getLocation";

    // 用户登录接口
    public final static String URL_LOGIN = "/geeyayun/app/user/signIn";

    // 用户注册接口
    public final static String URL_REGISTER = "/geeyayun/app/user/signUp";

    // APP更新接口
    public final static String URL_CHECKUPDATE = "/PRO_AD/update_appCheck";

    // 广告请求接口
    public static final String URL_AD = "/geeyayun/app/ad/getAd";

    // 直播列表请求接口
    public static final String API_CHANNELINFO = "/PRO_AD/list_getChannelList";

    // 点播列表请求接口
    public static final String API_PROGRAMINFO = "/PRO_AD/list_getProgramList";

    // 点播详细信息请求接口
    public static final String API_PROGRAMDETAIL = "/PRO_AD/list_getDetailList";

    // 点播页面标题
    public static final String PROGRAM_TITLES[] = {"电影", "电视剧", "音乐", "体育", "娱乐", "游戏"};

    // 点券页面标题
    public static final String COUNPONS_TITLES[] = {"现金券", "优惠券"};

    // 点播页面请求参数
    public static final String PROGRAM_TYPE[] = {"MOVIE", "TELEPLAY", "MUSIC", "SPORTS", "ENTERTAINMENT", "TRAVEL"};

    // 点播页面分页请求每条数据的个数
    public static final int PROGRAM_PERNUM = 9;

    // 用户行为数据上传接口
    public static final String API_USERACTION = "/geeyayun/app/action/pushActionData";

    // 游戏大厅html接口
    public static final String GAME_URL = "http://cloud.wifitv.com.cn/wifiCheck/game";

    // 应用市场html接口
    public static final String APPMARKET_URL = "http://mobile.baidu.com/?source=mobres&from=1010680m#/";

    // 乐生活html接口
    public static final String LIFE_URL = "http://cloud.wifitv.com.cn/wifiCheck/teambuy";

    // 广告类型
    public static final int ADTYPE_HOME = 1;
    public static final int ADTYPE_LIVELIST = 2;
    public static final int ADTYPE_SUBTITLE = 3;
    public static final int ADTYPE_STARTUP = 4;
    public static final int ADTYPE_CORNER = 5;
    public static final int ADTYPE_PAUSE = 6;

    private Boolean autoLogin = true;

    private Boolean autoUpgrade = true;

    public void setAutoLogin(Boolean autoLogin) {
        this.autoLogin = autoLogin;
    }

    public Boolean getAutoLogin() {
        return autoLogin;
    }

    public void setAutoCheckUpdate(Boolean checkUpdate) {
        this.autoUpgrade = checkUpdate;
    }

    public Boolean getAutoLoginUpdate() {
        return autoUpgrade;
    }
}
