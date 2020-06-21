package com.wstro.common.dto;

import com.powerbridge.sso.dto.ButtonDto;

import java.io.Serializable;
import java.util.List;

public class FunctionDto implements Serializable{

	//功能代码
	private String code;

	//功能名称
	private String name;

	//父节点代码
	private String preCode;

	//链接地址
	private String url;

	//图表
	private String icon;

	//分组号
	private String groupNo;

	//功能描述
	private String description;

	//扩展字段1
	private String extendCol1;

    //扩展字段2
    private String extendCol2;

    //扩展字段3
    private String extendCol3;

    //扩展字段4
    private String extendCol4;

	//子功能列表
	private List<FunctionDto> childFunc;

	//按钮列表
	private List<com.powerbridge.sso.dto.ButtonDto> button;

	private String type;

	//功能标识
	private String flag;

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPreCode() {
		return preCode;
	}

	public void setPreCode(String preCode) {
		this.preCode = preCode;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<com.powerbridge.sso.dto.ButtonDto> getButton() {
		return button;
	}

	public void setButton(List<ButtonDto> button) {
		this.button = button;
	}

	public List<FunctionDto> getChildFunc() {
		return childFunc;
	}

	public void setChildFunc(List<FunctionDto> childFunc) {
		this.childFunc = childFunc;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
