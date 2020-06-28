package com.dsg.common.dto;

import com.powerbridge.sso.dto.FunctionDto;

import java.io.Serializable;
import java.util.List;

public class ProjectDto implements Serializable{
	
	private String projectCode;
	
	private String projectName;
	
	private List<com.powerbridge.sso.dto.FunctionDto> data;

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public List<com.powerbridge.sso.dto.FunctionDto> getData() {
		return data;
	}

	public void setData(List<FunctionDto> data) {
		this.data = data;
	}

	
}
