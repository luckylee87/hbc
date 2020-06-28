package com.dsg.service.setting;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dsg.entity.setting.SsoProAuth;

public interface ISsoProAuthService extends IService<SsoProAuth> {

    public Page<SsoProAuth> selectByList(Page<SsoProAuth> page, SsoProAuth ssoProAuth);

}
