package com.dsg.service.setting.impl;

import com.dsg.service.base.impl.BaseServiceImpl;
import com.dsg.dao.setting.SsoRoleUserMapper;
import com.dsg.entity.setting.SsoRoleUser;
import com.dsg.service.setting.ISsoRoleUserService;
import org.springframework.stereotype.Service;

@Service("ssoRoleUserService")
public class SsoRoleUserServiceImpl extends BaseServiceImpl<SsoRoleUserMapper,SsoRoleUser> implements ISsoRoleUserService{

}
