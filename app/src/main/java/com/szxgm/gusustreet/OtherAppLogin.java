package com.szxgm.gusustreet;

import com.szxgm.gusustreet.utils.SHA1Util;

import java.util.Date;
import java.util.Map;

import kiun.com.bvroutine.net.LoginInterceptor;
import kiun.com.bvroutine.utils.ByteUtil;
import kiun.com.bvroutine.utils.MD5Util;
import lte.trunk.terminal.mdmclient.utils.Sha2Utils;
import okhttp3.Headers;

public class OtherAppLogin extends LoginInterceptor<MainApplication> {

    public OtherAppLogin(MainApplication activityApplication) {
        super(activityApplication);
    }

    @Override
    public String getAuthorizeToken() {
        return "OTHER";
    }

    @Override
    public void refineToken(Headers headers, String s) {

    }

    @Override
    public Map<String, String> getHeader() {
        String time = String.valueOf(new Date().getTime());
        headers.put("t", time);
        headers.put("s", ByteUtil.bytesToHexString(MD5Util.MD5(SHA1Util.getSHA(String.format("XGM!AFSDAD5454ADE&%s", time)))));
        return headers;
    }

    @Override
    public void clear() {

    }
}
