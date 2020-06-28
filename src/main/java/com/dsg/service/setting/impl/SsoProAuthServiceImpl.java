package com.dsg.service.setting.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dsg.service.base.impl.BaseServiceImpl;
import com.dsg.dao.setting.SsoProAuthMapper;
import com.dsg.entity.setting.SsoProAuth;
import com.dsg.service.setting.ISsoProAuthService;
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
