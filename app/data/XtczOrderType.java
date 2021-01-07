package com.ruoyi.xtcz.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

/**
 * 事件类型表 xtcz_order_type
 * 
 * @author ruoyi
 * @date 2020-04-10
 */
public class XtczOrderType extends BaseEntity implements Comparable<XtczOrderType>
{
	private static final long serialVersionUID = 1L;
	
	/** 事件类型ID */
	private String typeId;
	/** 事件类型名称 */
	@NotBlank(message = "事件类型名称不能为空")
	@Excel(name = "事件类型名称")
	private String typeName;
	/** 父级ID */
	private String parentId;
	/** 父级集合 */
	private String ancestors;
	/** 排序 */
	private Long orderNum;
	/** 创建时间 */
	private Date createDate;
	/** 删除标记（0未删除，2已删除） */
	private String delFlag;

    /** 对应事项 */
    private String matterId;
    /** 对应事项名 */
    private String matterName;
    //类型编号
	@NotBlank(message = "事件类型编号不能为空")
	@Excel(name = "事件类型编号")
    private String typeCode;

	/** 父级对象 */
	private String parentName;

	/** 父级对象 */
	@Excel(name = "父级事件类型编号")
	private String parentCode;

	//是否是高效处置事项
	private String isPriority;

	private List<XtczOrderType> childs;

	public void setTypeId(String typeId) 
	{
		this.typeId = typeId;
	}

	public String getTypeId() 
	{
		return typeId;
	}
	public void setTypeName(String typeName) 
	{
		this.typeName = typeName;
	}

	public String getTypeName() 
	{
		return typeName;
	}
	public void setParentId(String parentId) 
	{
		this.parentId = parentId;
	}

	public String getParentId() 
	{
		return parentId;
	}
	public void setAncestors(String ancestors) 
	{
		this.ancestors = ancestors;
	}

	public String getAncestors() 
	{
		return ancestors;
	}
	public void setOrderNum(Long orderNum)
	{
		this.orderNum = orderNum;
	}

	public Long getOrderNum()
	{
		return orderNum;
	}
	public void setCreateDate(Date createDate) 
	{
		this.createDate = createDate;
	}

	public Date getCreateDate() 
	{
		return createDate;
	}
	public void setDelFlag(String delFlag) 
	{
		this.delFlag = delFlag;
	}

	public String getDelFlag() 
	{
		return delFlag;
	}

    public String getMatterId() {
        return matterId;
    }

    public void setMatterId(String matterId) {
        this.matterId = matterId;
    }

    public String getMatterName() {
        return matterName;
    }

    public void setMatterName(String matterName) {
        this.matterName = matterName;
    }

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getIsPriority() {
		return isPriority;
	}

	public void setIsPriority(String isPriority) {
		this.isPriority = isPriority;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public List<XtczOrderType> getChilds() {
		return childs;
	}

	public void setChilds(List<XtczOrderType> childs) {
		this.childs = childs;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("typeId", getTypeId())
            .append("typeName", getTypeName())
            .append("parentId", getParentId())
            .append("ancestors", getAncestors())
            .append("orderNum", getOrderNum())
            .append("createBy", getCreateBy())
            .append("createDate", getCreateDate())
            .append("delFlag", getDelFlag())
            .toString();
    }

	@Override
	public int compareTo(XtczOrderType o) {
		return (this.orderNum - o.getOrderNum()) > 0 ? 1 : -1;
	}
}
