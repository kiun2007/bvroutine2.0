package com.szxgm.gusustreet.model.query;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.utils.DateUtil;
import kiun.com.bvroutine.utils.MCString;

public class OrderFieldReq extends PagerBean {

    private String orderByColumn = null;
    @JSONField(name = "act.beginDate")
    private String beginDate = MCString.formatDate("yyyy-MM-dd", new Date());
    @JSONField(name = "act.endDate")
    private String endDate;
    private String isAsc = "asc";

    public OrderFieldReq(boolean isTotal) {

        Date date = DateUtil.addDay(new Date(), 1);
        if (!isTotal){
            Date begin = DateUtil.getWeekStart(new Date());
            beginDate = MCString.formatDate("yyyy-MM-dd", begin);

            date = DateUtil.addDay(begin, 8);
        }
        endDate = MCString.formatDate("yyyy-MM-dd", date);
    }

    public String getOrderByColumn() {
        return orderByColumn;
    }

    public void setOrderByColumn(String orderByColumn) {
        this.orderByColumn = orderByColumn;
    }

    public String getIsAsc() {
        return isAsc;
    }

    public void setIsAsc(String isAsc) {
        this.isAsc = isAsc;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
