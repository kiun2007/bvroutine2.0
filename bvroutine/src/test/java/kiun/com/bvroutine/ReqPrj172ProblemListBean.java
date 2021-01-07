package kiun.com.bvroutine;

import androidx.databinding.ObservableField;

import com.alibaba.fastjson.annotation.JSONField;
import java.io.Serializable;

import kiun.com.bvroutine.data.PagerBean;

/**
 * Created by sky on 2019/4/26.
 */

public class ReqPrj172ProblemListBean extends PagerBean implements Serializable {


    String regId; //管理单位唯一标示 ,
    String reviConc; //复查状态 1未复查，2已复查，0新问题
    String ifCasePblm;//是否典型，1典型，0非典型

    Integer inspPblmCate;  //查询  问题严重等级 0 一般  1 较重  2 严重  3 特别严重

    String orderBy;     //排序
    String problemType; //问题类型  0  违规
    String startTime;  // 开始时间 yyyy-MM-dd
    String endTime;    // 结束时间 yyyy-MM-dd
    String villType;   //8工程建设，9工程运行
    String rectConc; //0未整改,1整改中,2已整改.
    String persId;   //用户ID
    String pType;

    String orderField = "COLL_TIME";

    Boolean desc = false;

    boolean isBuild = false;

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public void setReviConc(String reviConc) {
        this.reviConc = reviConc;
    }

    public boolean isNewProblem(){
        return "0".equals(reviConc);
    }

    public void setPersId(String persId) {
        this.persId = persId;
    }

    public String getInspPblmCateStr(){
        if(inspPblmCate == null || inspPblmCate > 3 || inspPblmCate < 0){
            return "";
        }
        return new String[]{"一般","较重","严重","特别严重"}[inspPblmCate];
    }

    public String getRegId() {
        return regId;
    }

    public String getReviConc() {
        return reviConc;
    }

    public String getIfCasePblm() {
        return ifCasePblm;
    }

    public void setIfCasePblm(String ifCasePblm) {
        this.ifCasePblm = ifCasePblm;
    }

    public Integer getInspPblmCate() {
        return inspPblmCate;
    }

    public void setInspPblmCate(Integer inspPblmCate) {
        this.inspPblmCate = inspPblmCate;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getProblemType() {
        return problemType;
    }

    public void setProblemType(String problemType) {
        this.problemType = problemType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getVillType() {
        return villType;
    }

    public void setVillType(String villType) {
        this.villType = villType;
    }

    public String getPersId() {
        return persId;
    }

    public ReqPrj172ProblemListBean(){
        super();
    }

    public String getDefect(){
        return isBuild ? "1" : "3";
    }

    public String getDefectTitle(){
        return isBuild ? "质量缺陷" : "工程缺陷";
    }

    public void setBuild(boolean build) {
        isBuild = build;
    }

    public boolean isBuild(){
        return isBuild;
    }

    private void initOrder(){
        orderBy = String.format("ORDER BY %s %s", orderField, desc ? "DESC" : "ASC");
    }

    public void setDesc(Boolean desc){
        this.desc = desc;
        initOrder();
    }

    public Boolean getDesc(){
        return desc;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
        initOrder();
    }

    public String getOrderField() {
        return orderField;
    }

    public String getpType() {
        return pType;
    }

    public String getTimeTitle(){
        return "2".equals(reviConc) ? "复查时间" : "上报时间";
    }

    public String getTimeOrder(){
        return "2".equals(reviConc) ? "START_TIME" : "COLL_TIME";
    }

    public void setpType(String pType) {
        this.pType = pType;
    }

    public boolean isCheck(){
        return "2".equals(reviConc);
    }

    public void clearValue(){
        startTime = null;
        endTime = null;
        orderBy = null;
        ifCasePblm = null;
        problemType = null;
        inspPblmCate = null;
    }
}
