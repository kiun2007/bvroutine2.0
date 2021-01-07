package com.ruoyi.xtcz.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 诉求类型表 xtcz_appeal_type
 * 
 * @author ruoyi
 * @date 2020-08-21
 */
public class XtczAppealType extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 创建者 */
	private String id;
	/** 诉求类型名称 */
	private String appealName;
	/** 天数 */
	private Long hourNumber;
	//处理时限类型0处办类，1跟踪类
	private String type;
	//编号，对应联动平台字典编号
	private String code;

	public void setId(String id) 
	{
		this.id = id;
	}

	public String getId() 
	{
		return id;
	}
	public void setAppealName(String appealName) 
	{
		this.appealName = appealName;
	}

	public String getAppealName() 
	{
		return appealName;
	}
	public void setHourNumber(Long hourNumber)
	{
		this.hourNumber = hourNumber;
	}

	public Long getHourNumber()
	{
		return hourNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("appealName", getAppealName())
            .append("hourNumber", getHourNumber())
            .toString();
    }
}
