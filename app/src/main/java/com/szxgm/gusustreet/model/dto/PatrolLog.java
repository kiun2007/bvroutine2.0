package com.szxgm.gusustreet.model.dto;

import kiun.com.bvroutine.base.EventBean;
import kiun.com.bvroutine.data.verify.LengthLimit;
import kiun.com.bvroutine.data.verify.NotNull;
import kiun.com.bvroutine.interfaces.verify.Verify;
import kiun.com.bvroutine.interfaces.verify.Verifys;

/**
 * 河长巡查日志
 * 
 * RCG_RIVER_LOG
 *
 * 2020-07-11 15:17:36
 */
public class PatrolLog extends EventBean {

    /**
     * 河长日志表主键
     */
    private String id;

    /**
     * 删除标记（0未删除，1已删除）
     */
    private String delFalg;

    /**
     * 日志标题
     */
    private String title;

    /**
     * 河道id
     */
    private String riverId;

    /**
     * 巡查记录id
     */
    private String patrolId;

    /**
     * 日志图片
     */
    @Verifys(@Verify(value = NotNull.class, desc = "请选择图片"))
    private String logImg;

    /**
     * 巡查日志
     */
    private String content;

    /**
     * 展示日志
     */
    @Verifys({
            @Verify(value = LengthLimit.class, extra = "[10,100]", desc = "日志长度应该为10-500个字符")
    })
    private String contentShow;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDelFalg() {
        return delFalg;
    }

    public void setDelFalg(String delFalg) {
        this.delFalg = delFalg == null ? null : delFalg.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getRiverId() {
        return riverId;
    }

    public void setRiverId(String riverId) {
        this.riverId = riverId == null ? null : riverId.trim();
    }

    public String getPatrolId() {
        return patrolId;
    }

    public void setPatrolId(String patrolId) {
        this.patrolId = patrolId == null ? null : patrolId.trim();
    }

    public String getLogImg() {
        return logImg;
    }

    public void setLogImg(String logImg) {
        this.logImg = logImg == null ? null : logImg.trim();
        onChanged();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getContentShow() {
        return contentShow;
    }

    public void setContentShow(String contentShow) {
        this.contentShow = contentShow == null ? null : contentShow.trim();
    }
}