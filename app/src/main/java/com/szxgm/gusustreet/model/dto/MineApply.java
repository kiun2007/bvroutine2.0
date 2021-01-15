package com.szxgm.gusustreet.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.szxgm.gusustreet.R;

import java.io.Serializable;
import java.util.Date;

public class MineApply implements Serializable {

    /**
     * 原因
     */
    private String reason;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 申请人
     */
    private String name;

    /**
     * 结束日期
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date end;

    /**
     * 编号
     */
    private String id;

    /**
     * 申请类型 1请假,2加班,3调班,4申报
     */
    private String type;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 开始时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date begin;

    /**
     * 审核状态 0未提交,1待审批,2已通过,3已驳回
     */
    private int status;

    /**
     * 申报类型
     */
    private String typeInner;

    /**
     * 调班班次
     */
    private String tbbeforetimes;

    /**
     * 被调班次
     */
    private String tblatertimes;

    /**
     * 调班申请人
     */
    private String tbmenbefore;

    /**
     * 被调班人
     */
    private String tbmenlater;

    /**
     * 时长
     */
    private Integer totaltime;

    /**
     * 附件
     */
    private String annex;

    /**
     * 是否是调班审批.
     */
    private boolean isReplace;


    public int getLayout(){
        if ("1".equals(type)){
            return R.layout.item_apply_leave;
        }else if ("2".equals(type)){
            return R.layout.item_apply_overtime;
        }else if ("3".equals(type)){
            return R.layout.item_apply_replace;
        }else if ("4".equals(type)){
            return R.layout.item_apply_supplement;
        }
        return 0;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTypeInner() {
        return typeInner;
    }

    public void setTypeInner(String typeInner) {
        this.typeInner = typeInner;
    }

    public String getTbmenbefore() {
        return tbmenbefore;
    }

    public void setTbmenbefore(String tbmenbefore) {
        this.tbmenbefore = tbmenbefore;
    }

    public String getTbmenlater() {
        return tbmenlater;
    }

    public void setTbmenlater(String tbmenlater) {
        this.tbmenlater = tbmenlater;
    }

    public String getTbbeforetimes() {
        return tbbeforetimes;
    }

    public void setTbbeforetimes(String tbbeforetimes) {
        this.tbbeforetimes = tbbeforetimes;
    }

    public String getTblatertimes() {
        return tblatertimes;
    }

    public void setTblatertimes(String tblatertimes) {
        this.tblatertimes = tblatertimes;
    }

    public Integer getTotaltime() {
        return totaltime;
    }

    public void setTotaltime(Integer totaltime) {
        this.totaltime = totaltime;
    }

    public String getAnnex() {
        return annex;
    }

    public void setAnnex(String annex) {
        this.annex = annex;
    }

    /**
     * 是否需要展示操作.
     * @return
     */
    public boolean isShowAction(){
        return status == 1 || (!isReplace && status == 4);
    }

    public String getAgreeTitle(){
        if ("3".equals(type) && status == 1){
            return "同意调班";
        }
        return "通过";
    }

    public String getRefuseTitle(){
        if ("3".equals(type) && status == 1){
            return "拒绝调班";
        }
        return "驳回";
    }

    public String getAgreeMessage(){
        if ("3".equals(type) && status == 1){
            return "是否同意对方调班请求?";
        }
        return "通过";
    }

    public String getRefuseMessage(){
        if ("3".equals(type) && status == 1){
            return "是否拒绝对方调班请求?";
        }
        return "是否驳回审批";
    }

    public boolean isReplace() {
        return isReplace;
    }

    public void setReplace(boolean replace) {
        isReplace = replace;
    }
}
