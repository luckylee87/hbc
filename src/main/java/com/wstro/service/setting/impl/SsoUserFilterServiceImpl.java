package com.wstro.service.setting.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.wstro.common.util.UUIDGenerator;
import com.wstro.dao.setting.SsoUserFilterMapper;
import com.wstro.entity.setting.SsoUserFilter;
import com.wstro.service.base.impl.BaseServiceImpl;
import com.wstro.service.setting.ISsoUserFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("ssoUserFilterService")
public class SsoUserFilterServiceImpl extends BaseServiceImpl<SsoUserFilterMapper,SsoUserFilter> implements ISsoUserFilterService{

    @Autowired
    private SsoUserFilterMapper ssoUserFilterMapper;

    @Override
    @Transactional
    public boolean insertEntity(String userName, String userOrgFilter) {
        QueryWrapper entityWrapper = new QueryWrapper();
        entityWrapper.eq("USER_NAME",userName);
        ssoUserFilterMapper.delete(entityWrapper);
        SsoUserFilter userFilter = new SsoUserFilter();
        userFilter.setUserName(userName);
        userFilter.setOrgCodeArray(userOrgFilter);
        userFilter.setOid(UUIDGenerator.getUUID());
        return SqlHelper.retBool(ssoUserFilterMapper.insert(userFilter));
    }

}
