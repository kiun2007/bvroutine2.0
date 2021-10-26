package com.szxgm.gusustreet.base.presenter;

import com.szxgm.gusustreet.base.AppComponent;
import com.szxgm.gusustreet.model.dto.DatVersion;
import java.io.IOException;
import kiun.com.bvroutine.interfaces.callers.SetCaller;

public interface VersionUpdate {

    DatVersion checkVersion(String packageName);

    DatVersion checkVersion();

    DatVersion getVersion();

    void share();

    boolean download(SetCaller<Integer> callBack) throws IOException;

    boolean installApp();

    /**
     * 检查、更新、启动、
     * @return
     */
    boolean compound(AppComponent appComponent);

    /**
     * 检查、更新、本应用.
     * @return
     */
    boolean compound();
}