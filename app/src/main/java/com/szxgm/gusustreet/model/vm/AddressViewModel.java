package com.szxgm.gusustreet.model.vm;

import androidx.lifecycle.ViewModel;

import java.util.LinkedList;
import java.util.List;

public class AddressViewModel extends ViewModel{

    private List addressList = new LinkedList();

    public List getAddressList() {
        return addressList;
    }
}
