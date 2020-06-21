package com.wstro.service.ws.impl;

import com.powerbridge.sso.dto.FunctionDto;
import com.powerbridge.sso.dto.ProjectDto;
import com.wstro.common.dto.GetMenuInputDto;
import com.wstro.dao.ws.APIMapper;
import com.wstro.service.ws.IAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("apiService")
public class IAPIServiceImpl implements IAPIService{

    @Autowired
    private APIMapper apiMapper;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
//    @Cacheable(value="api-menu", key = "'menu_list_userName_'+#inputDto.projectCode")
    public List<ProjectDto> selectList(GetMenuInputDto inputDto) {
        String projectCode = inputDto.getProjectCode();
        String userName = inputDto.getUserName();
        List<ProjectDto> projectDtoList = new ArrayList<>();
        if (projectCode.indexOf("|") == -1) {    //单个projectCode
            ProjectDto projectDto = apiMapper.selectProjectByCode(projectCode);
            if(null == projectDto)
                return null;
            List<FunctionDto> funcList = apiMapper.selectFuncList(userName, projectCode);
            projectDto.setData(reorganize(funcList));
            projectDtoList.add(projectDto);
        } else {                                               //多个projectCode，拆分后分别查询
            String[] projectCodes = projectCode.split("\\|");
            for (int j = 0, len = projectCodes.length; j < len; j++) {
                String tmpProjectCode = projectCodes[j];
                ProjectDto projectDto = apiMapper.selectProjectByCode(tmpProjectCode);
                if (null == projectDto)
                    continue;
                List<FunctionDto> funcList = apiMapper.selectFuncList(userName, tmpProjectCode);
                projectDto.setData(reorganize(funcList));
                projectDtoList.add(projectDto);
            }
        }
        return projectDtoList;
    }

    /**
     *
     * @param inputDto
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<ProjectDto> getProjectCode(GetMenuInputDto inputDto) {
        String projectCode = inputDto.getProjectCode();
        String userName = inputDto.getUserName();
        List<ProjectDto> projectDtoList = new ArrayList<>();
        //单个projectCode
        if (projectCode.indexOf("|") == -1) {
            ProjectDto projectDto = apiMapper.selectProjectByCode(projectCode);
            if(null == projectDto){
                return projectDtoList;
            }
            List<FunctionDto> funcList = apiMapper.selectFuncList(userName, projectCode);
            if (!funcList.isEmpty()){
                projectDtoList.add(projectDto);
            }
        } else {                                               //多个projectCode，拆分后分别查询
            String[] projectCodes = projectCode.split("\\|");
            for (int j = 0, len = projectCodes.length; j < len; j++) {
                String tmpProjectCode = projectCodes[j];
                ProjectDto projectDto = apiMapper.selectProjectByCode(tmpProjectCode);
                if (null == projectDto){
                    continue;
                }
                List<FunctionDto> funcList = apiMapper.selectFuncList(userName, tmpProjectCode);
                if (!funcList.isEmpty()){
                    projectDtoList.add(projectDto);
                }
            }
        }
        return projectDtoList;
    }

    /**
     * 重组功能列表
     * @param funcList
     * @return
     */
    private List<FunctionDto> reorganize(List<FunctionDto> funcList) {
        List<FunctionDto> topList = new ArrayList();
        if(null != funcList) {
            Map<String, FunctionDto> map = new HashMap();
            //所用function按照code放入map，方便下面排列操作查找数据
            for (FunctionDto dto : funcList) {
                map.put(dto.getCode(), dto);
            }
            //循环遍历funcList排列节点，并把子节点加到对应的父节点
            for (FunctionDto dto : funcList) {
                FunctionDto parentFunc = map.get(dto.getPreCode());
                if (null != parentFunc) {    //找到父节点
                    if (null == parentFunc.getChildFunc()) {
                        List<FunctionDto> childFunc = new ArrayList<>();
                        childFunc.add(dto);
                        parentFunc.setChildFunc(childFunc);
                    } else {
                        parentFunc.getChildFunc().add(dto);
                    }
                } else  //未找到父节点，放入topList
                    topList.add(dto);
            }
        }
        return topList;
    }
}
