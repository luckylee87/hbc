package com.dsg.service.setting;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.powerbridge.core.dto.PullDown;
import com.dsg.entity.setting.SsoProject;

import java.util.List;

public interface IProjectSerivce extends IService<SsoProject> {

    Page<SsoProject> selectByList(Page<SsoProject> page, SsoProject project);

    SsoProject selectByCode(String code);

    List<String> selectCodeByName(String userName);

    List<PullDown> selectRedisDropDown();
}
