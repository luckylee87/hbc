package com.wstro.service.setting.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wstro.service.base.impl.BaseServiceImpl;
import com.wstro.dao.setting.SsoProAuthMapper;
import com.wstro.entity.setting.SsoProAuth;
import com.wstro.service.setting.ISsoProAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ssoProAuthService")
public class SsoProAuthServiceImpl extends BaseServiceImpl<SsoProAuthMapper,SsoProAuth> implements ISsoProAuthService {

    @Autowired
    private SsoProAuthMapper ssoProAuthMapper;

    public Page<SsoProAuth> selectByList(Page<SsoProAuth> page, SsoProAuth ssoProAuth){
        page.setRecords(ssoProAuthMapper.selectByList(page,ssoProAuth));
        return page;
    }
}
