package com.dsg.service.setting.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.powerbridge.core.dto.PullDown;
import com.dsg.service.base.impl.BaseServiceImpl;
import com.dsg.dao.setting.ProjectMapper;
import com.dsg.entity.setting.SsoProject;
import com.dsg.service.setting.IProjectSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("projectSerivce")
public class ProjectSerivceImpl extends BaseServiceImpl<ProjectMapper,SsoProject> implements IProjectSerivce{

    @Autowired
    private ProjectMapper projectMapper;

    public Page<SsoProject> selectByList(Page<SsoProject> page, SsoProject project){
        page.setRecords(projectMapper.selectByList(page,project));
        return page;
    }

    public SsoProject selectByCode(String code){
        return projectMapper.selectByCode(code);
    }

    @Override
    public List<String> selectCodeByName(String userName) {
        return projectMapper.selectCodeByName(userName);
    }

    @Override
    public List<PullDown> selectRedisDropDown() {
        return projectMapper.selectRedisDropDown();
    }
}
