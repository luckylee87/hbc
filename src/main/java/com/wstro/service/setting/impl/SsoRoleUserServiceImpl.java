package com.wstro.service.setting.impl;

import com.wstro.service.base.impl.BaseServiceImpl;
import com.wstro.dao.setting.SsoRoleUserMapper;
import com.wstro.entity.setting.SsoRoleUser;
import com.wstro.service.setting.ISsoRoleUserService;
import org.springframework.stereotype.Service;

@Service("ssoRoleUserService")
public class SsoRoleUserServiceImpl extends BaseServiceImpl<SsoRoleUserMapper,SsoRoleUser> implements ISsoRoleUserService{

}
