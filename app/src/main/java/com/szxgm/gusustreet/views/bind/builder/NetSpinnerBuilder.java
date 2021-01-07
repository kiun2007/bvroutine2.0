//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.szxgm.gusustreet.views.bind.builder;

import com.szxgm.gusustreet.R;

import kiun.com.bvroutine.base.binding.value.NetSpinnerBindConvert.Builder;
import retrofit2.Call;

public class NetSpinnerBuilder extends Builder {
    public NetSpinnerBuilder(Call var1) {
        super(var1, R.layout.item_spinner_text, R.layout.spinner_dropdown_item);
    }

    public static NetSpinnerBuilder create(Call var0) {
        return new NetSpinnerBuilder(var0);
    }
}
