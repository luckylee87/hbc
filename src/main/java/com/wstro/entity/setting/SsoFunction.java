package com.wstro.entity.setting;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 功能维护表
 * </p>
 *
 * @author zcb
 * @since 2017-10-18
 */
@TableName("sso_function")
public class SsoFunction implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * GUID主键
     */
    @TableId("OID")
    private String oid;
    /**
     * 功能代码（唯一）
     */
    @TableField("CODE")
    private String code;
    /**
     * 功能名称（唯一）
     */
    @TableField("NAME")
    private String name;
    /**
     * 功能类型 M-菜单 B-按钮
     */
    @TableField("TYPE")
    private String type;
    /**
     * 链接地址
     */
    @TableField("PAGE_URL")
    private String pageUrl;
    /**
     * 排序
     */
    @TableField("ORDER_INDEX")
    private BigDecimal orderIndex;
    /**
     * 功能描述
     */
    @TableField("DESCRIPTION")
    private String description;
    /**
     * 是否显示 Y-是 N-否
     */
    @TableField("IS_SHOW")
    private String isShow;
    /**
     * 是否可以对其授权 Y-是 N-否
     */
    @TableField("IS_AUTHORIZED")
    private String isAuthorized;
    /**
     * 父节点代码
     */
    @TableField("PARENT_CODE")
    private String parentCode;
    /**
     * 创建人
     */
    @TableField("CREATE_NAME")
    private String createName;
    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;
    /**
     * 操作人
     */
    @TableField("OP_USER")
    private String opUser;
    /**
     * 操作时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd  HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd  HH:mm:ss", timezone = "GMT+8")
    @TableField("OP_TIME")
    private Date opTime;

    /**
     * 图标
     */
    @TableField("ICON")
    private String urlIcon;

    //分组号
    @TableField("GROUP_NO")
    private String groupNo;

    //分组号
    @TableField("GROUP_BY")
    private String groupBy;

    //扩展字段1
    @TableField("EXTEND_COL1")
    private String extendCol1;

    //扩展字段2
    @TableField("EXTEND_COL2")
    private String extendCol2;

    //扩展字段3
    @TableField("EXTEND_COL3")
    private String extendCol3;

    //扩展字段4
    @TableField("EXTEND_COL4")
    private String extendCol4;

    //功能标识
    @TableField("flag")
    private String flag;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getUrlIcon() {
        return urlIcon;
    }

    public void setUrlIcon(String urlIcon) {
        this.urlIcon = urlIcon;
    }

    @TableField(exist = false)
    private boolean checked;

    @TableField(exist = false)
    private boolean nocheck;


    public boolean isNocheck() {
        return nocheck;
    }

    public void setNocheck(boolean nocheck) {
        this.nocheck = nocheck;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public BigDecimal getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(BigDecimal orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getIsAuthorized() {
        return isAuthorized;
    }

    public void setIsAuthorized(String isAuthorized) {
        this.isAuthorized = isAuthorized;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOpUser() {
        return opUser;
    }

    public void setOpUser(String opUser) {
        this.opUser = opUser;
    }


    public Date getOpTime() {
        return opTime;
    }

    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(String groupNo) {
        this.groupNo = groupNo;
    }

    public String getExtendCol1() {
        return extendCol1;
    }

    public void setExtendCol1(String extendCol1) {
        this.extendCol1 = extendCol1;
    }

    public String getExtendCol2() {
        return extendCol2;
    }

    public void setExtendCol2(String extendCol2) {
        this.extendCol2 = extendCol2;
    }

    public String getExtendCol3() {
        return extendCol3;
    }

    public void setExtendCol3(String extendCol3) {
        this.extendCol3 = extendCol3;
    }

    public String getExtendCol4() {
        return extendCol4;
    }

    public void setExtendCol4(String extendCol4) {
        this.extendCol4 = extendCol4;
    }

    public String getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }
}
