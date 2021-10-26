package com.szxgm.gusustreet;

import android.content.Intent;
import android.text.TextUtils;

import com.szxgm.gusustreet.service.TrailService;
import com.szxgm.gusustreet.ui.activity.LoginActivity;

import java.util.Map;

import kiun.com.bvroutine.net.LoginInterceptor;
import kiun.com.bvroutine.utils.SharedUtil;
import kiun.com.bvroutine.utils.Timer;
import okhttp3.Headers;

public class AppLogin extends LoginInterceptor<MainApplication> {

    public static final String clzName = AppLogin.class.getName();

    private Timer loginOutTimer;

    public AppLogin(MainApplication activityApplication) {
        super(activityApplication);
        authorizeToken = SharedUtil.getValue("sessionId", "");
        loginOutTimer = new Timer(activityApplication).addListener(this::logOut);
        isLogin = !TextUtils.isEmpty(authorizeToken);
    }

    @Override
    public String getAuthorizeToken() {
        return authorizeToken;
    }

    @Override
    public void refineToken(Headers headers, String body) {

        String sessionId = getSessionId(headers);
        if (sessionId != null){
            authorizeToken = sessionId;
            SharedUtil.saveValue("sessionId", sessionId);
        }
    }

    @Override
    public Map<String, String> getHeader() {
        headers.put("Cookie", String.format("JSESSIONID=%s", authorizeToken));
        return headers;
    }

    private boolean logOut(){

        Intent intent = new Intent(application.getTop(), LoginActivity.class);
        application.getTop().startActivity(intent);

        //退出登陆操作.
        application.finish(LoginActivity.class);
        return false;
    }

    @Override
    public void clear() {

        if (clearToken()){
            SharedUtil.saveValue("sessionId", "");
            authorizeToken = null;
            if (application != null){
                application.stopService(new Intent(application, TrailService.class));
                application.eCommLogin.logout();
                loginOutTimer.start(0);
            }
        }
    }
}
