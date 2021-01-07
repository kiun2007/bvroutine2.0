package com.szxgm.gusustreet.model.query;

import android.content.DialogInterface;
import androidx.annotation.NonNull;
import com.szxgm.gusustreet.base.QueryType;
import java.util.Date;

public class FilterQuery extends GeneralListQuery {

    public FilterQuery(@NonNull Class clz){
        super(clz);
    }

    private Date begin;

    private Date end;

    private String orderByField;

    private boolean isAsc;

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
        if (end != null){
            put("reportDate", QueryType.Between, begin, end);
        }
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
        if (begin != null){
            put("reportDate", QueryType.Between, begin, end);
        }
    }

    public void clearAll(DialogInterface dialog){
        clear("orderType");
        clear("seriousDegree");
        clear("reportDate");
        chose(dialog);
    }

    public void chose(DialogInterface dialog){
        onChanged(false);
        dialog.dismiss();
    }

    public boolean isEmpty(){
        return find("orderType") == null &&
                find("seriousDegree") == null &&
                 find("reportDate") == null;
    }

    public String getOrderByField() {
        return orderByField;
    }

    public void setOrderByField(String orderByField) {
        this.orderByField = orderByField;
        orderBy(orderByField, isAsc ? QueryType.Asc : QueryType.Desc);
    }

    public boolean isAsc() {
        return isAsc;
    }

    public void setAsc(boolean asc) {
        isAsc = asc;
        if (orderByField != null){
            orderBy(orderByField, isAsc ? QueryType.Asc : QueryType.Desc);
        }
    }
}
